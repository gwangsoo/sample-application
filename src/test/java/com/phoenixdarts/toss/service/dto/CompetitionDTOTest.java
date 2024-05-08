package com.phoenixdarts.toss.backend.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.phoenixdarts.toss.backend.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CompetitionDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompetitionDTO.class);
        CompetitionDTO competitionDTO1 = new CompetitionDTO();
        competitionDTO1.setId("id1");
        CompetitionDTO competitionDTO2 = new CompetitionDTO();
        assertThat(competitionDTO1).isNotEqualTo(competitionDTO2);
        competitionDTO2.setId(competitionDTO1.getId());
        assertThat(competitionDTO1).isEqualTo(competitionDTO2);
        competitionDTO2.setId("id2");
        assertThat(competitionDTO1).isNotEqualTo(competitionDTO2);
        competitionDTO1.setId(null);
        assertThat(competitionDTO1).isNotEqualTo(competitionDTO2);
    }
}
