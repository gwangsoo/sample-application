package com.phoenixdarts.toss.backend.service.mapper;

import static com.phoenixdarts.toss.backend.domain.MatchCallAsserts.*;
import static com.phoenixdarts.toss.backend.domain.MatchCallTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MatchCallMapperTest {

    private MatchCallMapper matchCallMapper;

    @BeforeEach
    void setUp() {
        matchCallMapper = new MatchCallMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getMatchCallSample1();
        var actual = matchCallMapper.toEntity(matchCallMapper.toDto(expected));
        assertMatchCallAllPropertiesEquals(expected, actual);
    }
}
