package com.phoenixdarts.toss.backend.domain.enumeration;

/**
 * 매치포맷 Freeze Option
 */
public enum MatchFormatFreezeOptionType {
    BUST("Bust"),
    LOSE("Lose");

    private final String value;

    MatchFormatFreezeOptionType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
