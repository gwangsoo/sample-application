package com.phoenixdarts.toss.backend.service.mapper;

import static com.phoenixdarts.toss.backend.domain.RewardItemAsserts.*;
import static com.phoenixdarts.toss.backend.domain.RewardItemTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RewardItemMapperTest {

    private RewardItemMapper rewardItemMapper;

    @BeforeEach
    void setUp() {
        rewardItemMapper = new RewardItemMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getRewardItemSample1();
        var actual = rewardItemMapper.toEntity(rewardItemMapper.toDto(expected));
        assertRewardItemAllPropertiesEquals(expected, actual);
    }
}
