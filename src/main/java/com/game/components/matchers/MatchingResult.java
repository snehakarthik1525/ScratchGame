package com.game.components.matchers;

import java.math.BigDecimal;

import com.game.config.CombinationGroup;

public record MatchingResult(
        String symbol,
        String combination,
        CombinationGroup group,
        BigDecimal rewardMultiplier
) {
}
