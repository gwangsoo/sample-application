package com.phoenixdarts.toss.domain;

import static com.phoenixdarts.toss.domain.AffiliatedInfoTestSamples.*;
import static com.phoenixdarts.toss.domain.FileInfoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.phoenixdarts.toss.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class FileInfoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FileInfo.class);
        FileInfo fileInfo1 = getFileInfoSample1();
        FileInfo fileInfo2 = new FileInfo();
        assertThat(fileInfo1).isNotEqualTo(fileInfo2);

        fileInfo2.setId(fileInfo1.getId());
        assertThat(fileInfo1).isEqualTo(fileInfo2);

        fileInfo2 = getFileInfoSample2();
        assertThat(fileInfo1).isNotEqualTo(fileInfo2);
    }

    @Test
    void affiliatedInfoTest() throws Exception {
        FileInfo fileInfo = getFileInfoRandomSampleGenerator();
        AffiliatedInfo affiliatedInfoBack = getAffiliatedInfoRandomSampleGenerator();

        fileInfo.addAffiliatedInfo(affiliatedInfoBack);
        assertThat(fileInfo.getAffiliatedInfos()).containsOnly(affiliatedInfoBack);
        assertThat(affiliatedInfoBack.getFileInfo()).isEqualTo(fileInfo);

        fileInfo.removeAffiliatedInfo(affiliatedInfoBack);
        assertThat(fileInfo.getAffiliatedInfos()).doesNotContain(affiliatedInfoBack);
        assertThat(affiliatedInfoBack.getFileInfo()).isNull();

        fileInfo.affiliatedInfos(new HashSet<>(Set.of(affiliatedInfoBack)));
        assertThat(fileInfo.getAffiliatedInfos()).containsOnly(affiliatedInfoBack);
        assertThat(affiliatedInfoBack.getFileInfo()).isEqualTo(fileInfo);

        fileInfo.setAffiliatedInfos(new HashSet<>());
        assertThat(fileInfo.getAffiliatedInfos()).doesNotContain(affiliatedInfoBack);
        assertThat(affiliatedInfoBack.getFileInfo()).isNull();
    }
}
