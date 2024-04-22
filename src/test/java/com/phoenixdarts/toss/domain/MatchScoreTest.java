package com.phoenixdarts.toss.domain;

import static com.phoenixdarts.toss.domain.MatchScoreTestSamples.*;
import static com.phoenixdarts.toss.domain.MatchTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.phoenixdarts.toss.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MatchScoreTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MatchScore.class);
        MatchScore matchScore1 = getMatchScoreSample1();
        MatchScore matchScore2 = new MatchScore();
        assertThat(matchScore1).isNotEqualTo(matchScore2);

        matchScore2.setId(matchScore1.getId());
        assertThat(matchScore1).isEqualTo(matchScore2);

        matchScore2 = getMatchScoreSample2();
        assertThat(matchScore1).isNotEqualTo(matchScore2);
    }

    @Test
    void matchTest() throws Exception {
        MatchScore matchScore = getMatchScoreRandomSampleGenerator();
        Match matchBack = getMatchRandomSampleGenerator();

        matchScore.setMatch(matchBack);
        assertThat(matchScore.getMatch()).isEqualTo(matchBack);

        matchScore.match(null);
        assertThat(matchScore.getMatch()).isNull();
    }
}
