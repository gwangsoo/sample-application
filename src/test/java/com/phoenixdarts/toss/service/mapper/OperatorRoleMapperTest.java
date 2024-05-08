package com.phoenixdarts.toss.backend.service.mapper;

import static com.phoenixdarts.toss.backend.domain.OperatorRoleAsserts.*;
import static com.phoenixdarts.toss.backend.domain.OperatorRoleTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperatorRoleMapperTest {

    private OperatorRoleMapper operatorRoleMapper;

    @BeforeEach
    void setUp() {
        operatorRoleMapper = new OperatorRoleMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getOperatorRoleSample1();
        var actual = operatorRoleMapper.toEntity(operatorRoleMapper.toDto(expected));
        assertOperatorRoleAllPropertiesEquals(expected, actual);
    }
}
