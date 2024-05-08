package com.phoenixdarts.toss.backend.domain.enumeration;

/**
 * 권한수준
 */
public enum AuthLevelType {
    WRITE("Write"),
    VIEW("View"),
    OPEN("Open");

    private final String value;

    AuthLevelType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
