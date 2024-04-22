package com.phoenixdarts.toss.service.mapper;

import static com.phoenixdarts.toss.domain.MatchAttendanceAsserts.*;
import static com.phoenixdarts.toss.domain.MatchAttendanceTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MatchAttendanceMapperTest {

    private MatchAttendanceMapper matchAttendanceMapper;

    @BeforeEach
    void setUp() {
        matchAttendanceMapper = new MatchAttendanceMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getMatchAttendanceSample1();
        var actual = matchAttendanceMapper.toEntity(matchAttendanceMapper.toDto(expected));
        assertMatchAttendanceAllPropertiesEquals(expected, actual);
    }
}
