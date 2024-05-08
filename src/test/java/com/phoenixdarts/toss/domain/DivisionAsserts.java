package com.phoenixdarts.toss.backend.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class DivisionAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertDivisionAllPropertiesEquals(Division expected, Division actual) {
        assertDivisionAutoGeneratedPropertiesEquals(expected, actual);
        assertDivisionAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertDivisionAllUpdatablePropertiesEquals(Division expected, Division actual) {
        assertDivisionUpdatableFieldsEquals(expected, actual);
        assertDivisionUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertDivisionAutoGeneratedPropertiesEquals(Division expected, Division actual) {
        assertThat(expected)
            .as("Verify Division auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertDivisionUpdatableFieldsEquals(Division expected, Division actual) {
        assertThat(expected)
            .as("Verify Division relevant properties")
            .satisfies(e -> assertThat(e.getSeq()).as("check seq").isEqualTo(actual.getSeq()))
            .satisfies(e -> assertThat(e.getName()).as("check name").isEqualTo(actual.getName()))
            .satisfies(e -> assertThat(e.getRatingRuleTeamMin()).as("check ratingRuleTeamMin").isEqualTo(actual.getRatingRuleTeamMin()))
            .satisfies(e -> assertThat(e.getRatingRuleTeamMax()).as("check ratingRuleTeamMax").isEqualTo(actual.getRatingRuleTeamMax()))
            .satisfies(
                e ->
                    assertThat(e.getRatingRuleIndividualLimit())
                        .as("check ratingRuleIndividualLimit")
                        .isEqualTo(actual.getRatingRuleIndividualLimit())
            )
            .satisfies(
                e ->
                    assertThat(e.getRatingRuleIndividualMin())
                        .as("check ratingRuleIndividualMin")
                        .isEqualTo(actual.getRatingRuleIndividualMin())
            )
            .satisfies(
                e ->
                    assertThat(e.getRatingRuleIndividualMax())
                        .as("check ratingRuleIndividualMax")
                        .isEqualTo(actual.getRatingRuleIndividualMax())
            )
            .satisfies(e -> assertThat(e.getEntryLimit()).as("check entryLimit").isEqualTo(actual.getEntryLimit()))
            .satisfies(
                e ->
                    assertThat(e.getRoundRobinRankingDecisionType())
                        .as("check roundRobinRankingDecisionType")
                        .isEqualTo(actual.getRoundRobinRankingDecisionType())
            )
            .satisfies(
                e -> assertThat(e.getRoundRobinGroupType()).as("check roundRobinGroupType").isEqualTo(actual.getRoundRobinGroupType())
            )
            .satisfies(
                e -> assertThat(e.getNextRoundDecisionType()).as("check nextRoundDecisionType").isEqualTo(actual.getNextRoundDecisionType())
            )
            .satisfies(
                e ->
                    assertThat(e.getRoundRoginThirdDecision())
                        .as("check roundRoginThirdDecision")
                        .isEqualTo(actual.getRoundRoginThirdDecision())
            )
            .satisfies(
                e ->
                    assertThat(e.getThirdDecisionRankingRule())
                        .as("check thirdDecisionRankingRule")
                        .isEqualTo(actual.getThirdDecisionRankingRule())
            )
            .satisfies(
                e -> assertThat(e.getUseAllRoundSameFormat()).as("check useAllRoundSameFormat").isEqualTo(actual.getUseAllRoundSameFormat())
            )
            .satisfies(e -> assertThat(e.getEventRangeType()).as("check eventRangeType").isEqualTo(actual.getEventRangeType()))
            .satisfies(
                e -> assertThat(e.getEliminationTeamCount()).as("check eliminationTeamCount").isEqualTo(actual.getEliminationTeamCount())
            );
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertDivisionUpdatableRelationshipsEquals(Division expected, Division actual) {
        assertThat(expected)
            .as("Verify Division relationships")
            .satisfies(e -> assertThat(e.getTournament()).as("check tournament").isEqualTo(actual.getTournament()));
    }
}
