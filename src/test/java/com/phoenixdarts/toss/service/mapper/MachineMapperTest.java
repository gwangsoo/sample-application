package com.phoenixdarts.toss.service.mapper;

import static com.phoenixdarts.toss.domain.MachineAsserts.*;
import static com.phoenixdarts.toss.domain.MachineTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MachineMapperTest {

    private MachineMapper machineMapper;

    @BeforeEach
    void setUp() {
        machineMapper = new MachineMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getMachineSample1();
        var actual = machineMapper.toEntity(machineMapper.toDto(expected));
        assertMachineAllPropertiesEquals(expected, actual);
    }
}
