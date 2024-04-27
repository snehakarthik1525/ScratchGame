package com.game.components;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.game.config.Config;

class ScratchGameTest {

    private Config config;

    @BeforeEach
    void setUp() throws IOException {
        ObjectMapper objectMapper = JsonMapper.builder()
                .enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS)
                .build();
        config = objectMapper.readValue(getClass().getClassLoader().getResource("config.json"), Config.class);
    }

    @Test
    void scratch() {
        ScratchGame scratch = new ScratchGame(config);
        GameBoard gameBoard = scratch.scratch(BigDecimal.valueOf(100));

        assertNotNull(gameBoard);
        assertNotNull(gameBoard.matrix());
        assertEquals(config.rows(), gameBoard.matrix().length);
        assertEquals(config.columns(), gameBoard.matrix()[0].length);
        assertNotNull(gameBoard.reward());
        assertEquals(gameBoard.reward().signum() == 0, gameBoard.winningCombinations() == null);
    }
}