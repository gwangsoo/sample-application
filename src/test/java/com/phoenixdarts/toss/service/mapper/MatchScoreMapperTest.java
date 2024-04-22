package com.phoenixdarts.toss.service.mapper;

import static com.phoenixdarts.toss.domain.MatchScoreAsserts.*;
import static com.phoenixdarts.toss.domain.MatchScoreTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MatchScoreMapperTest {

    private MatchScoreMapper matchScoreMapper;

    @BeforeEach
    void setUp() {
        matchScoreMapper = new MatchScoreMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getMatchScoreSample1();
        var actual = matchScoreMapper.toEntity(matchScoreMapper.toDto(expected));
        assertMatchScoreAllPropertiesEquals(expected, actual);
    }
}
