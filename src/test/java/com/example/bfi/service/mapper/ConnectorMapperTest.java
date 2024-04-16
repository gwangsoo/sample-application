package com.example.bfi.service.mapper;

import static com.example.bfi.domain.ConnectorAsserts.*;
import static com.example.bfi.domain.ConnectorTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ConnectorMapperTest {

    private ConnectorMapper connectorMapper;

    @BeforeEach
    void setUp() {
        connectorMapper = new ConnectorMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getConnectorSample1();
        var actual = connectorMapper.toEntity(connectorMapper.toDto(expected));
        assertConnectorAllPropertiesEquals(expected, actual);
    }
}
