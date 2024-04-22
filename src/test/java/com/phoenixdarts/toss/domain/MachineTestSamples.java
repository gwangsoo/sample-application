package com.phoenixdarts.toss.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class MachineTestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Machine getMachineSample1() {
        return new Machine().id("id1").machineNo(1);
    }

    public static Machine getMachineSample2() {
        return new Machine().id("id2").machineNo(2);
    }

    public static Machine getMachineRandomSampleGenerator() {
        return new Machine().id(UUID.randomUUID().toString()).machineNo(intCount.incrementAndGet());
    }
}
