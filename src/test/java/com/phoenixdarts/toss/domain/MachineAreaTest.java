package com.phoenixdarts.toss.domain;

import static com.phoenixdarts.toss.domain.CompetitionTestSamples.*;
import static com.phoenixdarts.toss.domain.MachineAreaTestSamples.*;
import static com.phoenixdarts.toss.domain.MachineTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.phoenixdarts.toss.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class MachineAreaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MachineArea.class);
        MachineArea machineArea1 = getMachineAreaSample1();
        MachineArea machineArea2 = new MachineArea();
        assertThat(machineArea1).isNotEqualTo(machineArea2);

        machineArea2.setId(machineArea1.getId());
        assertThat(machineArea1).isEqualTo(machineArea2);

        machineArea2 = getMachineAreaSample2();
        assertThat(machineArea1).isNotEqualTo(machineArea2);
    }

    @Test
    void machineTest() throws Exception {
        MachineArea machineArea = getMachineAreaRandomSampleGenerator();
        Machine machineBack = getMachineRandomSampleGenerator();

        machineArea.addMachine(machineBack);
        assertThat(machineArea.getMachines()).containsOnly(machineBack);
        assertThat(machineBack.getMachineArea()).isEqualTo(machineArea);

        machineArea.removeMachine(machineBack);
        assertThat(machineArea.getMachines()).doesNotContain(machineBack);
        assertThat(machineBack.getMachineArea()).isNull();

        machineArea.machines(new HashSet<>(Set.of(machineBack)));
        assertThat(machineArea.getMachines()).containsOnly(machineBack);
        assertThat(machineBack.getMachineArea()).isEqualTo(machineArea);

        machineArea.setMachines(new HashSet<>());
        assertThat(machineArea.getMachines()).doesNotContain(machineBack);
        assertThat(machineBack.getMachineArea()).isNull();
    }

    @Test
    void competitionTest() throws Exception {
        MachineArea machineArea = getMachineAreaRandomSampleGenerator();
        Competition competitionBack = getCompetitionRandomSampleGenerator();

        machineArea.setCompetition(competitionBack);
        assertThat(machineArea.getCompetition()).isEqualTo(competitionBack);

        machineArea.competition(null);
        assertThat(machineArea.getCompetition()).isNull();
    }
}
