package com.phoenixdarts.toss.domain.enumeration;

/**
 * Leg Play Mode
 */
public enum LegPlayMode {
    SINGLE("Single"),
    DOUBLE("Double"),
    TRIO("Trio"),
    GALLON("Gallon"),
    TEAM_2("Team 2");

    private final String value;

    LegPlayMode(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
