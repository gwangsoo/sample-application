package com.phoenixdarts.toss.backend.domain.enumeration;

/**
 * 디비전 배정 방법
 */
public enum DivisionAssignMethod {
    AUTO("자동배정 (미공개"),
    PLAYER("플레이어 선택 (공개");

    private final String value;

    DivisionAssignMethod(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
