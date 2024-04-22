package com.phoenixdarts.toss.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.phoenixdarts.toss.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RewardItemDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RewardItemDTO.class);
        RewardItemDTO rewardItemDTO1 = new RewardItemDTO();
        rewardItemDTO1.setId("id1");
        RewardItemDTO rewardItemDTO2 = new RewardItemDTO();
        assertThat(rewardItemDTO1).isNotEqualTo(rewardItemDTO2);
        rewardItemDTO2.setId(rewardItemDTO1.getId());
        assertThat(rewardItemDTO1).isEqualTo(rewardItemDTO2);
        rewardItemDTO2.setId("id2");
        assertThat(rewardItemDTO1).isNotEqualTo(rewardItemDTO2);
        rewardItemDTO1.setId(null);
        assertThat(rewardItemDTO1).isNotEqualTo(rewardItemDTO2);
    }
}
