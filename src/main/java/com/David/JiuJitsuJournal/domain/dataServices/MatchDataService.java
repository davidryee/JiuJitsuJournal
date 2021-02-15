package com.David.JiuJitsuJournal.domain.dataServices;

import com.David.JiuJitsuJournal.domain.models.Match;

import java.time.LocalDate;
import java.util.List;

public interface MatchDataService {
    Match createMatch(LocalDate matchDate, Long opponentId, String description, String username) throws Exception;
    List<Match> getMatches(String opponentName, Integer beltRank, LocalDate matchDate, String username);
    Match getMatchById(Long id, String username);
    Match updateMatch(Long id, LocalDate matchDate, Long opponentId, String description, String username) throws Exception;
}
