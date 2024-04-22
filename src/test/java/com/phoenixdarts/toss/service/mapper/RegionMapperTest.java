package com.phoenixdarts.toss.service.mapper;

import static com.phoenixdarts.toss.domain.RegionAsserts.*;
import static com.phoenixdarts.toss.domain.RegionTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegionMapperTest {

    private RegionMapper regionMapper;

    @BeforeEach
    void setUp() {
        regionMapper = new RegionMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getRegionSample1();
        var actual = regionMapper.toEntity(regionMapper.toDto(expected));
        assertRegionAllPropertiesEquals(expected, actual);
    }
}
