package com.phoenixdarts.toss.backend.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.phoenixdarts.toss.backend.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RewardDetailDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RewardDetailDTO.class);
        RewardDetailDTO rewardDetailDTO1 = new RewardDetailDTO();
        rewardDetailDTO1.setId("id1");
        RewardDetailDTO rewardDetailDTO2 = new RewardDetailDTO();
        assertThat(rewardDetailDTO1).isNotEqualTo(rewardDetailDTO2);
        rewardDetailDTO2.setId(rewardDetailDTO1.getId());
        assertThat(rewardDetailDTO1).isEqualTo(rewardDetailDTO2);
        rewardDetailDTO2.setId("id2");
        assertThat(rewardDetailDTO1).isNotEqualTo(rewardDetailDTO2);
        rewardDetailDTO1.setId(null);
        assertThat(rewardDetailDTO1).isNotEqualTo(rewardDetailDTO2);
    }
}
