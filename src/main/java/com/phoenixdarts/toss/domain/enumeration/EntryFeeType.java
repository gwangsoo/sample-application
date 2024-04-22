package com.phoenixdarts.toss.domain.enumeration;

/**
 * 참가비 구분
 */
public enum EntryFeeType {
    FREE("무료"),
    PAY("유료");

    private final String value;

    EntryFeeType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
