package com.David.JiuJitsuJournal.domain.models;

import java.time.LocalDate;

public class Match {
    private Long id;
    private LocalDate matchDate;
    private Opponent opponent;
    private String description;

    public Match(Long id, LocalDate matchDate, Opponent opponent, String description) {
        this.id = id;
        this.matchDate = matchDate;
        this.opponent = opponent;
        this.description = description;
    }

    public LocalDate getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(LocalDate matchDate) {
        this.matchDate = matchDate;
    }

    public Opponent getOpponent() {
        return opponent;
    }

    public void setOpponent(Opponent opponent) {
        this.opponent = opponent;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
