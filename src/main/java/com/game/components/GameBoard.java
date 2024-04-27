package com.game.components;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record GameBoard(
        String[][] matrix,
        BigDecimal reward,
        @JsonProperty("applied_winning_combinations")
        Map<String, List<String>> winningCombinations,
        @JsonProperty("applied_bonus_symbol")
        String bonusSymbol
        ) {
}
