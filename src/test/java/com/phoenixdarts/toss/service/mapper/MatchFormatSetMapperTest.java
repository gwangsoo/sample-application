package com.phoenixdarts.toss.service.mapper;

import static com.phoenixdarts.toss.domain.MatchFormatSetAsserts.*;
import static com.phoenixdarts.toss.domain.MatchFormatSetTestSamples.*;

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
