package com.phoenixdarts.toss.service.mapper;

import static com.phoenixdarts.toss.domain.CompetitionAsserts.*;
import static com.phoenixdarts.toss.domain.CompetitionTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CompetitionMapperTest {

    private CompetitionMapper competitionMapper;

    @BeforeEach
    void setUp() {
        competitionMapper = new CompetitionMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getCompetitionSample1();
        var actual = competitionMapper.toEntity(competitionMapper.toDto(expected));
        assertCompetitionAllPropertiesEquals(expected, actual);
    }
}
