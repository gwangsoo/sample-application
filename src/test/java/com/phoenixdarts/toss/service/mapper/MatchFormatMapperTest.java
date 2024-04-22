package com.phoenixdarts.toss.service.mapper;

import static com.phoenixdarts.toss.domain.MatchFormatAsserts.*;
import static com.phoenixdarts.toss.domain.MatchFormatTestSamples.*;

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
