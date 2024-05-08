package com.phoenixdarts.toss.backend.web.rest;

import static com.phoenixdarts.toss.backend.domain.MatchFormatAsserts.*;
import static com.phoenixdarts.toss.backend.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.phoenixdarts.toss.IntegrationTest;
import com.phoenixdarts.toss.backend.domain.MatchFormat;
import com.phoenixdarts.toss.backend.domain.enumeration.FirstThrowType;
import com.phoenixdarts.toss.backend.domain.enumeration.FirstThrowType;
import com.phoenixdarts.toss.backend.domain.enumeration.FirstThrowType;
import com.phoenixdarts.toss.backend.domain.enumeration.MatchFormatType;
import com.phoenixdarts.toss.backend.repository.MatchFormatRepository;
import com.phoenixdarts.toss.backend.service.dto.MatchFormatDTO;
import com.phoenixdarts.toss.backend.service.mapper.MatchFormatMapper;
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
 * Integration tests for the {@link MatchFormatResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MatchFormatResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final MatchFormatType DEFAULT_MATCH_FORMAT_TYPE = MatchFormatType.GENERAL;
    private static final MatchFormatType UPDATED_MATCH_FORMAT_TYPE = MatchFormatType.TEMPLATE;

    private static final FirstThrowType DEFAULT_FIRST_SET = FirstThrowType.COIN_TOSS;
    private static final FirstThrowType UPDATED_FIRST_SET = FirstThrowType.CENTER_CORK;

    private static final FirstThrowType DEFAULT_MIDDLE_SET = FirstThrowType.COIN_TOSS;
    private static final FirstThrowType UPDATED_MIDDLE_SET = FirstThrowType.CENTER_CORK;

    private static final FirstThrowType DEFAULT_LAST_SET = FirstThrowType.COIN_TOSS;
    private static final FirstThrowType UPDATED_LAST_SET = FirstThrowType.CENTER_CORK;

    private static final String ENTITY_API_URL = "/api/match-formats";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private MatchFormatRepository matchFormatRepository;

    @Autowired
    private MatchFormatMapper matchFormatMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMatchFormatMockMvc;

    private MatchFormat matchFormat;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MatchFormat createEntity(EntityManager em) {
        MatchFormat matchFormat = new MatchFormat()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .matchFormatType(DEFAULT_MATCH_FORMAT_TYPE)
            .firstSet(DEFAULT_FIRST_SET)
            .middleSet(DEFAULT_MIDDLE_SET)
            .lastSet(DEFAULT_LAST_SET);
        return matchFormat;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MatchFormat createUpdatedEntity(EntityManager em) {
        MatchFormat matchFormat = new MatchFormat()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .matchFormatType(UPDATED_MATCH_FORMAT_TYPE)
            .firstSet(UPDATED_FIRST_SET)
            .middleSet(UPDATED_MIDDLE_SET)
            .lastSet(UPDATED_LAST_SET);
        return matchFormat;
    }

    @BeforeEach
    public void initTest() {
        matchFormat = createEntity(em);
    }

    @Test
    @Transactional
    void createMatchFormat() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the MatchFormat
        MatchFormatDTO matchFormatDTO = matchFormatMapper.toDto(matchFormat);
        var returnedMatchFormatDTO = om.readValue(
            restMatchFormatMockMvc
                .perform(
                    post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(matchFormatDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            MatchFormatDTO.class
        );

        // Validate the MatchFormat in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedMatchFormat = matchFormatMapper.toEntity(returnedMatchFormatDTO);
        assertMatchFormatUpdatableFieldsEquals(returnedMatchFormat, getPersistedMatchFormat(returnedMatchFormat));
    }

    @Test
    @Transactional
    void createMatchFormatWithExistingId() throws Exception {
        // Create the MatchFormat with an existing ID
        matchFormat.setId("existing_id");
        MatchFormatDTO matchFormatDTO = matchFormatMapper.toDto(matchFormat);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMatchFormatMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(matchFormatDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MatchFormat in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        matchFormat.setName(null);

        // Create the MatchFormat, which fails.
        MatchFormatDTO matchFormatDTO = matchFormatMapper.toDto(matchFormat);

        restMatchFormatMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(matchFormatDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDescriptionIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        matchFormat.setDescription(null);

        // Create the MatchFormat, which fails.
        MatchFormatDTO matchFormatDTO = matchFormatMapper.toDto(matchFormat);

        restMatchFormatMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(matchFormatDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMatchFormatTypeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        matchFormat.setMatchFormatType(null);

        // Create the MatchFormat, which fails.
        MatchFormatDTO matchFormatDTO = matchFormatMapper.toDto(matchFormat);

        restMatchFormatMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(matchFormatDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllMatchFormats() throws Exception {
        // Initialize the database
        matchFormatRepository.saveAndFlush(matchFormat);

        // Get all the matchFormatList
        restMatchFormatMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(matchFormat.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].matchFormatType").value(hasItem(DEFAULT_MATCH_FORMAT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].firstSet").value(hasItem(DEFAULT_FIRST_SET.toString())))
            .andExpect(jsonPath("$.[*].middleSet").value(hasItem(DEFAULT_MIDDLE_SET.toString())))
            .andExpect(jsonPath("$.[*].lastSet").value(hasItem(DEFAULT_LAST_SET.toString())));
    }

    @Test
    @Transactional
    void getMatchFormat() throws Exception {
        // Initialize the database
        matchFormatRepository.saveAndFlush(matchFormat);

        // Get the matchFormat
        restMatchFormatMockMvc
            .perform(get(ENTITY_API_URL_ID, matchFormat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(matchFormat.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.matchFormatType").value(DEFAULT_MATCH_FORMAT_TYPE.toString()))
            .andExpect(jsonPath("$.firstSet").value(DEFAULT_FIRST_SET.toString()))
            .andExpect(jsonPath("$.middleSet").value(DEFAULT_MIDDLE_SET.toString()))
            .andExpect(jsonPath("$.lastSet").value(DEFAULT_LAST_SET.toString()));
    }

    @Test
    @Transactional
    void getNonExistingMatchFormat() throws Exception {
        // Get the matchFormat
        restMatchFormatMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMatchFormat() throws Exception {
        // Initialize the database
        matchFormatRepository.saveAndFlush(matchFormat);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the matchFormat
        MatchFormat updatedMatchFormat = matchFormatRepository.findById(matchFormat.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedMatchFormat are not directly saved in db
        em.detach(updatedMatchFormat);
        updatedMatchFormat
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .matchFormatType(UPDATED_MATCH_FORMAT_TYPE)
            .firstSet(UPDATED_FIRST_SET)
            .middleSet(UPDATED_MIDDLE_SET)
            .lastSet(UPDATED_LAST_SET);
        MatchFormatDTO matchFormatDTO = matchFormatMapper.toDto(updatedMatchFormat);

        restMatchFormatMockMvc
            .perform(
                put(ENTITY_API_URL_ID, matchFormatDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(matchFormatDTO))
            )
            .andExpect(status().isOk());

        // Validate the MatchFormat in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedMatchFormatToMatchAllProperties(updatedMatchFormat);
    }

    @Test
    @Transactional
    void putNonExistingMatchFormat() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        matchFormat.setId(UUID.randomUUID().toString());

        // Create the MatchFormat
        MatchFormatDTO matchFormatDTO = matchFormatMapper.toDto(matchFormat);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMatchFormatMockMvc
            .perform(
                put(ENTITY_API_URL_ID, matchFormatDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(matchFormatDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MatchFormat in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMatchFormat() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        matchFormat.setId(UUID.randomUUID().toString());

        // Create the MatchFormat
        MatchFormatDTO matchFormatDTO = matchFormatMapper.toDto(matchFormat);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMatchFormatMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(matchFormatDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MatchFormat in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMatchFormat() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        matchFormat.setId(UUID.randomUUID().toString());

        // Create the MatchFormat
        MatchFormatDTO matchFormatDTO = matchFormatMapper.toDto(matchFormat);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMatchFormatMockMvc
            .perform(put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(matchFormatDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the MatchFormat in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMatchFormatWithPatch() throws Exception {
        // Initialize the database
        matchFormatRepository.saveAndFlush(matchFormat);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the matchFormat using partial update
        MatchFormat partialUpdatedMatchFormat = new MatchFormat();
        partialUpdatedMatchFormat.setId(matchFormat.getId());

        partialUpdatedMatchFormat
            .matchFormatType(UPDATED_MATCH_FORMAT_TYPE)
            .firstSet(UPDATED_FIRST_SET)
            .middleSet(UPDATED_MIDDLE_SET)
            .lastSet(UPDATED_LAST_SET);

        restMatchFormatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMatchFormat.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMatchFormat))
            )
            .andExpect(status().isOk());

        // Validate the MatchFormat in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMatchFormatUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedMatchFormat, matchFormat),
            getPersistedMatchFormat(matchFormat)
        );
    }

    @Test
    @Transactional
    void fullUpdateMatchFormatWithPatch() throws Exception {
        // Initialize the database
        matchFormatRepository.saveAndFlush(matchFormat);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the matchFormat using partial update
        MatchFormat partialUpdatedMatchFormat = new MatchFormat();
        partialUpdatedMatchFormat.setId(matchFormat.getId());

        partialUpdatedMatchFormat
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .matchFormatType(UPDATED_MATCH_FORMAT_TYPE)
            .firstSet(UPDATED_FIRST_SET)
            .middleSet(UPDATED_MIDDLE_SET)
            .lastSet(UPDATED_LAST_SET);

        restMatchFormatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMatchFormat.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMatchFormat))
            )
            .andExpect(status().isOk());

        // Validate the MatchFormat in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMatchFormatUpdatableFieldsEquals(partialUpdatedMatchFormat, getPersistedMatchFormat(partialUpdatedMatchFormat));
    }

    @Test
    @Transactional
    void patchNonExistingMatchFormat() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        matchFormat.setId(UUID.randomUUID().toString());

        // Create the MatchFormat
        MatchFormatDTO matchFormatDTO = matchFormatMapper.toDto(matchFormat);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMatchFormatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, matchFormatDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(matchFormatDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MatchFormat in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMatchFormat() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        matchFormat.setId(UUID.randomUUID().toString());

        // Create the MatchFormat
        MatchFormatDTO matchFormatDTO = matchFormatMapper.toDto(matchFormat);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMatchFormatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(matchFormatDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MatchFormat in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMatchFormat() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        matchFormat.setId(UUID.randomUUID().toString());

        // Create the MatchFormat
        MatchFormatDTO matchFormatDTO = matchFormatMapper.toDto(matchFormat);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMatchFormatMockMvc
            .perform(
                patch(ENTITY_API_URL).with(csrf()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(matchFormatDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MatchFormat in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMatchFormat() throws Exception {
        // Initialize the database
        matchFormatRepository.saveAndFlush(matchFormat);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the matchFormat
        restMatchFormatMockMvc
            .perform(delete(ENTITY_API_URL_ID, matchFormat.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return matchFormatRepository.count();
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

    protected MatchFormat getPersistedMatchFormat(MatchFormat matchFormat) {
        return matchFormatRepository.findById(matchFormat.getId()).orElseThrow();
    }

    protected void assertPersistedMatchFormatToMatchAllProperties(MatchFormat expectedMatchFormat) {
        assertMatchFormatAllPropertiesEquals(expectedMatchFormat, getPersistedMatchFormat(expectedMatchFormat));
    }

    protected void assertPersistedMatchFormatToMatchUpdatableProperties(MatchFormat expectedMatchFormat) {
        assertMatchFormatAllUpdatablePropertiesEquals(expectedMatchFormat, getPersistedMatchFormat(expectedMatchFormat));
    }
}
