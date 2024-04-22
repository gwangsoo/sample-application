package com.phoenixdarts.toss.domain.enumeration;

/**
 * 머신 상태
 */
public enum MachineStatusType {
    GOOD("정상"),
    BREAKDOWN("고장");

    private final String value;

    MachineStatusType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
