package com.phoenixdarts.toss.backend.domain;

import static com.phoenixdarts.toss.backend.domain.MatchFormatLegTestSamples.*;
import static com.phoenixdarts.toss.backend.domain.MatchFormatSetTestSamples.*;
import static com.phoenixdarts.toss.backend.domain.MatchFormatTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.phoenixdarts.toss.backend.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class MatchFormatSetTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MatchFormatSet.class);
        MatchFormatSet matchFormatSet1 = getMatchFormatSetSample1();
        MatchFormatSet matchFormatSet2 = new MatchFormatSet();
        assertThat(matchFormatSet1).isNotEqualTo(matchFormatSet2);

        matchFormatSet2.setId(matchFormatSet1.getId());
        assertThat(matchFormatSet1).isEqualTo(matchFormatSet2);

        matchFormatSet2 = getMatchFormatSetSample2();
        assertThat(matchFormatSet1).isNotEqualTo(matchFormatSet2);
    }

    @Test
    void matchFormatLegTest() throws Exception {
        MatchFormatSet matchFormatSet = getMatchFormatSetRandomSampleGenerator();
        MatchFormatLeg matchFormatLegBack = getMatchFormatLegRandomSampleGenerator();

        matchFormatSet.addMatchFormatLeg(matchFormatLegBack);
        assertThat(matchFormatSet.getMatchFormatLegs()).containsOnly(matchFormatLegBack);
        assertThat(matchFormatLegBack.getMatchFormatSet()).isEqualTo(matchFormatSet);

        matchFormatSet.removeMatchFormatLeg(matchFormatLegBack);
        assertThat(matchFormatSet.getMatchFormatLegs()).doesNotContain(matchFormatLegBack);
        assertThat(matchFormatLegBack.getMatchFormatSet()).isNull();

        matchFormatSet.matchFormatLegs(new HashSet<>(Set.of(matchFormatLegBack)));
        assertThat(matchFormatSet.getMatchFormatLegs()).containsOnly(matchFormatLegBack);
        assertThat(matchFormatLegBack.getMatchFormatSet()).isEqualTo(matchFormatSet);

        matchFormatSet.setMatchFormatLegs(new HashSet<>());
        assertThat(matchFormatSet.getMatchFormatLegs()).doesNotContain(matchFormatLegBack);
        assertThat(matchFormatLegBack.getMatchFormatSet()).isNull();
    }

    @Test
    void matchFormatTest() throws Exception {
        MatchFormatSet matchFormatSet = getMatchFormatSetRandomSampleGenerator();
        MatchFormat matchFormatBack = getMatchFormatRandomSampleGenerator();

        matchFormatSet.setMatchFormat(matchFormatBack);
        assertThat(matchFormatSet.getMatchFormat()).isEqualTo(matchFormatBack);

        matchFormatSet.matchFormat(null);
        assertThat(matchFormatSet.getMatchFormat()).isNull();
    }
}
