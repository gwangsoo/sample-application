package com.phoenixdarts.toss.service.mapper;

import static com.phoenixdarts.toss.domain.FileInfoAsserts.*;
import static com.phoenixdarts.toss.domain.FileInfoTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileInfoMapperTest {

    private FileInfoMapper fileInfoMapper;

    @BeforeEach
    void setUp() {
        fileInfoMapper = new FileInfoMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getFileInfoSample1();
        var actual = fileInfoMapper.toEntity(fileInfoMapper.toDto(expected));
        assertFileInfoAllPropertiesEquals(expected, actual);
    }
}
