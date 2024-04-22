package com.phoenixdarts.toss.domain.enumeration;

/**
 * 매치상태
 */
public enum MatchStatus {
    WAIT("대기중"),
    PLAYING("진행중"),
    CLOSE("종료");

    private final String value;

    MatchStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
