package com.phoenixdarts.toss.backend.domain.enumeration;

/**
 * 매치구분
 */
public enum MatchType {
    ROUND_ROBIN("Round Robin"),
    WINNER_ELIMINATION("Winner Elimination"),
    LOSER_ELIMINATION("Loser Elimination");

    private final String value;

    MatchType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
