package com.game;

import java.io.File;
import java.io.IOException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.game.components.GameBoard;
import com.game.components.ScratchGame;
import com.game.config.Config;
import com.game.helpers.Params;

public class ScratchGameApp {
    public static void main(String[] args) throws IOException {
        Params params = new Params();
        if (params.init(args)) {
            ObjectMapper objectMapper = JsonMapper.builder()
                    .enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS)
                    .build();
            Config config = objectMapper.readValue(new File(params.getConfigPath()), Config.class);

            GameBoard output = new ScratchGame(config).scratch(params.getBettingAmount());

            objectMapper.writerWithDefaultPrettyPrinter().writeValue(System.out, output);
        }
    }
}
