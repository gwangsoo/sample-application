package com.phoenixdarts.toss.domain;

import static com.phoenixdarts.toss.domain.CurrencyTestSamples.*;
import static com.phoenixdarts.toss.domain.EntryFeeTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.phoenixdarts.toss.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class CurrencyTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Currency.class);
        Currency currency1 = getCurrencySample1();
        Currency currency2 = new Currency();
        assertThat(currency1).isNotEqualTo(currency2);

        currency2.setId(currency1.getId());
        assertThat(currency1).isEqualTo(currency2);

        currency2 = getCurrencySample2();
        assertThat(currency1).isNotEqualTo(currency2);
    }

    @Test
    void entryFeeTest() throws Exception {
        Currency currency = getCurrencyRandomSampleGenerator();
        EntryFee entryFeeBack = getEntryFeeRandomSampleGenerator();

        currency.addEntryFee(entryFeeBack);
        assertThat(currency.getEntryFees()).containsOnly(entryFeeBack);
        assertThat(entryFeeBack.getCurrency()).isEqualTo(currency);

        currency.removeEntryFee(entryFeeBack);
        assertThat(currency.getEntryFees()).doesNotContain(entryFeeBack);
        assertThat(entryFeeBack.getCurrency()).isNull();

        currency.entryFees(new HashSet<>(Set.of(entryFeeBack)));
        assertThat(currency.getEntryFees()).containsOnly(entryFeeBack);
        assertThat(entryFeeBack.getCurrency()).isEqualTo(currency);

        currency.setEntryFees(new HashSet<>());
        assertThat(currency.getEntryFees()).doesNotContain(entryFeeBack);
        assertThat(entryFeeBack.getCurrency()).isNull();
    }
}
