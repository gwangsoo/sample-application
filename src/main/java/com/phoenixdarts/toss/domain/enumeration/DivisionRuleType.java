package com.phoenixdarts.toss.backend.domain.enumeration;

/**
 * Division Rule
 */
public enum DivisionRuleType {
    CARD_RATING("Card Rating"),
    GENERAL_RATING("General Rating"),
    COMPETITION_RATING("Competition Rating"),
    INPUT_RATING("Input Rating Operator"),
    HIGHER_RATING("Higher Rating Competition General"),
    NO_CLASSIFICATION("No Classification"),
    EVENT_POINT("Event Point");

    private final String value;

    DivisionRuleType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
