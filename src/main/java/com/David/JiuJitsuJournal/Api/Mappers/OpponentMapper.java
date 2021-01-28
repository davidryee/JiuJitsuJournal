package com.David.JiuJitsuJournal.Api.Mappers;

import com.David.JiuJitsuJournal.Api.Responses.Opponent;

public class OpponentMapper {
    public static Opponent mapToDto(com.David.JiuJitsuJournal.Domain.Models.Opponent opponentModel){
        Opponent opponentDto = new Opponent();
        opponentDto.setName(opponentModel.getName());
        opponentDto.setBeltRank(opponentModel.getBeltRank());
        opponentDto.setHeightInInches(opponentModel.getHeightInInches());
        opponentDto.setWeightInLbs(opponentModel.getWeightInLbs());
        return opponentDto;
    }
}
