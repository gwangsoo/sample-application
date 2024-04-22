package com.phoenixdarts.toss.service.mapper;

import static com.phoenixdarts.toss.domain.PaymentInfoAsserts.*;
import static com.phoenixdarts.toss.domain.PaymentInfoTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PaymentInfoMapperTest {

    private PaymentInfoMapper paymentInfoMapper;

    @BeforeEach
    void setUp() {
        paymentInfoMapper = new PaymentInfoMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getPaymentInfoSample1();
        var actual = paymentInfoMapper.toEntity(paymentInfoMapper.toDto(expected));
        assertPaymentInfoAllPropertiesEquals(expected, actual);
    }
}
