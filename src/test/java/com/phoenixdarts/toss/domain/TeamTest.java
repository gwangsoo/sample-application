package com.phoenixdarts.toss.backend.domain;

import static com.phoenixdarts.toss.backend.domain.AffiliatedInfoTestSamples.*;
import static com.phoenixdarts.toss.backend.domain.DivisionTestSamples.*;
import static com.phoenixdarts.toss.backend.domain.EntryTestSamples.*;
import static com.phoenixdarts.toss.backend.domain.MatchTeamTestSamples.*;
import static com.phoenixdarts.toss.backend.domain.PaymentInfoTestSamples.*;
import static com.phoenixdarts.toss.backend.domain.TeamTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.phoenixdarts.toss.backend.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class TeamTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Team.class);
        Team team1 = getTeamSample1();
        Team team2 = new Team();
        assertThat(team1).isNotEqualTo(team2);

        team2.setId(team1.getId());
        assertThat(team1).isEqualTo(team2);

        team2 = getTeamSample2();
        assertThat(team1).isNotEqualTo(team2);
    }

    @Test
    void entryTest() throws Exception {
        Team team = getTeamRandomSampleGenerator();
        Entry entryBack = getEntryRandomSampleGenerator();

        team.addEntry(entryBack);
        assertThat(team.getEntries()).containsOnly(entryBack);
        assertThat(entryBack.getTeam()).isEqualTo(team);

        team.removeEntry(entryBack);
        assertThat(team.getEntries()).doesNotContain(entryBack);
        assertThat(entryBack.getTeam()).isNull();

        team.entries(new HashSet<>(Set.of(entryBack)));
        assertThat(team.getEntries()).containsOnly(entryBack);
        assertThat(entryBack.getTeam()).isEqualTo(team);

        team.setEntries(new HashSet<>());
        assertThat(team.getEntries()).doesNotContain(entryBack);
        assertThat(entryBack.getTeam()).isNull();
    }

    @Test
    void captainTest() throws Exception {
        Team team = getTeamRandomSampleGenerator();
        Entry entryBack = getEntryRandomSampleGenerator();

        team.setCaptain(entryBack);
        assertThat(team.getCaptain()).isEqualTo(entryBack);

        team.captain(null);
        assertThat(team.getCaptain()).isNull();
    }

    @Test
    void tteamTest() throws Exception {
        Team team = getTeamRandomSampleGenerator();
        Division divisionBack = getDivisionRandomSampleGenerator();

        team.setTteam(divisionBack);
        assertThat(team.getTteam()).isEqualTo(divisionBack);

        team.tteam(null);
        assertThat(team.getTteam()).isNull();
    }

    @Test
    void affiliatedInfoTest() throws Exception {
        Team team = getTeamRandomSampleGenerator();
        AffiliatedInfo affiliatedInfoBack = getAffiliatedInfoRandomSampleGenerator();

        team.setAffiliatedInfo(affiliatedInfoBack);
        assertThat(team.getAffiliatedInfo()).isEqualTo(affiliatedInfoBack);

        team.affiliatedInfo(null);
        assertThat(team.getAffiliatedInfo()).isNull();
    }

    @Test
    void paymentInfoTest() throws Exception {
        Team team = getTeamRandomSampleGenerator();
        PaymentInfo paymentInfoBack = getPaymentInfoRandomSampleGenerator();

        team.setPaymentInfo(paymentInfoBack);
        assertThat(team.getPaymentInfo()).isEqualTo(paymentInfoBack);

        team.paymentInfo(null);
        assertThat(team.getPaymentInfo()).isNull();
    }

    @Test
    void divisionTest() throws Exception {
        Team team = getTeamRandomSampleGenerator();
        Division divisionBack = getDivisionRandomSampleGenerator();

        team.setDivision(divisionBack);
        assertThat(team.getDivision()).isEqualTo(divisionBack);

        team.division(null);
        assertThat(team.getDivision()).isNull();
    }

    @Test
    void matchTeamTest() throws Exception {
        Team team = getTeamRandomSampleGenerator();
        MatchTeam matchTeamBack = getMatchTeamRandomSampleGenerator();

        team.addMatchTeam(matchTeamBack);
        assertThat(team.getMatchTeams()).containsOnly(matchTeamBack);
        assertThat(matchTeamBack.getTeam()).isEqualTo(team);

        team.removeMatchTeam(matchTeamBack);
        assertThat(team.getMatchTeams()).doesNotContain(matchTeamBack);
        assertThat(matchTeamBack.getTeam()).isNull();

        team.matchTeams(new HashSet<>(Set.of(matchTeamBack)));
        assertThat(team.getMatchTeams()).containsOnly(matchTeamBack);
        assertThat(matchTeamBack.getTeam()).isEqualTo(team);

        team.setMatchTeams(new HashSet<>());
        assertThat(team.getMatchTeams()).doesNotContain(matchTeamBack);
        assertThat(matchTeamBack.getTeam()).isNull();
    }
}
