package com.phoenixdarts.toss.backend.domain;

import static com.phoenixdarts.toss.backend.domain.CompetitionTestSamples.*;
import static com.phoenixdarts.toss.backend.domain.OperatorRoleTestSamples.*;
import static com.phoenixdarts.toss.backend.domain.OperatorTestSamples.*;
import static com.phoenixdarts.toss.backend.domain.RegionTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.phoenixdarts.toss.backend.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class OperatorTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Operator.class);
        Operator operator1 = getOperatorSample1();
        Operator operator2 = new Operator();
        assertThat(operator1).isNotEqualTo(operator2);

        operator2.setId(operator1.getId());
        assertThat(operator1).isEqualTo(operator2);

        operator2 = getOperatorSample2();
        assertThat(operator1).isNotEqualTo(operator2);
    }

    @Test
    void operatorRoleTest() throws Exception {
        Operator operator = getOperatorRandomSampleGenerator();
        OperatorRole operatorRoleBack = getOperatorRoleRandomSampleGenerator();

        operator.setOperatorRole(operatorRoleBack);
        assertThat(operator.getOperatorRole()).isEqualTo(operatorRoleBack);

        operator.operatorRole(null);
        assertThat(operator.getOperatorRole()).isNull();
    }

    @Test
    void regionTest() throws Exception {
        Operator operator = getOperatorRandomSampleGenerator();
        Region regionBack = getRegionRandomSampleGenerator();

        operator.setRegion(regionBack);
        assertThat(operator.getRegion()).isEqualTo(regionBack);

        operator.region(null);
        assertThat(operator.getRegion()).isNull();
    }

    @Test
    void competitionTest() throws Exception {
        Operator operator = getOperatorRandomSampleGenerator();
        Competition competitionBack = getCompetitionRandomSampleGenerator();

        operator.addCompetition(competitionBack);
        assertThat(operator.getCompetitions()).containsOnly(competitionBack);
        assertThat(competitionBack.getOperator()).isEqualTo(operator);

        operator.removeCompetition(competitionBack);
        assertThat(operator.getCompetitions()).doesNotContain(competitionBack);
        assertThat(competitionBack.getOperator()).isNull();

        operator.competitions(new HashSet<>(Set.of(competitionBack)));
        assertThat(operator.getCompetitions()).containsOnly(competitionBack);
        assertThat(competitionBack.getOperator()).isEqualTo(operator);

        operator.setCompetitions(new HashSet<>());
        assertThat(operator.getCompetitions()).doesNotContain(competitionBack);
        assertThat(competitionBack.getOperator()).isNull();
    }
}
