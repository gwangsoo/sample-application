package com.phoenixdarts.toss.backend.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class MatchTestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Match getMatchSample1() {
        return new Match().id("id1").matchNo("matchNo1").groupNo(1).groupMatchSeq(1).roundNum(1).nextMatchNo("nextMatchNo1");
    }

    public static Match getMatchSample2() {
        return new Match().id("id2").matchNo("matchNo2").groupNo(2).groupMatchSeq(2).roundNum(2).nextMatchNo("nextMatchNo2");
    }

    public static Match getMatchRandomSampleGenerator() {
        return new Match()
            .id(UUID.randomUUID().toString())
            .matchNo(UUID.randomUUID().toString())
            .groupNo(intCount.incrementAndGet())
            .groupMatchSeq(intCount.incrementAndGet())
            .roundNum(intCount.incrementAndGet())
            .nextMatchNo(UUID.randomUUID().toString());
    }
}
