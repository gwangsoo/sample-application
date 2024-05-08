package com.phoenixdarts.toss.backend.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class CompetitionAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertCompetitionAllPropertiesEquals(Competition expected, Competition actual) {
        assertCompetitionAutoGeneratedPropertiesEquals(expected, actual);
        assertCompetitionAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertCompetitionAllUpdatablePropertiesEquals(Competition expected, Competition actual) {
        assertCompetitionUpdatableFieldsEquals(expected, actual);
        assertCompetitionUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertCompetitionAutoGeneratedPropertiesEquals(Competition expected, Competition actual) {
        assertThat(expected)
            .as("Verify Competition auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertCompetitionUpdatableFieldsEquals(Competition expected, Competition actual) {
        assertThat(expected)
            .as("Verify Competition relevant properties")
            .satisfies(e -> assertThat(e.getName()).as("check name").isEqualTo(actual.getName()))
            .satisfies(e -> assertThat(e.getStartDateTime()).as("check startDateTime").isEqualTo(actual.getStartDateTime()))
            .satisfies(e -> assertThat(e.getEndDateTime()).as("check endDateTime").isEqualTo(actual.getEndDateTime()))
            .satisfies(e -> assertThat(e.getEntryStartDateTime()).as("check entryStartDateTime").isEqualTo(actual.getEntryStartDateTime()))
            .satisfies(e -> assertThat(e.getEntryEndDateTime()).as("check entryEndDateTime").isEqualTo(actual.getEntryEndDateTime()))
            .satisfies(e -> assertThat(e.getStatus()).as("check status").isEqualTo(actual.getStatus()))
            .satisfies(e -> assertThat(e.getApproval()).as("check approval").isEqualTo(actual.getApproval()))
            .satisfies(e -> assertThat(e.getEntryApplyType()).as("check entryApplyType").isEqualTo(actual.getEntryApplyType()))
            .satisfies(e -> assertThat(e.getEntryRatingType()).as("check entryRatingType").isEqualTo(actual.getEntryRatingType()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertCompetitionUpdatableRelationshipsEquals(Competition expected, Competition actual) {
        assertThat(expected)
            .as("Verify Competition relationships")
            .satisfies(e -> assertThat(e.getCompetitionImage()).as("check competitionImage").isEqualTo(actual.getCompetitionImage()))
            .satisfies(e -> assertThat(e.getReward()).as("check reward").isEqualTo(actual.getReward()))
            .satisfies(e -> assertThat(e.getCountry()).as("check country").isEqualTo(actual.getCountry()))
            .satisfies(e -> assertThat(e.getOperator()).as("check operator").isEqualTo(actual.getOperator()))
            .satisfies(e -> assertThat(e.getEntryFee()).as("check entryFee").isEqualTo(actual.getEntryFee()));
    }
}
