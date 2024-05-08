package com.phoenixdarts.toss.backend.domain;

import static com.phoenixdarts.toss.backend.domain.MachineAreaTestSamples.*;
import static com.phoenixdarts.toss.backend.domain.MachineTestSamples.*;
import static com.phoenixdarts.toss.backend.domain.MatchTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.phoenixdarts.toss.backend.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MachineTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Machine.class);
        Machine machine1 = getMachineSample1();
        Machine machine2 = new Machine();
        assertThat(machine1).isNotEqualTo(machine2);

        machine2.setId(machine1.getId());
        assertThat(machine1).isEqualTo(machine2);

        machine2 = getMachineSample2();
        assertThat(machine1).isNotEqualTo(machine2);
    }

    @Test
    void matchTest() throws Exception {
        Machine machine = getMachineRandomSampleGenerator();
        Match matchBack = getMatchRandomSampleGenerator();

        machine.setMatch(matchBack);
        assertThat(machine.getMatch()).isEqualTo(matchBack);

        machine.match(null);
        assertThat(machine.getMatch()).isNull();
    }

    @Test
    void machineAreaTest() throws Exception {
        Machine machine = getMachineRandomSampleGenerator();
        MachineArea machineAreaBack = getMachineAreaRandomSampleGenerator();

        machine.setMachineArea(machineAreaBack);
        assertThat(machine.getMachineArea()).isEqualTo(machineAreaBack);

        machine.machineArea(null);
        assertThat(machine.getMachineArea()).isNull();
    }
}
