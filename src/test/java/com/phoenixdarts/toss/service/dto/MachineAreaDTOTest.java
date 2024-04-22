package com.phoenixdarts.toss.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.phoenixdarts.toss.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MachineAreaDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MachineAreaDTO.class);
        MachineAreaDTO machineAreaDTO1 = new MachineAreaDTO();
        machineAreaDTO1.setId("id1");
        MachineAreaDTO machineAreaDTO2 = new MachineAreaDTO();
        assertThat(machineAreaDTO1).isNotEqualTo(machineAreaDTO2);
        machineAreaDTO2.setId(machineAreaDTO1.getId());
        assertThat(machineAreaDTO1).isEqualTo(machineAreaDTO2);
        machineAreaDTO2.setId("id2");
        assertThat(machineAreaDTO1).isNotEqualTo(machineAreaDTO2);
        machineAreaDTO1.setId(null);
        assertThat(machineAreaDTO1).isNotEqualTo(machineAreaDTO2);
    }
}
