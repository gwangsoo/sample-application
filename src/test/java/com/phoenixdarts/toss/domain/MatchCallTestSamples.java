package com.phoenixdarts.toss.domain;

import java.util.UUID;

public class MatchCallTestSamples {

    public static MatchCall getMatchCallSample1() {
        return new MatchCall().id("id1").callMessage("callMessage1");
    }

    public static MatchCall getMatchCallSample2() {
        return new MatchCall().id("id2").callMessage("callMessage2");
    }

    public static MatchCall getMatchCallRandomSampleGenerator() {
        return new MatchCall().id(UUID.randomUUID().toString()).callMessage(UUID.randomUUID().toString());
    }
}
