package com.example.bfi.web.rest;

import static com.example.bfi.domain.EvseAsserts.*;
import static com.example.bfi.web.rest.TestUtil.createUpdateProxyForBean;
import static com.example.bfi.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.bfi.IntegrationTest;
import com.example.bfi.domain.Evse;
import com.example.bfi.domain.enumeration.EvseStatus;
import com.example.bfi.repository.EvseRepository;
import com.example.bfi.service.dto.EvseDTO;
import com.example.bfi.service.mapper.EvseMapper;
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
 * Integration tests for the {@link EvseResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EvseResourceIT {

    private static final String DEFAULT_UID = "AAAAAAAAAA";
    private static final String UPDATED_UID = "BBBBBBBBBB";

    private static final String DEFAULT_EVSE_ID = "AAAAAAAAAA";
    private static final String UPDATED_EVSE_ID = "BBBBBBBBBB";

    private static final EvseStatus DEFAULT_STATUS = EvseStatus.AVAILABLE;
    private static final EvseStatus UPDATED_STATUS = EvseStatus.BLOCKED;

    private static final String DEFAULT_DIRECTIONS = "AAAAAAAAAA";
    private static final String UPDATED_DIRECTIONS = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_LAST_UPDATED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LAST_UPDATED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/evses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private EvseRepository evseRepository;

    @Autowired
    private EvseMapper evseMapper;

    @Autowired
    private MockMvc restEvseMockMvc;

    private Evse evse;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Evse createEntity() {
        Evse evse = new Evse()
            .uid(DEFAULT_UID)
            .evseId(DEFAULT_EVSE_ID)
            .status(DEFAULT_STATUS)
            .directions(DEFAULT_DIRECTIONS)
            .lastUpdated(DEFAULT_LAST_UPDATED);
        return evse;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Evse createUpdatedEntity() {
        Evse evse = new Evse()
            .uid(UPDATED_UID)
            .evseId(UPDATED_EVSE_ID)
            .status(UPDATED_STATUS)
            .directions(UPDATED_DIRECTIONS)
            .lastUpdated(UPDATED_LAST_UPDATED);
        return evse;
    }

    @BeforeEach
    public void initTest() {
        evseRepository.deleteAll();
        evse = createEntity();
    }

    @Test
    void createEvse() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Evse
        EvseDTO evseDTO = evseMapper.toDto(evse);
        var returnedEvseDTO = om.readValue(
            restEvseMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(evseDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            EvseDTO.class
        );

        // Validate the Evse in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedEvse = evseMapper.toEntity(returnedEvseDTO);
        assertEvseUpdatableFieldsEquals(returnedEvse, getPersistedEvse(returnedEvse));
    }

    @Test
    void createEvseWithExistingId() throws Exception {
        // Create the Evse with an existing ID
        evse.setId("existing_id");
        EvseDTO evseDTO = evseMapper.toDto(evse);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEvseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(evseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Evse in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void checkUidIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        evse.setUid(null);

        // Create the Evse, which fails.
        EvseDTO evseDTO = evseMapper.toDto(evse);

        restEvseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(evseDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void getAllEvses() throws Exception {
        // Initialize the database
        evseRepository.save(evse);

        // Get all the evseList
        restEvseMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(evse.getId())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID)))
            .andExpect(jsonPath("$.[*].evseId").value(hasItem(DEFAULT_EVSE_ID)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].directions").value(hasItem(DEFAULT_DIRECTIONS)))
            .andExpect(jsonPath("$.[*].lastUpdated").value(hasItem(sameInstant(DEFAULT_LAST_UPDATED))));
    }

    @Test
    void getEvse() throws Exception {
        // Initialize the database
        evseRepository.save(evse);

        // Get the evse
        restEvseMockMvc
            .perform(get(ENTITY_API_URL_ID, evse.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(evse.getId()))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID))
            .andExpect(jsonPath("$.evseId").value(DEFAULT_EVSE_ID))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.directions").value(DEFAULT_DIRECTIONS))
            .andExpect(jsonPath("$.lastUpdated").value(sameInstant(DEFAULT_LAST_UPDATED)));
    }

    @Test
    void getNonExistingEvse() throws Exception {
        // Get the evse
        restEvseMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingEvse() throws Exception {
        // Initialize the database
        evseRepository.save(evse);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the evse
        Evse updatedEvse = evseRepository.findById(evse.getId()).orElseThrow();
        updatedEvse
            .uid(UPDATED_UID)
            .evseId(UPDATED_EVSE_ID)
            .status(UPDATED_STATUS)
            .directions(UPDATED_DIRECTIONS)
            .lastUpdated(UPDATED_LAST_UPDATED);
        EvseDTO evseDTO = evseMapper.toDto(updatedEvse);

        restEvseMockMvc
            .perform(put(ENTITY_API_URL_ID, evseDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(evseDTO)))
            .andExpect(status().isOk());

        // Validate the Evse in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedEvseToMatchAllProperties(updatedEvse);
    }

    @Test
    void putNonExistingEvse() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        evse.setId(UUID.randomUUID().toString());

        // Create the Evse
        EvseDTO evseDTO = evseMapper.toDto(evse);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEvseMockMvc
            .perform(put(ENTITY_API_URL_ID, evseDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(evseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Evse in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchEvse() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        evse.setId(UUID.randomUUID().toString());

        // Create the Evse
        EvseDTO evseDTO = evseMapper.toDto(evse);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEvseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(evseDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Evse in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamEvse() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        evse.setId(UUID.randomUUID().toString());

        // Create the Evse
        EvseDTO evseDTO = evseMapper.toDto(evse);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEvseMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(evseDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Evse in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateEvseWithPatch() throws Exception {
        // Initialize the database
        evseRepository.save(evse);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the evse using partial update
        Evse partialUpdatedEvse = new Evse();
        partialUpdatedEvse.setId(evse.getId());

        partialUpdatedEvse.evseId(UPDATED_EVSE_ID).status(UPDATED_STATUS).lastUpdated(UPDATED_LAST_UPDATED);

        restEvseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEvse.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEvse))
            )
            .andExpect(status().isOk());

        // Validate the Evse in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEvseUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedEvse, evse), getPersistedEvse(evse));
    }

    @Test
    void fullUpdateEvseWithPatch() throws Exception {
        // Initialize the database
        evseRepository.save(evse);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the evse using partial update
        Evse partialUpdatedEvse = new Evse();
        partialUpdatedEvse.setId(evse.getId());

        partialUpdatedEvse
            .uid(UPDATED_UID)
            .evseId(UPDATED_EVSE_ID)
            .status(UPDATED_STATUS)
            .directions(UPDATED_DIRECTIONS)
            .lastUpdated(UPDATED_LAST_UPDATED);

        restEvseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEvse.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEvse))
            )
            .andExpect(status().isOk());

        // Validate the Evse in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEvseUpdatableFieldsEquals(partialUpdatedEvse, getPersistedEvse(partialUpdatedEvse));
    }

    @Test
    void patchNonExistingEvse() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        evse.setId(UUID.randomUUID().toString());

        // Create the Evse
        EvseDTO evseDTO = evseMapper.toDto(evse);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEvseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, evseDTO.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(evseDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Evse in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchEvse() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        evse.setId(UUID.randomUUID().toString());

        // Create the Evse
        EvseDTO evseDTO = evseMapper.toDto(evse);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEvseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(evseDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Evse in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamEvse() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        evse.setId(UUID.randomUUID().toString());

        // Create the Evse
        EvseDTO evseDTO = evseMapper.toDto(evse);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEvseMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(evseDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Evse in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteEvse() throws Exception {
        // Initialize the database
        evseRepository.save(evse);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the evse
        restEvseMockMvc
            .perform(delete(ENTITY_API_URL_ID, evse.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return evseRepository.count();
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

    protected Evse getPersistedEvse(Evse evse) {
        return evseRepository.findById(evse.getId()).orElseThrow();
    }

    protected void assertPersistedEvseToMatchAllProperties(Evse expectedEvse) {
        assertEvseAllPropertiesEquals(expectedEvse, getPersistedEvse(expectedEvse));
    }

    protected void assertPersistedEvseToMatchUpdatableProperties(Evse expectedEvse) {
        assertEvseAllUpdatablePropertiesEquals(expectedEvse, getPersistedEvse(expectedEvse));
    }
}
