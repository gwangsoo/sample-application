package com.phoenixdarts.toss.backend.service.mapper;

import static com.phoenixdarts.toss.backend.domain.AffiliatedInfoAsserts.*;
import static com.phoenixdarts.toss.backend.domain.AffiliatedInfoTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AffiliatedInfoMapperTest {

    private AffiliatedInfoMapper affiliatedInfoMapper;

    @BeforeEach
    void setUp() {
        affiliatedInfoMapper = new AffiliatedInfoMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getAffiliatedInfoSample1();
        var actual = affiliatedInfoMapper.toEntity(affiliatedInfoMapper.toDto(expected));
        assertAffiliatedInfoAllPropertiesEquals(expected, actual);
    }
}
