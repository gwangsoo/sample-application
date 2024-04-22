package com.phoenixdarts.toss.service.mapper;

import static com.phoenixdarts.toss.domain.EntryAsserts.*;
import static com.phoenixdarts.toss.domain.EntryTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EntryMapperTest {

    private EntryMapper entryMapper;

    @BeforeEach
    void setUp() {
        entryMapper = new EntryMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getEntrySample1();
        var actual = entryMapper.toEntity(entryMapper.toDto(expected));
        assertEntryAllPropertiesEquals(expected, actual);
    }
}
