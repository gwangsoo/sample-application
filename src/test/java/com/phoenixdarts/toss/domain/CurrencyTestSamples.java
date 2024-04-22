package com.phoenixdarts.toss.domain;

import java.util.UUID;

public class CurrencyTestSamples {

    public static Currency getCurrencySample1() {
        return new Currency().id("id1").name("name1");
    }

    public static Currency getCurrencySample2() {
        return new Currency().id("id2").name("name2");
    }

    public static Currency getCurrencyRandomSampleGenerator() {
        return new Currency().id(UUID.randomUUID().toString()).name(UUID.randomUUID().toString());
    }
}
