package com.David.JiuJitsuJournal.api.mappers;

import com.David.JiuJitsuJournal.api.responses.Opponent;

public class OpponentMapper {
    public static Opponent mapToDto(com.David.JiuJitsuJournal.domain.models.Opponent opponentModel){
        Opponent opponentDto = new Opponent();
        opponentDto.setName(opponentModel.getName());
        opponentDto.setBeltRank(opponentModel.getBeltRank());
        opponentDto.setHeightInInches(opponentModel.getHeightInInches());
        opponentDto.setWeightInLbs(opponentModel.getWeightInLbs());
        opponentDto.setId(opponentModel.getId());
        return opponentDto;
    }
}
