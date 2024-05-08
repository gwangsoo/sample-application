package com.phoenixdarts.toss.backend.domain.enumeration;

/**
 * 다음(결승) 라운드 진출 방식
 */
public enum NextRoundDecisionType {
    FIRST("1위"),
    FIRST_SECOND("1위 ~ 2위"),
    FIRST_THIRD("1위 ~ 3위"),
    FIRST_FOURTH("1위 ~ 4위"),
    FIRST_FIFTH("1위 ~ 5위");

    private final String value;

    NextRoundDecisionType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
