package com.David.JiuJitsuJournal.domain;

import com.David.JiuJitsuJournal.domain.models.Opponent;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OpponentManager {
    private OpponentDataService opponentDataService;

    public OpponentManager(OpponentDataService opponentDataService){
        this.opponentDataService = opponentDataService;
    }

    public Opponent getOpponentById(Long id) {
        return this.opponentDataService.getOpponentById(id);
    }

    public List<Opponent> getOpponents(String name, Integer beltRank){
        return this.opponentDataService.getAllOpponents(name, beltRank);
    }

    public Opponent createOpponent(String name, int beltRank, int heightInches, int weightInLbs) throws Exception {
        return this.opponentDataService.createOpponent(name, BeltRankEnum.values()[beltRank], heightInches, weightInLbs);
    }

    public Opponent updateOpponent(Long id, String name, int beltRank, int heightInches, int weightInLbs) throws Exception {
        return this.opponentDataService.updateOpponent(id, name, BeltRankEnum.values()[beltRank], heightInches, weightInLbs);
    }
}