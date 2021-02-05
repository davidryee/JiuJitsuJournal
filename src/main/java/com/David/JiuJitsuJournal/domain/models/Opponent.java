package com.David.JiuJitsuJournal.domain.models;

import com.David.JiuJitsuJournal.domain.BeltRankEnum;

public class Opponent {
    private String name;
    private BeltRankEnum beltRank;
    private int heightInInches;
    private int weightInLbs;

    public Opponent(String name, BeltRankEnum beltRank, int heightInInches, int weightInLbs) {
        this.name = name;
        this.beltRank = beltRank;
        this.heightInInches = heightInInches;
        this.weightInLbs = weightInLbs;
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

    public int getWeightInLbs() {
        return weightInLbs;
    }

    public void setWeightInLbs(int weightInLbs) {
        this.weightInLbs = weightInLbs;
    }

    public int getHeightInInches() {
        return heightInInches;
    }

    public void setHeightInInches(int heightInInches) {
        this.heightInInches = heightInInches;
    }
}
