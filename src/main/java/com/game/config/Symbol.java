package com.game.config;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Symbol(
        @JsonProperty("reward_multiplier")
        BigDecimal rewardMultiplier,
        BigDecimal extra,
        SymbolType type,
        ImpactType impact
) {
}
