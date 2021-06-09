package com.David.JiuJitsuJournal.api.responses;

import java.time.LocalDate;

public class Match {
    private Long id;
    private LocalDate matchDate;
    private Opponent opponent;
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(LocalDate matchDate) {
        this.matchDate = matchDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Opponent getOpponent() { return opponent; }

    public void setOpponent(Opponent opponent) { this.opponent = opponent; }
}
