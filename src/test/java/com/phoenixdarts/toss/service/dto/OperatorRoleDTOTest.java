package com.phoenixdarts.toss.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.phoenixdarts.toss.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OperatorRoleDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OperatorRoleDTO.class);
        OperatorRoleDTO operatorRoleDTO1 = new OperatorRoleDTO();
        operatorRoleDTO1.setId("id1");
        OperatorRoleDTO operatorRoleDTO2 = new OperatorRoleDTO();
        assertThat(operatorRoleDTO1).isNotEqualTo(operatorRoleDTO2);
        operatorRoleDTO2.setId(operatorRoleDTO1.getId());
        assertThat(operatorRoleDTO1).isEqualTo(operatorRoleDTO2);
        operatorRoleDTO2.setId("id2");
        assertThat(operatorRoleDTO1).isNotEqualTo(operatorRoleDTO2);
        operatorRoleDTO1.setId(null);
        assertThat(operatorRoleDTO1).isNotEqualTo(operatorRoleDTO2);
    }
}
