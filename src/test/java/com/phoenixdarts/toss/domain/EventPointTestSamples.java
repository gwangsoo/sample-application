package com.phoenixdarts.toss.backend.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class EventPointTestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static EventPoint getEventPointSample1() {
        return new EventPoint().id("id1").seq(1).rating(1);
    }

    public static EventPoint getEventPointSample2() {
        return new EventPoint().id("id2").seq(2).rating(2);
    }

    public static EventPoint getEventPointRandomSampleGenerator() {
        return new EventPoint().id(UUID.randomUUID().toString()).seq(intCount.incrementAndGet()).rating(intCount.incrementAndGet());
    }
}
