package com.phoenixdarts.toss.backend.domain.enumeration;

/**
 * 결제 상태
 */
public enum PaymentStatusType {
    /**
     * 결제 대기/요청
     */
    WAITING_PAYMENT("1"),
    /**
     * 결제 완료
     */
    COMPLETE_PAYMENT("2"),
    /**
     * 취소 대기
     */
    WAITING_PAYMENT_CANCELLATION("3"),
    /**
     * 취소 완료
     */
    PAYMENT_CANCELLATION_COMPLETED("4");

    private final String value;

    PaymentStatusType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
