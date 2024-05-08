package com.phoenixdarts.toss.backend.service.mapper;

import static com.phoenixdarts.toss.backend.domain.EntryFeeAsserts.*;
import static com.phoenixdarts.toss.backend.domain.EntryFeeTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EntryFeeMapperTest {

    private EntryFeeMapper entryFeeMapper;

    @BeforeEach
    void setUp() {
        entryFeeMapper = new EntryFeeMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getEntryFeeSample1();
        var actual = entryFeeMapper.toEntity(entryFeeMapper.toDto(expected));
        assertEntryFeeAllPropertiesEquals(expected, actual);
    }
}
