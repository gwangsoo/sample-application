package com.phoenixdarts.toss.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class MatchScoreTestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static MatchScore getMatchScoreSample1() {
        return new MatchScore().id("id1").setNo(1).lgeNo(1).legGameName("legGameName1");
    }

    public static MatchScore getMatchScoreSample2() {
        return new MatchScore().id("id2").setNo(2).lgeNo(2).legGameName("legGameName2");
    }

    public static MatchScore getMatchScoreRandomSampleGenerator() {
        return new MatchScore()
            .id(UUID.randomUUID().toString())
            .setNo(intCount.incrementAndGet())
            .lgeNo(intCount.incrementAndGet())
            .legGameName(UUID.randomUUID().toString());
    }
}
