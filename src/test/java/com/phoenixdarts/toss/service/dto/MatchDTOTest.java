package com.phoenixdarts.toss.backend.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.phoenixdarts.toss.backend.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MatchDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MatchDTO.class);
        MatchDTO matchDTO1 = new MatchDTO();
        matchDTO1.setId("id1");
        MatchDTO matchDTO2 = new MatchDTO();
        assertThat(matchDTO1).isNotEqualTo(matchDTO2);
        matchDTO2.setId(matchDTO1.getId());
        assertThat(matchDTO1).isEqualTo(matchDTO2);
        matchDTO2.setId("id2");
        assertThat(matchDTO1).isNotEqualTo(matchDTO2);
        matchDTO1.setId(null);
        assertThat(matchDTO1).isNotEqualTo(matchDTO2);
    }
}
