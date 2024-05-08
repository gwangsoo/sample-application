package com.phoenixdarts.toss.backend.domain.enumeration;

/**
 * 납부방법
 */
public enum PaymentMethodType {
    PG("Payment Gateway"),
    CASH("현금");

    private final String value;

    PaymentMethodType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
