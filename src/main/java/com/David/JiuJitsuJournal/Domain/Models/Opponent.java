package com.David.JiuJitsuJournal.Domain.Models;

import com.David.JiuJitsuJournal.Domain.BeltRankEnum;

public class Opponent {
    private String name;
    private BeltRankEnum beltRank;

    public Opponent(String name, BeltRankEnum beltRank) {
        this.name = name;
        this.beltRank = beltRank;
    }
    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setBeltRank(BeltRankEnum beltRank){
        this.beltRank = beltRank;
    }
    public BeltRankEnum getBeltRank(){
        return this.beltRank;
    }
}
