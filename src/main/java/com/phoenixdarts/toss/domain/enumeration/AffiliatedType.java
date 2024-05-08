package com.phoenixdarts.toss.backend.domain.enumeration;

/**
 * 플레이어소속
 */
public enum AffiliatedType {
    SHOP("Shop"),
    CLUB("Club");

    private final String value;

    AffiliatedType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
