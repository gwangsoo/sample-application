package com.phoenixdarts.toss.backend.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class EntryFeeAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertEntryFeeAllPropertiesEquals(EntryFee expected, EntryFee actual) {
        assertEntryFeeAutoGeneratedPropertiesEquals(expected, actual);
        assertEntryFeeAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertEntryFeeAllUpdatablePropertiesEquals(EntryFee expected, EntryFee actual) {
        assertEntryFeeUpdatableFieldsEquals(expected, actual);
        assertEntryFeeUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertEntryFeeAutoGeneratedPropertiesEquals(EntryFee expected, EntryFee actual) {
        assertThat(expected)
            .as("Verify EntryFee auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertEntryFeeUpdatableFieldsEquals(EntryFee expected, EntryFee actual) {
        assertThat(expected)
            .as("Verify EntryFee relevant properties")
            .satisfies(e -> assertThat(e.getEntryFeeType()).as("check entryFeeType").isEqualTo(actual.getEntryFeeType()))
            .satisfies(e -> assertThat(e.getEntryFeeSubType()).as("check entryFeeSubType").isEqualTo(actual.getEntryFeeSubType()))
            .satisfies(e -> assertThat(e.getPaymentMethodType()).as("check paymentMethodType").isEqualTo(actual.getPaymentMethodType()))
            .satisfies(e -> assertThat(e.getScheduleNumber()).as("check scheduleNumber").isEqualTo(actual.getScheduleNumber()))
            .satisfies(e -> assertThat(e.getFee()).as("check fee").isEqualTo(actual.getFee()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertEntryFeeUpdatableRelationshipsEquals(EntryFee expected, EntryFee actual) {
        assertThat(expected)
            .as("Verify EntryFee relationships")
            .satisfies(e -> assertThat(e.getCurrency()).as("check currency").isEqualTo(actual.getCurrency()));
    }
}
