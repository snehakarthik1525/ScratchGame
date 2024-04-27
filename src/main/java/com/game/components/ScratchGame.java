package com.game.components;

import static java.math.BigDecimal.ZERO;

import java.math.BigDecimal;
import java.util.Random;

import com.game.config.CellDistribution;
import com.game.config.Config;

public class ScratchGame {
    private final Config config;

    public ScratchGame(Config config) {
        this.config = config;
    }

    public GameBoard scratch(BigDecimal bet) {
        return new GameBoardAnalyzer(config).analyze(distribute(), bet);
    }

    private GameBoard distribute() {
        boolean isBonus = config.probabilities().bonusSymbols() != null;
        String[][] board = new String[config.rows()][config.columns()];
        String bonusSymbol = null;
        for (CellDistribution cellDistribution : config.probabilities().standardSymbols()) {
            if (isBonus && new Random().nextBoolean()) {
                bonusSymbol = new Distribution(config.probabilities().bonusSymbols()).next();
                board[cellDistribution.row()][cellDistribution.column()] = bonusSymbol;
                isBonus = false;
            } else {
                board[cellDistribution.row()][cellDistribution.column()] = new Distribution(cellDistribution).next();
            }
        }
        return new GameBoard(board, ZERO, null, bonusSymbol);
    }

}
