package com.phoenixdarts.toss.backend.domain;

import java.util.UUID;

public class AffiliatedInfoTestSamples {

    public static AffiliatedInfo getAffiliatedInfoSample1() {
        return new AffiliatedInfo().id("id1").seq("seq1").name("name1").address("address1").telNo("telNo1").homepageUrl("homepageUrl1");
    }

    public static AffiliatedInfo getAffiliatedInfoSample2() {
        return new AffiliatedInfo().id("id2").seq("seq2").name("name2").address("address2").telNo("telNo2").homepageUrl("homepageUrl2");
    }

    public static AffiliatedInfo getAffiliatedInfoRandomSampleGenerator() {
        return new AffiliatedInfo()
            .id(UUID.randomUUID().toString())
            .seq(UUID.randomUUID().toString())
            .name(UUID.randomUUID().toString())
            .address(UUID.randomUUID().toString())
            .telNo(UUID.randomUUID().toString())
            .homepageUrl(UUID.randomUUID().toString());
    }
}
