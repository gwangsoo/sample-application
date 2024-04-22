package com.phoenixdarts.toss.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class MachineAreaTestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static MachineArea getMachineAreaSample1() {
        return new MachineArea().id("id1").mame("mame1").seq(1).numOfMachine(1);
    }

    public static MachineArea getMachineAreaSample2() {
        return new MachineArea().id("id2").mame("mame2").seq(2).numOfMachine(2);
    }

    public static MachineArea getMachineAreaRandomSampleGenerator() {
        return new MachineArea()
            .id(UUID.randomUUID().toString())
            .mame(UUID.randomUUID().toString())
            .seq(intCount.incrementAndGet())
            .numOfMachine(intCount.incrementAndGet());
    }
}
