package com.phoenixdarts.toss.domain;

import static com.phoenixdarts.toss.domain.FileInfoTestSamples.*;
import static com.phoenixdarts.toss.domain.RewardDetailTestSamples.*;
import static com.phoenixdarts.toss.domain.RewardItemTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.phoenixdarts.toss.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RewardItemTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RewardItem.class);
        RewardItem rewardItem1 = getRewardItemSample1();
        RewardItem rewardItem2 = new RewardItem();
        assertThat(rewardItem1).isNotEqualTo(rewardItem2);

        rewardItem2.setId(rewardItem1.getId());
        assertThat(rewardItem1).isEqualTo(rewardItem2);

        rewardItem2 = getRewardItemSample2();
        assertThat(rewardItem1).isNotEqualTo(rewardItem2);
    }

    @Test
    void itemImageTest() throws Exception {
        RewardItem rewardItem = getRewardItemRandomSampleGenerator();
        FileInfo fileInfoBack = getFileInfoRandomSampleGenerator();

        rewardItem.setItemImage(fileInfoBack);
        assertThat(rewardItem.getItemImage()).isEqualTo(fileInfoBack);

        rewardItem.itemImage(null);
        assertThat(rewardItem.getItemImage()).isNull();
    }

    @Test
    void rewardDetailTest() throws Exception {
        RewardItem rewardItem = getRewardItemRandomSampleGenerator();
        RewardDetail rewardDetailBack = getRewardDetailRandomSampleGenerator();

        rewardItem.setRewardDetail(rewardDetailBack);
        assertThat(rewardItem.getRewardDetail()).isEqualTo(rewardDetailBack);

        rewardItem.rewardDetail(null);
        assertThat(rewardItem.getRewardDetail()).isNull();
    }
}
