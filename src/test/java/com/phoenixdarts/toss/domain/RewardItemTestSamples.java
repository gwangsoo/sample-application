package com.phoenixdarts.toss.domain;

import java.util.UUID;

public class RewardItemTestSamples {

    public static RewardItem getRewardItemSample1() {
        return new RewardItem().id("id1").name("name1");
    }

    public static RewardItem getRewardItemSample2() {
        return new RewardItem().id("id2").name("name2");
    }

    public static RewardItem getRewardItemRandomSampleGenerator() {
        return new RewardItem().id(UUID.randomUUID().toString()).name(UUID.randomUUID().toString());
    }
}
