package com.phoenixdarts.toss.backend.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class MatchScoreAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertMatchScoreAllPropertiesEquals(MatchScore expected, MatchScore actual) {
        assertMatchScoreAutoGeneratedPropertiesEquals(expected, actual);
        assertMatchScoreAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertMatchScoreAllUpdatablePropertiesEquals(MatchScore expected, MatchScore actual) {
        assertMatchScoreUpdatableFieldsEquals(expected, actual);
        assertMatchScoreUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertMatchScoreAutoGeneratedPropertiesEquals(MatchScore expected, MatchScore actual) {
        assertThat(expected)
            .as("Verify MatchScore auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertMatchScoreUpdatableFieldsEquals(MatchScore expected, MatchScore actual) {
        assertThat(expected)
            .as("Verify MatchScore relevant properties")
            .satisfies(e -> assertThat(e.getSetNo()).as("check setNo").isEqualTo(actual.getSetNo()))
            .satisfies(e -> assertThat(e.getLgeNo()).as("check lgeNo").isEqualTo(actual.getLgeNo()))
            .satisfies(e -> assertThat(e.getLegGameName()).as("check legGameName").isEqualTo(actual.getLegGameName()))
            .satisfies(e -> assertThat(e.getHomeLegScore()).as("check homeLegScore").isEqualTo(actual.getHomeLegScore()))
            .satisfies(e -> assertThat(e.getAwayLegScore()).as("check awayLegScore").isEqualTo(actual.getAwayLegScore()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertMatchScoreUpdatableRelationshipsEquals(MatchScore expected, MatchScore actual) {
        assertThat(expected)
            .as("Verify MatchScore relationships")
            .satisfies(e -> assertThat(e.getMatch()).as("check match").isEqualTo(actual.getMatch()));
    }
}
