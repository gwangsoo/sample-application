package com.phoenixdarts.toss.domain.enumeration;

/**
 * 리워드지급방법1
 */
public enum RewardMethodType {
    DAY("일별"),
    TOURNAMENT("토너먼트별");

    private final String value;

    RewardMethodType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
