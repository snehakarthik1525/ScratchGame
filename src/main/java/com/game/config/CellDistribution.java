package com.game.config;

import java.util.Map;

public record CellDistribution(
        int column,
        int row,
        Map<String, Integer> symbols
) {
}
