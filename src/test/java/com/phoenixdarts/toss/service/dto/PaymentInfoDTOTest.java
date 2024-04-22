package com.phoenixdarts.toss.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.phoenixdarts.toss.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PaymentInfoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PaymentInfoDTO.class);
        PaymentInfoDTO paymentInfoDTO1 = new PaymentInfoDTO();
        paymentInfoDTO1.setId("id1");
        PaymentInfoDTO paymentInfoDTO2 = new PaymentInfoDTO();
        assertThat(paymentInfoDTO1).isNotEqualTo(paymentInfoDTO2);
        paymentInfoDTO2.setId(paymentInfoDTO1.getId());
        assertThat(paymentInfoDTO1).isEqualTo(paymentInfoDTO2);
        paymentInfoDTO2.setId("id2");
        assertThat(paymentInfoDTO1).isNotEqualTo(paymentInfoDTO2);
        paymentInfoDTO1.setId(null);
        assertThat(paymentInfoDTO1).isNotEqualTo(paymentInfoDTO2);
    }
}
