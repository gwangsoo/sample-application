package com.phoenixdarts.toss.domain.enumeration;

/**
 * 참가자 참여 여부 상태
 */
public enum EntryApprovalStatusType {
    WAITING_ENTRY("승인 대기"),
    COMPLETE_ENTRY("승인 완료"),
    WAITING_ENTRY_CANCELLATION("취소 대기"),
    ENTRY_CANCELLATION_COMPLETED("취소 완료");

    private final String value;

    EntryApprovalStatusType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
