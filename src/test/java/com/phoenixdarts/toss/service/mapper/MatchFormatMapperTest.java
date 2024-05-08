package com.phoenixdarts.toss.backend.service.mapper;

import static com.phoenixdarts.toss.backend.domain.MatchFormatAsserts.*;
import static com.phoenixdarts.toss.backend.domain.MatchFormatTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MatchFormatMapperTest {

    private MatchFormatMapper matchFormatMapper;

    @BeforeEach
    void setUp() {
        matchFormatMapper = new MatchFormatMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getMatchFormatSample1();
        var actual = matchFormatMapper.toEntity(matchFormatMapper.toDto(expected));
        assertMatchFormatAllPropertiesEquals(expected, actual);
    }
}
