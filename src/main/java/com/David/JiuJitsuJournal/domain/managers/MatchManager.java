package com.David.JiuJitsuJournal.domain.managers;

import com.David.JiuJitsuJournal.domain.dataServices.MatchDataService;
import com.David.JiuJitsuJournal.domain.models.Match;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class MatchManager {
    private MatchDataService matchDataService;
    public MatchManager(MatchDataService matchDataService) {
        this.matchDataService = matchDataService;
    }

    public Match createMatch(LocalDate matchDate, Long opponentId, String description, String username) throws Exception {
        return this.matchDataService.createMatch(matchDate, opponentId, description, username);
    }

    public List<Match> getMatches(String opponentName, Integer opponentBeltRank, LocalDate matchDate, String username) {
        return this.matchDataService.getMatches(opponentName, opponentBeltRank, matchDate, username);
    }

    public Match getMatchById(Long id, String username) {
        return this.matchDataService.getMatchById(id, username);
    }

    public Match updateMatch(Long id, LocalDate matchDate, Long opponentId, String description, String username) throws Exception {
        return this.matchDataService.updateMatch(id, matchDate, opponentId, description, username);
    }

    public void deleteMatch(Long id, String username){
        this.matchDataService.deleteMatch(id, username);
    }
}
