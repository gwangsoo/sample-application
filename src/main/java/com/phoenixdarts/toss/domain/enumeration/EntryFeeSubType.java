package com.phoenixdarts.toss.backend.domain.enumeration;

/**
 * 참가비 상세 구분
 */
public enum EntryFeeSubType {
    DAY("일별"),
    TOURNAMENT("토너먼트별");

    private final String value;

    EntryFeeSubType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
