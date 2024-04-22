package com.phoenixdarts.toss.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.phoenixdarts.toss.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MachineDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MachineDTO.class);
        MachineDTO machineDTO1 = new MachineDTO();
        machineDTO1.setId("id1");
        MachineDTO machineDTO2 = new MachineDTO();
        assertThat(machineDTO1).isNotEqualTo(machineDTO2);
        machineDTO2.setId(machineDTO1.getId());
        assertThat(machineDTO1).isEqualTo(machineDTO2);
        machineDTO2.setId("id2");
        assertThat(machineDTO1).isNotEqualTo(machineDTO2);
        machineDTO1.setId(null);
        assertThat(machineDTO1).isNotEqualTo(machineDTO2);
    }
}
