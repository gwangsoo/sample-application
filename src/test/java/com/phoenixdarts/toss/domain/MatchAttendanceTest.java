package com.phoenixdarts.toss.domain;

import static com.phoenixdarts.toss.domain.EntryTestSamples.*;
import static com.phoenixdarts.toss.domain.MatchAttendanceTestSamples.*;
import static com.phoenixdarts.toss.domain.MatchTeamTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.phoenixdarts.toss.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MatchAttendanceTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MatchAttendance.class);
        MatchAttendance matchAttendance1 = getMatchAttendanceSample1();
        MatchAttendance matchAttendance2 = new MatchAttendance();
        assertThat(matchAttendance1).isNotEqualTo(matchAttendance2);

        matchAttendance2.setId(matchAttendance1.getId());
        assertThat(matchAttendance1).isEqualTo(matchAttendance2);

        matchAttendance2 = getMatchAttendanceSample2();
        assertThat(matchAttendance1).isNotEqualTo(matchAttendance2);
    }

    @Test
    void entryTest() throws Exception {
        MatchAttendance matchAttendance = getMatchAttendanceRandomSampleGenerator();
        Entry entryBack = getEntryRandomSampleGenerator();

        matchAttendance.setEntry(entryBack);
        assertThat(matchAttendance.getEntry()).isEqualTo(entryBack);

        matchAttendance.entry(null);
        assertThat(matchAttendance.getEntry()).isNull();
    }

    @Test
    void matchTeamTest() throws Exception {
        MatchAttendance matchAttendance = getMatchAttendanceRandomSampleGenerator();
        MatchTeam matchTeamBack = getMatchTeamRandomSampleGenerator();

        matchAttendance.setMatchTeam(matchTeamBack);
        assertThat(matchAttendance.getMatchTeam()).isEqualTo(matchTeamBack);

        matchAttendance.matchTeam(null);
        assertThat(matchAttendance.getMatchTeam()).isNull();
    }
}
