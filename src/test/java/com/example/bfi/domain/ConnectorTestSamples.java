package com.example.bfi.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class ConnectorTestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Connector getConnectorSample1() {
        return new Connector().id("id1").maxVoltage(1).maxAmperage(1).maxElectricPower(1).tariffIds("tariffIds1");
    }

    public static Connector getConnectorSample2() {
        return new Connector().id("id2").maxVoltage(2).maxAmperage(2).maxElectricPower(2).tariffIds("tariffIds2");
    }

    public static Connector getConnectorRandomSampleGenerator() {
        return new Connector()
            .id(UUID.randomUUID().toString())
            .maxVoltage(intCount.incrementAndGet())
            .maxAmperage(intCount.incrementAndGet())
            .maxElectricPower(intCount.incrementAndGet())
            .tariffIds(UUID.randomUUID().toString());
    }
}
