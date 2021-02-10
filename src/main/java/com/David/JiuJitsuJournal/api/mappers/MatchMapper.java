package com.David.JiuJitsuJournal.api.mappers;

import com.David.JiuJitsuJournal.domain.models.Match;

public class MatchMapper {
    public static com.David.JiuJitsuJournal.api.responses.Match mapToDto(Match matchModel){
        com.David.JiuJitsuJournal.api.responses.Match matchDto = new com.David.JiuJitsuJournal.api.responses.Match();
        matchDto.setMatchDate(matchModel.getMatchDate());
        matchDto.setId(matchModel.getId());
        matchDto.setOpponentId(matchModel.getOpponent().getId());
        matchDto.setDescription(matchModel.getDescription());

        return matchDto;
    }
}
