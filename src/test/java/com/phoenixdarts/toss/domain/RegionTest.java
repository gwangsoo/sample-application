package com.phoenixdarts.toss.backend.domain;

import static com.phoenixdarts.toss.backend.domain.CountryTestSamples.*;
import static com.phoenixdarts.toss.backend.domain.OperatorTestSamples.*;
import static com.phoenixdarts.toss.backend.domain.RegionTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.phoenixdarts.toss.backend.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class RegionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Region.class);
        Region region1 = getRegionSample1();
        Region region2 = new Region();
        assertThat(region1).isNotEqualTo(region2);

        region2.setId(region1.getId());
        assertThat(region1).isEqualTo(region2);

        region2 = getRegionSample2();
        assertThat(region1).isNotEqualTo(region2);
    }

    @Test
    void countryTest() throws Exception {
        Region region = getRegionRandomSampleGenerator();
        Country countryBack = getCountryRandomSampleGenerator();

        region.setCountry(countryBack);
        assertThat(region.getCountry()).isEqualTo(countryBack);

        region.country(null);
        assertThat(region.getCountry()).isNull();
    }

    @Test
    void operatorTest() throws Exception {
        Region region = getRegionRandomSampleGenerator();
        Operator operatorBack = getOperatorRandomSampleGenerator();

        region.addOperator(operatorBack);
        assertThat(region.getOperators()).containsOnly(operatorBack);
        assertThat(operatorBack.getRegion()).isEqualTo(region);

        region.removeOperator(operatorBack);
        assertThat(region.getOperators()).doesNotContain(operatorBack);
        assertThat(operatorBack.getRegion()).isNull();

        region.operators(new HashSet<>(Set.of(operatorBack)));
        assertThat(region.getOperators()).containsOnly(operatorBack);
        assertThat(operatorBack.getRegion()).isEqualTo(region);

        region.setOperators(new HashSet<>());
        assertThat(region.getOperators()).doesNotContain(operatorBack);
        assertThat(operatorBack.getRegion()).isNull();
    }
}
