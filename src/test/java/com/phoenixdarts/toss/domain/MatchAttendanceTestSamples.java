package com.phoenixdarts.toss.domain;

import java.util.UUID;

public class MatchAttendanceTestSamples {

    public static MatchAttendance getMatchAttendanceSample1() {
        return new MatchAttendance().id("id1");
    }

    public static MatchAttendance getMatchAttendanceSample2() {
        return new MatchAttendance().id("id2");
    }

    public static MatchAttendance getMatchAttendanceRandomSampleGenerator() {
        return new MatchAttendance().id(UUID.randomUUID().toString());
    }
}
