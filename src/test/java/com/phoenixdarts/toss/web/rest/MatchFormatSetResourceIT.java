package com.phoenixdarts.toss.backend.web.rest;

import static com.phoenixdarts.toss.backend.domain.MatchFormatSetAsserts.*;
import static com.phoenixdarts.toss.backend.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.phoenixdarts.toss.IntegrationTest;
import com.phoenixdarts.toss.backend.domain.MatchFormatSet;
import com.phoenixdarts.toss.backend.repository.MatchFormatSetRepository;
import com.phoenixdarts.toss.backend.service.dto.MatchFormatSetDTO;
import com.phoenixdarts.toss.backend.service.mapper.MatchFormatSetMapper;
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
 * Integration tests for the {@link MatchFormatSetResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MatchFormatSetResourceIT {

    private static final Integer DEFAULT_POINT = 1;
    private static final Integer UPDATED_POINT = 2;

    private static final String ENTITY_API_URL = "/api/match-format-sets";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private MatchFormatSetRepository matchFormatSetRepository;

    @Autowired
    private MatchFormatSetMapper matchFormatSetMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMatchFormatSetMockMvc;

    private MatchFormatSet matchFormatSet;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MatchFormatSet createEntity(EntityManager em) {
        MatchFormatSet matchFormatSet = new MatchFormatSet().point(DEFAULT_POINT);
        return matchFormatSet;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MatchFormatSet createUpdatedEntity(EntityManager em) {
        MatchFormatSet matchFormatSet = new MatchFormatSet().point(UPDATED_POINT);
        return matchFormatSet;
    }

    @BeforeEach
    public void initTest() {
        matchFormatSet = createEntity(em);
    }

    @Test
    @Transactional
    void createMatchFormatSet() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the MatchFormatSet
        MatchFormatSetDTO matchFormatSetDTO = matchFormatSetMapper.toDto(matchFormatSet);
        var returnedMatchFormatSetDTO = om.readValue(
            restMatchFormatSetMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(matchFormatSetDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            MatchFormatSetDTO.class
        );

        // Validate the MatchFormatSet in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedMatchFormatSet = matchFormatSetMapper.toEntity(returnedMatchFormatSetDTO);
        assertMatchFormatSetUpdatableFieldsEquals(returnedMatchFormatSet, getPersistedMatchFormatSet(returnedMatchFormatSet));
    }

    @Test
    @Transactional
    void createMatchFormatSetWithExistingId() throws Exception {
        // Create the MatchFormatSet with an existing ID
        matchFormatSet.setId("existing_id");
        MatchFormatSetDTO matchFormatSetDTO = matchFormatSetMapper.toDto(matchFormatSet);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMatchFormatSetMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(matchFormatSetDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MatchFormatSet in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkPointIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        matchFormatSet.setPoint(null);

        // Create the MatchFormatSet, which fails.
        MatchFormatSetDTO matchFormatSetDTO = matchFormatSetMapper.toDto(matchFormatSet);

        restMatchFormatSetMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(matchFormatSetDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllMatchFormatSets() throws Exception {
        // Initialize the database
        matchFormatSetRepository.saveAndFlush(matchFormatSet);

        // Get all the matchFormatSetList
        restMatchFormatSetMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(matchFormatSet.getId())))
            .andExpect(jsonPath("$.[*].point").value(hasItem(DEFAULT_POINT)));
    }

    @Test
    @Transactional
    void getMatchFormatSet() throws Exception {
        // Initialize the database
        matchFormatSetRepository.saveAndFlush(matchFormatSet);

        // Get the matchFormatSet
        restMatchFormatSetMockMvc
            .perform(get(ENTITY_API_URL_ID, matchFormatSet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(matchFormatSet.getId()))
            .andExpect(jsonPath("$.point").value(DEFAULT_POINT));
    }

    @Test
    @Transactional
    void getNonExistingMatchFormatSet() throws Exception {
        // Get the matchFormatSet
        restMatchFormatSetMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMatchFormatSet() throws Exception {
        // Initialize the database
        matchFormatSetRepository.saveAndFlush(matchFormatSet);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the matchFormatSet
        MatchFormatSet updatedMatchFormatSet = matchFormatSetRepository.findById(matchFormatSet.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedMatchFormatSet are not directly saved in db
        em.detach(updatedMatchFormatSet);
        updatedMatchFormatSet.point(UPDATED_POINT);
        MatchFormatSetDTO matchFormatSetDTO = matchFormatSetMapper.toDto(updatedMatchFormatSet);

        restMatchFormatSetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, matchFormatSetDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(matchFormatSetDTO))
            )
            .andExpect(status().isOk());

        // Validate the MatchFormatSet in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedMatchFormatSetToMatchAllProperties(updatedMatchFormatSet);
    }

    @Test
    @Transactional
    void putNonExistingMatchFormatSet() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        matchFormatSet.setId(UUID.randomUUID().toString());

        // Create the MatchFormatSet
        MatchFormatSetDTO matchFormatSetDTO = matchFormatSetMapper.toDto(matchFormatSet);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMatchFormatSetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, matchFormatSetDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(matchFormatSetDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MatchFormatSet in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMatchFormatSet() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        matchFormatSet.setId(UUID.randomUUID().toString());

        // Create the MatchFormatSet
        MatchFormatSetDTO matchFormatSetDTO = matchFormatSetMapper.toDto(matchFormatSet);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMatchFormatSetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(matchFormatSetDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MatchFormatSet in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMatchFormatSet() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        matchFormatSet.setId(UUID.randomUUID().toString());

        // Create the MatchFormatSet
        MatchFormatSetDTO matchFormatSetDTO = matchFormatSetMapper.toDto(matchFormatSet);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMatchFormatSetMockMvc
            .perform(
                put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(matchFormatSetDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MatchFormatSet in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMatchFormatSetWithPatch() throws Exception {
        // Initialize the database
        matchFormatSetRepository.saveAndFlush(matchFormatSet);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the matchFormatSet using partial update
        MatchFormatSet partialUpdatedMatchFormatSet = new MatchFormatSet();
        partialUpdatedMatchFormatSet.setId(matchFormatSet.getId());

        restMatchFormatSetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMatchFormatSet.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMatchFormatSet))
            )
            .andExpect(status().isOk());

        // Validate the MatchFormatSet in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMatchFormatSetUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedMatchFormatSet, matchFormatSet),
            getPersistedMatchFormatSet(matchFormatSet)
        );
    }

    @Test
    @Transactional
    void fullUpdateMatchFormatSetWithPatch() throws Exception {
        // Initialize the database
        matchFormatSetRepository.saveAndFlush(matchFormatSet);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the matchFormatSet using partial update
        MatchFormatSet partialUpdatedMatchFormatSet = new MatchFormatSet();
        partialUpdatedMatchFormatSet.setId(matchFormatSet.getId());

        partialUpdatedMatchFormatSet.point(UPDATED_POINT);

        restMatchFormatSetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMatchFormatSet.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMatchFormatSet))
            )
            .andExpect(status().isOk());

        // Validate the MatchFormatSet in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMatchFormatSetUpdatableFieldsEquals(partialUpdatedMatchFormatSet, getPersistedMatchFormatSet(partialUpdatedMatchFormatSet));
    }

    @Test
    @Transactional
    void patchNonExistingMatchFormatSet() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        matchFormatSet.setId(UUID.randomUUID().toString());

        // Create the MatchFormatSet
        MatchFormatSetDTO matchFormatSetDTO = matchFormatSetMapper.toDto(matchFormatSet);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMatchFormatSetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, matchFormatSetDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(matchFormatSetDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MatchFormatSet in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMatchFormatSet() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        matchFormatSet.setId(UUID.randomUUID().toString());

        // Create the MatchFormatSet
        MatchFormatSetDTO matchFormatSetDTO = matchFormatSetMapper.toDto(matchFormatSet);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMatchFormatSetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(matchFormatSetDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MatchFormatSet in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMatchFormatSet() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        matchFormatSet.setId(UUID.randomUUID().toString());

        // Create the MatchFormatSet
        MatchFormatSetDTO matchFormatSetDTO = matchFormatSetMapper.toDto(matchFormatSet);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMatchFormatSetMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(matchFormatSetDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MatchFormatSet in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMatchFormatSet() throws Exception {
        // Initialize the database
        matchFormatSetRepository.saveAndFlush(matchFormatSet);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the matchFormatSet
        restMatchFormatSetMockMvc
            .perform(delete(ENTITY_API_URL_ID, matchFormatSet.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return matchFormatSetRepository.count();
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

    protected MatchFormatSet getPersistedMatchFormatSet(MatchFormatSet matchFormatSet) {
        return matchFormatSetRepository.findById(matchFormatSet.getId()).orElseThrow();
    }

    protected void assertPersistedMatchFormatSetToMatchAllProperties(MatchFormatSet expectedMatchFormatSet) {
        assertMatchFormatSetAllPropertiesEquals(expectedMatchFormatSet, getPersistedMatchFormatSet(expectedMatchFormatSet));
    }

    protected void assertPersistedMatchFormatSetToMatchUpdatableProperties(MatchFormatSet expectedMatchFormatSet) {
        assertMatchFormatSetAllUpdatablePropertiesEquals(expectedMatchFormatSet, getPersistedMatchFormatSet(expectedMatchFormatSet));
    }
}
