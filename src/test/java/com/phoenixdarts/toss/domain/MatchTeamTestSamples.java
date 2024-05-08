package com.phoenixdarts.toss.backend.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class MatchTeamTestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static MatchTeam getMatchTeamSample1() {
        return new MatchTeam().id("id1").winSet(1).winLeg(1);
    }

    public static MatchTeam getMatchTeamSample2() {
        return new MatchTeam().id("id2").winSet(2).winLeg(2);
    }

    public static MatchTeam getMatchTeamRandomSampleGenerator() {
        return new MatchTeam().id(UUID.randomUUID().toString()).winSet(intCount.incrementAndGet()).winLeg(intCount.incrementAndGet());
    }
}
