package com.David.JiuJitsuJournal.domain;

import com.David.JiuJitsuJournal.domain.models.Opponent;

import java.util.List;

public interface OpponentDataService {
    List<Opponent> getAllOpponents(String name, Integer beltRank, String username);
    Opponent getOpponentById(Long id);
    Opponent createOpponent(String name, BeltRankEnum beltRank, int heightInInches, int weightInLbs, String username) throws Exception;
    Opponent updateOpponent(Long id, String name, BeltRankEnum beltRank, int heightInInches, int weightInLbs) throws Exception;
}
