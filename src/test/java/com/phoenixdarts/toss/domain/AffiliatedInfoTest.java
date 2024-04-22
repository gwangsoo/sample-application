package com.phoenixdarts.toss.domain;

import static com.phoenixdarts.toss.domain.AffiliatedInfoTestSamples.*;
import static com.phoenixdarts.toss.domain.FileInfoTestSamples.*;
import static com.phoenixdarts.toss.domain.TeamTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.phoenixdarts.toss.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class AffiliatedInfoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AffiliatedInfo.class);
        AffiliatedInfo affiliatedInfo1 = getAffiliatedInfoSample1();
        AffiliatedInfo affiliatedInfo2 = new AffiliatedInfo();
        assertThat(affiliatedInfo1).isNotEqualTo(affiliatedInfo2);

        affiliatedInfo2.setId(affiliatedInfo1.getId());
        assertThat(affiliatedInfo1).isEqualTo(affiliatedInfo2);

        affiliatedInfo2 = getAffiliatedInfoSample2();
        assertThat(affiliatedInfo1).isNotEqualTo(affiliatedInfo2);
    }

    @Test
    void fileInfoTest() throws Exception {
        AffiliatedInfo affiliatedInfo = getAffiliatedInfoRandomSampleGenerator();
        FileInfo fileInfoBack = getFileInfoRandomSampleGenerator();

        affiliatedInfo.setFileInfo(fileInfoBack);
        assertThat(affiliatedInfo.getFileInfo()).isEqualTo(fileInfoBack);

        affiliatedInfo.fileInfo(null);
        assertThat(affiliatedInfo.getFileInfo()).isNull();
    }

    @Test
    void teamTest() throws Exception {
        AffiliatedInfo affiliatedInfo = getAffiliatedInfoRandomSampleGenerator();
        Team teamBack = getTeamRandomSampleGenerator();

        affiliatedInfo.addTeam(teamBack);
        assertThat(affiliatedInfo.getTeams()).containsOnly(teamBack);
        assertThat(teamBack.getAffiliatedInfo()).isEqualTo(affiliatedInfo);

        affiliatedInfo.removeTeam(teamBack);
        assertThat(affiliatedInfo.getTeams()).doesNotContain(teamBack);
        assertThat(teamBack.getAffiliatedInfo()).isNull();

        affiliatedInfo.teams(new HashSet<>(Set.of(teamBack)));
        assertThat(affiliatedInfo.getTeams()).containsOnly(teamBack);
        assertThat(teamBack.getAffiliatedInfo()).isEqualTo(affiliatedInfo);

        affiliatedInfo.setTeams(new HashSet<>());
        assertThat(affiliatedInfo.getTeams()).doesNotContain(teamBack);
        assertThat(teamBack.getAffiliatedInfo()).isNull();
    }
}
