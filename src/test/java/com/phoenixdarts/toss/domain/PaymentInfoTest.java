package com.phoenixdarts.toss.backend.domain;

import static com.phoenixdarts.toss.backend.domain.PaymentInfoTestSamples.*;
import static com.phoenixdarts.toss.backend.domain.TeamTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.phoenixdarts.toss.backend.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class PaymentInfoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PaymentInfo.class);
        PaymentInfo paymentInfo1 = getPaymentInfoSample1();
        PaymentInfo paymentInfo2 = new PaymentInfo();
        assertThat(paymentInfo1).isNotEqualTo(paymentInfo2);

        paymentInfo2.setId(paymentInfo1.getId());
        assertThat(paymentInfo1).isEqualTo(paymentInfo2);

        paymentInfo2 = getPaymentInfoSample2();
        assertThat(paymentInfo1).isNotEqualTo(paymentInfo2);
    }

    @Test
    void teamTest() throws Exception {
        PaymentInfo paymentInfo = getPaymentInfoRandomSampleGenerator();
        Team teamBack = getTeamRandomSampleGenerator();

        paymentInfo.addTeam(teamBack);
        assertThat(paymentInfo.getTeams()).containsOnly(teamBack);
        assertThat(teamBack.getPaymentInfo()).isEqualTo(paymentInfo);

        paymentInfo.removeTeam(teamBack);
        assertThat(paymentInfo.getTeams()).doesNotContain(teamBack);
        assertThat(teamBack.getPaymentInfo()).isNull();

        paymentInfo.teams(new HashSet<>(Set.of(teamBack)));
        assertThat(paymentInfo.getTeams()).containsOnly(teamBack);
        assertThat(teamBack.getPaymentInfo()).isEqualTo(paymentInfo);

        paymentInfo.setTeams(new HashSet<>());
        assertThat(paymentInfo.getTeams()).doesNotContain(teamBack);
        assertThat(teamBack.getPaymentInfo()).isNull();
    }
}
