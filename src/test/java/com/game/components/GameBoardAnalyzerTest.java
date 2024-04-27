package com.game.components;

import static java.math.BigDecimal.ZERO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.game.config.Config;

class GameBoardAnalyzerTest {

    private Config config;

    @BeforeEach
    void setUp() throws IOException {
        ObjectMapper objectMapper = JsonMapper.builder()
                .enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS)
                .build();
        config = objectMapper.readValue(getClass().getClassLoader().getResource("config.json"), Config.class);
    }

    @Test
    void testAnalyzeFail() {
        GameBoardAnalyzer analyzer = new GameBoardAnalyzer(config);
        GameBoard gameBoard = new GameBoard(
                new String[][]{
                        {"A", "B", "C"},
                        {"E", "B", "5x"},
                        {"F", "D", "C"}
                },
                ZERO,
                null,
                "5x"
        );

        gameBoard = analyzer.analyze(gameBoard, new BigDecimal("100"));

        assertEquals(ZERO, gameBoard.reward());
        assertNull(gameBoard.winningCombinations());
        assertNull(gameBoard.bonusSymbol());
    }
    @Test
    void testAnalyzeWin_same_symbol_3_times() {
        GameBoardAnalyzer analyzer = new GameBoardAnalyzer(config);
        GameBoard gameBoard = new GameBoard(
                new String[][]{
                        {"A", "B", "C"},
                        {"E", "B", "10x"},
                        {"F", "D", "B"}
                },
                ZERO,
                null,
                "10x"
        );

        gameBoard = analyzer.analyze(gameBoard, new BigDecimal("100"));

        //There is an inconsistency in the assignment: example config values "same_symbol_3_times" as 1,
        //but the example calculation uses 2.
        //My config uses 1 as the reward multiplier for "same_symbol_3_times"
        //B = 25
        assertEquals(25000, gameBoard.reward().intValue());
        assertEquals(1, gameBoard.winningCombinations().size());
        assertEquals(1, gameBoard.winningCombinations().get("B").size());
        assertEquals("same_symbol_3_times", gameBoard.winningCombinations().get("B").get(0));
        assertEquals("10x", gameBoard.bonusSymbol());
    }
    @Test
    void testAnalyzeWin3Combinations() {
        GameBoardAnalyzer analyzer = new GameBoardAnalyzer(config);
        GameBoard gameBoard = new GameBoard(
                new String[][]{
                        {"B", "B", "C"},
                        {"B", "B", "+1000"},
                        {"B", "D", "B"}
                },
                ZERO,
                null,
                "+1000"
        );

        gameBoard = analyzer.analyze(gameBoard, new BigDecimal("100"));

        //B = 25
        //+1000 = 1000
        //same_symbol_6_times = 3
        //same_symbols_vertically = 2
        //same_symbols_diagonally_left_to_right = 5
        assertEquals(76000, gameBoard.reward().intValue());
        assertEquals(1, gameBoard.winningCombinations().size());
        assertEquals(3, gameBoard.winningCombinations().get("B").size());
        assertTrue(gameBoard.winningCombinations().get("B").contains("same_symbol_6_times"));
        assertTrue(gameBoard.winningCombinations().get("B").contains("same_symbols_vertically"));
        assertTrue(gameBoard.winningCombinations().get("B").contains("same_symbols_diagonally_left_to_right"));
        assertEquals("+1000", gameBoard.bonusSymbol());
    }

    @Test
    void testAnalyzeWin2Symbols() {
        GameBoardAnalyzer analyzer = new GameBoardAnalyzer(config);
        GameBoard gameBoard = new GameBoard(
                new String[][]{
                        {"B", "B", "B"},
                        {"F", "F", "F"},
                        {"C", "F", "C"}
                },
                ZERO,
                null,
                null
        );

        gameBoard = analyzer.analyze(gameBoard, new BigDecimal("100"));

        System.out.println(gameBoard);

        assertEquals(5450, gameBoard.reward().intValue());
        assertEquals(2, gameBoard.winningCombinations().size());
        assertEquals(2, gameBoard.winningCombinations().get("B").size());
        assertEquals(2, gameBoard.winningCombinations().get("F").size());
        assertTrue(gameBoard.winningCombinations().get("B").contains("same_symbol_3_times"));
        assertTrue(gameBoard.winningCombinations().get("B").contains("same_symbols_horizontally"));
        assertTrue(gameBoard.winningCombinations().get("F").contains("same_symbols_horizontally"));
        assertTrue(gameBoard.winningCombinations().get("F").contains("same_symbol_4_times"));
        assertNull(gameBoard.bonusSymbol());
    }

    @Test
    void testAnalyzeWinMultipleInTheSameGroup() {
        GameBoardAnalyzer analyzer = new GameBoardAnalyzer(config);
        GameBoard gameBoard = new GameBoard(
                new String[][]{
                        {"B", "B", "B"},
                        {"B", "B", "B"},
                        {"C", "F", "MISS"}
                },
                ZERO,
                null,
                "MISS"
        );

        gameBoard = analyzer.analyze(gameBoard, new BigDecimal("100"));

        System.out.println(gameBoard);

        assertEquals(15000, gameBoard.reward().intValue());
        assertEquals(1, gameBoard.winningCombinations().size());
        assertEquals(2, gameBoard.winningCombinations().get("B").size());
        assertTrue(gameBoard.winningCombinations().get("B").contains("same_symbol_6_times"));
        assertTrue(gameBoard.winningCombinations().get("B").contains("same_symbols_horizontally"));
        assertEquals("MISS", gameBoard.bonusSymbol());
    }
}