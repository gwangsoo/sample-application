package com.phoenixdarts.toss.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.phoenixdarts.toss.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MatchScoreDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MatchScoreDTO.class);
        MatchScoreDTO matchScoreDTO1 = new MatchScoreDTO();
        matchScoreDTO1.setId("id1");
        MatchScoreDTO matchScoreDTO2 = new MatchScoreDTO();
        assertThat(matchScoreDTO1).isNotEqualTo(matchScoreDTO2);
        matchScoreDTO2.setId(matchScoreDTO1.getId());
        assertThat(matchScoreDTO1).isEqualTo(matchScoreDTO2);
        matchScoreDTO2.setId("id2");
        assertThat(matchScoreDTO1).isNotEqualTo(matchScoreDTO2);
        matchScoreDTO1.setId(null);
        assertThat(matchScoreDTO1).isNotEqualTo(matchScoreDTO2);
    }
}
