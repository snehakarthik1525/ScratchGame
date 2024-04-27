package com.game.config;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public record WinCombination(
        @JsonProperty("reward_multiplier")
        BigDecimal rewardMultiplier,
        CombinationMatch when,
        CombinationGroup group,
        int count,
        @JsonProperty("covered_areas")
        List<List<Coordinate>> coveredAreas
) {

    public record Coordinate(int row, int column){
        @JsonCreator
        public static Coordinate from(String s){
            String[] split = s.split(":");
            return new Coordinate(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
        }
    }
}
