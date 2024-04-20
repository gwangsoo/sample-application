package com.example.bfi.web.rest;

import static com.example.bfi.domain.ConnectorAsserts.*;
import static com.example.bfi.web.rest.TestUtil.createUpdateProxyForBean;
import static com.example.bfi.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.bfi.IntegrationTest;
import com.example.bfi.domain.Connector;
import com.example.bfi.repository.ConnectorRepository;
import com.example.bfi.service.dto.ConnectorDTO;
import com.example.bfi.service.mapper.ConnectorMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Integration tests for the {@link ConnectorResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ConnectorResourceIT {

    private static final Integer DEFAULT_MAX_VOLTAGE = 1;
    private static final Integer UPDATED_MAX_VOLTAGE = 2;

    private static final Integer DEFAULT_MAX_AMPERAGE = 1;
    private static final Integer UPDATED_MAX_AMPERAGE = 2;

    private static final Integer DEFAULT_MAX_ELECTRIC_POWER = 1;
    private static final Integer UPDATED_MAX_ELECTRIC_POWER = 2;

    private static final String DEFAULT_TARIFF_IDS = "AAAAAAAAAA";
    private static final String UPDATED_TARIFF_IDS = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_LAST_UPDATED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LAST_UPDATED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/connectors";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ConnectorRepository connectorRepository;

    @Autowired
    private ConnectorMapper connectorMapper;

    @Autowired
    private MockMvc restConnectorMockMvc;

    private Connector connector;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Connector createEntity() {
        Connector connector = new Connector()
            .maxVoltage(DEFAULT_MAX_VOLTAGE)
            .maxAmperage(DEFAULT_MAX_AMPERAGE)
            .maxElectricPower(DEFAULT_MAX_ELECTRIC_POWER)
            .tariffIds(DEFAULT_TARIFF_IDS)
            .lastUpdated(DEFAULT_LAST_UPDATED);
        return connector;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Connector createUpdatedEntity() {
        Connector connector = new Connector()
            .maxVoltage(UPDATED_MAX_VOLTAGE)
            .maxAmperage(UPDATED_MAX_AMPERAGE)
            .maxElectricPower(UPDATED_MAX_ELECTRIC_POWER)
            .tariffIds(UPDATED_TARIFF_IDS)
            .lastUpdated(UPDATED_LAST_UPDATED);
        return connector;
    }

    @BeforeEach
    public void initTest() {
        connectorRepository.deleteAll();
        connector = createEntity();
    }

    @Test
    void createConnector() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Connector
        ConnectorDTO connectorDTO = connectorMapper.toDto(connector);
        var returnedConnectorDTO = om.readValue(
            restConnectorMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(connectorDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ConnectorDTO.class
        );

        // Validate the Connector in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedConnector = connectorMapper.toEntity(returnedConnectorDTO);
        assertConnectorUpdatableFieldsEquals(returnedConnector, getPersistedConnector(returnedConnector));
    }

    @Test
    void createConnectorWithExistingId() throws Exception {
        // Create the Connector with an existing ID
        connector.setId("existing_id");
        ConnectorDTO connectorDTO = connectorMapper.toDto(connector);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restConnectorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(connectorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Connector in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void getAllConnectors() throws Exception {
        // Initialize the database
        connectorRepository.save(connector);

        // Get all the connectorList
        restConnectorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(connector.getId())))
            .andExpect(jsonPath("$.[*].maxVoltage").value(hasItem(DEFAULT_MAX_VOLTAGE)))
            .andExpect(jsonPath("$.[*].maxAmperage").value(hasItem(DEFAULT_MAX_AMPERAGE)))
            .andExpect(jsonPath("$.[*].maxElectricPower").value(hasItem(DEFAULT_MAX_ELECTRIC_POWER)))
            .andExpect(jsonPath("$.[*].tariffIds").value(hasItem(DEFAULT_TARIFF_IDS)))
            .andExpect(jsonPath("$.[*].lastUpdated").value(hasItem(sameInstant(DEFAULT_LAST_UPDATED))));
    }

    @Test
    void getConnector() throws Exception {
        // Initialize the database
        connectorRepository.save(connector);

        // Get the connector
        restConnectorMockMvc
            .perform(get(ENTITY_API_URL_ID, connector.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(connector.getId()))
            .andExpect(jsonPath("$.maxVoltage").value(DEFAULT_MAX_VOLTAGE))
            .andExpect(jsonPath("$.maxAmperage").value(DEFAULT_MAX_AMPERAGE))
            .andExpect(jsonPath("$.maxElectricPower").value(DEFAULT_MAX_ELECTRIC_POWER))
            .andExpect(jsonPath("$.tariffIds").value(DEFAULT_TARIFF_IDS))
            .andExpect(jsonPath("$.lastUpdated").value(sameInstant(DEFAULT_LAST_UPDATED)));
    }

    @Test
    void getNonExistingConnector() throws Exception {
        // Get the connector
        restConnectorMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingConnector() throws Exception {
        // Initialize the database
        connectorRepository.save(connector);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the connector
        Connector updatedConnector = connectorRepository.findById(connector.getId()).orElseThrow();
        updatedConnector
            .maxVoltage(UPDATED_MAX_VOLTAGE)
            .maxAmperage(UPDATED_MAX_AMPERAGE)
            .maxElectricPower(UPDATED_MAX_ELECTRIC_POWER)
            .tariffIds(UPDATED_TARIFF_IDS)
            .lastUpdated(UPDATED_LAST_UPDATED);
        ConnectorDTO connectorDTO = connectorMapper.toDto(updatedConnector);

        restConnectorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, connectorDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(connectorDTO))
            )
            .andExpect(status().isOk());

        // Validate the Connector in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedConnectorToMatchAllProperties(updatedConnector);
    }

    @Test
    void putNonExistingConnector() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        connector.setId(UUID.randomUUID().toString());

        // Create the Connector
        ConnectorDTO connectorDTO = connectorMapper.toDto(connector);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConnectorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, connectorDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(connectorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Connector in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchConnector() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        connector.setId(UUID.randomUUID().toString());

        // Create the Connector
        ConnectorDTO connectorDTO = connectorMapper.toDto(connector);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConnectorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(connectorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Connector in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamConnector() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        connector.setId(UUID.randomUUID().toString());

        // Create the Connector
        ConnectorDTO connectorDTO = connectorMapper.toDto(connector);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConnectorMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(connectorDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Connector in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateConnectorWithPatch() throws Exception {
        // Initialize the database
        connectorRepository.save(connector);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the connector using partial update
        Connector partialUpdatedConnector = new Connector();
        partialUpdatedConnector.setId(connector.getId());

        partialUpdatedConnector.maxAmperage(UPDATED_MAX_AMPERAGE);

        restConnectorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedConnector.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedConnector))
            )
            .andExpect(status().isOk());

        // Validate the Connector in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertConnectorUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedConnector, connector),
            getPersistedConnector(connector)
        );
    }

    @Test
    void fullUpdateConnectorWithPatch() throws Exception {
        // Initialize the database
        connectorRepository.save(connector);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the connector using partial update
        Connector partialUpdatedConnector = new Connector();
        partialUpdatedConnector.setId(connector.getId());

        partialUpdatedConnector
            .maxVoltage(UPDATED_MAX_VOLTAGE)
            .maxAmperage(UPDATED_MAX_AMPERAGE)
            .maxElectricPower(UPDATED_MAX_ELECTRIC_POWER)
            .tariffIds(UPDATED_TARIFF_IDS)
            .lastUpdated(UPDATED_LAST_UPDATED);

        restConnectorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedConnector.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedConnector))
            )
            .andExpect(status().isOk());

        // Validate the Connector in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertConnectorUpdatableFieldsEquals(partialUpdatedConnector, getPersistedConnector(partialUpdatedConnector));
    }

    @Test
    void patchNonExistingConnector() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        connector.setId(UUID.randomUUID().toString());

        // Create the Connector
        ConnectorDTO connectorDTO = connectorMapper.toDto(connector);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConnectorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, connectorDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(connectorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Connector in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchConnector() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        connector.setId(UUID.randomUUID().toString());

        // Create the Connector
        ConnectorDTO connectorDTO = connectorMapper.toDto(connector);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConnectorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(connectorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Connector in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamConnector() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        connector.setId(UUID.randomUUID().toString());

        // Create the Connector
        ConnectorDTO connectorDTO = connectorMapper.toDto(connector);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConnectorMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(connectorDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Connector in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteConnector() throws Exception {
        // Initialize the database
        connectorRepository.save(connector);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the connector
        restConnectorMockMvc
            .perform(delete(ENTITY_API_URL_ID, connector.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return connectorRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected Connector getPersistedConnector(Connector connector) {
        return connectorRepository.findById(connector.getId()).orElseThrow();
    }

    protected void assertPersistedConnectorToMatchAllProperties(Connector expectedConnector) {
        assertConnectorAllPropertiesEquals(expectedConnector, getPersistedConnector(expectedConnector));
    }

    protected void assertPersistedConnectorToMatchUpdatableProperties(Connector expectedConnector) {
        assertConnectorAllUpdatablePropertiesEquals(expectedConnector, getPersistedConnector(expectedConnector));
    }
}
