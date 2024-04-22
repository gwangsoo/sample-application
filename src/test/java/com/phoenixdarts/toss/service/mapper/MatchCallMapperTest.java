package com.phoenixdarts.toss.service.mapper;

import static com.phoenixdarts.toss.domain.MatchCallAsserts.*;
import static com.phoenixdarts.toss.domain.MatchCallTestSamples.*;

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
