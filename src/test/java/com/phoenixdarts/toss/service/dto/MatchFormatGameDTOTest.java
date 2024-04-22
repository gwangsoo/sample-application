package com.phoenixdarts.toss.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.phoenixdarts.toss.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MatchFormatGameDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MatchFormatGameDTO.class);
        MatchFormatGameDTO matchFormatGameDTO1 = new MatchFormatGameDTO();
        matchFormatGameDTO1.setId("id1");
        MatchFormatGameDTO matchFormatGameDTO2 = new MatchFormatGameDTO();
        assertThat(matchFormatGameDTO1).isNotEqualTo(matchFormatGameDTO2);
        matchFormatGameDTO2.setId(matchFormatGameDTO1.getId());
        assertThat(matchFormatGameDTO1).isEqualTo(matchFormatGameDTO2);
        matchFormatGameDTO2.setId("id2");
        assertThat(matchFormatGameDTO1).isNotEqualTo(matchFormatGameDTO2);
        matchFormatGameDTO1.setId(null);
        assertThat(matchFormatGameDTO1).isNotEqualTo(matchFormatGameDTO2);
    }
}
