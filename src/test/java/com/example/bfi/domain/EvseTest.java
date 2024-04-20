package com.example.bfi.domain;

import static com.example.bfi.domain.ConnectorTestSamples.*;
import static com.example.bfi.domain.EvseTestSamples.*;
import static com.example.bfi.domain.LocationTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.example.bfi.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class EvseTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Evse.class);
        Evse evse1 = getEvseSample1();
        Evse evse2 = new Evse();
        assertThat(evse1).isNotEqualTo(evse2);

        evse2.setId(evse1.getId());
        assertThat(evse1).isEqualTo(evse2);

        evse2 = getEvseSample2();
        assertThat(evse1).isNotEqualTo(evse2);
    }

    @Test
    void connectorTest() throws Exception {
        Evse evse = getEvseRandomSampleGenerator();
        Connector connectorBack = getConnectorRandomSampleGenerator();

        evse.addConnector(connectorBack);
        assertThat(evse.getConnectors()).containsOnly(connectorBack);
        assertThat(connectorBack.getEvse()).isEqualTo(evse);

        evse.removeConnector(connectorBack);
        assertThat(evse.getConnectors()).doesNotContain(connectorBack);
        assertThat(connectorBack.getEvse()).isNull();

        evse.connectors(new HashSet<>(Set.of(connectorBack)));
        assertThat(evse.getConnectors()).containsOnly(connectorBack);
        assertThat(connectorBack.getEvse()).isEqualTo(evse);

        evse.setConnectors(new HashSet<>());
        assertThat(evse.getConnectors()).doesNotContain(connectorBack);
        assertThat(connectorBack.getEvse()).isNull();
    }

    @Test
    void locationTest() throws Exception {
        Evse evse = getEvseRandomSampleGenerator();
        Location locationBack = getLocationRandomSampleGenerator();

        evse.setLocation(locationBack);
        assertThat(evse.getLocation()).isEqualTo(locationBack);

        evse.location(null);
        assertThat(evse.getLocation()).isNull();
    }
}
