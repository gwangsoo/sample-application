package com.phoenixdarts.toss.domain;

import static com.phoenixdarts.toss.domain.CompetitionTestSamples.*;
import static com.phoenixdarts.toss.domain.DivisionTestSamples.*;
import static com.phoenixdarts.toss.domain.EntryFeeTestSamples.*;
import static com.phoenixdarts.toss.domain.TournamentTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.phoenixdarts.toss.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class TournamentTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Tournament.class);
        Tournament tournament1 = getTournamentSample1();
        Tournament tournament2 = new Tournament();
        assertThat(tournament1).isNotEqualTo(tournament2);

        tournament2.setId(tournament1.getId());
        assertThat(tournament1).isEqualTo(tournament2);

        tournament2 = getTournamentSample2();
        assertThat(tournament1).isNotEqualTo(tournament2);
    }

    @Test
    void divisionTest() throws Exception {
        Tournament tournament = getTournamentRandomSampleGenerator();
        Division divisionBack = getDivisionRandomSampleGenerator();

        tournament.addDivision(divisionBack);
        assertThat(tournament.getDivisions()).containsOnly(divisionBack);
        assertThat(divisionBack.getTournament()).isEqualTo(tournament);

        tournament.removeDivision(divisionBack);
        assertThat(tournament.getDivisions()).doesNotContain(divisionBack);
        assertThat(divisionBack.getTournament()).isNull();

        tournament.divisions(new HashSet<>(Set.of(divisionBack)));
        assertThat(tournament.getDivisions()).containsOnly(divisionBack);
        assertThat(divisionBack.getTournament()).isEqualTo(tournament);

        tournament.setDivisions(new HashSet<>());
        assertThat(tournament.getDivisions()).doesNotContain(divisionBack);
        assertThat(divisionBack.getTournament()).isNull();
    }

    @Test
    void entryFeeTest() throws Exception {
        Tournament tournament = getTournamentRandomSampleGenerator();
        EntryFee entryFeeBack = getEntryFeeRandomSampleGenerator();

        tournament.setEntryFee(entryFeeBack);
        assertThat(tournament.getEntryFee()).isEqualTo(entryFeeBack);

        tournament.entryFee(null);
        assertThat(tournament.getEntryFee()).isNull();
    }

    @Test
    void competitionTest() throws Exception {
        Tournament tournament = getTournamentRandomSampleGenerator();
        Competition competitionBack = getCompetitionRandomSampleGenerator();

        tournament.setCompetition(competitionBack);
        assertThat(tournament.getCompetition()).isEqualTo(competitionBack);

        tournament.competition(null);
        assertThat(tournament.getCompetition()).isNull();
    }
}
