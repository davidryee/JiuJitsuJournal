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

    public Opponent getOpponentById(Long id, String username) {
        return this.opponentDataService.getOpponentById(id, username);
    }

    public List<Opponent> getOpponents(String name, Integer beltRank, String username){
        return this.opponentDataService.getAllOpponents(name, beltRank, username);
    }

    public Opponent createOpponent(String name, int beltRank, int heightInches, int weightInLbs, String username) throws Exception {
        return this.opponentDataService.createOpponent(name, BeltRankEnum.values()[beltRank], heightInches, weightInLbs, username);
    }

    public Opponent updateOpponent(Long id, String name, int beltRank, int heightInches, int weightInLbs, String username) throws Exception {
        return this.opponentDataService.updateOpponent(id, name, BeltRankEnum.values()[beltRank], heightInches, weightInLbs, username);
    }

    public void deleteOpponent(Long id, String username) {
        this.opponentDataService.deleteOpponent(id, username);
    }
}
