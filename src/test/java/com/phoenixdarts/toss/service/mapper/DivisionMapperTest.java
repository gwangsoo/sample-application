package com.phoenixdarts.toss.service.mapper;

import static com.phoenixdarts.toss.domain.DivisionAsserts.*;
import static com.phoenixdarts.toss.domain.DivisionTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DivisionMapperTest {

    private DivisionMapper divisionMapper;

    @BeforeEach
    void setUp() {
        divisionMapper = new DivisionMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getDivisionSample1();
        var actual = divisionMapper.toEntity(divisionMapper.toDto(expected));
        assertDivisionAllPropertiesEquals(expected, actual);
    }
}
