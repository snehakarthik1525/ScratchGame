package com.game.helpers;

import java.io.File;
import java.math.BigDecimal;

public class Params {
    private String configPath;
    private BigDecimal bettingAmount;

    public String getConfigPath() {
        return configPath;
    }

    public BigDecimal getBettingAmount() {
        return bettingAmount;
    }

    public boolean init(String[] args) {
        if (args.length == 0) {
            printHelp();
            return false;
        }

        try {
            for (int i = 0; i < args.length; i++) {
                String arg = args[i];
                switch (arg) {
                    case "-c":
                    case "--config":
                        configPath = args[++i];
                        break;
                    case "-b":
                    case "--betting-amount":
                        try {
                            bettingAmount = new BigDecimal(args[++i]);
                        } catch (NumberFormatException e) {
                            System.err.println("Invalid betting amount: " + args[i]);
                            return false;
                        }
                        break;
                    case "-h":
                    case "--help":
                        return printHelp();
                    default:
                        System.err.println("Unknown argument: " + arg);
                        return printHelp();
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("Invalid arguments");
            return printHelp();
        }

        return verify() || printHelp();
    }

    private boolean verify() {
        if (configPath == null) {
            System.err.println("Config path is not specified");
            return false;
        } else {
            if (!new File(configPath).exists()) {
                System.err.println("Config file does not exist: " + configPath);
                return false;
            }
        }

        if (bettingAmount == null || bettingAmount.signum() <= 0) {
            System.err.println("Correct betting amount is not specified");
            return false;
        }

        return true;
    }

    private boolean printHelp() {
        System.out.println("Usage: java -jar ScratchGame.jar [options]");
        System.out.println("Options:");
        System.out.println("  -c, --config <path>            Path to config file");
        System.out.println("  -b, --betting-amount <amount>  Betting amount");
        System.out.println("  -h, --help                     Print this help");
        return false;
    }
}
