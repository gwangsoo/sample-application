package com.phoenixdarts.toss.service.mapper;

import static com.phoenixdarts.toss.domain.MachineAreaAsserts.*;
import static com.phoenixdarts.toss.domain.MachineAreaTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MachineAreaMapperTest {

    private MachineAreaMapper machineAreaMapper;

    @BeforeEach
    void setUp() {
        machineAreaMapper = new MachineAreaMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getMachineAreaSample1();
        var actual = machineAreaMapper.toEntity(machineAreaMapper.toDto(expected));
        assertMachineAreaAllPropertiesEquals(expected, actual);
    }
}
