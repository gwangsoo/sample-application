package com.phoenixdarts.toss.backend.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class OperatorRoleTestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static OperatorRole getOperatorRoleSample1() {
        return new OperatorRole().id("id1").name("name1").displayOrder(1);
    }

    public static OperatorRole getOperatorRoleSample2() {
        return new OperatorRole().id("id2").name("name2").displayOrder(2);
    }

    public static OperatorRole getOperatorRoleRandomSampleGenerator() {
        return new OperatorRole()
            .id(UUID.randomUUID().toString())
            .name(UUID.randomUUID().toString())
            .displayOrder(intCount.incrementAndGet());
    }
}
