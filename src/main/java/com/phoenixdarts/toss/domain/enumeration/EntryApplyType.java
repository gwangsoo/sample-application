package com.phoenixdarts.toss.backend.domain.enumeration;

/**
 * 참가신청방법
 */
public enum EntryApplyType {
    APP("App(user"),
    DIRECT("직접입력(Operator");

    private final String value;

    EntryApplyType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
