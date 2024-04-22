package com.phoenixdarts.toss.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class FileInfoTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static FileInfo getFileInfoSample1() {
        return new FileInfo().id("id1").originalName("originalName1").mimeType("mimeType1").fileSize(1L).savedPath("savedPath1");
    }

    public static FileInfo getFileInfoSample2() {
        return new FileInfo().id("id2").originalName("originalName2").mimeType("mimeType2").fileSize(2L).savedPath("savedPath2");
    }

    public static FileInfo getFileInfoRandomSampleGenerator() {
        return new FileInfo()
            .id(UUID.randomUUID().toString())
            .originalName(UUID.randomUUID().toString())
            .mimeType(UUID.randomUUID().toString())
            .fileSize(longCount.incrementAndGet())
            .savedPath(UUID.randomUUID().toString());
    }
}
