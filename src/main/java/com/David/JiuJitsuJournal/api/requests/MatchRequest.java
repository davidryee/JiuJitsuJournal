package com.David.JiuJitsuJournal.api.requests;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

public class MatchRequest {

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @PastOrPresent
    private LocalDate matchDate;

    @NotNull
    @Min(1)
    private Long opponentId;

    @NotNull
    @NotBlank
    private String description;

    public MatchRequest(LocalDate matchDate, Long opponentId, String description) {
        this.matchDate = matchDate;
        this.opponentId = opponentId;
        this.description = description;
    }

    public LocalDate getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(LocalDate matchDate) {
        this.matchDate = matchDate;
    }

    public Long getOpponentId() {
        return opponentId;
    }

    public void setOpponentId(Long opponentId) {
        this.opponentId = opponentId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
