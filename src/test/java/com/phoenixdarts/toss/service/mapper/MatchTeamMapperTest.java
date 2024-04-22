package com.phoenixdarts.toss.service.mapper;

import static com.phoenixdarts.toss.domain.MatchTeamAsserts.*;
import static com.phoenixdarts.toss.domain.MatchTeamTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MatchTeamMapperTest {

    private MatchTeamMapper matchTeamMapper;

    @BeforeEach
    void setUp() {
        matchTeamMapper = new MatchTeamMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getMatchTeamSample1();
        var actual = matchTeamMapper.toEntity(matchTeamMapper.toDto(expected));
        assertMatchTeamAllPropertiesEquals(expected, actual);
    }
}
