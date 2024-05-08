package com.phoenixdarts.toss.backend.domain.enumeration;

/**
 * Event Range 구분 기준
 */
public enum EventRangeType {
    CARD_RATING("Card Rating"),
    GENERAL_RATING("General Rating"),
    COMPETITION_RATING("Competition Rating"),
    HIGHER_RATING("Higher Rating(Competition Genera");

    private final String value;

    EventRangeType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
