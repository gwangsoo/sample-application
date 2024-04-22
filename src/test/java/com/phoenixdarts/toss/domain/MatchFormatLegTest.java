package com.phoenixdarts.toss.domain;

import static com.phoenixdarts.toss.domain.MatchFormatLegTestSamples.*;
import static com.phoenixdarts.toss.domain.MatchFormatOptionTestSamples.*;
import static com.phoenixdarts.toss.domain.MatchFormatSetTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.phoenixdarts.toss.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MatchFormatLegTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MatchFormatLeg.class);
        MatchFormatLeg matchFormatLeg1 = getMatchFormatLegSample1();
        MatchFormatLeg matchFormatLeg2 = new MatchFormatLeg();
        assertThat(matchFormatLeg1).isNotEqualTo(matchFormatLeg2);

        matchFormatLeg2.setId(matchFormatLeg1.getId());
        assertThat(matchFormatLeg1).isEqualTo(matchFormatLeg2);

        matchFormatLeg2 = getMatchFormatLegSample2();
        assertThat(matchFormatLeg1).isNotEqualTo(matchFormatLeg2);
    }

    @Test
    void optionTest() throws Exception {
        MatchFormatLeg matchFormatLeg = getMatchFormatLegRandomSampleGenerator();
        MatchFormatOption matchFormatOptionBack = getMatchFormatOptionRandomSampleGenerator();

        matchFormatLeg.setOption(matchFormatOptionBack);
        assertThat(matchFormatLeg.getOption()).isEqualTo(matchFormatOptionBack);

        matchFormatLeg.option(null);
        assertThat(matchFormatLeg.getOption()).isNull();
    }

    @Test
    void matchFormatSetTest() throws Exception {
        MatchFormatLeg matchFormatLeg = getMatchFormatLegRandomSampleGenerator();
        MatchFormatSet matchFormatSetBack = getMatchFormatSetRandomSampleGenerator();

        matchFormatLeg.setMatchFormatSet(matchFormatSetBack);
        assertThat(matchFormatLeg.getMatchFormatSet()).isEqualTo(matchFormatSetBack);

        matchFormatLeg.matchFormatSet(null);
        assertThat(matchFormatLeg.getMatchFormatSet()).isNull();
    }
}
