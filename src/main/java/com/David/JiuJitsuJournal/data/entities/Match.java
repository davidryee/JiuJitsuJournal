package com.David.JiuJitsuJournal.data.entities;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="matches")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    public Match() {

    }

    public Match(LocalDate matchDate, User user, Opponent opponent, String description) {
        this.matchDate = matchDate;
        this.user = user;
        this.opponent = opponent;
        this.description = description;
    }

    @Column(name = "matchDate", columnDefinition = "DATE")
    private LocalDate matchDate;

    @ManyToOne
    private User user;

    @ManyToOne
    private Opponent opponent;

    @Column(name="description")
    private String description;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(LocalDate matchDate) {
        this.matchDate = matchDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
}
