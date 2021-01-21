package com.David.JiuJitsuJournal.Api.Responses;

import com.David.JiuJitsuJournal.Domain.BeltRankEnum;

public class Opponent {
    private String name;
    private BeltRankEnum beltRank;

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
