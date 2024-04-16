package com.example.bfi.domain;

import java.util.UUID;

public class EvseTestSamples {

    public static Evse getEvseSample1() {
        return new Evse().id("id1").uid("uid1").evseId("evseId1").directions("directions1");
    }

    public static Evse getEvseSample2() {
        return new Evse().id("id2").uid("uid2").evseId("evseId2").directions("directions2");
    }

    public static Evse getEvseRandomSampleGenerator() {
        return new Evse()
            .id(UUID.randomUUID().toString())
            .uid(UUID.randomUUID().toString())
            .evseId(UUID.randomUUID().toString())
            .directions(UUID.randomUUID().toString());
    }
}
