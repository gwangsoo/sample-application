package com.phoenixdarts.toss.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class MatchFormatGameAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertMatchFormatGameAllPropertiesEquals(MatchFormatGame expected, MatchFormatGame actual) {
        assertMatchFormatGameAutoGeneratedPropertiesEquals(expected, actual);
        assertMatchFormatGameAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertMatchFormatGameAllUpdatablePropertiesEquals(MatchFormatGame expected, MatchFormatGame actual) {
        assertMatchFormatGameUpdatableFieldsEquals(expected, actual);
        assertMatchFormatGameUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertMatchFormatGameAutoGeneratedPropertiesEquals(MatchFormatGame expected, MatchFormatGame actual) {
        assertThat(expected)
            .as("Verify MatchFormatGame auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertMatchFormatGameUpdatableFieldsEquals(MatchFormatGame expected, MatchFormatGame actual) {
        assertThat(expected)
            .as("Verify MatchFormatGame relevant properties")
            .satisfies(e -> assertThat(e.getGameCategoryType()).as("check gameCategoryType").isEqualTo(actual.getGameCategoryType()))
            .satisfies(e -> assertThat(e.getGameType()).as("check gameType").isEqualTo(actual.getGameType()))
            .satisfies(e -> assertThat(e.getRoundNum()).as("check roundNum").isEqualTo(actual.getRoundNum()))
            .satisfies(e -> assertThat(e.getMachineCreditType()).as("check machineCreditType").isEqualTo(actual.getMachineCreditType()))
            .satisfies(
                e -> assertThat(e.getIncludingChoiceGame()).as("check includingChoiceGame").isEqualTo(actual.getIncludingChoiceGame())
            );
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertMatchFormatGameUpdatableRelationshipsEquals(MatchFormatGame expected, MatchFormatGame actual) {
        assertThat(expected)
            .as("Verify MatchFormatGame relationships")
            .satisfies(e -> assertThat(e.getGame()).as("check game").isEqualTo(actual.getGame()))
            .satisfies(e -> assertThat(e.getMatchFormat()).as("check matchFormat").isEqualTo(actual.getMatchFormat()));
    }
}
