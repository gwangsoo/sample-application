package com.phoenixdarts.toss.domain.enumeration;

/**
 * Handicap
 */
public enum HandicapType {
    NO("No Handicap"),
    OFFICIAL("Official Handicap"),
    BIGINNER("Biginner Handicap");

    private final String value;

    HandicapType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
