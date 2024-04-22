package com.phoenixdarts.toss.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.phoenixdarts.toss.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MatchFormatDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MatchFormatDTO.class);
        MatchFormatDTO matchFormatDTO1 = new MatchFormatDTO();
        matchFormatDTO1.setId("id1");
        MatchFormatDTO matchFormatDTO2 = new MatchFormatDTO();
        assertThat(matchFormatDTO1).isNotEqualTo(matchFormatDTO2);
        matchFormatDTO2.setId(matchFormatDTO1.getId());
        assertThat(matchFormatDTO1).isEqualTo(matchFormatDTO2);
        matchFormatDTO2.setId("id2");
        assertThat(matchFormatDTO1).isNotEqualTo(matchFormatDTO2);
        matchFormatDTO1.setId(null);
        assertThat(matchFormatDTO1).isNotEqualTo(matchFormatDTO2);
    }
}
