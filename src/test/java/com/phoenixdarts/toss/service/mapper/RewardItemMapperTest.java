package com.phoenixdarts.toss.service.mapper;

import static com.phoenixdarts.toss.domain.RewardItemAsserts.*;
import static com.phoenixdarts.toss.domain.RewardItemTestSamples.*;

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
