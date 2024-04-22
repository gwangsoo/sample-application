package com.phoenixdarts.toss.service.mapper;

import static com.phoenixdarts.toss.domain.RewardAsserts.*;
import static com.phoenixdarts.toss.domain.RewardTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RewardMapperTest {

    private RewardMapper rewardMapper;

    @BeforeEach
    void setUp() {
        rewardMapper = new RewardMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getRewardSample1();
        var actual = rewardMapper.toEntity(rewardMapper.toDto(expected));
        assertRewardAllPropertiesEquals(expected, actual);
    }
}
