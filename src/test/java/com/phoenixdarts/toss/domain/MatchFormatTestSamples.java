package com.phoenixdarts.toss.domain;

import java.util.UUID;

public class MatchFormatTestSamples {

    public static MatchFormat getMatchFormatSample1() {
        return new MatchFormat().id("id1").name("name1").description("description1");
    }

    public static MatchFormat getMatchFormatSample2() {
        return new MatchFormat().id("id2").name("name2").description("description2");
    }

    public static MatchFormat getMatchFormatRandomSampleGenerator() {
        return new MatchFormat()
            .id(UUID.randomUUID().toString())
            .name(UUID.randomUUID().toString())
            .description(UUID.randomUUID().toString());
    }
}
