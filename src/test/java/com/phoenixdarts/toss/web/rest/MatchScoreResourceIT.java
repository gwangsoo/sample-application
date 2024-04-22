package com.phoenixdarts.toss.web.rest;

import static com.phoenixdarts.toss.domain.MatchScoreAsserts.*;
import static com.phoenixdarts.toss.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.phoenixdarts.toss.IntegrationTest;
import com.phoenixdarts.toss.domain.MatchScore;
import com.phoenixdarts.toss.repository.MatchScoreRepository;
import com.phoenixdarts.toss.service.dto.MatchScoreDTO;
import com.phoenixdarts.toss.service.mapper.MatchScoreMapper;
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
 * Integration tests for the {@link MatchScoreResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MatchScoreResourceIT {

    private static final Integer DEFAULT_SET_NO = 1;
    private static final Integer UPDATED_SET_NO = 2;

    private static final Integer DEFAULT_LGE_NO = 1;
    private static final Integer UPDATED_LGE_NO = 2;

    private static final String DEFAULT_LEG_GAME_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LEG_GAME_NAME = "BBBBBBBBBB";

    private static final Float DEFAULT_HOME_LEG_SCORE = 1F;
    private static final Float UPDATED_HOME_LEG_SCORE = 2F;

    private static final Float DEFAULT_AWAY_LEG_SCORE = 1F;
    private static final Float UPDATED_AWAY_LEG_SCORE = 2F;

    private static final String ENTITY_API_URL = "/api/match-scores";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private MatchScoreRepository matchScoreRepository;

    @Autowired
    private MatchScoreMapper matchScoreMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMatchScoreMockMvc;

    private MatchScore matchScore;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MatchScore createEntity(EntityManager em) {
        MatchScore matchScore = new MatchScore()
            .setNo(DEFAULT_SET_NO)
            .lgeNo(DEFAULT_LGE_NO)
            .legGameName(DEFAULT_LEG_GAME_NAME)
            .homeLegScore(DEFAULT_HOME_LEG_SCORE)
            .awayLegScore(DEFAULT_AWAY_LEG_SCORE);
        return matchScore;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MatchScore createUpdatedEntity(EntityManager em) {
        MatchScore matchScore = new MatchScore()
            .setNo(UPDATED_SET_NO)
            .lgeNo(UPDATED_LGE_NO)
            .legGameName(UPDATED_LEG_GAME_NAME)
            .homeLegScore(UPDATED_HOME_LEG_SCORE)
            .awayLegScore(UPDATED_AWAY_LEG_SCORE);
        return matchScore;
    }

    @BeforeEach
    public void initTest() {
        matchScore = createEntity(em);
    }

    @Test
    @Transactional
    void createMatchScore() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the MatchScore
        MatchScoreDTO matchScoreDTO = matchScoreMapper.toDto(matchScore);
        var returnedMatchScoreDTO = om.readValue(
            restMatchScoreMockMvc
                .perform(
                    post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(matchScoreDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            MatchScoreDTO.class
        );

        // Validate the MatchScore in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedMatchScore = matchScoreMapper.toEntity(returnedMatchScoreDTO);
        assertMatchScoreUpdatableFieldsEquals(returnedMatchScore, getPersistedMatchScore(returnedMatchScore));
    }

    @Test
    @Transactional
    void createMatchScoreWithExistingId() throws Exception {
        // Create the MatchScore with an existing ID
        matchScore.setId("existing_id");
        MatchScoreDTO matchScoreDTO = matchScoreMapper.toDto(matchScore);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMatchScoreMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(matchScoreDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MatchScore in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMatchScores() throws Exception {
        // Initialize the database
        matchScoreRepository.saveAndFlush(matchScore);

        // Get all the matchScoreList
        restMatchScoreMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(matchScore.getId())))
            .andExpect(jsonPath("$.[*].setNo").value(hasItem(DEFAULT_SET_NO)))
            .andExpect(jsonPath("$.[*].lgeNo").value(hasItem(DEFAULT_LGE_NO)))
            .andExpect(jsonPath("$.[*].legGameName").value(hasItem(DEFAULT_LEG_GAME_NAME)))
            .andExpect(jsonPath("$.[*].homeLegScore").value(hasItem(DEFAULT_HOME_LEG_SCORE.doubleValue())))
            .andExpect(jsonPath("$.[*].awayLegScore").value(hasItem(DEFAULT_AWAY_LEG_SCORE.doubleValue())));
    }

    @Test
    @Transactional
    void getMatchScore() throws Exception {
        // Initialize the database
        matchScoreRepository.saveAndFlush(matchScore);

        // Get the matchScore
        restMatchScoreMockMvc
            .perform(get(ENTITY_API_URL_ID, matchScore.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(matchScore.getId()))
            .andExpect(jsonPath("$.setNo").value(DEFAULT_SET_NO))
            .andExpect(jsonPath("$.lgeNo").value(DEFAULT_LGE_NO))
            .andExpect(jsonPath("$.legGameName").value(DEFAULT_LEG_GAME_NAME))
            .andExpect(jsonPath("$.homeLegScore").value(DEFAULT_HOME_LEG_SCORE.doubleValue()))
            .andExpect(jsonPath("$.awayLegScore").value(DEFAULT_AWAY_LEG_SCORE.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingMatchScore() throws Exception {
        // Get the matchScore
        restMatchScoreMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMatchScore() throws Exception {
        // Initialize the database
        matchScoreRepository.saveAndFlush(matchScore);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the matchScore
        MatchScore updatedMatchScore = matchScoreRepository.findById(matchScore.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedMatchScore are not directly saved in db
        em.detach(updatedMatchScore);
        updatedMatchScore
            .setNo(UPDATED_SET_NO)
            .lgeNo(UPDATED_LGE_NO)
            .legGameName(UPDATED_LEG_GAME_NAME)
            .homeLegScore(UPDATED_HOME_LEG_SCORE)
            .awayLegScore(UPDATED_AWAY_LEG_SCORE);
        MatchScoreDTO matchScoreDTO = matchScoreMapper.toDto(updatedMatchScore);

        restMatchScoreMockMvc
            .perform(
                put(ENTITY_API_URL_ID, matchScoreDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(matchScoreDTO))
            )
            .andExpect(status().isOk());

        // Validate the MatchScore in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedMatchScoreToMatchAllProperties(updatedMatchScore);
    }

    @Test
    @Transactional
    void putNonExistingMatchScore() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        matchScore.setId(UUID.randomUUID().toString());

        // Create the MatchScore
        MatchScoreDTO matchScoreDTO = matchScoreMapper.toDto(matchScore);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMatchScoreMockMvc
            .perform(
                put(ENTITY_API_URL_ID, matchScoreDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(matchScoreDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MatchScore in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMatchScore() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        matchScore.setId(UUID.randomUUID().toString());

        // Create the MatchScore
        MatchScoreDTO matchScoreDTO = matchScoreMapper.toDto(matchScore);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMatchScoreMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(matchScoreDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MatchScore in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMatchScore() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        matchScore.setId(UUID.randomUUID().toString());

        // Create the MatchScore
        MatchScoreDTO matchScoreDTO = matchScoreMapper.toDto(matchScore);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMatchScoreMockMvc
            .perform(put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(matchScoreDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the MatchScore in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMatchScoreWithPatch() throws Exception {
        // Initialize the database
        matchScoreRepository.saveAndFlush(matchScore);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the matchScore using partial update
        MatchScore partialUpdatedMatchScore = new MatchScore();
        partialUpdatedMatchScore.setId(matchScore.getId());

        partialUpdatedMatchScore
            .setNo(UPDATED_SET_NO)
            .lgeNo(UPDATED_LGE_NO)
            .legGameName(UPDATED_LEG_GAME_NAME)
            .homeLegScore(UPDATED_HOME_LEG_SCORE)
            .awayLegScore(UPDATED_AWAY_LEG_SCORE);

        restMatchScoreMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMatchScore.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMatchScore))
            )
            .andExpect(status().isOk());

        // Validate the MatchScore in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMatchScoreUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedMatchScore, matchScore),
            getPersistedMatchScore(matchScore)
        );
    }

    @Test
    @Transactional
    void fullUpdateMatchScoreWithPatch() throws Exception {
        // Initialize the database
        matchScoreRepository.saveAndFlush(matchScore);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the matchScore using partial update
        MatchScore partialUpdatedMatchScore = new MatchScore();
        partialUpdatedMatchScore.setId(matchScore.getId());

        partialUpdatedMatchScore
            .setNo(UPDATED_SET_NO)
            .lgeNo(UPDATED_LGE_NO)
            .legGameName(UPDATED_LEG_GAME_NAME)
            .homeLegScore(UPDATED_HOME_LEG_SCORE)
            .awayLegScore(UPDATED_AWAY_LEG_SCORE);

        restMatchScoreMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMatchScore.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMatchScore))
            )
            .andExpect(status().isOk());

        // Validate the MatchScore in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMatchScoreUpdatableFieldsEquals(partialUpdatedMatchScore, getPersistedMatchScore(partialUpdatedMatchScore));
    }

    @Test
    @Transactional
    void patchNonExistingMatchScore() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        matchScore.setId(UUID.randomUUID().toString());

        // Create the MatchScore
        MatchScoreDTO matchScoreDTO = matchScoreMapper.toDto(matchScore);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMatchScoreMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, matchScoreDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(matchScoreDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MatchScore in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMatchScore() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        matchScore.setId(UUID.randomUUID().toString());

        // Create the MatchScore
        MatchScoreDTO matchScoreDTO = matchScoreMapper.toDto(matchScore);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMatchScoreMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(matchScoreDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MatchScore in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMatchScore() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        matchScore.setId(UUID.randomUUID().toString());

        // Create the MatchScore
        MatchScoreDTO matchScoreDTO = matchScoreMapper.toDto(matchScore);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMatchScoreMockMvc
            .perform(
                patch(ENTITY_API_URL).with(csrf()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(matchScoreDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MatchScore in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMatchScore() throws Exception {
        // Initialize the database
        matchScoreRepository.saveAndFlush(matchScore);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the matchScore
        restMatchScoreMockMvc
            .perform(delete(ENTITY_API_URL_ID, matchScore.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return matchScoreRepository.count();
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

    protected MatchScore getPersistedMatchScore(MatchScore matchScore) {
        return matchScoreRepository.findById(matchScore.getId()).orElseThrow();
    }

    protected void assertPersistedMatchScoreToMatchAllProperties(MatchScore expectedMatchScore) {
        assertMatchScoreAllPropertiesEquals(expectedMatchScore, getPersistedMatchScore(expectedMatchScore));
    }

    protected void assertPersistedMatchScoreToMatchUpdatableProperties(MatchScore expectedMatchScore) {
        assertMatchScoreAllUpdatablePropertiesEquals(expectedMatchScore, getPersistedMatchScore(expectedMatchScore));
    }
}
