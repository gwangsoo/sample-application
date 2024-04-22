package com.phoenixdarts.toss.domain.enumeration;

/**
 * 성별
 */
public enum GenderType {
    MALE("남자"),
    FEMAIL("여자");

    private final String value;

    GenderType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
