package uj.wmii.pwj.anns;

import java.util.Map;
import java.util.HashMap;

public class OutputManager {
    // ANSI color codes
    private static final String RESET = "\u001B[0m";

    // Combined maps for all styles and colors
    private static final Map<String, String> ANSI_CODES = new HashMap<>();

    static {
        // Styles
        ANSI_CODES.put("bold", "\u001B[1m");
        ANSI_CODES.put("dim", "\u001B[2m");
        ANSI_CODES.put("italic", "\u001B[3m");
        ANSI_CODES.put("underline", "\u001B[4m");

        // Text colors
        ANSI_CODES.put("black", "\u001B[30m");
        ANSI_CODES.put("red", "\u001B[31m");
        ANSI_CODES.put("green", "\u001B[32m");
        ANSI_CODES.put("yellow", "\u001B[33m");
        ANSI_CODES.put("blue", "\u001B[34m");
        ANSI_CODES.put("magenta", "\u001B[35m");
        ANSI_CODES.put("cyan", "\u001B[36m");
        ANSI_CODES.put("white", "\u001B[37m");
        ANSI_CODES.put("bright_black", "\u001B[90m");
        ANSI_CODES.put("bright_red", "\u001B[91m");
        ANSI_CODES.put("bright_green", "\u001B[92m");
        ANSI_CODES.put("bright_yellow", "\u001B[93m");
        ANSI_CODES.put("bright_blue", "\u001B[94m");
        ANSI_CODES.put("bright_magenta", "\u001B[95m");
        ANSI_CODES.put("bright_cyan", "\u001B[96m");
        ANSI_CODES.put("bright_white", "\u001B[97m");
    }

    private String getCode(String name) {
        return ANSI_CODES.getOrDefault(name.toLowerCase(), "");
    }

    public void printDivide() {
        System.out.print(ANSI_CODES.get("bold"));
        System.out.print("\n------------------------------------------------------------------\n\n");
        System.out.print(RESET);
    }

    public void printTestResults(String color, String style, String message) {
        String colorCode = getCode(color);
        String styleCode = getCode(style);

        System.out.print(styleCode);
        System.out.print(colorCode);
        System.out.print(message + "\n");
        System.out.print(RESET);
    }

    private static final String[] ASCII_ART =
            {"      _______        _            ",
    "     |__   __|      | |           ",
    "        | | ___  ___| |_          ",
    "        | |/ _ \\/ __| __|         ",
    "        | |  __/\\__ \\ |_          ",
    "  ______|_|\\___||___/\\__|         ",
    " |  ____|     (_)                 ",
    " | |__   _ __  _  __ _ _ __   ___ ",
    " |  __| | '_ \\| |/ _` | '_ \\ / _ \\",
    " | |____| | | | | (_| | | | |  __/",
    " |______|_| |_|_|\\__, |_| |_|\\___|",
    "                  __/ |           ",
    "                 |___/            ",
    };

    public void displayHeader() {
        printTestResults("cyan", "bold", "\n");
        for (String line : ASCII_ART) {
            printTestResults("bright_cyan", "bold", line);
        }
        System.out.print("\n\n");
    }

    public void displayTestInfo(String className) {
        printTestResults("WHITE", "bold", "-----------------------------------------------------------\n");
        printTestResults("white", "bold", "                     TEST INFORMATION                      \n");
        printTestResults("white", "bold", "-----------------------------------------------------------\n\n");
        printTestResults("white", "bold", String.format("  Test Class:  %-40s  \n", className));
        printTestResults("white", "bold", String.format("  Date/Time:   %-40s  \n", java.time.LocalDateTime.now()));
        printTestResults("white", "bold", String.format("  Java:        %-40s  \n", System.getProperty("java.version")));
        printTestResults("white", "bold", "------------------------------------------------------------\n\n");
    }

}