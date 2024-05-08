package com.phoenixdarts.toss.backend.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class MatchFormatLegTestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static MatchFormatLeg getMatchFormatLegSample1() {
        return new MatchFormatLeg().id("id1").seq(1);
    }

    public static MatchFormatLeg getMatchFormatLegSample2() {
        return new MatchFormatLeg().id("id2").seq(2);
    }

    public static MatchFormatLeg getMatchFormatLegRandomSampleGenerator() {
        return new MatchFormatLeg().id(UUID.randomUUID().toString()).seq(intCount.incrementAndGet());
    }
}
