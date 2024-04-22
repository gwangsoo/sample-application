package com.phoenixdarts.toss.domain;

import static com.phoenixdarts.toss.domain.CompetitionTestSamples.*;
import static com.phoenixdarts.toss.domain.CountryTestSamples.*;
import static com.phoenixdarts.toss.domain.EntryFeeTestSamples.*;
import static com.phoenixdarts.toss.domain.FileInfoTestSamples.*;
import static com.phoenixdarts.toss.domain.MachineAreaTestSamples.*;
import static com.phoenixdarts.toss.domain.OperatorTestSamples.*;
import static com.phoenixdarts.toss.domain.RewardTestSamples.*;
import static com.phoenixdarts.toss.domain.TournamentTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.phoenixdarts.toss.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class CompetitionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Competition.class);
        Competition competition1 = getCompetitionSample1();
        Competition competition2 = new Competition();
        assertThat(competition1).isNotEqualTo(competition2);

        competition2.setId(competition1.getId());
        assertThat(competition1).isEqualTo(competition2);

        competition2 = getCompetitionSample2();
        assertThat(competition1).isNotEqualTo(competition2);
    }

    @Test
    void tournamentTest() throws Exception {
        Competition competition = getCompetitionRandomSampleGenerator();
        Tournament tournamentBack = getTournamentRandomSampleGenerator();

        competition.addTournament(tournamentBack);
        assertThat(competition.getTournaments()).containsOnly(tournamentBack);
        assertThat(tournamentBack.getCompetition()).isEqualTo(competition);

        competition.removeTournament(tournamentBack);
        assertThat(competition.getTournaments()).doesNotContain(tournamentBack);
        assertThat(tournamentBack.getCompetition()).isNull();

        competition.tournaments(new HashSet<>(Set.of(tournamentBack)));
        assertThat(competition.getTournaments()).containsOnly(tournamentBack);
        assertThat(tournamentBack.getCompetition()).isEqualTo(competition);

        competition.setTournaments(new HashSet<>());
        assertThat(competition.getTournaments()).doesNotContain(tournamentBack);
        assertThat(tournamentBack.getCompetition()).isNull();
    }

    @Test
    void machineAreaTest() throws Exception {
        Competition competition = getCompetitionRandomSampleGenerator();
        MachineArea machineAreaBack = getMachineAreaRandomSampleGenerator();

        competition.addMachineArea(machineAreaBack);
        assertThat(competition.getMachineAreas()).containsOnly(machineAreaBack);
        assertThat(machineAreaBack.getCompetition()).isEqualTo(competition);

        competition.removeMachineArea(machineAreaBack);
        assertThat(competition.getMachineAreas()).doesNotContain(machineAreaBack);
        assertThat(machineAreaBack.getCompetition()).isNull();

        competition.machineAreas(new HashSet<>(Set.of(machineAreaBack)));
        assertThat(competition.getMachineAreas()).containsOnly(machineAreaBack);
        assertThat(machineAreaBack.getCompetition()).isEqualTo(competition);

        competition.setMachineAreas(new HashSet<>());
        assertThat(competition.getMachineAreas()).doesNotContain(machineAreaBack);
        assertThat(machineAreaBack.getCompetition()).isNull();
    }

    @Test
    void competitionImageTest() throws Exception {
        Competition competition = getCompetitionRandomSampleGenerator();
        FileInfo fileInfoBack = getFileInfoRandomSampleGenerator();

        competition.setCompetitionImage(fileInfoBack);
        assertThat(competition.getCompetitionImage()).isEqualTo(fileInfoBack);

        competition.competitionImage(null);
        assertThat(competition.getCompetitionImage()).isNull();
    }

    @Test
    void rewardTest() throws Exception {
        Competition competition = getCompetitionRandomSampleGenerator();
        Reward rewardBack = getRewardRandomSampleGenerator();

        competition.setReward(rewardBack);
        assertThat(competition.getReward()).isEqualTo(rewardBack);

        competition.reward(null);
        assertThat(competition.getReward()).isNull();
    }

    @Test
    void countryTest() throws Exception {
        Competition competition = getCompetitionRandomSampleGenerator();
        Country countryBack = getCountryRandomSampleGenerator();

        competition.setCountry(countryBack);
        assertThat(competition.getCountry()).isEqualTo(countryBack);

        competition.country(null);
        assertThat(competition.getCountry()).isNull();
    }

    @Test
    void operatorTest() throws Exception {
        Competition competition = getCompetitionRandomSampleGenerator();
        Operator operatorBack = getOperatorRandomSampleGenerator();

        competition.setOperator(operatorBack);
        assertThat(competition.getOperator()).isEqualTo(operatorBack);

        competition.operator(null);
        assertThat(competition.getOperator()).isNull();
    }

    @Test
    void entryFeeTest() throws Exception {
        Competition competition = getCompetitionRandomSampleGenerator();
        EntryFee entryFeeBack = getEntryFeeRandomSampleGenerator();

        competition.setEntryFee(entryFeeBack);
        assertThat(competition.getEntryFee()).isEqualTo(entryFeeBack);

        competition.entryFee(null);
        assertThat(competition.getEntryFee()).isNull();
    }
}
