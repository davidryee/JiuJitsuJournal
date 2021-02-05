package com.David.JiuJitsuJournal.data.mappers;

import com.David.JiuJitsuJournal.domain.BeltRankEnum;
import com.David.JiuJitsuJournal.domain.models.Opponent;

public class OpponentMapper {
    public static Opponent mapEntityToDomain(com.David.JiuJitsuJournal.data.entities.Opponent opponentEntity){
        return new Opponent(
                opponentEntity.getName(),
                BeltRankEnum.values()[opponentEntity.getBeltRank().getBeltRankId()],
                opponentEntity.getHeightInInches(),
                opponentEntity.getWeightInLbs());
    }
}
