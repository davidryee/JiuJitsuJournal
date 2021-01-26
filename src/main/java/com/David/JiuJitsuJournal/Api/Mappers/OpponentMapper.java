package com.David.JiuJitsuJournal.Api.Mappers;

import com.David.JiuJitsuJournal.Api.Responses.Opponent;

public class OpponentMapper {
    public static Opponent MapToDto(com.David.JiuJitsuJournal.Domain.Models.Opponent opponentModel){
        Opponent opponentDto = new Opponent();
        opponentDto.setName(opponentModel.getName());
        opponentDto.setBeltRank(opponentModel.getBeltRank());
        return opponentDto;
    }
}
