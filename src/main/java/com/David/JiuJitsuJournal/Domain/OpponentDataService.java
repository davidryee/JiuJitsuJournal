package com.David.JiuJitsuJournal.Domain;

import com.David.JiuJitsuJournal.Domain.Models.Opponent;

import java.util.List;

public interface OpponentDataService {
    List<Opponent> GetAllOpponents(String name, Integer beltRank);
    Opponent createOpponent(String name, BeltRankEnum beltRank, int heightInInches, int weightInLbs) throws Exception;
}
