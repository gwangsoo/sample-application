package com.phoenixdarts.toss.backend.domain;

import static com.phoenixdarts.toss.backend.domain.DivisionTestSamples.*;
import static com.phoenixdarts.toss.backend.domain.EventPointTestSamples.*;
import static com.phoenixdarts.toss.backend.domain.MatchFormatTestSamples.*;
import static com.phoenixdarts.toss.backend.domain.MatchTestSamples.*;
import static com.phoenixdarts.toss.backend.domain.TeamTestSamples.*;
import static com.phoenixdarts.toss.backend.domain.TournamentTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.phoenixdarts.toss.backend.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class DivisionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Division.class);
        Division division1 = getDivisionSample1();
        Division division2 = new Division();
        assertThat(division1).isNotEqualTo(division2);

        division2.setId(division1.getId());
        assertThat(division1).isEqualTo(division2);

        division2 = getDivisionSample2();
        assertThat(division1).isNotEqualTo(division2);
    }

    @Test
    void matchFormatTest() throws Exception {
        Division division = getDivisionRandomSampleGenerator();
        MatchFormat matchFormatBack = getMatchFormatRandomSampleGenerator();

        division.addMatchFormat(matchFormatBack);
        assertThat(division.getMatchFormats()).containsOnly(matchFormatBack);
        assertThat(matchFormatBack.getDivision()).isEqualTo(division);

        division.removeMatchFormat(matchFormatBack);
        assertThat(division.getMatchFormats()).doesNotContain(matchFormatBack);
        assertThat(matchFormatBack.getDivision()).isNull();

        division.matchFormats(new HashSet<>(Set.of(matchFormatBack)));
        assertThat(division.getMatchFormats()).containsOnly(matchFormatBack);
        assertThat(matchFormatBack.getDivision()).isEqualTo(division);

        division.setMatchFormats(new HashSet<>());
        assertThat(division.getMatchFormats()).doesNotContain(matchFormatBack);
        assertThat(matchFormatBack.getDivision()).isNull();
    }

    @Test
    void eventPointTest() throws Exception {
        Division division = getDivisionRandomSampleGenerator();
        EventPoint eventPointBack = getEventPointRandomSampleGenerator();

        division.addEventPoint(eventPointBack);
        assertThat(division.getEventPoints()).containsOnly(eventPointBack);
        assertThat(eventPointBack.getDivision()).isEqualTo(division);

        division.removeEventPoint(eventPointBack);
        assertThat(division.getEventPoints()).doesNotContain(eventPointBack);
        assertThat(eventPointBack.getDivision()).isNull();

        division.eventPoints(new HashSet<>(Set.of(eventPointBack)));
        assertThat(division.getEventPoints()).containsOnly(eventPointBack);
        assertThat(eventPointBack.getDivision()).isEqualTo(division);

        division.setEventPoints(new HashSet<>());
        assertThat(division.getEventPoints()).doesNotContain(eventPointBack);
        assertThat(eventPointBack.getDivision()).isNull();
    }

    @Test
    void teamTest() throws Exception {
        Division division = getDivisionRandomSampleGenerator();
        Team teamBack = getTeamRandomSampleGenerator();

        division.addTeam(teamBack);
        assertThat(division.getTeams()).containsOnly(teamBack);
        assertThat(teamBack.getDivision()).isEqualTo(division);

        division.removeTeam(teamBack);
        assertThat(division.getTeams()).doesNotContain(teamBack);
        assertThat(teamBack.getDivision()).isNull();

        division.teams(new HashSet<>(Set.of(teamBack)));
        assertThat(division.getTeams()).containsOnly(teamBack);
        assertThat(teamBack.getDivision()).isEqualTo(division);

        division.setTeams(new HashSet<>());
        assertThat(division.getTeams()).doesNotContain(teamBack);
        assertThat(teamBack.getDivision()).isNull();
    }

    @Test
    void tournamentTest() throws Exception {
        Division division = getDivisionRandomSampleGenerator();
        Tournament tournamentBack = getTournamentRandomSampleGenerator();

        division.setTournament(tournamentBack);
        assertThat(division.getTournament()).isEqualTo(tournamentBack);

        division.tournament(null);
        assertThat(division.getTournament()).isNull();
    }

    @Test
    void matchTest() throws Exception {
        Division division = getDivisionRandomSampleGenerator();
        Match matchBack = getMatchRandomSampleGenerator();

        division.addMatch(matchBack);
        assertThat(division.getMatches()).containsOnly(matchBack);
        assertThat(matchBack.getDivision()).isEqualTo(division);

        division.removeMatch(matchBack);
        assertThat(division.getMatches()).doesNotContain(matchBack);
        assertThat(matchBack.getDivision()).isNull();

        division.matches(new HashSet<>(Set.of(matchBack)));
        assertThat(division.getMatches()).containsOnly(matchBack);
        assertThat(matchBack.getDivision()).isEqualTo(division);

        division.setMatches(new HashSet<>());
        assertThat(division.getMatches()).doesNotContain(matchBack);
        assertThat(matchBack.getDivision()).isNull();
    }
}
