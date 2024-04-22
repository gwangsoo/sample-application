package com.phoenixdarts.toss.domain;

import java.util.UUID;

public class RegionTestSamples {

    public static Region getRegionSample1() {
        return new Region().id("id1").name("name1").parentAreaId("parentAreaId1");
    }

    public static Region getRegionSample2() {
        return new Region().id("id2").name("name2").parentAreaId("parentAreaId2");
    }

    public static Region getRegionRandomSampleGenerator() {
        return new Region().id(UUID.randomUUID().toString()).name(UUID.randomUUID().toString()).parentAreaId(UUID.randomUUID().toString());
    }
}
