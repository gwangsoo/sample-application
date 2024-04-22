package com.phoenixdarts.toss.service.mapper;

import static com.phoenixdarts.toss.domain.CurrencyAsserts.*;
import static com.phoenixdarts.toss.domain.CurrencyTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CurrencyMapperTest {

    private CurrencyMapper currencyMapper;

    @BeforeEach
    void setUp() {
        currencyMapper = new CurrencyMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getCurrencySample1();
        var actual = currencyMapper.toEntity(currencyMapper.toDto(expected));
        assertCurrencyAllPropertiesEquals(expected, actual);
    }
}
