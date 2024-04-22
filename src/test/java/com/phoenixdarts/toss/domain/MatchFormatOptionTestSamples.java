package com.phoenixdarts.toss.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class MatchFormatOptionTestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static MatchFormatOption getMatchFormatOptionSample1() {
        return new MatchFormatOption().id("id1").cricketScore(1);
    }

    public static MatchFormatOption getMatchFormatOptionSample2() {
        return new MatchFormatOption().id("id2").cricketScore(2);
    }

    public static MatchFormatOption getMatchFormatOptionRandomSampleGenerator() {
        return new MatchFormatOption().id(UUID.randomUUID().toString()).cricketScore(intCount.incrementAndGet());
    }
}
