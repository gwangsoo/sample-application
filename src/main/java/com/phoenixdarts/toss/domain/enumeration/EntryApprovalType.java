package com.phoenixdarts.toss.backend.domain.enumeration;

/**
 * 참가승인방법
 */
public enum EntryApprovalType {
    AUTO("자동승인"),
    MANUAL("수동승인");

    private final String value;

    EntryApprovalType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
