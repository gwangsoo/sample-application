package com.phoenixdarts.toss.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class GameTestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Game getGameSample1() {
        return new Game()
            .id("id1")
            .name("name1")
            .description("description1")
            .roundNumDefault(1)
            .roundNumMin(1)
            .roundNumMax(1)
            .roundNumUnlimit(1);
    }

    public static Game getGameSample2() {
        return new Game()
            .id("id2")
            .name("name2")
            .description("description2")
            .roundNumDefault(2)
            .roundNumMin(2)
            .roundNumMax(2)
            .roundNumUnlimit(2);
    }

    public static Game getGameRandomSampleGenerator() {
        return new Game()
            .id(UUID.randomUUID().toString())
            .name(UUID.randomUUID().toString())
            .description(UUID.randomUUID().toString())
            .roundNumDefault(intCount.incrementAndGet())
            .roundNumMin(intCount.incrementAndGet())
            .roundNumMax(intCount.incrementAndGet())
            .roundNumUnlimit(intCount.incrementAndGet());
    }
}
