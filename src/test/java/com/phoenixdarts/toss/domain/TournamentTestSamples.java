package com.phoenixdarts.toss.backend.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class TournamentTestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Tournament getTournamentSample1() {
        return new Tournament().id("id1").seq(1).name("name1").day(1).numOfEntry(1);
    }

    public static Tournament getTournamentSample2() {
        return new Tournament().id("id2").seq(2).name("name2").day(2).numOfEntry(2);
    }

    public static Tournament getTournamentRandomSampleGenerator() {
        return new Tournament()
            .id(UUID.randomUUID().toString())
            .seq(intCount.incrementAndGet())
            .name(UUID.randomUUID().toString())
            .day(intCount.incrementAndGet())
            .numOfEntry(intCount.incrementAndGet());
    }
}
