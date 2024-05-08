package com.phoenixdarts.toss.backend.domain;

import java.util.UUID;

public class RewardDetailTestSamples {

    public static RewardDetail getRewardDetailSample1() {
        return new RewardDetail().id("id1").name("name1").tournamentId("tournamentId1").divisionId("divisionId1");
    }

    public static RewardDetail getRewardDetailSample2() {
        return new RewardDetail().id("id2").name("name2").tournamentId("tournamentId2").divisionId("divisionId2");
    }

    public static RewardDetail getRewardDetailRandomSampleGenerator() {
        return new RewardDetail()
            .id(UUID.randomUUID().toString())
            .name(UUID.randomUUID().toString())
            .tournamentId(UUID.randomUUID().toString())
            .divisionId(UUID.randomUUID().toString());
    }
}
