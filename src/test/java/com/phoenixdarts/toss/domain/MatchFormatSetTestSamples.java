package com.phoenixdarts.toss.backend.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class MatchFormatSetTestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static MatchFormatSet getMatchFormatSetSample1() {
        return new MatchFormatSet().id("id1").point(1);
    }

    public static MatchFormatSet getMatchFormatSetSample2() {
        return new MatchFormatSet().id("id2").point(2);
    }

    public static MatchFormatSet getMatchFormatSetRandomSampleGenerator() {
        return new MatchFormatSet().id(UUID.randomUUID().toString()).point(intCount.incrementAndGet());
    }
}
