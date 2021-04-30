package com.David.JiuJitsuJournal.domain;

import java.util.Arrays;

public enum BeltRankEnum {
    WHITE(1), BLUE(2), PURPLE(3), BROWN(4), BLACK(5);
    private final int beltRankId;
    BeltRankEnum(int beltRankId) { this.beltRankId = beltRankId;}

    public int getBeltRankId() {
        return beltRankId;
    }

    public static BeltRankEnum getByBeltRankId(int id) {
        return Arrays.stream(BeltRankEnum.values()).filter((beltRankEnum -> beltRankEnum.getBeltRankId() == id))
                .findFirst().get();
    }
}

