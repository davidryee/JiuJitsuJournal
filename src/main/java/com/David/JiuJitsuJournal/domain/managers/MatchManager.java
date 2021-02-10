package com.David.JiuJitsuJournal.domain.managers;

import com.David.JiuJitsuJournal.domain.dataServices.MatchDataService;
import com.David.JiuJitsuJournal.domain.models.Match;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class MatchManager {
    private MatchDataService matchDataService;
    public MatchManager(MatchDataService matchDataService) {
        this.matchDataService = matchDataService;
    }

    public Match createMatch(LocalDate matchDate, Long opponentId, String description, String username) throws Exception {
        return this.matchDataService.createMatch(matchDate, opponentId, description, username);
    }
}
