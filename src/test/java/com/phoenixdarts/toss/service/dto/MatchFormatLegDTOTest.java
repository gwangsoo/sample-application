package com.phoenixdarts.toss.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.phoenixdarts.toss.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MatchFormatLegDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MatchFormatLegDTO.class);
        MatchFormatLegDTO matchFormatLegDTO1 = new MatchFormatLegDTO();
        matchFormatLegDTO1.setId("id1");
        MatchFormatLegDTO matchFormatLegDTO2 = new MatchFormatLegDTO();
        assertThat(matchFormatLegDTO1).isNotEqualTo(matchFormatLegDTO2);
        matchFormatLegDTO2.setId(matchFormatLegDTO1.getId());
        assertThat(matchFormatLegDTO1).isEqualTo(matchFormatLegDTO2);
        matchFormatLegDTO2.setId("id2");
        assertThat(matchFormatLegDTO1).isNotEqualTo(matchFormatLegDTO2);
        matchFormatLegDTO1.setId(null);
        assertThat(matchFormatLegDTO1).isNotEqualTo(matchFormatLegDTO2);
    }
}
