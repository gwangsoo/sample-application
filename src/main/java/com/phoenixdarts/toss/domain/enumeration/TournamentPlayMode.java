package com.phoenixdarts.toss.backend.domain.enumeration;

/**
 * 토너먼트 Play Mode
 */
public enum TournamentPlayMode {
    SINGLE("Single"),
    DOUBLE("Double"),
    TRIO("Trio"),
    GALLON("Gallon");

    private final String value;

    TournamentPlayMode(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
