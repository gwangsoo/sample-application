package com.phoenixdarts.toss.backend.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class RewardTestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Reward getRewardSample1() {
        return new Reward().id("id1").rewardCategoryNum(1);
    }

    public static Reward getRewardSample2() {
        return new Reward().id("id2").rewardCategoryNum(2);
    }

    public static Reward getRewardRandomSampleGenerator() {
        return new Reward().id(UUID.randomUUID().toString()).rewardCategoryNum(intCount.incrementAndGet());
    }
}
