package com.phoenixdarts.toss.backend.domain;

import java.util.UUID;

public class EntryTestSamples {

    public static Entry getEntrySample1() {
        return new Entry()
            .id("id1")
            .entryNo("entryNo1")
            .phoenixNo("phoenixNo1")
            .cardNo("cardNo1")
            .name("name1")
            .englishName("englishName1")
            .mobileNo("mobileNo1")
            .email("email1");
    }

    public static Entry getEntrySample2() {
        return new Entry()
            .id("id2")
            .entryNo("entryNo2")
            .phoenixNo("phoenixNo2")
            .cardNo("cardNo2")
            .name("name2")
            .englishName("englishName2")
            .mobileNo("mobileNo2")
            .email("email2");
    }

    public static Entry getEntryRandomSampleGenerator() {
        return new Entry()
            .id(UUID.randomUUID().toString())
            .entryNo(UUID.randomUUID().toString())
            .phoenixNo(UUID.randomUUID().toString())
            .cardNo(UUID.randomUUID().toString())
            .name(UUID.randomUUID().toString())
            .englishName(UUID.randomUUID().toString())
            .mobileNo(UUID.randomUUID().toString())
            .email(UUID.randomUUID().toString());
    }
}
