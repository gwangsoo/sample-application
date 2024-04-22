package com.phoenixdarts.toss.domain.enumeration;

/**
 * 선수 호출 구분
 */
public enum PlayerCallModeType {
    AUTO_PROGRESS("자동 진행"),
    MANUAL_PROGRESS("수동 진행");

    private final String value;

    PlayerCallModeType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
