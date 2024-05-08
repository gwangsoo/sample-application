package com.phoenixdarts.toss.backend.domain.enumeration;

/**
 * 선공설정
 */
public enum FirstThrowType {
    COIN_TOSS("Coin Toss"),
    CENTER_CORK("Center Cork"),
    PREV_LOSER_FIRST("Previous Loser First"),
    RESERVE("Reserve");

    private final String value;

    FirstThrowType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
