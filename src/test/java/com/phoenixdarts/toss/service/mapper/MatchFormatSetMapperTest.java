package com.phoenixdarts.toss.backend.service.mapper;

import static com.phoenixdarts.toss.backend.domain.MatchFormatSetAsserts.*;
import static com.phoenixdarts.toss.backend.domain.MatchFormatSetTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MatchFormatSetMapperTest {

    private MatchFormatSetMapper matchFormatSetMapper;

    @BeforeEach
    void setUp() {
        matchFormatSetMapper = new MatchFormatSetMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getMatchFormatSetSample1();
        var actual = matchFormatSetMapper.toEntity(matchFormatSetMapper.toDto(expected));
        assertMatchFormatSetAllPropertiesEquals(expected, actual);
    }
}
