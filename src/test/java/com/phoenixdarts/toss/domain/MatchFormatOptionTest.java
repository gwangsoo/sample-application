package com.phoenixdarts.toss.backend.domain;

import static com.phoenixdarts.toss.backend.domain.MatchFormatOptionTestSamples.*;
import static com.phoenixdarts.toss.backend.domain.MatchFormatTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.phoenixdarts.toss.backend.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MatchFormatOptionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MatchFormatOption.class);
        MatchFormatOption matchFormatOption1 = getMatchFormatOptionSample1();
        MatchFormatOption matchFormatOption2 = new MatchFormatOption();
        assertThat(matchFormatOption1).isNotEqualTo(matchFormatOption2);

        matchFormatOption2.setId(matchFormatOption1.getId());
        assertThat(matchFormatOption1).isEqualTo(matchFormatOption2);

        matchFormatOption2 = getMatchFormatOptionSample2();
        assertThat(matchFormatOption1).isNotEqualTo(matchFormatOption2);
    }

    @Test
    void matchFormatTest() throws Exception {
        MatchFormatOption matchFormatOption = getMatchFormatOptionRandomSampleGenerator();
        MatchFormat matchFormatBack = getMatchFormatRandomSampleGenerator();

        matchFormatOption.setMatchFormat(matchFormatBack);
        assertThat(matchFormatOption.getMatchFormat()).isEqualTo(matchFormatBack);

        matchFormatOption.matchFormat(null);
        assertThat(matchFormatOption.getMatchFormat()).isNull();
    }
}
