package com.phoenixdarts.toss.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class RoleTestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Role getRoleSample1() {
        return new Role().id("id1").name("name1").displayOrder(1);
    }

    public static Role getRoleSample2() {
        return new Role().id("id2").name("name2").displayOrder(2);
    }

    public static Role getRoleRandomSampleGenerator() {
        return new Role().id(UUID.randomUUID().toString()).name(UUID.randomUUID().toString()).displayOrder(intCount.incrementAndGet());
    }
}
