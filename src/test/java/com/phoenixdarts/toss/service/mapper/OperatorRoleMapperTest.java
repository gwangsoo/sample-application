package com.phoenixdarts.toss.service.mapper;

import static com.phoenixdarts.toss.domain.OperatorRoleAsserts.*;
import static com.phoenixdarts.toss.domain.OperatorRoleTestSamples.*;

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
