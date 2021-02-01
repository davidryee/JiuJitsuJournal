package com.David.JiuJitsuJournal.Domain;

import com.David.JiuJitsuJournal.Domain.Models.Opponent;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OpponentManager {
    private OpponentDataService opponentDataService;

    public OpponentManager(OpponentDataService opponentDataService){
        this.opponentDataService = opponentDataService;
    }

    public List<Opponent> getOpponents(String name, Integer beltRank){
        return this.opponentDataService.getAllOpponents(name, beltRank);
    }

    public Opponent createOpponent(String name, int beltRank, int heightInches, int weightInLbs) throws Exception {
        return this.opponentDataService.createOpponent(name, BeltRankEnum.values()[beltRank], heightInches, weightInLbs);
    }
}
