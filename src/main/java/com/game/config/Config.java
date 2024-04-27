package com.game.config;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Config(
        int columns,
        int rows,
        Map<String, Symbol> symbols,
        Probabilities probabilities,
        @JsonProperty("win_combinations")
        Map<String, WinCombination> winCombinations
) {

    boolean isValid() {
        return columns > 0 && rows > 0
                && !symbols.isEmpty()
                && probabilities != null
                && !probabilities.standardSymbols.isEmpty()
                && probabilities.standardSymbols.size() == columns * rows
                && !winCombinations.isEmpty();
    }

    public record Probabilities(
            @JsonProperty("standard_symbols")
            List<CellDistribution> standardSymbols,
            @JsonProperty("bonus_symbols")
            CellDistribution bonusSymbols
    ){}
}
