package com.phoenixdarts.toss.backend.domain.enumeration;

/**
 * 매치포맷 유형
 */
public enum MatchFormatType {
    GENERAL("General"),
    TEMPLATE("Template");

    private final String value;

    MatchFormatType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
