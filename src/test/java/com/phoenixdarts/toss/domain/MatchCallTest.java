package com.phoenixdarts.toss.domain;

import static com.phoenixdarts.toss.domain.MatchCallTestSamples.*;
import static com.phoenixdarts.toss.domain.MatchTeamTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.phoenixdarts.toss.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MatchCallTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MatchCall.class);
        MatchCall matchCall1 = getMatchCallSample1();
        MatchCall matchCall2 = new MatchCall();
        assertThat(matchCall1).isNotEqualTo(matchCall2);

        matchCall2.setId(matchCall1.getId());
        assertThat(matchCall1).isEqualTo(matchCall2);

        matchCall2 = getMatchCallSample2();
        assertThat(matchCall1).isNotEqualTo(matchCall2);
    }

    @Test
    void matchTeamTest() throws Exception {
        MatchCall matchCall = getMatchCallRandomSampleGenerator();
        MatchTeam matchTeamBack = getMatchTeamRandomSampleGenerator();

        matchCall.setMatchTeam(matchTeamBack);
        assertThat(matchCall.getMatchTeam()).isEqualTo(matchTeamBack);

        matchCall.matchTeam(null);
        assertThat(matchCall.getMatchTeam()).isNull();
    }
}
