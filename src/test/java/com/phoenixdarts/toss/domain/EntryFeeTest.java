package com.phoenixdarts.toss.domain;

import static com.phoenixdarts.toss.domain.CompetitionTestSamples.*;
import static com.phoenixdarts.toss.domain.CurrencyTestSamples.*;
import static com.phoenixdarts.toss.domain.EntryFeeTestSamples.*;
import static com.phoenixdarts.toss.domain.TournamentTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.phoenixdarts.toss.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class EntryFeeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EntryFee.class);
        EntryFee entryFee1 = getEntryFeeSample1();
        EntryFee entryFee2 = new EntryFee();
        assertThat(entryFee1).isNotEqualTo(entryFee2);

        entryFee2.setId(entryFee1.getId());
        assertThat(entryFee1).isEqualTo(entryFee2);

        entryFee2 = getEntryFeeSample2();
        assertThat(entryFee1).isNotEqualTo(entryFee2);
    }

    @Test
    void currencyTest() throws Exception {
        EntryFee entryFee = getEntryFeeRandomSampleGenerator();
        Currency currencyBack = getCurrencyRandomSampleGenerator();

        entryFee.setCurrency(currencyBack);
        assertThat(entryFee.getCurrency()).isEqualTo(currencyBack);

        entryFee.currency(null);
        assertThat(entryFee.getCurrency()).isNull();
    }

    @Test
    void tournamentTest() throws Exception {
        EntryFee entryFee = getEntryFeeRandomSampleGenerator();
        Tournament tournamentBack = getTournamentRandomSampleGenerator();

        entryFee.addTournament(tournamentBack);
        assertThat(entryFee.getTournaments()).containsOnly(tournamentBack);
        assertThat(tournamentBack.getEntryFee()).isEqualTo(entryFee);

        entryFee.removeTournament(tournamentBack);
        assertThat(entryFee.getTournaments()).doesNotContain(tournamentBack);
        assertThat(tournamentBack.getEntryFee()).isNull();

        entryFee.tournaments(new HashSet<>(Set.of(tournamentBack)));
        assertThat(entryFee.getTournaments()).containsOnly(tournamentBack);
        assertThat(tournamentBack.getEntryFee()).isEqualTo(entryFee);

        entryFee.setTournaments(new HashSet<>());
        assertThat(entryFee.getTournaments()).doesNotContain(tournamentBack);
        assertThat(tournamentBack.getEntryFee()).isNull();
    }

    @Test
    void competitionTest() throws Exception {
        EntryFee entryFee = getEntryFeeRandomSampleGenerator();
        Competition competitionBack = getCompetitionRandomSampleGenerator();

        entryFee.addCompetition(competitionBack);
        assertThat(entryFee.getCompetitions()).containsOnly(competitionBack);
        assertThat(competitionBack.getEntryFee()).isEqualTo(entryFee);

        entryFee.removeCompetition(competitionBack);
        assertThat(entryFee.getCompetitions()).doesNotContain(competitionBack);
        assertThat(competitionBack.getEntryFee()).isNull();

        entryFee.competitions(new HashSet<>(Set.of(competitionBack)));
        assertThat(entryFee.getCompetitions()).containsOnly(competitionBack);
        assertThat(competitionBack.getEntryFee()).isEqualTo(entryFee);

        entryFee.setCompetitions(new HashSet<>());
        assertThat(entryFee.getCompetitions()).doesNotContain(competitionBack);
        assertThat(competitionBack.getEntryFee()).isNull();
    }
}
