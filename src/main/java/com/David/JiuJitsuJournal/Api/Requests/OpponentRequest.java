package com.David.JiuJitsuJournal.Api.Requests;

public class OpponentRequest {
    private String name;
    private int beltRank;
    private int heightInInches;
    private int weightInLbs;


    public OpponentRequest(String name, int beltRank, int heightInInches, int weightInLbs) {
        this.name = name;
        this.beltRank = beltRank;
        this.heightInInches = heightInInches;
        this.weightInLbs = weightInLbs;
    }

    public String getName() {
        return name;
    }

    public int getBeltRank() {
        return beltRank;
    }

    public int getHeightInInches() {
        return heightInInches;
    }

    public int getWeightInLbs() {
        return weightInLbs;
    }
}
