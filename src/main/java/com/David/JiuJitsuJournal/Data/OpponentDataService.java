package com.David.JiuJitsuJournal.Data;

import com.David.JiuJitsuJournal.Data.Mappers.OpponentMapper;
import com.David.JiuJitsuJournal.Data.Repository.OpponentRepository;
import com.David.JiuJitsuJournal.Domain.Models.Opponent;
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
    public List<Opponent> GetAllOpponents() {
        List<Opponent> opponents = new LinkedList();
        List<com.David.JiuJitsuJournal.Data.Entities.Opponent> opponentEntities = this.opponentRepository.findAll();
        for (com.David.JiuJitsuJournal.Data.Entities.Opponent opponentEntity : opponentEntities) {
            opponents.add(OpponentMapper.MapEntityToDomain(opponentEntity));
        }
        return opponents;
    }
}
