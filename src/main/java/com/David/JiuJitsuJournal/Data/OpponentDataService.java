package com.David.JiuJitsuJournal.Data;

import com.David.JiuJitsuJournal.Data.Entities.BeltRank;
import com.David.JiuJitsuJournal.Data.Mappers.OpponentMapper;
import com.David.JiuJitsuJournal.Data.Repository.OpponentRepository;
import com.David.JiuJitsuJournal.Data.Specification.OpponentSpecification;
import com.David.JiuJitsuJournal.Domain.BeltRankEnum;
import com.David.JiuJitsuJournal.Domain.Models.Opponent;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class OpponentDataService implements com.David.JiuJitsuJournal.Domain.OpponentDataService {

    OpponentRepository opponentRepository;
    public OpponentDataService(OpponentRepository opponentRepository){
        this.opponentRepository = opponentRepository;
    }
    @Override
    public List<Opponent> GetAllOpponents(String name, Integer beltRank) {
        Specification<com.David.JiuJitsuJournal.Data.Entities.Opponent> spec = Specification.where(
                                                                                OpponentSpecification.withName(name))
                                                                                .and(OpponentSpecification.
                                                                                        withBeltRank(beltRank));
        List<Opponent> opponents = new LinkedList();
        List<com.David.JiuJitsuJournal.Data.Entities.Opponent> opponentEntities = this.opponentRepository.findAll(spec);

        for (com.David.JiuJitsuJournal.Data.Entities.Opponent opponentEntity : opponentEntities) {
            opponents.add(OpponentMapper.mapEntityToDomain(opponentEntity));
        }
        return opponents;
    }

    @Override
    public Opponent createOpponent(String name, BeltRankEnum beltRank, int heightInInches, int weightInLbs)
            throws Exception {
        BeltRank beltRankToPersist = new BeltRank(beltRank.ordinal(), beltRank.name());
        com.David.JiuJitsuJournal.Data.Entities.Opponent opponentToSave = new com.David.JiuJitsuJournal.Data.Entities.Opponent(name, beltRankToPersist, heightInInches, weightInLbs);
        com.David.JiuJitsuJournal.Data.Entities.Opponent savedOpponent = this.opponentRepository.save(opponentToSave);
        if(savedOpponent != null){
            return OpponentMapper.mapEntityToDomain(savedOpponent);
        }
        throw new Exception("Opponent not saved to database!");
    }
}
