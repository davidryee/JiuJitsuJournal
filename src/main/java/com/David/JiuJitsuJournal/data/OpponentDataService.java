package com.David.JiuJitsuJournal.data;

import com.David.JiuJitsuJournal.data.entities.BeltRank;
import com.David.JiuJitsuJournal.data.entities.User;
import com.David.JiuJitsuJournal.data.mappers.OpponentMapper;
import com.David.JiuJitsuJournal.data.repository.OpponentRepository;
import com.David.JiuJitsuJournal.data.repository.UserRepository;
import com.David.JiuJitsuJournal.data.specification.OpponentSpecification;
import com.David.JiuJitsuJournal.domain.BeltRankEnum;
import com.David.JiuJitsuJournal.domain.models.Opponent;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class OpponentDataService implements com.David.JiuJitsuJournal.domain.OpponentDataService {

    OpponentRepository opponentRepository;
    UserRepository userRepository;
    public OpponentDataService(OpponentRepository opponentRepository, UserRepository userRepository){
        this.opponentRepository = opponentRepository;
        this.userRepository = userRepository;
    }
    @Override
    public List<Opponent> getAllOpponents(String name, Integer beltRank, String username) {
        User user = this.userRepository.findByUsername(username).get();
        Specification<com.David.JiuJitsuJournal.data.entities.Opponent> spec = Specification.where(
                                                                                OpponentSpecification.withName(name))
                                                                                .and(OpponentSpecification.
                                                                                        withBeltRank(beltRank))
                                                                                .and(OpponentSpecification.withUser(user));
        List<Opponent> opponents = new LinkedList();
        List<com.David.JiuJitsuJournal.data.entities.Opponent> opponentEntities = this.opponentRepository.findAll(spec);

        for (com.David.JiuJitsuJournal.data.entities.Opponent opponentEntity : opponentEntities) {
            opponents.add(OpponentMapper.mapEntityToDomain(opponentEntity));
        }
        return opponents;
    }

    @Override
    public Opponent getOpponentById(Long id, String username) {
        User user = this.userRepository.findByUsername(username).get();
        Specification<com.David.JiuJitsuJournal.data.entities.Opponent> spec = Specification.where(
                                                                                OpponentSpecification.withId(id))
                                                                                .and(OpponentSpecification.withUser(user));
        Optional<com.David.JiuJitsuJournal.data.entities.Opponent> opponentEntity = this.opponentRepository.findOne(spec);

        if(opponentEntity.isEmpty()){
            return null;
        }
        else {
            return OpponentMapper.mapEntityToDomain(opponentEntity.get());
        }
    }

    @Override
    public Opponent createOpponent(String name, BeltRankEnum beltRank, int heightInInches, int weightInLbs, String username)
            throws Exception {
        BeltRank beltRankToPersist = new BeltRank(beltRank.ordinal(), beltRank.name());
        User userEntity = userRepository.findByUsername(username).get();
        com.David.JiuJitsuJournal.data.entities.Opponent opponentToSave = new com.David.JiuJitsuJournal.data.entities.Opponent(name, beltRankToPersist, heightInInches, weightInLbs);
        opponentToSave.setUser(userEntity);
        com.David.JiuJitsuJournal.data.entities.Opponent savedOpponent = this.opponentRepository.save(opponentToSave);
        if(savedOpponent != null){
            return OpponentMapper.mapEntityToDomain(savedOpponent);
        }
        throw new Exception("Opponent not saved to database!");
    }

    @Override
    public Opponent updateOpponent(Long id, String name, BeltRankEnum beltRank, int heightInInches, int weightInLbs, String username)
            throws Exception {
        User user = this.userRepository.findByUsername(username).get();
        Specification<com.David.JiuJitsuJournal.data.entities.Opponent> spec = Specification.where(
                OpponentSpecification.withId(id))
                .and(OpponentSpecification.withUser(user));
        Optional<com.David.JiuJitsuJournal.data.entities.Opponent> opponentToUpdate = this.opponentRepository.findOne(spec);

        if(opponentToUpdate.isEmpty()){
            return null;
        }

        BeltRank beltRankToPersist = new BeltRank(beltRank.ordinal(), beltRank.name());

        com.David.JiuJitsuJournal.data.entities.Opponent retrievedOpponent = opponentToUpdate.get();
        retrievedOpponent.setName(name);
        retrievedOpponent.setBeltRank(beltRankToPersist);
        retrievedOpponent.setHeightInInches(heightInInches);
        retrievedOpponent.setWeightInLbs(weightInLbs);
        com.David.JiuJitsuJournal.data.entities.Opponent savedOpponent = opponentRepository.save(retrievedOpponent);
        if(savedOpponent != null){
            return OpponentMapper.mapEntityToDomain(savedOpponent);
        }
        throw new Exception("Opponent not saved to database!");
    }
}
