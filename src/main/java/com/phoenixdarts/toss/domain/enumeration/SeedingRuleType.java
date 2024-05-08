package com.phoenixdarts.toss.backend.domain.enumeration;

/**
 * Seeding Rule
 */
public enum SeedingRuleType {
    RANDOM("Random"),
    RATING("Rating"),
    PPD,
    MPR,
    SHUFFLE("Shuffle"),
    SPANISH("Spanish"),
    CSV_SEED_LOADING("CSV Seed Loading"),
    ROUND_ROBIN("Round Robin 연동");

    private String value;

    SeedingRuleType() {}

    SeedingRuleType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
