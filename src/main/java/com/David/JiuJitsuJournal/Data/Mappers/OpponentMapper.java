package com.David.JiuJitsuJournal.Data.Mappers;

import com.David.JiuJitsuJournal.Domain.BeltRankEnum;
import com.David.JiuJitsuJournal.Domain.Models.Opponent;

public class OpponentMapper {
    public static Opponent MapEntityToDomain(com.David.JiuJitsuJournal.Data.Entities.Opponent opponentEntity){
        return new Opponent(
                opponentEntity.getName(),
                BeltRankEnum.values()[opponentEntity.getBeltRank().getBeltRankId()],
                opponentEntity.getHeightInInches(),
                opponentEntity.getWeightInLbs());
    }
}
