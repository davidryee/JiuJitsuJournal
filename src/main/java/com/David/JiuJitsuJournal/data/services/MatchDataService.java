package com.David.JiuJitsuJournal.data.services;

import com.David.JiuJitsuJournal.data.entities.Opponent;
import com.David.JiuJitsuJournal.data.entities.User;
import com.David.JiuJitsuJournal.data.mappers.MatchMapper;
import com.David.JiuJitsuJournal.data.repository.MatchRepository;
import com.David.JiuJitsuJournal.data.repository.OpponentRepository;
import com.David.JiuJitsuJournal.data.repository.UserRepository;
import com.David.JiuJitsuJournal.data.specification.MatchSpecification;
import com.David.JiuJitsuJournal.data.specification.OpponentSpecification;
import com.David.JiuJitsuJournal.domain.models.Match;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class MatchDataService implements com.David.JiuJitsuJournal.domain.dataServices.MatchDataService {
    UserRepository userRepository;
    OpponentRepository opponentRepository;
    MatchRepository matchRepository;

    public MatchDataService(UserRepository userRepository, OpponentRepository opponentRepository,
                            MatchRepository matchRepository){
        this.userRepository = userRepository;
        this.opponentRepository = opponentRepository;
        this.matchRepository = matchRepository;
    }
    @Override
    public Match createMatch(LocalDate matchDate, Long opponentId, String description, String username) throws Exception {
        User userEntity = userRepository.findByUsername(username).get();
        Specification<Opponent> opponentSpec = Specification.where(
                OpponentSpecification.withId(opponentId))
                .and(OpponentSpecification.withUser(userEntity));
        Optional<Opponent> opponentEntity = this.opponentRepository.findOne(opponentSpec);

        if(opponentEntity.isEmpty()) {
            throw new EntityNotFoundException(String.format("Opponent with id %d not found.", opponentId));
        }

        com.David.JiuJitsuJournal.data.entities.Match matchToSave = new com.David.JiuJitsuJournal.data.entities.Match
                                                                    (matchDate, userEntity, opponentEntity.get(),
                                                                            description);
        com.David.JiuJitsuJournal.data.entities.Match savedMatch = this.matchRepository.save(matchToSave);
        if(savedMatch != null) {
            return MatchMapper.mapEntityToDomain(savedMatch);
        }
        throw new Exception("Match not saved to database");
    }

    @Override
    public List<Match> getMatches(String opponentName, Integer beltRank, LocalDate matchDate, String username) {
        User user = this.userRepository.findByUsername(username).get();
        List<Opponent> opponents;
        Specification<com.David.JiuJitsuJournal.data.entities.Match> matchWithNoOpponentSpec = Specification.where(
                MatchSpecification.withDate(matchDate)).and(MatchSpecification.withUser(user));
        List<com.David.JiuJitsuJournal.data.entities.Match> matchEntities;
        //separate case because if a user is searching for an opponent and it comes back null, we dont want to search
        //a match that has a null opponent
        if(opponentName != null || beltRank != null) {
            Specification<Opponent> opponentSpec = Specification.where(OpponentSpecification.withName(opponentName))
                    .and(OpponentSpecification.withBeltRank(beltRank))
                    .and(OpponentSpecification.withUser(user));
            opponents = this.opponentRepository.findAll(opponentSpec);
            if(opponents.isEmpty()){
                return Collections.emptyList();
            }
            else {
                Specification<com.David.JiuJitsuJournal.data.entities.Match> matchWithOpponentSpec = matchWithNoOpponentSpec
                        .and(MatchSpecification.withOpponents(opponents));
                matchEntities = this.matchRepository.findAll(matchWithOpponentSpec);
            }
        }
        else {
            matchEntities = this.matchRepository.findAll(matchWithNoOpponentSpec);
        }

        List<Match> matches = new LinkedList<>();
        for (com.David.JiuJitsuJournal.data.entities.Match matchEntity : matchEntities) {
            matches.add(MatchMapper.mapEntityToDomain(matchEntity));
        }

        return matches;
    }


}
