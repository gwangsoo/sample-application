package com.phoenixdarts.toss.service.mapper;

import static com.phoenixdarts.toss.domain.TeamAsserts.*;
import static com.phoenixdarts.toss.domain.TeamTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TeamMapperTest {

    private TeamMapper teamMapper;

    @BeforeEach
    void setUp() {
        teamMapper = new TeamMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getTeamSample1();
        var actual = teamMapper.toEntity(teamMapper.toDto(expected));
        assertTeamAllPropertiesEquals(expected, actual);
    }
}
