package com.phoenixdarts.toss.domain;

import static com.phoenixdarts.toss.domain.GameTestSamples.*;
import static com.phoenixdarts.toss.domain.MatchFormatGameTestSamples.*;
import static com.phoenixdarts.toss.domain.MatchFormatTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.phoenixdarts.toss.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MatchFormatGameTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MatchFormatGame.class);
        MatchFormatGame matchFormatGame1 = getMatchFormatGameSample1();
        MatchFormatGame matchFormatGame2 = new MatchFormatGame();
        assertThat(matchFormatGame1).isNotEqualTo(matchFormatGame2);

        matchFormatGame2.setId(matchFormatGame1.getId());
        assertThat(matchFormatGame1).isEqualTo(matchFormatGame2);

        matchFormatGame2 = getMatchFormatGameSample2();
        assertThat(matchFormatGame1).isNotEqualTo(matchFormatGame2);
    }

    @Test
    void gameTest() throws Exception {
        MatchFormatGame matchFormatGame = getMatchFormatGameRandomSampleGenerator();
        Game gameBack = getGameRandomSampleGenerator();

        matchFormatGame.setGame(gameBack);
        assertThat(matchFormatGame.getGame()).isEqualTo(gameBack);

        matchFormatGame.game(null);
        assertThat(matchFormatGame.getGame()).isNull();
    }

    @Test
    void matchFormatTest() throws Exception {
        MatchFormatGame matchFormatGame = getMatchFormatGameRandomSampleGenerator();
        MatchFormat matchFormatBack = getMatchFormatRandomSampleGenerator();

        matchFormatGame.setMatchFormat(matchFormatBack);
        assertThat(matchFormatGame.getMatchFormat()).isEqualTo(matchFormatBack);

        matchFormatGame.matchFormat(null);
        assertThat(matchFormatGame.getMatchFormat()).isNull();
    }
}
