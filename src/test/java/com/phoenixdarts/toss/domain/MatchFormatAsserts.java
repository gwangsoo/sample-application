package com.phoenixdarts.toss.backend.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class MatchFormatAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertMatchFormatAllPropertiesEquals(MatchFormat expected, MatchFormat actual) {
        assertMatchFormatAutoGeneratedPropertiesEquals(expected, actual);
        assertMatchFormatAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertMatchFormatAllUpdatablePropertiesEquals(MatchFormat expected, MatchFormat actual) {
        assertMatchFormatUpdatableFieldsEquals(expected, actual);
        assertMatchFormatUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertMatchFormatAutoGeneratedPropertiesEquals(MatchFormat expected, MatchFormat actual) {
        assertThat(expected)
            .as("Verify MatchFormat auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertMatchFormatUpdatableFieldsEquals(MatchFormat expected, MatchFormat actual) {
        assertThat(expected)
            .as("Verify MatchFormat relevant properties")
            .satisfies(e -> assertThat(e.getName()).as("check name").isEqualTo(actual.getName()))
            .satisfies(e -> assertThat(e.getDescription()).as("check description").isEqualTo(actual.getDescription()))
            .satisfies(e -> assertThat(e.getMatchFormatType()).as("check matchFormatType").isEqualTo(actual.getMatchFormatType()))
            .satisfies(e -> assertThat(e.getFirstSet()).as("check firstSet").isEqualTo(actual.getFirstSet()))
            .satisfies(e -> assertThat(e.getMiddleSet()).as("check middleSet").isEqualTo(actual.getMiddleSet()))
            .satisfies(e -> assertThat(e.getLastSet()).as("check lastSet").isEqualTo(actual.getLastSet()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertMatchFormatUpdatableRelationshipsEquals(MatchFormat expected, MatchFormat actual) {
        assertThat(expected)
            .as("Verify MatchFormat relationships")
            .satisfies(e -> assertThat(e.getDivision()).as("check division").isEqualTo(actual.getDivision()));
    }
}
