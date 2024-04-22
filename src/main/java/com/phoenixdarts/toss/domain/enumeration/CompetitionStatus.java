package com.phoenixdarts.toss.domain.enumeration;

/**
 * 대회상태
 */
public enum CompetitionStatus {
    PREPARE("Open 대기"),
    OPENING("Open 중"),
    CLOSE("종료");

    private final String value;

    CompetitionStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
