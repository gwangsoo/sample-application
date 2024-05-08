package com.phoenixdarts.toss.backend.service.mapper;

import static com.phoenixdarts.toss.backend.domain.RewardDetailAsserts.*;
import static com.phoenixdarts.toss.backend.domain.RewardDetailTestSamples.*;

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
