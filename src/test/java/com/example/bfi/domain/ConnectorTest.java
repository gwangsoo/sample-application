package com.example.bfi.domain;

import static com.example.bfi.domain.ConnectorTestSamples.*;
import static com.example.bfi.domain.EvseTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.example.bfi.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ConnectorTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Connector.class);
        Connector connector1 = getConnectorSample1();
        Connector connector2 = new Connector();
        assertThat(connector1).isNotEqualTo(connector2);

        connector2.setId(connector1.getId());
        assertThat(connector1).isEqualTo(connector2);

        connector2 = getConnectorSample2();
        assertThat(connector1).isNotEqualTo(connector2);
    }

    @Test
    void evseTest() throws Exception {
        Connector connector = getConnectorRandomSampleGenerator();
        Evse evseBack = getEvseRandomSampleGenerator();

        connector.setEvse(evseBack);
        assertThat(connector.getEvse()).isEqualTo(evseBack);

        connector.evse(null);
        assertThat(connector.getEvse()).isNull();
    }
}
