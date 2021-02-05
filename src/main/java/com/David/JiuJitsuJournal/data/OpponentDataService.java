package com.David.JiuJitsuJournal.data;

import com.David.JiuJitsuJournal.data.entities.BeltRank;
import com.David.JiuJitsuJournal.data.mappers.OpponentMapper;
import com.David.JiuJitsuJournal.data.repository.OpponentRepository;
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
    public OpponentDataService(OpponentRepository opponentRepository){
        this.opponentRepository = opponentRepository;
    }
    @Override
    public List<Opponent> getAllOpponents(String name, Integer beltRank) {
        Specification<com.David.JiuJitsuJournal.data.entities.Opponent> spec = Specification.where(
                                                                                OpponentSpecification.withName(name))
                                                                                .and(OpponentSpecification.
                                                                                        withBeltRank(beltRank));
        List<Opponent> opponents = new LinkedList();
        List<com.David.JiuJitsuJournal.data.entities.Opponent> opponentEntities = this.opponentRepository.findAll(spec);

        for (com.David.JiuJitsuJournal.data.entities.Opponent opponentEntity : opponentEntities) {
            opponents.add(OpponentMapper.mapEntityToDomain(opponentEntity));
        }
        return opponents;
    }

    @Override
    public Opponent getOpponentById(Long id) {
        Optional<com.David.JiuJitsuJournal.data.entities.Opponent> opponentEntity =  this.opponentRepository.findById(id);
        if(opponentEntity.isEmpty()){
            return null;
        }
        else {
            return OpponentMapper.mapEntityToDomain(opponentEntity.get());
        }
    }

    @Override
    public Opponent createOpponent(String name, BeltRankEnum beltRank, int heightInInches, int weightInLbs)
            throws Exception {
        BeltRank beltRankToPersist = new BeltRank(beltRank.ordinal(), beltRank.name());
        com.David.JiuJitsuJournal.data.entities.Opponent opponentToSave = new com.David.JiuJitsuJournal.data.entities.Opponent(name, beltRankToPersist, heightInInches, weightInLbs);
        com.David.JiuJitsuJournal.data.entities.Opponent savedOpponent = this.opponentRepository.save(opponentToSave);
        if(savedOpponent != null){
            return OpponentMapper.mapEntityToDomain(savedOpponent);
        }
        throw new Exception("Opponent not saved to database!");
    }

    @Override
    public Opponent updateOpponent(Long id, String name, BeltRankEnum beltRank, int heightInInches, int weightInLbs)
            throws Exception {
        Optional<com.David.JiuJitsuJournal.data.entities.Opponent> opponentToUpdate = this.opponentRepository.findById(id);
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
