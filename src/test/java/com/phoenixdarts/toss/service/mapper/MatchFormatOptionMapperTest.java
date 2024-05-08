package com.phoenixdarts.toss.backend.service.mapper;

import static com.phoenixdarts.toss.backend.domain.MatchFormatOptionAsserts.*;
import static com.phoenixdarts.toss.backend.domain.MatchFormatOptionTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MatchFormatOptionMapperTest {

    private MatchFormatOptionMapper matchFormatOptionMapper;

    @BeforeEach
    void setUp() {
        matchFormatOptionMapper = new MatchFormatOptionMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getMatchFormatOptionSample1();
        var actual = matchFormatOptionMapper.toEntity(matchFormatOptionMapper.toDto(expected));
        assertMatchFormatOptionAllPropertiesEquals(expected, actual);
    }
}
