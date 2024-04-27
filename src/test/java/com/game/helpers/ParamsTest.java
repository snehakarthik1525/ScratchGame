package com.game.helpers;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class ParamsTest {

    private final PrintStream standardOut = System.out;
    private final PrintStream standardErr = System.err;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
        System.setErr(new PrintStream(errStreamCaptor));
    }

    @AfterEach
    void tearDown() {
        System.setOut(standardOut);
        System.setErr(standardErr);
    }

    @Test
    void initEmptyShouldPrintHelpAndReturnFalse() {
        Params params = new Params();

        assertFalse(params.init(new String[]{}));
        String out = outputStreamCaptor.toString();
        assertTrue(out.contains("Usage:"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"-b", "--betting-amount"})
    void initWithInvalidBettingAmountShouldPrintErrorAndReturnFalse(String key) {
        Params params = new Params();

        assertFalse(params.init(new String[]{key, "invalid"}));
        String err = errStreamCaptor.toString();
        assertTrue(err.contains("Invalid betting amount: invalid"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"-c", "--config"})
    void initWithInvalidConfigPathShouldPrintErrorAndReturnFalse(String key) {
        Params params = new Params();

        assertFalse(params.init(new String[]{key, "invalid"}));
        String err = errStreamCaptor.toString();
        assertTrue(err.contains("Config file does not exist: invalid"));
    }

    @ParameterizedTest
    @CsvSource(value = {"-c,--betting-amount", "--config,-b"}, delimiter = ',')
    void initWithValidParamsShouldReturnTrue(String cKey, String bKey) {
        Params params = new Params();

        assertTrue(params.init(new String[]{cKey, "src/test/resources/config.json", bKey, "10"}));
        assertTrue(params.init(new String[]{bKey, "10", cKey, "src/test/resources/config.json"}));
    }

    @ParameterizedTest
    @ValueSource(strings = {"-c,src/test/resources/config.json,--betting-amount,", "-b,10,-c"})
    void initWithInvalidParamsShouldPrintHelpAndReturnFalse(String args) {
        Params params = new Params();

        assertFalse(params.init(args.split(",")));
        String err = errStreamCaptor.toString();
        String out = outputStreamCaptor.toString();
        assertTrue(err.contains("Invalid arguments"));
        assertTrue(out.contains("Usage:"));
    }

    @Test
    void initWithUnknownParamsShouldPrintHelpAndReturnFalse() {
        Params params = new Params();

        assertFalse(params.init(new String[]{"--config", "src/test/resources/config.json", "100"}));
        String err = errStreamCaptor.toString();
        String out = outputStreamCaptor.toString();
        assertTrue(err.contains("Unknown argument: 100"));
        assertTrue(out.contains("Usage:"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"-h", "--help"})
    void initWithHelpParamsShouldPrintHelpAndReturnFalse(String key) {
        Params params = new Params();

        assertFalse(params.init(new String[]{key}));
        String out = outputStreamCaptor.toString();
        assertTrue(out.contains("Usage:"));
    }
}