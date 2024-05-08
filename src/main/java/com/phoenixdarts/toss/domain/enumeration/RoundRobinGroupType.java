package com.phoenixdarts.toss.backend.domain.enumeration;

/**
 * 라운드로빈 그룹 종류
 */
public enum RoundRobinGroupType {
    TEAM4("4 (Team"),
    TEAM5("5 (Team"),
    TEAM6("6 (Team"),
    TEAM6_4MATCH("6 (Team"),
    TEAM4_2TIME_MATCH("4 (Team");

    private final String value;

    RoundRobinGroupType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
