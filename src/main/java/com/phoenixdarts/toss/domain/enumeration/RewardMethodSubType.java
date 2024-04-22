package com.phoenixdarts.toss.domain.enumeration;

/**
 * 리워드지급방법2
 */
public enum RewardMethodSubType {
    ALL("All Division"),
    DIVISION("Division별");

    private final String value;

    RewardMethodSubType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
