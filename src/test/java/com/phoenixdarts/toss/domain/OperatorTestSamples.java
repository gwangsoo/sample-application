package com.phoenixdarts.toss.backend.domain;

import java.util.UUID;

public class OperatorTestSamples {

    public static Operator getOperatorSample1() {
        return new Operator().id("id1").userId("userId1").userName("userName1").phone("phone1").email("email1").address("address1");
    }

    public static Operator getOperatorSample2() {
        return new Operator().id("id2").userId("userId2").userName("userName2").phone("phone2").email("email2").address("address2");
    }

    public static Operator getOperatorRandomSampleGenerator() {
        return new Operator()
            .id(UUID.randomUUID().toString())
            .userId(UUID.randomUUID().toString())
            .userName(UUID.randomUUID().toString())
            .phone(UUID.randomUUID().toString())
            .email(UUID.randomUUID().toString())
            .address(UUID.randomUUID().toString());
    }
}
