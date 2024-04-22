package com.phoenixdarts.toss.domain;

import static com.phoenixdarts.toss.domain.CompetitionTestSamples.*;
import static com.phoenixdarts.toss.domain.CountryTestSamples.*;
import static com.phoenixdarts.toss.domain.FileInfoTestSamples.*;
import static com.phoenixdarts.toss.domain.RegionTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.phoenixdarts.toss.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class CountryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Country.class);
        Country country1 = getCountrySample1();
        Country country2 = new Country();
        assertThat(country1).isNotEqualTo(country2);

        country2.setId(country1.getId());
        assertThat(country1).isEqualTo(country2);

        country2 = getCountrySample2();
        assertThat(country1).isNotEqualTo(country2);
    }

    @Test
    void imageTest() throws Exception {
        Country country = getCountryRandomSampleGenerator();
        FileInfo fileInfoBack = getFileInfoRandomSampleGenerator();

        country.setImage(fileInfoBack);
        assertThat(country.getImage()).isEqualTo(fileInfoBack);

        country.image(null);
        assertThat(country.getImage()).isNull();
    }

    @Test
    void competitionTest() throws Exception {
        Country country = getCountryRandomSampleGenerator();
        Competition competitionBack = getCompetitionRandomSampleGenerator();

        country.addCompetition(competitionBack);
        assertThat(country.getCompetitions()).containsOnly(competitionBack);
        assertThat(competitionBack.getCountry()).isEqualTo(country);

        country.removeCompetition(competitionBack);
        assertThat(country.getCompetitions()).doesNotContain(competitionBack);
        assertThat(competitionBack.getCountry()).isNull();

        country.competitions(new HashSet<>(Set.of(competitionBack)));
        assertThat(country.getCompetitions()).containsOnly(competitionBack);
        assertThat(competitionBack.getCountry()).isEqualTo(country);

        country.setCompetitions(new HashSet<>());
        assertThat(country.getCompetitions()).doesNotContain(competitionBack);
        assertThat(competitionBack.getCountry()).isNull();
    }

    @Test
    void regionTest() throws Exception {
        Country country = getCountryRandomSampleGenerator();
        Region regionBack = getRegionRandomSampleGenerator();

        country.addRegion(regionBack);
        assertThat(country.getRegions()).containsOnly(regionBack);
        assertThat(regionBack.getCountry()).isEqualTo(country);

        country.removeRegion(regionBack);
        assertThat(country.getRegions()).doesNotContain(regionBack);
        assertThat(regionBack.getCountry()).isNull();

        country.regions(new HashSet<>(Set.of(regionBack)));
        assertThat(country.getRegions()).containsOnly(regionBack);
        assertThat(regionBack.getCountry()).isEqualTo(country);

        country.setRegions(new HashSet<>());
        assertThat(country.getRegions()).doesNotContain(regionBack);
        assertThat(regionBack.getCountry()).isNull();
    }
}
