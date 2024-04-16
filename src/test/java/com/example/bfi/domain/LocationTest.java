package com.example.bfi.domain;

import static com.example.bfi.domain.EvseTestSamples.*;
import static com.example.bfi.domain.LocationTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.example.bfi.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class LocationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Location.class);
        Location location1 = getLocationSample1();
        Location location2 = new Location();
        assertThat(location1).isNotEqualTo(location2);

        location2.setId(location1.getId());
        assertThat(location1).isEqualTo(location2);

        location2 = getLocationSample2();
        assertThat(location1).isNotEqualTo(location2);
    }

    @Test
    void evseTest() throws Exception {
        Location location = getLocationRandomSampleGenerator();
        Evse evseBack = getEvseRandomSampleGenerator();

        location.addEvse(evseBack);
        assertThat(location.getEvses()).containsOnly(evseBack);
        assertThat(evseBack.getLocation()).isEqualTo(location);

        location.removeEvse(evseBack);
        assertThat(location.getEvses()).doesNotContain(evseBack);
        assertThat(evseBack.getLocation()).isNull();

        location.evses(new HashSet<>(Set.of(evseBack)));
        assertThat(location.getEvses()).containsOnly(evseBack);
        assertThat(evseBack.getLocation()).isEqualTo(location);

        location.setEvses(new HashSet<>());
        assertThat(location.getEvses()).doesNotContain(evseBack);
        assertThat(evseBack.getLocation()).isNull();
    }
}
