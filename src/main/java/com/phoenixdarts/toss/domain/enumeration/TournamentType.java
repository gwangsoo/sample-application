package com.phoenixdarts.toss.domain.enumeration;

/**
 * 경기유형
 */
public enum TournamentType {
    ROUNDROBIN("RoundRobin"),
    SINGLE_ELIMINATION("SingleElimination"),
    DOUBLE_ELIMINATION("DoubleElimination"),
    ROUNDROBIN_SINGLE_ELIMINATION("RoundRobin + SingleElimination"),
    ROUNDROBIN_DOUBLE_ELIMINATION("RoundRobin + DoubleElimination");

    private final String value;

    TournamentType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
