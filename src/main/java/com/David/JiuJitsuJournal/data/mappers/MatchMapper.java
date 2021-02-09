package com.David.JiuJitsuJournal.data.mappers;

import com.David.JiuJitsuJournal.data.entities.Match;

public class MatchMapper {
    public static com.David.JiuJitsuJournal.domain.models.Match mapEntityToDomain(Match matchEntity) {
        return new com.David.JiuJitsuJournal.domain.models.Match(
                matchEntity.getId(),
                matchEntity.getMatchDate(),
                OpponentMapper.mapEntityToDomain(matchEntity.getOpponent()),
                matchEntity.getDescription()
        );
    }
}
