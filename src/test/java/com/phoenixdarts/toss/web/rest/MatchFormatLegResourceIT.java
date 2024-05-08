package com.phoenixdarts.toss.backend.web.rest;

import static com.phoenixdarts.toss.backend.domain.MatchFormatLegAsserts.*;
import static com.phoenixdarts.toss.backend.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.phoenixdarts.toss.IntegrationTest;
import com.phoenixdarts.toss.backend.domain.MatchFormatLeg;
import com.phoenixdarts.toss.backend.domain.enumeration.FirstThrowType;
import com.phoenixdarts.toss.backend.domain.enumeration.LegPlayMode;
import com.phoenixdarts.toss.backend.repository.MatchFormatLegRepository;
import com.phoenixdarts.toss.backend.service.dto.MatchFormatLegDTO;
import com.phoenixdarts.toss.backend.service.mapper.MatchFormatLegMapper;
import jakarta.persistence.EntityManager;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link MatchFormatLegResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MatchFormatLegResourceIT {

    private static final Integer DEFAULT_SEQ = 1;
    private static final Integer UPDATED_SEQ = 2;

    private static final FirstThrowType DEFAULT_FIRST_THROW_TYPE = FirstThrowType.COIN_TOSS;
    private static final FirstThrowType UPDATED_FIRST_THROW_TYPE = FirstThrowType.CENTER_CORK;

    private static final LegPlayMode DEFAULT_PLAY_MODE = LegPlayMode.SINGLE;
    private static final LegPlayMode UPDATED_PLAY_MODE = LegPlayMode.DOUBLE;

    private static final String ENTITY_API_URL = "/api/match-format-legs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private MatchFormatLegRepository matchFormatLegRepository;

    @Autowired
    private MatchFormatLegMapper matchFormatLegMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMatchFormatLegMockMvc;

    private MatchFormatLeg matchFormatLeg;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MatchFormatLeg createEntity(EntityManager em) {
        MatchFormatLeg matchFormatLeg = new MatchFormatLeg()
            .seq(DEFAULT_SEQ)
            .firstThrowType(DEFAULT_FIRST_THROW_TYPE)
            .playMode(DEFAULT_PLAY_MODE);
        return matchFormatLeg;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MatchFormatLeg createUpdatedEntity(EntityManager em) {
        MatchFormatLeg matchFormatLeg = new MatchFormatLeg()
            .seq(UPDATED_SEQ)
            .firstThrowType(UPDATED_FIRST_THROW_TYPE)
            .playMode(UPDATED_PLAY_MODE);
        return matchFormatLeg;
    }

    @BeforeEach
    public void initTest() {
        matchFormatLeg = createEntity(em);
    }

    @Test
    @Transactional
    void createMatchFormatLeg() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the MatchFormatLeg
        MatchFormatLegDTO matchFormatLegDTO = matchFormatLegMapper.toDto(matchFormatLeg);
        var returnedMatchFormatLegDTO = om.readValue(
            restMatchFormatLegMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(matchFormatLegDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            MatchFormatLegDTO.class
        );

        // Validate the MatchFormatLeg in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedMatchFormatLeg = matchFormatLegMapper.toEntity(returnedMatchFormatLegDTO);
        assertMatchFormatLegUpdatableFieldsEquals(returnedMatchFormatLeg, getPersistedMatchFormatLeg(returnedMatchFormatLeg));
    }

    @Test
    @Transactional
    void createMatchFormatLegWithExistingId() throws Exception {
        // Create the MatchFormatLeg with an existing ID
        matchFormatLeg.setId("existing_id");
        MatchFormatLegDTO matchFormatLegDTO = matchFormatLegMapper.toDto(matchFormatLeg);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMatchFormatLegMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(matchFormatLegDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MatchFormatLeg in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkFirstThrowTypeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        matchFormatLeg.setFirstThrowType(null);

        // Create the MatchFormatLeg, which fails.
        MatchFormatLegDTO matchFormatLegDTO = matchFormatLegMapper.toDto(matchFormatLeg);

        restMatchFormatLegMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(matchFormatLegDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllMatchFormatLegs() throws Exception {
        // Initialize the database
        matchFormatLegRepository.saveAndFlush(matchFormatLeg);

        // Get all the matchFormatLegList
        restMatchFormatLegMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(matchFormatLeg.getId())))
            .andExpect(jsonPath("$.[*].seq").value(hasItem(DEFAULT_SEQ)))
            .andExpect(jsonPath("$.[*].firstThrowType").value(hasItem(DEFAULT_FIRST_THROW_TYPE.toString())))
            .andExpect(jsonPath("$.[*].playMode").value(hasItem(DEFAULT_PLAY_MODE.toString())));
    }

    @Test
    @Transactional
    void getMatchFormatLeg() throws Exception {
        // Initialize the database
        matchFormatLegRepository.saveAndFlush(matchFormatLeg);

        // Get the matchFormatLeg
        restMatchFormatLegMockMvc
            .perform(get(ENTITY_API_URL_ID, matchFormatLeg.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(matchFormatLeg.getId()))
            .andExpect(jsonPath("$.seq").value(DEFAULT_SEQ))
            .andExpect(jsonPath("$.firstThrowType").value(DEFAULT_FIRST_THROW_TYPE.toString()))
            .andExpect(jsonPath("$.playMode").value(DEFAULT_PLAY_MODE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingMatchFormatLeg() throws Exception {
        // Get the matchFormatLeg
        restMatchFormatLegMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMatchFormatLeg() throws Exception {
        // Initialize the database
        matchFormatLegRepository.saveAndFlush(matchFormatLeg);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the matchFormatLeg
        MatchFormatLeg updatedMatchFormatLeg = matchFormatLegRepository.findById(matchFormatLeg.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedMatchFormatLeg are not directly saved in db
        em.detach(updatedMatchFormatLeg);
        updatedMatchFormatLeg.seq(UPDATED_SEQ).firstThrowType(UPDATED_FIRST_THROW_TYPE).playMode(UPDATED_PLAY_MODE);
        MatchFormatLegDTO matchFormatLegDTO = matchFormatLegMapper.toDto(updatedMatchFormatLeg);

        restMatchFormatLegMockMvc
            .perform(
                put(ENTITY_API_URL_ID, matchFormatLegDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(matchFormatLegDTO))
            )
            .andExpect(status().isOk());

        // Validate the MatchFormatLeg in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedMatchFormatLegToMatchAllProperties(updatedMatchFormatLeg);
    }

    @Test
    @Transactional
    void putNonExistingMatchFormatLeg() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        matchFormatLeg.setId(UUID.randomUUID().toString());

        // Create the MatchFormatLeg
        MatchFormatLegDTO matchFormatLegDTO = matchFormatLegMapper.toDto(matchFormatLeg);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMatchFormatLegMockMvc
            .perform(
                put(ENTITY_API_URL_ID, matchFormatLegDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(matchFormatLegDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MatchFormatLeg in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMatchFormatLeg() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        matchFormatLeg.setId(UUID.randomUUID().toString());

        // Create the MatchFormatLeg
        MatchFormatLegDTO matchFormatLegDTO = matchFormatLegMapper.toDto(matchFormatLeg);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMatchFormatLegMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(matchFormatLegDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MatchFormatLeg in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMatchFormatLeg() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        matchFormatLeg.setId(UUID.randomUUID().toString());

        // Create the MatchFormatLeg
        MatchFormatLegDTO matchFormatLegDTO = matchFormatLegMapper.toDto(matchFormatLeg);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMatchFormatLegMockMvc
            .perform(
                put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(matchFormatLegDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MatchFormatLeg in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMatchFormatLegWithPatch() throws Exception {
        // Initialize the database
        matchFormatLegRepository.saveAndFlush(matchFormatLeg);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the matchFormatLeg using partial update
        MatchFormatLeg partialUpdatedMatchFormatLeg = new MatchFormatLeg();
        partialUpdatedMatchFormatLeg.setId(matchFormatLeg.getId());

        partialUpdatedMatchFormatLeg.seq(UPDATED_SEQ).playMode(UPDATED_PLAY_MODE);

        restMatchFormatLegMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMatchFormatLeg.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMatchFormatLeg))
            )
            .andExpect(status().isOk());

        // Validate the MatchFormatLeg in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMatchFormatLegUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedMatchFormatLeg, matchFormatLeg),
            getPersistedMatchFormatLeg(matchFormatLeg)
        );
    }

    @Test
    @Transactional
    void fullUpdateMatchFormatLegWithPatch() throws Exception {
        // Initialize the database
        matchFormatLegRepository.saveAndFlush(matchFormatLeg);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the matchFormatLeg using partial update
        MatchFormatLeg partialUpdatedMatchFormatLeg = new MatchFormatLeg();
        partialUpdatedMatchFormatLeg.setId(matchFormatLeg.getId());

        partialUpdatedMatchFormatLeg.seq(UPDATED_SEQ).firstThrowType(UPDATED_FIRST_THROW_TYPE).playMode(UPDATED_PLAY_MODE);

        restMatchFormatLegMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMatchFormatLeg.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMatchFormatLeg))
            )
            .andExpect(status().isOk());

        // Validate the MatchFormatLeg in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMatchFormatLegUpdatableFieldsEquals(partialUpdatedMatchFormatLeg, getPersistedMatchFormatLeg(partialUpdatedMatchFormatLeg));
    }

    @Test
    @Transactional
    void patchNonExistingMatchFormatLeg() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        matchFormatLeg.setId(UUID.randomUUID().toString());

        // Create the MatchFormatLeg
        MatchFormatLegDTO matchFormatLegDTO = matchFormatLegMapper.toDto(matchFormatLeg);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMatchFormatLegMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, matchFormatLegDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(matchFormatLegDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MatchFormatLeg in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMatchFormatLeg() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        matchFormatLeg.setId(UUID.randomUUID().toString());

        // Create the MatchFormatLeg
        MatchFormatLegDTO matchFormatLegDTO = matchFormatLegMapper.toDto(matchFormatLeg);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMatchFormatLegMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(matchFormatLegDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MatchFormatLeg in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMatchFormatLeg() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        matchFormatLeg.setId(UUID.randomUUID().toString());

        // Create the MatchFormatLeg
        MatchFormatLegDTO matchFormatLegDTO = matchFormatLegMapper.toDto(matchFormatLeg);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMatchFormatLegMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(matchFormatLegDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MatchFormatLeg in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMatchFormatLeg() throws Exception {
        // Initialize the database
        matchFormatLegRepository.saveAndFlush(matchFormatLeg);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the matchFormatLeg
        restMatchFormatLegMockMvc
            .perform(delete(ENTITY_API_URL_ID, matchFormatLeg.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return matchFormatLegRepository.count();
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

    protected MatchFormatLeg getPersistedMatchFormatLeg(MatchFormatLeg matchFormatLeg) {
        return matchFormatLegRepository.findById(matchFormatLeg.getId()).orElseThrow();
    }

    protected void assertPersistedMatchFormatLegToMatchAllProperties(MatchFormatLeg expectedMatchFormatLeg) {
        assertMatchFormatLegAllPropertiesEquals(expectedMatchFormatLeg, getPersistedMatchFormatLeg(expectedMatchFormatLeg));
    }

    protected void assertPersistedMatchFormatLegToMatchUpdatableProperties(MatchFormatLeg expectedMatchFormatLeg) {
        assertMatchFormatLegAllUpdatablePropertiesEquals(expectedMatchFormatLeg, getPersistedMatchFormatLeg(expectedMatchFormatLeg));
    }
}
