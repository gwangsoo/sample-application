package com.phoenixdarts.toss.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.phoenixdarts.toss.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MatchFormatOptionDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MatchFormatOptionDTO.class);
        MatchFormatOptionDTO matchFormatOptionDTO1 = new MatchFormatOptionDTO();
        matchFormatOptionDTO1.setId("id1");
        MatchFormatOptionDTO matchFormatOptionDTO2 = new MatchFormatOptionDTO();
        assertThat(matchFormatOptionDTO1).isNotEqualTo(matchFormatOptionDTO2);
        matchFormatOptionDTO2.setId(matchFormatOptionDTO1.getId());
        assertThat(matchFormatOptionDTO1).isEqualTo(matchFormatOptionDTO2);
        matchFormatOptionDTO2.setId("id2");
        assertThat(matchFormatOptionDTO1).isNotEqualTo(matchFormatOptionDTO2);
        matchFormatOptionDTO1.setId(null);
        assertThat(matchFormatOptionDTO1).isNotEqualTo(matchFormatOptionDTO2);
    }
}
