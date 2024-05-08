package com.phoenixdarts.toss.backend.domain;

import static com.phoenixdarts.toss.backend.domain.OperatorRoleTestSamples.*;
import static com.phoenixdarts.toss.backend.domain.OperatorTestSamples.*;
import static com.phoenixdarts.toss.backend.domain.RoleTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.phoenixdarts.toss.backend.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class OperatorRoleTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OperatorRole.class);
        OperatorRole operatorRole1 = getOperatorRoleSample1();
        OperatorRole operatorRole2 = new OperatorRole();
        assertThat(operatorRole1).isNotEqualTo(operatorRole2);

        operatorRole2.setId(operatorRole1.getId());
        assertThat(operatorRole1).isEqualTo(operatorRole2);

        operatorRole2 = getOperatorRoleSample2();
        assertThat(operatorRole1).isNotEqualTo(operatorRole2);
    }

    @Test
    void roleTest() throws Exception {
        OperatorRole operatorRole = getOperatorRoleRandomSampleGenerator();
        Role roleBack = getRoleRandomSampleGenerator();

        operatorRole.addRole(roleBack);
        assertThat(operatorRole.getRoles()).containsOnly(roleBack);
        assertThat(roleBack.getOperatorRole()).isEqualTo(operatorRole);

        operatorRole.removeRole(roleBack);
        assertThat(operatorRole.getRoles()).doesNotContain(roleBack);
        assertThat(roleBack.getOperatorRole()).isNull();

        operatorRole.roles(new HashSet<>(Set.of(roleBack)));
        assertThat(operatorRole.getRoles()).containsOnly(roleBack);
        assertThat(roleBack.getOperatorRole()).isEqualTo(operatorRole);

        operatorRole.setRoles(new HashSet<>());
        assertThat(operatorRole.getRoles()).doesNotContain(roleBack);
        assertThat(roleBack.getOperatorRole()).isNull();
    }

    @Test
    void operatorTest() throws Exception {
        OperatorRole operatorRole = getOperatorRoleRandomSampleGenerator();
        Operator operatorBack = getOperatorRandomSampleGenerator();

        operatorRole.addOperator(operatorBack);
        assertThat(operatorRole.getOperators()).containsOnly(operatorBack);
        assertThat(operatorBack.getOperatorRole()).isEqualTo(operatorRole);

        operatorRole.removeOperator(operatorBack);
        assertThat(operatorRole.getOperators()).doesNotContain(operatorBack);
        assertThat(operatorBack.getOperatorRole()).isNull();

        operatorRole.operators(new HashSet<>(Set.of(operatorBack)));
        assertThat(operatorRole.getOperators()).containsOnly(operatorBack);
        assertThat(operatorBack.getOperatorRole()).isEqualTo(operatorRole);

        operatorRole.setOperators(new HashSet<>());
        assertThat(operatorRole.getOperators()).doesNotContain(operatorBack);
        assertThat(operatorBack.getOperatorRole()).isNull();
    }
}
