package com.phoenixdarts.toss.backend.service.mapper;

import static com.phoenixdarts.toss.backend.domain.MatchFormatGameAsserts.*;
import static com.phoenixdarts.toss.backend.domain.MatchFormatGameTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MatchFormatGameMapperTest {

    private MatchFormatGameMapper matchFormatGameMapper;

    @BeforeEach
    void setUp() {
        matchFormatGameMapper = new MatchFormatGameMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getMatchFormatGameSample1();
        var actual = matchFormatGameMapper.toEntity(matchFormatGameMapper.toDto(expected));
        assertMatchFormatGameAllPropertiesEquals(expected, actual);
    }
}
