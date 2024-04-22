package com.phoenixdarts.toss.domain;

import static com.phoenixdarts.toss.domain.RewardDetailTestSamples.*;
import static com.phoenixdarts.toss.domain.RewardItemTestSamples.*;
import static com.phoenixdarts.toss.domain.RewardTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.phoenixdarts.toss.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class RewardDetailTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RewardDetail.class);
        RewardDetail rewardDetail1 = getRewardDetailSample1();
        RewardDetail rewardDetail2 = new RewardDetail();
        assertThat(rewardDetail1).isNotEqualTo(rewardDetail2);

        rewardDetail2.setId(rewardDetail1.getId());
        assertThat(rewardDetail1).isEqualTo(rewardDetail2);

        rewardDetail2 = getRewardDetailSample2();
        assertThat(rewardDetail1).isNotEqualTo(rewardDetail2);
    }

    @Test
    void rewardItemTest() throws Exception {
        RewardDetail rewardDetail = getRewardDetailRandomSampleGenerator();
        RewardItem rewardItemBack = getRewardItemRandomSampleGenerator();

        rewardDetail.addRewardItem(rewardItemBack);
        assertThat(rewardDetail.getRewardItems()).containsOnly(rewardItemBack);
        assertThat(rewardItemBack.getRewardDetail()).isEqualTo(rewardDetail);

        rewardDetail.removeRewardItem(rewardItemBack);
        assertThat(rewardDetail.getRewardItems()).doesNotContain(rewardItemBack);
        assertThat(rewardItemBack.getRewardDetail()).isNull();

        rewardDetail.rewardItems(new HashSet<>(Set.of(rewardItemBack)));
        assertThat(rewardDetail.getRewardItems()).containsOnly(rewardItemBack);
        assertThat(rewardItemBack.getRewardDetail()).isEqualTo(rewardDetail);

        rewardDetail.setRewardItems(new HashSet<>());
        assertThat(rewardDetail.getRewardItems()).doesNotContain(rewardItemBack);
        assertThat(rewardItemBack.getRewardDetail()).isNull();
    }

    @Test
    void rewardTest() throws Exception {
        RewardDetail rewardDetail = getRewardDetailRandomSampleGenerator();
        Reward rewardBack = getRewardRandomSampleGenerator();

        rewardDetail.setReward(rewardBack);
        assertThat(rewardDetail.getReward()).isEqualTo(rewardBack);

        rewardDetail.reward(null);
        assertThat(rewardDetail.getReward()).isNull();
    }
}
