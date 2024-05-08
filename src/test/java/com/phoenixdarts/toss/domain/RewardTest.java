package com.phoenixdarts.toss.backend.domain;

import static com.phoenixdarts.toss.backend.domain.CompetitionTestSamples.*;
import static com.phoenixdarts.toss.backend.domain.RewardDetailTestSamples.*;
import static com.phoenixdarts.toss.backend.domain.RewardTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.phoenixdarts.toss.backend.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class RewardTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Reward.class);
        Reward reward1 = getRewardSample1();
        Reward reward2 = new Reward();
        assertThat(reward1).isNotEqualTo(reward2);

        reward2.setId(reward1.getId());
        assertThat(reward1).isEqualTo(reward2);

        reward2 = getRewardSample2();
        assertThat(reward1).isNotEqualTo(reward2);
    }

    @Test
    void rewardDetailTest() throws Exception {
        Reward reward = getRewardRandomSampleGenerator();
        RewardDetail rewardDetailBack = getRewardDetailRandomSampleGenerator();

        reward.addRewardDetail(rewardDetailBack);
        assertThat(reward.getRewardDetails()).containsOnly(rewardDetailBack);
        assertThat(rewardDetailBack.getReward()).isEqualTo(reward);

        reward.removeRewardDetail(rewardDetailBack);
        assertThat(reward.getRewardDetails()).doesNotContain(rewardDetailBack);
        assertThat(rewardDetailBack.getReward()).isNull();

        reward.rewardDetails(new HashSet<>(Set.of(rewardDetailBack)));
        assertThat(reward.getRewardDetails()).containsOnly(rewardDetailBack);
        assertThat(rewardDetailBack.getReward()).isEqualTo(reward);

        reward.setRewardDetails(new HashSet<>());
        assertThat(reward.getRewardDetails()).doesNotContain(rewardDetailBack);
        assertThat(rewardDetailBack.getReward()).isNull();
    }

    @Test
    void competitionTest() throws Exception {
        Reward reward = getRewardRandomSampleGenerator();
        Competition competitionBack = getCompetitionRandomSampleGenerator();

        reward.addCompetition(competitionBack);
        assertThat(reward.getCompetitions()).containsOnly(competitionBack);
        assertThat(competitionBack.getReward()).isEqualTo(reward);

        reward.removeCompetition(competitionBack);
        assertThat(reward.getCompetitions()).doesNotContain(competitionBack);
        assertThat(competitionBack.getReward()).isNull();

        reward.competitions(new HashSet<>(Set.of(competitionBack)));
        assertThat(reward.getCompetitions()).containsOnly(competitionBack);
        assertThat(competitionBack.getReward()).isEqualTo(reward);

        reward.setCompetitions(new HashSet<>());
        assertThat(reward.getCompetitions()).doesNotContain(competitionBack);
        assertThat(competitionBack.getReward()).isNull();
    }
}
