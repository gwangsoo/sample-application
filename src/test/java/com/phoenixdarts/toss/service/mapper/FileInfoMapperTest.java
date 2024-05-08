package com.phoenixdarts.toss.backend.service.mapper;

import static com.phoenixdarts.toss.backend.domain.FileInfoAsserts.*;
import static com.phoenixdarts.toss.backend.domain.FileInfoTestSamples.*;

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
