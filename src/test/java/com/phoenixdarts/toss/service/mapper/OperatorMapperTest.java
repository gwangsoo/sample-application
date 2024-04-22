package com.phoenixdarts.toss.service.mapper;

import static com.phoenixdarts.toss.domain.OperatorAsserts.*;
import static com.phoenixdarts.toss.domain.OperatorTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperatorMapperTest {

    private OperatorMapper operatorMapper;

    @BeforeEach
    void setUp() {
        operatorMapper = new OperatorMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getOperatorSample1();
        var actual = operatorMapper.toEntity(operatorMapper.toDto(expected));
        assertOperatorAllPropertiesEquals(expected, actual);
    }
}
