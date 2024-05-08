package com.phoenixdarts.toss.backend.domain;

import static com.phoenixdarts.toss.backend.domain.EntryTestSamples.*;
import static com.phoenixdarts.toss.backend.domain.MatchAttendanceTestSamples.*;
import static com.phoenixdarts.toss.backend.domain.TeamTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.phoenixdarts.toss.backend.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class EntryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Entry.class);
        Entry entry1 = getEntrySample1();
        Entry entry2 = new Entry();
        assertThat(entry1).isNotEqualTo(entry2);

        entry2.setId(entry1.getId());
        assertThat(entry1).isEqualTo(entry2);

        entry2 = getEntrySample2();
        assertThat(entry1).isNotEqualTo(entry2);
    }

    @Test
    void teamTest() throws Exception {
        Entry entry = getEntryRandomSampleGenerator();
        Team teamBack = getTeamRandomSampleGenerator();

        entry.setTeam(teamBack);
        assertThat(entry.getTeam()).isEqualTo(teamBack);

        entry.team(null);
        assertThat(entry.getTeam()).isNull();
    }

    @Test
    void matchAttendanceTest() throws Exception {
        Entry entry = getEntryRandomSampleGenerator();
        MatchAttendance matchAttendanceBack = getMatchAttendanceRandomSampleGenerator();

        entry.addMatchAttendance(matchAttendanceBack);
        assertThat(entry.getMatchAttendances()).containsOnly(matchAttendanceBack);
        assertThat(matchAttendanceBack.getEntry()).isEqualTo(entry);

        entry.removeMatchAttendance(matchAttendanceBack);
        assertThat(entry.getMatchAttendances()).doesNotContain(matchAttendanceBack);
        assertThat(matchAttendanceBack.getEntry()).isNull();

        entry.matchAttendances(new HashSet<>(Set.of(matchAttendanceBack)));
        assertThat(entry.getMatchAttendances()).containsOnly(matchAttendanceBack);
        assertThat(matchAttendanceBack.getEntry()).isEqualTo(entry);

        entry.setMatchAttendances(new HashSet<>());
        assertThat(entry.getMatchAttendances()).doesNotContain(matchAttendanceBack);
        assertThat(matchAttendanceBack.getEntry()).isNull();
    }
}
