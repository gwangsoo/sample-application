package com.phoenixdarts.toss.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class EventPointAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertEventPointAllPropertiesEquals(EventPoint expected, EventPoint actual) {
        assertEventPointAutoGeneratedPropertiesEquals(expected, actual);
        assertEventPointAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertEventPointAllUpdatablePropertiesEquals(EventPoint expected, EventPoint actual) {
        assertEventPointUpdatableFieldsEquals(expected, actual);
        assertEventPointUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertEventPointAutoGeneratedPropertiesEquals(EventPoint expected, EventPoint actual) {
        assertThat(expected)
            .as("Verify EventPoint auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertEventPointUpdatableFieldsEquals(EventPoint expected, EventPoint actual) {
        assertThat(expected)
            .as("Verify EventPoint relevant properties")
            .satisfies(e -> assertThat(e.getSeq()).as("check seq").isEqualTo(actual.getSeq()))
            .satisfies(e -> assertThat(e.getRating()).as("check rating").isEqualTo(actual.getRating()))
            .satisfies(e -> assertThat(e.getRatingMin()).as("check ratingMin").isEqualTo(actual.getRatingMin()))
            .satisfies(e -> assertThat(e.getRatingMax()).as("check ratingMax").isEqualTo(actual.getRatingMax()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertEventPointUpdatableRelationshipsEquals(EventPoint expected, EventPoint actual) {
        assertThat(expected)
            .as("Verify EventPoint relationships")
            .satisfies(e -> assertThat(e.getDivision()).as("check division").isEqualTo(actual.getDivision()));
    }
}
