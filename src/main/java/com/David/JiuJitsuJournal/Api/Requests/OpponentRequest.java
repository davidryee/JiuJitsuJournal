package com.David.JiuJitsuJournal.Api.Requests;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class OpponentRequest {
    public static final int FOUR_FEET_TALL = 48;
    public static final int EIGHT_FEET_TALL = 96;

    public static final int EIGHTY_POUNDS = 80;
    public static final int FIVE_HUNDRED_POUNDS = 500;

    public static final int WHITE_BELT = 0;
    public static final int BLACK_BELT = 4;


    @NotNull(message = "Opponent name must be provided.")
    @NotBlank(message = "Opponent name must not be blank.")
    private String name;

    @NotNull(message = "Opponent belt rank must be provided.")
    @Min(WHITE_BELT)
    @Max(BLACK_BELT)
    private Integer beltRank;

    @NotNull(message = "Opponent height must be provided.")
    @Min(value = FOUR_FEET_TALL)
    @Max(EIGHT_FEET_TALL)
    private Integer heightInInches;

    @NotNull
    @Min(EIGHTY_POUNDS)
    @Max(FIVE_HUNDRED_POUNDS)
    private Integer weightInLbs;


    public OpponentRequest(String name, Integer beltRank, Integer heightInInches, Integer weightInLbs) {
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
