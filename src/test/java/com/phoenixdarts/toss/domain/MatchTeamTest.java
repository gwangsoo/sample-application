package com.phoenixdarts.toss.domain;

import static com.phoenixdarts.toss.domain.MatchAttendanceTestSamples.*;
import static com.phoenixdarts.toss.domain.MatchCallTestSamples.*;
import static com.phoenixdarts.toss.domain.MatchTeamTestSamples.*;
import static com.phoenixdarts.toss.domain.TeamTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.phoenixdarts.toss.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class MatchTeamTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MatchTeam.class);
        MatchTeam matchTeam1 = getMatchTeamSample1();
        MatchTeam matchTeam2 = new MatchTeam();
        assertThat(matchTeam1).isNotEqualTo(matchTeam2);

        matchTeam2.setId(matchTeam1.getId());
        assertThat(matchTeam1).isEqualTo(matchTeam2);

        matchTeam2 = getMatchTeamSample2();
        assertThat(matchTeam1).isNotEqualTo(matchTeam2);
    }

    @Test
    void matchAttendanceTest() throws Exception {
        MatchTeam matchTeam = getMatchTeamRandomSampleGenerator();
        MatchAttendance matchAttendanceBack = getMatchAttendanceRandomSampleGenerator();

        matchTeam.addMatchAttendance(matchAttendanceBack);
        assertThat(matchTeam.getMatchAttendances()).containsOnly(matchAttendanceBack);
        assertThat(matchAttendanceBack.getMatchTeam()).isEqualTo(matchTeam);

        matchTeam.removeMatchAttendance(matchAttendanceBack);
        assertThat(matchTeam.getMatchAttendances()).doesNotContain(matchAttendanceBack);
        assertThat(matchAttendanceBack.getMatchTeam()).isNull();

        matchTeam.matchAttendances(new HashSet<>(Set.of(matchAttendanceBack)));
        assertThat(matchTeam.getMatchAttendances()).containsOnly(matchAttendanceBack);
        assertThat(matchAttendanceBack.getMatchTeam()).isEqualTo(matchTeam);

        matchTeam.setMatchAttendances(new HashSet<>());
        assertThat(matchTeam.getMatchAttendances()).doesNotContain(matchAttendanceBack);
        assertThat(matchAttendanceBack.getMatchTeam()).isNull();
    }

    @Test
    void matchCallTest() throws Exception {
        MatchTeam matchTeam = getMatchTeamRandomSampleGenerator();
        MatchCall matchCallBack = getMatchCallRandomSampleGenerator();

        matchTeam.addMatchCall(matchCallBack);
        assertThat(matchTeam.getMatchCalls()).containsOnly(matchCallBack);
        assertThat(matchCallBack.getMatchTeam()).isEqualTo(matchTeam);

        matchTeam.removeMatchCall(matchCallBack);
        assertThat(matchTeam.getMatchCalls()).doesNotContain(matchCallBack);
        assertThat(matchCallBack.getMatchTeam()).isNull();

        matchTeam.matchCalls(new HashSet<>(Set.of(matchCallBack)));
        assertThat(matchTeam.getMatchCalls()).containsOnly(matchCallBack);
        assertThat(matchCallBack.getMatchTeam()).isEqualTo(matchTeam);

        matchTeam.setMatchCalls(new HashSet<>());
        assertThat(matchTeam.getMatchCalls()).doesNotContain(matchCallBack);
        assertThat(matchCallBack.getMatchTeam()).isNull();
    }

    @Test
    void teamTest() throws Exception {
        MatchTeam matchTeam = getMatchTeamRandomSampleGenerator();
        Team teamBack = getTeamRandomSampleGenerator();

        matchTeam.setTeam(teamBack);
        assertThat(matchTeam.getTeam()).isEqualTo(teamBack);

        matchTeam.team(null);
        assertThat(matchTeam.getTeam()).isNull();
    }
}
