package com.phoenixdarts.toss.backend.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class DivisionTestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Division getDivisionSample1() {
        return new Division().id("id1").seq(1).name("name1").eliminationTeamCount(1);
    }

    public static Division getDivisionSample2() {
        return new Division().id("id2").seq(2).name("name2").eliminationTeamCount(2);
    }

    public static Division getDivisionRandomSampleGenerator() {
        return new Division()
            .id(UUID.randomUUID().toString())
            .seq(intCount.incrementAndGet())
            .name(UUID.randomUUID().toString())
            .eliminationTeamCount(intCount.incrementAndGet());
    }
}
