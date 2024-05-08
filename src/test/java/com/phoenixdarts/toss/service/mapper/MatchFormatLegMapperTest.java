package com.phoenixdarts.toss.backend.service.mapper;

import static com.phoenixdarts.toss.backend.domain.MatchFormatLegAsserts.*;
import static com.phoenixdarts.toss.backend.domain.MatchFormatLegTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MatchFormatLegMapperTest {

    private MatchFormatLegMapper matchFormatLegMapper;

    @BeforeEach
    void setUp() {
        matchFormatLegMapper = new MatchFormatLegMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getMatchFormatLegSample1();
        var actual = matchFormatLegMapper.toEntity(matchFormatLegMapper.toDto(expected));
        assertMatchFormatLegAllPropertiesEquals(expected, actual);
    }
}
