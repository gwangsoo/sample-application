package com.phoenixdarts.toss.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class EntryAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertEntryAllPropertiesEquals(Entry expected, Entry actual) {
        assertEntryAutoGeneratedPropertiesEquals(expected, actual);
        assertEntryAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertEntryAllUpdatablePropertiesEquals(Entry expected, Entry actual) {
        assertEntryUpdatableFieldsEquals(expected, actual);
        assertEntryUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertEntryAutoGeneratedPropertiesEquals(Entry expected, Entry actual) {
        assertThat(expected)
            .as("Verify Entry auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertEntryUpdatableFieldsEquals(Entry expected, Entry actual) {
        assertThat(expected)
            .as("Verify Entry relevant properties")
            .satisfies(e -> assertThat(e.getEntryNo()).as("check entryNo").isEqualTo(actual.getEntryNo()))
            .satisfies(e -> assertThat(e.getPhoenixNo()).as("check phoenixNo").isEqualTo(actual.getPhoenixNo()))
            .satisfies(e -> assertThat(e.getCardNo()).as("check cardNo").isEqualTo(actual.getCardNo()))
            .satisfies(e -> assertThat(e.getName()).as("check name").isEqualTo(actual.getName()))
            .satisfies(e -> assertThat(e.getEnglishName()).as("check englishName").isEqualTo(actual.getEnglishName()))
            .satisfies(e -> assertThat(e.getRating()).as("check rating").isEqualTo(actual.getRating()))
            .satisfies(e -> assertThat(e.getMobileNo()).as("check mobileNo").isEqualTo(actual.getMobileNo()))
            .satisfies(e -> assertThat(e.getBirthDate()).as("check birthDate").isEqualTo(actual.getBirthDate()))
            .satisfies(e -> assertThat(e.getEmail()).as("check email").isEqualTo(actual.getEmail()))
            .satisfies(e -> assertThat(e.getGenderType()).as("check genderType").isEqualTo(actual.getGenderType()))
            .satisfies(
                e -> assertThat(e.getAttendanceStatusType()).as("check attendanceStatusType").isEqualTo(actual.getAttendanceStatusType())
            );
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertEntryUpdatableRelationshipsEquals(Entry expected, Entry actual) {
        assertThat(expected)
            .as("Verify Entry relationships")
            .satisfies(e -> assertThat(e.getTeam()).as("check team").isEqualTo(actual.getTeam()));
    }
}
