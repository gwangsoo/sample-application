package com.phoenixdarts.toss.backend.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.phoenixdarts.toss.backend.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MatchFormatSetDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MatchFormatSetDTO.class);
        MatchFormatSetDTO matchFormatSetDTO1 = new MatchFormatSetDTO();
        matchFormatSetDTO1.setId("id1");
        MatchFormatSetDTO matchFormatSetDTO2 = new MatchFormatSetDTO();
        assertThat(matchFormatSetDTO1).isNotEqualTo(matchFormatSetDTO2);
        matchFormatSetDTO2.setId(matchFormatSetDTO1.getId());
        assertThat(matchFormatSetDTO1).isEqualTo(matchFormatSetDTO2);
        matchFormatSetDTO2.setId("id2");
        assertThat(matchFormatSetDTO1).isNotEqualTo(matchFormatSetDTO2);
        matchFormatSetDTO1.setId(null);
        assertThat(matchFormatSetDTO1).isNotEqualTo(matchFormatSetDTO2);
    }
}
