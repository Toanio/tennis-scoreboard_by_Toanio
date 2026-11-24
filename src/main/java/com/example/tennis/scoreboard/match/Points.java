package com.example.tennis.scoreboard.match;

public enum Points {
    ZERO("0"),
    FIFTEEN("15"),
    THIRTY("30"),
    FORTY("40"),
    AD("AD");

    private final String displayValue;

    Points(String displayValue) {
        this.displayValue = displayValue;
    }

    public static String toDisplayValue(Points points) {
        return points != null ? points.displayValue : null; // или "0" / null
    }
}
