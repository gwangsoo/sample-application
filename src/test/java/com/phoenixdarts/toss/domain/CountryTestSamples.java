package com.phoenixdarts.toss.domain;

import java.util.UUID;

public class CountryTestSamples {

    public static Country getCountrySample1() {
        return new Country().id("id1").name("name1");
    }

    public static Country getCountrySample2() {
        return new Country().id("id2").name("name2");
    }

    public static Country getCountryRandomSampleGenerator() {
        return new Country().id(UUID.randomUUID().toString()).name(UUID.randomUUID().toString());
    }
}
