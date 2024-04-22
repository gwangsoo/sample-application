package com.phoenixdarts.toss.service.mapper;

import static com.phoenixdarts.toss.domain.RewardDetailAsserts.*;
import static com.phoenixdarts.toss.domain.RewardDetailTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RewardDetailMapperTest {

    private RewardDetailMapper rewardDetailMapper;

    @BeforeEach
    void setUp() {
        rewardDetailMapper = new RewardDetailMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getRewardDetailSample1();
        var actual = rewardDetailMapper.toEntity(rewardDetailMapper.toDto(expected));
        assertRewardDetailAllPropertiesEquals(expected, actual);
    }
}
