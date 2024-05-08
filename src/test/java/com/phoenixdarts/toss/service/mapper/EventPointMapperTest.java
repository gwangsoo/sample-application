package com.phoenixdarts.toss.backend.service.mapper;

import static com.phoenixdarts.toss.backend.domain.EventPointAsserts.*;
import static com.phoenixdarts.toss.backend.domain.EventPointTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EventPointMapperTest {

    private EventPointMapper eventPointMapper;

    @BeforeEach
    void setUp() {
        eventPointMapper = new EventPointMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getEventPointSample1();
        var actual = eventPointMapper.toEntity(eventPointMapper.toDto(expected));
        assertEventPointAllPropertiesEquals(expected, actual);
    }
}
