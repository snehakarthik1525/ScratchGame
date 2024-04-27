package com.game.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;

class ConfigTest {

    @Test
    public void testLoadConfig() throws IOException {
        ObjectMapper objectMapper = JsonMapper.builder()
                .enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS)
                .build();
        Config config = objectMapper.readValue(getClass().getClassLoader().getResource("config.json"), Config.class);

        assertNotNull(config);
        assertTrue(config.isValid());

        assertEquals(3, config.columns());
        assertEquals(3, config.rows());

        assertNotNull(config.symbols());
        assertFalse(config.symbols().isEmpty());
        assertNotNull(config.probabilities());
        assertNotNull(config.probabilities().standardSymbols());
        assertFalse(config.probabilities().standardSymbols().isEmpty());
        assertNotNull(config.probabilities().bonusSymbols());
        assertNotNull(config.winCombinations());
        assertFalse(config.winCombinations().isEmpty());
    }

}