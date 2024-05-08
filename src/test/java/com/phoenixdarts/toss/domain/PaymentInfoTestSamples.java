package com.phoenixdarts.toss.backend.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class PaymentInfoTestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static PaymentInfo getPaymentInfoSample1() {
        return new PaymentInfo()
            .id("id1")
            .orderNumber("orderNumber1")
            .pgTID(1)
            .pgStatus(1)
            .pgDetail("pgDetail1")
            .payer("payer1")
            .payerPhone("payerPhone1");
    }

    public static PaymentInfo getPaymentInfoSample2() {
        return new PaymentInfo()
            .id("id2")
            .orderNumber("orderNumber2")
            .pgTID(2)
            .pgStatus(2)
            .pgDetail("pgDetail2")
            .payer("payer2")
            .payerPhone("payerPhone2");
    }

    public static PaymentInfo getPaymentInfoRandomSampleGenerator() {
        return new PaymentInfo()
            .id(UUID.randomUUID().toString())
            .orderNumber(UUID.randomUUID().toString())
            .pgTID(intCount.incrementAndGet())
            .pgStatus(intCount.incrementAndGet())
            .pgDetail(UUID.randomUUID().toString())
            .payer(UUID.randomUUID().toString())
            .payerPhone(UUID.randomUUID().toString());
    }
}
