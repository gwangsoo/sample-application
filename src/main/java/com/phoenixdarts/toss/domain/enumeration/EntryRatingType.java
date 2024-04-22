package com.phoenixdarts.toss.domain.enumeration;

/**
 * 참가래이팅유형
 */
public enum EntryRatingType {
    AUTO("자동산출(Card"),
    DIRECT("직접등록(Player");

    private final String value;

    EntryRatingType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
