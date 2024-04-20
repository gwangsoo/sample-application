package com.example.bfi.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.bfi.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EvseDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EvseDTO.class);
        EvseDTO evseDTO1 = new EvseDTO();
        evseDTO1.setId("id1");
        EvseDTO evseDTO2 = new EvseDTO();
        assertThat(evseDTO1).isNotEqualTo(evseDTO2);
        evseDTO2.setId(evseDTO1.getId());
        assertThat(evseDTO1).isEqualTo(evseDTO2);
        evseDTO2.setId("id2");
        assertThat(evseDTO1).isNotEqualTo(evseDTO2);
        evseDTO1.setId(null);
        assertThat(evseDTO1).isNotEqualTo(evseDTO2);
    }
}
