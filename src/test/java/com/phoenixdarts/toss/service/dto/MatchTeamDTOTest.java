package com.phoenixdarts.toss.backend.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.phoenixdarts.toss.backend.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MatchTeamDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MatchTeamDTO.class);
        MatchTeamDTO matchTeamDTO1 = new MatchTeamDTO();
        matchTeamDTO1.setId("id1");
        MatchTeamDTO matchTeamDTO2 = new MatchTeamDTO();
        assertThat(matchTeamDTO1).isNotEqualTo(matchTeamDTO2);
        matchTeamDTO2.setId(matchTeamDTO1.getId());
        assertThat(matchTeamDTO1).isEqualTo(matchTeamDTO2);
        matchTeamDTO2.setId("id2");
        assertThat(matchTeamDTO1).isNotEqualTo(matchTeamDTO2);
        matchTeamDTO1.setId(null);
        assertThat(matchTeamDTO1).isNotEqualTo(matchTeamDTO2);
    }
}
