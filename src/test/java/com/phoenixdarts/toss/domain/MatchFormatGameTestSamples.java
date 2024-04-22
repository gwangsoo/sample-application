package com.phoenixdarts.toss.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class MatchFormatGameTestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static MatchFormatGame getMatchFormatGameSample1() {
        return new MatchFormatGame().id("id1").roundNum(1);
    }

    public static MatchFormatGame getMatchFormatGameSample2() {
        return new MatchFormatGame().id("id2").roundNum(2);
    }

    public static MatchFormatGame getMatchFormatGameRandomSampleGenerator() {
        return new MatchFormatGame().id(UUID.randomUUID().toString()).roundNum(intCount.incrementAndGet());
    }
}
