package com.phoenixdarts.toss.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class EntryFeeTestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static EntryFee getEntryFeeSample1() {
        return new EntryFee().id("id1").scheduleNumber(1).fee(1);
    }

    public static EntryFee getEntryFeeSample2() {
        return new EntryFee().id("id2").scheduleNumber(2).fee(2);
    }

    public static EntryFee getEntryFeeRandomSampleGenerator() {
        return new EntryFee().id(UUID.randomUUID().toString()).scheduleNumber(intCount.incrementAndGet()).fee(intCount.incrementAndGet());
    }
}
