package com.example.bfi.domain;

import java.util.UUID;

public class LocationTestSamples {

    public static Location getLocationSample1() {
        return new Location()
            .id("id1")
            .countryCode("countryCode1")
            .partyId("partyId1")
            .name("name1")
            .address("address1")
            .city("city1")
            .postalCode("postalCode1")
            .state("state1")
            .country("country1")
            .timeZone("timeZone1");
    }

    public static Location getLocationSample2() {
        return new Location()
            .id("id2")
            .countryCode("countryCode2")
            .partyId("partyId2")
            .name("name2")
            .address("address2")
            .city("city2")
            .postalCode("postalCode2")
            .state("state2")
            .country("country2")
            .timeZone("timeZone2");
    }

    public static Location getLocationRandomSampleGenerator() {
        return new Location()
            .id(UUID.randomUUID().toString())
            .countryCode(UUID.randomUUID().toString())
            .partyId(UUID.randomUUID().toString())
            .name(UUID.randomUUID().toString())
            .address(UUID.randomUUID().toString())
            .city(UUID.randomUUID().toString())
            .postalCode(UUID.randomUUID().toString())
            .state(UUID.randomUUID().toString())
            .country(UUID.randomUUID().toString())
            .timeZone(UUID.randomUUID().toString());
    }
}
