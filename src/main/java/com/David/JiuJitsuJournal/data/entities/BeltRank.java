package com.David.JiuJitsuJournal.data.entities;

import javax.persistence.*;

@Entity
@Table(name="beltRanks")
public class BeltRank {
    @Id
    @Column(name = "beltRankId")
    private int beltRankId;

    @Column(name = "color")
    private String beltColor;

    public BeltRank(){

    }

    public BeltRank(int beltRankId, String beltColor){
        this.beltRankId = beltRankId;
        this.beltColor = beltColor;
    }

    public int getBeltRankId() {
        return beltRankId;
    }

    public void setBeltRankId(int beltRankId) {
        this.beltRankId = beltRankId;
    }

    public String getBeltColor() {
        return beltColor;
    }

    public void setBeltColor(String beltColor) {
        this.beltColor = beltColor;
    }
}
