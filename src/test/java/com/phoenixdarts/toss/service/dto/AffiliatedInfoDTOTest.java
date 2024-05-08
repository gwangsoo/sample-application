package com.phoenixdarts.toss.backend.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.phoenixdarts.toss.backend.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AffiliatedInfoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AffiliatedInfoDTO.class);
        AffiliatedInfoDTO affiliatedInfoDTO1 = new AffiliatedInfoDTO();
        affiliatedInfoDTO1.setId("id1");
        AffiliatedInfoDTO affiliatedInfoDTO2 = new AffiliatedInfoDTO();
        assertThat(affiliatedInfoDTO1).isNotEqualTo(affiliatedInfoDTO2);
        affiliatedInfoDTO2.setId(affiliatedInfoDTO1.getId());
        assertThat(affiliatedInfoDTO1).isEqualTo(affiliatedInfoDTO2);
        affiliatedInfoDTO2.setId("id2");
        assertThat(affiliatedInfoDTO1).isNotEqualTo(affiliatedInfoDTO2);
        affiliatedInfoDTO1.setId(null);
        assertThat(affiliatedInfoDTO1).isNotEqualTo(affiliatedInfoDTO2);
    }
}
