package com.game.components;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.game.config.CellDistribution;

class DistributionTest {

    @Test
    void next() {
        CellDistribution cellDistribution = new CellDistribution(
                0, 0, Map.of(
                "A", 1,
                "B", 2,
                "C", 3,
                "D", 4,
                "E", 5,
                "F", 6
        ));

        Distribution distribution = new Distribution(cellDistribution);

        Map<String, Integer> counters = new HashMap<>();

        for (int i = 0; i < (1 + 2 + 3 + 4 + 5 + 6) * 1000000; i++) {
            String symbol = distribution.next();
            counters.compute(symbol, (k, v) -> v == null ? 1 : v + 1);
        }

        assertEquals(1, normalize(counters.get("A")));
        assertEquals(2, normalize(counters.get("B")));
        assertEquals(3, normalize(counters.get("C")));
        assertEquals(4, normalize(counters.get("D")));
        assertEquals(5, normalize(counters.get("E")));
        assertEquals(6, normalize(counters.get("F")));
    }

    private long normalize(int value) {
        return Math.round(value / 1000000.0);
    }
}