package com.David.JiuJitsuJournal.domain.dataServices;

import com.David.JiuJitsuJournal.domain.models.Match;
import com.David.JiuJitsuJournal.domain.models.Opponent;

import java.time.LocalDate;

public interface MatchDataService {
    Match createMatch(LocalDate matchDate, Long opponentId, String description, String username) throws Exception;
}
