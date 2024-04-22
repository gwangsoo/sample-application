package com.phoenixdarts.toss.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.phoenixdarts.toss.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MatchCallDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MatchCallDTO.class);
        MatchCallDTO matchCallDTO1 = new MatchCallDTO();
        matchCallDTO1.setId("id1");
        MatchCallDTO matchCallDTO2 = new MatchCallDTO();
        assertThat(matchCallDTO1).isNotEqualTo(matchCallDTO2);
        matchCallDTO2.setId(matchCallDTO1.getId());
        assertThat(matchCallDTO1).isEqualTo(matchCallDTO2);
        matchCallDTO2.setId("id2");
        assertThat(matchCallDTO1).isNotEqualTo(matchCallDTO2);
        matchCallDTO1.setId(null);
        assertThat(matchCallDTO1).isNotEqualTo(matchCallDTO2);
    }
}
