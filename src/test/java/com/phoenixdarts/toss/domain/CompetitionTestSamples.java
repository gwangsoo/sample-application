package com.phoenixdarts.toss.domain;

import java.util.UUID;

public class CompetitionTestSamples {

    public static Competition getCompetitionSample1() {
        return new Competition().id("id1").name("name1");
    }

    public static Competition getCompetitionSample2() {
        return new Competition().id("id2").name("name2");
    }

    public static Competition getCompetitionRandomSampleGenerator() {
        return new Competition().id(UUID.randomUUID().toString()).name(UUID.randomUUID().toString());
    }
}
