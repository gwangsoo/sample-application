package com.phoenixdarts.toss.domain.enumeration;

/**
 * 매치포맷 Bull Option
 */
public enum MatchFormatBullOptionType {
    BULL_25_50("25/50 (Separator Bull"),
    BULL_50_50("50/50 (Fat Bull");

    private final String value;

    MatchFormatBullOptionType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
