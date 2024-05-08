package com.phoenixdarts.toss.backend.domain;

import static com.phoenixdarts.toss.backend.domain.DivisionTestSamples.*;
import static com.phoenixdarts.toss.backend.domain.MatchFormatGameTestSamples.*;
import static com.phoenixdarts.toss.backend.domain.MatchFormatOptionTestSamples.*;
import static com.phoenixdarts.toss.backend.domain.MatchFormatSetTestSamples.*;
import static com.phoenixdarts.toss.backend.domain.MatchFormatTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.phoenixdarts.toss.backend.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class MatchFormatTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MatchFormat.class);
        MatchFormat matchFormat1 = getMatchFormatSample1();
        MatchFormat matchFormat2 = new MatchFormat();
        assertThat(matchFormat1).isNotEqualTo(matchFormat2);

        matchFormat2.setId(matchFormat1.getId());
        assertThat(matchFormat1).isEqualTo(matchFormat2);

        matchFormat2 = getMatchFormatSample2();
        assertThat(matchFormat1).isNotEqualTo(matchFormat2);
    }

    @Test
    void matchFormatGameTest() throws Exception {
        MatchFormat matchFormat = getMatchFormatRandomSampleGenerator();
        MatchFormatGame matchFormatGameBack = getMatchFormatGameRandomSampleGenerator();

        matchFormat.addMatchFormatGame(matchFormatGameBack);
        assertThat(matchFormat.getMatchFormatGames()).containsOnly(matchFormatGameBack);
        assertThat(matchFormatGameBack.getMatchFormat()).isEqualTo(matchFormat);

        matchFormat.removeMatchFormatGame(matchFormatGameBack);
        assertThat(matchFormat.getMatchFormatGames()).doesNotContain(matchFormatGameBack);
        assertThat(matchFormatGameBack.getMatchFormat()).isNull();

        matchFormat.matchFormatGames(new HashSet<>(Set.of(matchFormatGameBack)));
        assertThat(matchFormat.getMatchFormatGames()).containsOnly(matchFormatGameBack);
        assertThat(matchFormatGameBack.getMatchFormat()).isEqualTo(matchFormat);

        matchFormat.setMatchFormatGames(new HashSet<>());
        assertThat(matchFormat.getMatchFormatGames()).doesNotContain(matchFormatGameBack);
        assertThat(matchFormatGameBack.getMatchFormat()).isNull();
    }

    @Test
    void matchFormatOptionTest() throws Exception {
        MatchFormat matchFormat = getMatchFormatRandomSampleGenerator();
        MatchFormatOption matchFormatOptionBack = getMatchFormatOptionRandomSampleGenerator();

        matchFormat.addMatchFormatOption(matchFormatOptionBack);
        assertThat(matchFormat.getMatchFormatOptions()).containsOnly(matchFormatOptionBack);
        assertThat(matchFormatOptionBack.getMatchFormat()).isEqualTo(matchFormat);

        matchFormat.removeMatchFormatOption(matchFormatOptionBack);
        assertThat(matchFormat.getMatchFormatOptions()).doesNotContain(matchFormatOptionBack);
        assertThat(matchFormatOptionBack.getMatchFormat()).isNull();

        matchFormat.matchFormatOptions(new HashSet<>(Set.of(matchFormatOptionBack)));
        assertThat(matchFormat.getMatchFormatOptions()).containsOnly(matchFormatOptionBack);
        assertThat(matchFormatOptionBack.getMatchFormat()).isEqualTo(matchFormat);

        matchFormat.setMatchFormatOptions(new HashSet<>());
        assertThat(matchFormat.getMatchFormatOptions()).doesNotContain(matchFormatOptionBack);
        assertThat(matchFormatOptionBack.getMatchFormat()).isNull();
    }

    @Test
    void matchFormatSetTest() throws Exception {
        MatchFormat matchFormat = getMatchFormatRandomSampleGenerator();
        MatchFormatSet matchFormatSetBack = getMatchFormatSetRandomSampleGenerator();

        matchFormat.addMatchFormatSet(matchFormatSetBack);
        assertThat(matchFormat.getMatchFormatSets()).containsOnly(matchFormatSetBack);
        assertThat(matchFormatSetBack.getMatchFormat()).isEqualTo(matchFormat);

        matchFormat.removeMatchFormatSet(matchFormatSetBack);
        assertThat(matchFormat.getMatchFormatSets()).doesNotContain(matchFormatSetBack);
        assertThat(matchFormatSetBack.getMatchFormat()).isNull();

        matchFormat.matchFormatSets(new HashSet<>(Set.of(matchFormatSetBack)));
        assertThat(matchFormat.getMatchFormatSets()).containsOnly(matchFormatSetBack);
        assertThat(matchFormatSetBack.getMatchFormat()).isEqualTo(matchFormat);

        matchFormat.setMatchFormatSets(new HashSet<>());
        assertThat(matchFormat.getMatchFormatSets()).doesNotContain(matchFormatSetBack);
        assertThat(matchFormatSetBack.getMatchFormat()).isNull();
    }

    @Test
    void divisionTest() throws Exception {
        MatchFormat matchFormat = getMatchFormatRandomSampleGenerator();
        Division divisionBack = getDivisionRandomSampleGenerator();

        matchFormat.setDivision(divisionBack);
        assertThat(matchFormat.getDivision()).isEqualTo(divisionBack);

        matchFormat.division(null);
        assertThat(matchFormat.getDivision()).isNull();
    }
}
