package com.phoenixdarts.toss.service.mapper;

import static com.phoenixdarts.toss.domain.MatchAsserts.*;
import static com.phoenixdarts.toss.domain.MatchTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MatchMapperTest {

    private MatchMapper matchMapper;

    @BeforeEach
    void setUp() {
        matchMapper = new MatchMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getMatchSample1();
        var actual = matchMapper.toEntity(matchMapper.toDto(expected));
        assertMatchAllPropertiesEquals(expected, actual);
    }
}
