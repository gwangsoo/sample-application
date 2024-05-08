package com.phoenixdarts.toss.backend.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class TeamTestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Team getTeamSample1() {
        return new Team().id("id1").teamNo("teamNo1").memo("memo1").groupNo(1).groupSeq(1).seedNo(1);
    }

    public static Team getTeamSample2() {
        return new Team().id("id2").teamNo("teamNo2").memo("memo2").groupNo(2).groupSeq(2).seedNo(2);
    }

    public static Team getTeamRandomSampleGenerator() {
        return new Team()
            .id(UUID.randomUUID().toString())
            .teamNo(UUID.randomUUID().toString())
            .memo(UUID.randomUUID().toString())
            .groupNo(intCount.incrementAndGet())
            .groupSeq(intCount.incrementAndGet())
            .seedNo(intCount.incrementAndGet());
    }
}
