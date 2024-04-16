package com.example.bfi.service.mapper;

import static com.example.bfi.domain.EvseAsserts.*;
import static com.example.bfi.domain.EvseTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EvseMapperTest {

    private EvseMapper evseMapper;

    @BeforeEach
    void setUp() {
        evseMapper = new EvseMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getEvseSample1();
        var actual = evseMapper.toEntity(evseMapper.toDto(expected));
        assertEvseAllPropertiesEquals(expected, actual);
    }
}
