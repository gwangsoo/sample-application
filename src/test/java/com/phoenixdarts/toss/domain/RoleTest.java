package com.phoenixdarts.toss.backend.domain;

import static com.phoenixdarts.toss.backend.domain.OperatorRoleTestSamples.*;
import static com.phoenixdarts.toss.backend.domain.RoleTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.phoenixdarts.toss.backend.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RoleTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Role.class);
        Role role1 = getRoleSample1();
        Role role2 = new Role();
        assertThat(role1).isNotEqualTo(role2);

        role2.setId(role1.getId());
        assertThat(role1).isEqualTo(role2);

        role2 = getRoleSample2();
        assertThat(role1).isNotEqualTo(role2);
    }

    @Test
    void operatorRoleTest() throws Exception {
        Role role = getRoleRandomSampleGenerator();
        OperatorRole operatorRoleBack = getOperatorRoleRandomSampleGenerator();

        role.setOperatorRole(operatorRoleBack);
        assertThat(role.getOperatorRole()).isEqualTo(operatorRoleBack);

        role.operatorRole(null);
        assertThat(role.getOperatorRole()).isNull();
    }
}
