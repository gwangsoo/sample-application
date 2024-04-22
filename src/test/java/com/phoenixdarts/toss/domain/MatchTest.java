package com.phoenixdarts.toss.domain;

import static com.phoenixdarts.toss.domain.DivisionTestSamples.*;
import static com.phoenixdarts.toss.domain.MachineTestSamples.*;
import static com.phoenixdarts.toss.domain.MatchScoreTestSamples.*;
import static com.phoenixdarts.toss.domain.MatchTeamTestSamples.*;
import static com.phoenixdarts.toss.domain.MatchTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.phoenixdarts.toss.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class MatchTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Match.class);
        Match match1 = getMatchSample1();
        Match match2 = new Match();
        assertThat(match1).isNotEqualTo(match2);

        match2.setId(match1.getId());
        assertThat(match1).isEqualTo(match2);

        match2 = getMatchSample2();
        assertThat(match1).isNotEqualTo(match2);
    }

    @Test
    void matchScoreTest() throws Exception {
        Match match = getMatchRandomSampleGenerator();
        MatchScore matchScoreBack = getMatchScoreRandomSampleGenerator();

        match.addMatchScore(matchScoreBack);
        assertThat(match.getMatchScores()).containsOnly(matchScoreBack);
        assertThat(matchScoreBack.getMatch()).isEqualTo(match);

        match.removeMatchScore(matchScoreBack);
        assertThat(match.getMatchScores()).doesNotContain(matchScoreBack);
        assertThat(matchScoreBack.getMatch()).isNull();

        match.matchScores(new HashSet<>(Set.of(matchScoreBack)));
        assertThat(match.getMatchScores()).containsOnly(matchScoreBack);
        assertThat(matchScoreBack.getMatch()).isEqualTo(match);

        match.setMatchScores(new HashSet<>());
        assertThat(match.getMatchScores()).doesNotContain(matchScoreBack);
        assertThat(matchScoreBack.getMatch()).isNull();
    }

    @Test
    void homeTest() throws Exception {
        Match match = getMatchRandomSampleGenerator();
        MatchTeam matchTeamBack = getMatchTeamRandomSampleGenerator();

        match.setHome(matchTeamBack);
        assertThat(match.getHome()).isEqualTo(matchTeamBack);

        match.home(null);
        assertThat(match.getHome()).isNull();
    }

    @Test
    void awayTest() throws Exception {
        Match match = getMatchRandomSampleGenerator();
        MatchTeam matchTeamBack = getMatchTeamRandomSampleGenerator();

        match.setAway(matchTeamBack);
        assertThat(match.getAway()).isEqualTo(matchTeamBack);

        match.away(null);
        assertThat(match.getAway()).isNull();
    }

    @Test
    void tmatchTest() throws Exception {
        Match match = getMatchRandomSampleGenerator();
        Machine machineBack = getMachineRandomSampleGenerator();

        match.setTmatch(machineBack);
        assertThat(match.getTmatch()).isEqualTo(machineBack);

        match.tmatch(null);
        assertThat(match.getTmatch()).isNull();
    }

    @Test
    void divisionTest() throws Exception {
        Match match = getMatchRandomSampleGenerator();
        Division divisionBack = getDivisionRandomSampleGenerator();

        match.setDivision(divisionBack);
        assertThat(match.getDivision()).isEqualTo(divisionBack);

        match.division(null);
        assertThat(match.getDivision()).isNull();
    }

    @Test
    void machineTest() throws Exception {
        Match match = getMatchRandomSampleGenerator();
        Machine machineBack = getMachineRandomSampleGenerator();

        match.addMachine(machineBack);
        assertThat(match.getMachines()).containsOnly(machineBack);
        assertThat(machineBack.getMatch()).isEqualTo(match);

        match.removeMachine(machineBack);
        assertThat(match.getMachines()).doesNotContain(machineBack);
        assertThat(machineBack.getMatch()).isNull();

        match.machines(new HashSet<>(Set.of(machineBack)));
        assertThat(match.getMachines()).containsOnly(machineBack);
        assertThat(machineBack.getMatch()).isEqualTo(match);

        match.setMachines(new HashSet<>());
        assertThat(match.getMachines()).doesNotContain(machineBack);
        assertThat(machineBack.getMatch()).isNull();
    }
}
