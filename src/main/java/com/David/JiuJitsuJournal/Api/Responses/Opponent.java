package com.David.JiuJitsuJournal.Api.Responses;

import com.David.JiuJitsuJournal.Domain.BeltRankEnum;

public class Opponent {
    private String name;
    private BeltRankEnum beltRank;
    private int heightInInches;
    private int weightInLbs;

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

    public int getHeightInInches() {
        return heightInInches;
    }

    public void setHeightInInches(int heightInInches) {
        this.heightInInches = heightInInches;
    }

    public int getWeightInLbs() {
        return weightInLbs;
    }

    public void setWeightInLbs(int weightInLbs) {
        this.weightInLbs = weightInLbs;
    }
}
