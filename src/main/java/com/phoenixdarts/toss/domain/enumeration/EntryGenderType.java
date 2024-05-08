package com.phoenixdarts.toss.backend.domain.enumeration;

/**
 * 참가자 성별
 */
public enum EntryGenderType {
    ALL("All"),
    MALE("Male"),
    FEMALE("Female"),
    CONCOCTION("Concoction");

    private final String value;

    EntryGenderType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
