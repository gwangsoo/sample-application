package com.phoenixdarts.toss.backend.web.rest;

import static com.phoenixdarts.toss.backend.domain.MatchTeamAsserts.*;
import static com.phoenixdarts.toss.backend.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.phoenixdarts.toss.IntegrationTest;
import com.phoenixdarts.toss.backend.domain.MatchTeam;
import com.phoenixdarts.toss.backend.domain.enumeration.PlayerCallModeType;
import com.phoenixdarts.toss.backend.repository.MatchTeamRepository;
import com.phoenixdarts.toss.backend.service.dto.MatchTeamDTO;
import com.phoenixdarts.toss.backend.service.mapper.MatchTeamMapper;
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
 * Integration tests for the {@link MatchTeamResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MatchTeamResourceIT {

    private static final Boolean DEFAULT_IS_WINNER = false;
    private static final Boolean UPDATED_IS_WINNER = true;

    private static final Float DEFAULT_AVG_PPD = 1F;
    private static final Float UPDATED_AVG_PPD = 2F;

    private static final Float DEFAULT_AVG_MPR = 1F;
    private static final Float UPDATED_AVG_MPR = 2F;

    private static final Integer DEFAULT_WIN_SET = 1;
    private static final Integer UPDATED_WIN_SET = 2;

    private static final Integer DEFAULT_WIN_LEG = 1;
    private static final Integer UPDATED_WIN_LEG = 2;

    private static final PlayerCallModeType DEFAULT_PLAYER_CALL_MODE_TYPE = PlayerCallModeType.AUTO_PROGRESS;
    private static final PlayerCallModeType UPDATED_PLAYER_CALL_MODE_TYPE = PlayerCallModeType.MANUAL_PROGRESS;

    private static final String ENTITY_API_URL = "/api/match-teams";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private MatchTeamRepository matchTeamRepository;

    @Autowired
    private MatchTeamMapper matchTeamMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMatchTeamMockMvc;

    private MatchTeam matchTeam;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MatchTeam createEntity(EntityManager em) {
        MatchTeam matchTeam = new MatchTeam()
            .isWinner(DEFAULT_IS_WINNER)
            .avgPpd(DEFAULT_AVG_PPD)
            .avgMpr(DEFAULT_AVG_MPR)
            .winSet(DEFAULT_WIN_SET)
            .winLeg(DEFAULT_WIN_LEG)
            .playerCallModeType(DEFAULT_PLAYER_CALL_MODE_TYPE);
        return matchTeam;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MatchTeam createUpdatedEntity(EntityManager em) {
        MatchTeam matchTeam = new MatchTeam()
            .isWinner(UPDATED_IS_WINNER)
            .avgPpd(UPDATED_AVG_PPD)
            .avgMpr(UPDATED_AVG_MPR)
            .winSet(UPDATED_WIN_SET)
            .winLeg(UPDATED_WIN_LEG)
            .playerCallModeType(UPDATED_PLAYER_CALL_MODE_TYPE);
        return matchTeam;
    }

    @BeforeEach
    public void initTest() {
        matchTeam = createEntity(em);
    }

    @Test
    @Transactional
    void createMatchTeam() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the MatchTeam
        MatchTeamDTO matchTeamDTO = matchTeamMapper.toDto(matchTeam);
        var returnedMatchTeamDTO = om.readValue(
            restMatchTeamMockMvc
                .perform(
                    post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(matchTeamDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            MatchTeamDTO.class
        );

        // Validate the MatchTeam in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedMatchTeam = matchTeamMapper.toEntity(returnedMatchTeamDTO);
        assertMatchTeamUpdatableFieldsEquals(returnedMatchTeam, getPersistedMatchTeam(returnedMatchTeam));
    }

    @Test
    @Transactional
    void createMatchTeamWithExistingId() throws Exception {
        // Create the MatchTeam with an existing ID
        matchTeam.setId("existing_id");
        MatchTeamDTO matchTeamDTO = matchTeamMapper.toDto(matchTeam);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMatchTeamMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(matchTeamDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MatchTeam in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkPlayerCallModeTypeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        matchTeam.setPlayerCallModeType(null);

        // Create the MatchTeam, which fails.
        MatchTeamDTO matchTeamDTO = matchTeamMapper.toDto(matchTeam);

        restMatchTeamMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(matchTeamDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllMatchTeams() throws Exception {
        // Initialize the database
        matchTeamRepository.saveAndFlush(matchTeam);

        // Get all the matchTeamList
        restMatchTeamMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(matchTeam.getId())))
            .andExpect(jsonPath("$.[*].isWinner").value(hasItem(DEFAULT_IS_WINNER.booleanValue())))
            .andExpect(jsonPath("$.[*].avgPpd").value(hasItem(DEFAULT_AVG_PPD.doubleValue())))
            .andExpect(jsonPath("$.[*].avgMpr").value(hasItem(DEFAULT_AVG_MPR.doubleValue())))
            .andExpect(jsonPath("$.[*].winSet").value(hasItem(DEFAULT_WIN_SET)))
            .andExpect(jsonPath("$.[*].winLeg").value(hasItem(DEFAULT_WIN_LEG)))
            .andExpect(jsonPath("$.[*].playerCallModeType").value(hasItem(DEFAULT_PLAYER_CALL_MODE_TYPE.toString())));
    }

    @Test
    @Transactional
    void getMatchTeam() throws Exception {
        // Initialize the database
        matchTeamRepository.saveAndFlush(matchTeam);

        // Get the matchTeam
        restMatchTeamMockMvc
            .perform(get(ENTITY_API_URL_ID, matchTeam.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(matchTeam.getId()))
            .andExpect(jsonPath("$.isWinner").value(DEFAULT_IS_WINNER.booleanValue()))
            .andExpect(jsonPath("$.avgPpd").value(DEFAULT_AVG_PPD.doubleValue()))
            .andExpect(jsonPath("$.avgMpr").value(DEFAULT_AVG_MPR.doubleValue()))
            .andExpect(jsonPath("$.winSet").value(DEFAULT_WIN_SET))
            .andExpect(jsonPath("$.winLeg").value(DEFAULT_WIN_LEG))
            .andExpect(jsonPath("$.playerCallModeType").value(DEFAULT_PLAYER_CALL_MODE_TYPE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingMatchTeam() throws Exception {
        // Get the matchTeam
        restMatchTeamMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMatchTeam() throws Exception {
        // Initialize the database
        matchTeamRepository.saveAndFlush(matchTeam);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the matchTeam
        MatchTeam updatedMatchTeam = matchTeamRepository.findById(matchTeam.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedMatchTeam are not directly saved in db
        em.detach(updatedMatchTeam);
        updatedMatchTeam
            .isWinner(UPDATED_IS_WINNER)
            .avgPpd(UPDATED_AVG_PPD)
            .avgMpr(UPDATED_AVG_MPR)
            .winSet(UPDATED_WIN_SET)
            .winLeg(UPDATED_WIN_LEG)
            .playerCallModeType(UPDATED_PLAYER_CALL_MODE_TYPE);
        MatchTeamDTO matchTeamDTO = matchTeamMapper.toDto(updatedMatchTeam);

        restMatchTeamMockMvc
            .perform(
                put(ENTITY_API_URL_ID, matchTeamDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(matchTeamDTO))
            )
            .andExpect(status().isOk());

        // Validate the MatchTeam in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedMatchTeamToMatchAllProperties(updatedMatchTeam);
    }

    @Test
    @Transactional
    void putNonExistingMatchTeam() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        matchTeam.setId(UUID.randomUUID().toString());

        // Create the MatchTeam
        MatchTeamDTO matchTeamDTO = matchTeamMapper.toDto(matchTeam);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMatchTeamMockMvc
            .perform(
                put(ENTITY_API_URL_ID, matchTeamDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(matchTeamDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MatchTeam in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMatchTeam() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        matchTeam.setId(UUID.randomUUID().toString());

        // Create the MatchTeam
        MatchTeamDTO matchTeamDTO = matchTeamMapper.toDto(matchTeam);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMatchTeamMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(matchTeamDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MatchTeam in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMatchTeam() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        matchTeam.setId(UUID.randomUUID().toString());

        // Create the MatchTeam
        MatchTeamDTO matchTeamDTO = matchTeamMapper.toDto(matchTeam);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMatchTeamMockMvc
            .perform(put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(matchTeamDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the MatchTeam in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMatchTeamWithPatch() throws Exception {
        // Initialize the database
        matchTeamRepository.saveAndFlush(matchTeam);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the matchTeam using partial update
        MatchTeam partialUpdatedMatchTeam = new MatchTeam();
        partialUpdatedMatchTeam.setId(matchTeam.getId());

        partialUpdatedMatchTeam.winSet(UPDATED_WIN_SET);

        restMatchTeamMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMatchTeam.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMatchTeam))
            )
            .andExpect(status().isOk());

        // Validate the MatchTeam in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMatchTeamUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedMatchTeam, matchTeam),
            getPersistedMatchTeam(matchTeam)
        );
    }

    @Test
    @Transactional
    void fullUpdateMatchTeamWithPatch() throws Exception {
        // Initialize the database
        matchTeamRepository.saveAndFlush(matchTeam);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the matchTeam using partial update
        MatchTeam partialUpdatedMatchTeam = new MatchTeam();
        partialUpdatedMatchTeam.setId(matchTeam.getId());

        partialUpdatedMatchTeam
            .isWinner(UPDATED_IS_WINNER)
            .avgPpd(UPDATED_AVG_PPD)
            .avgMpr(UPDATED_AVG_MPR)
            .winSet(UPDATED_WIN_SET)
            .winLeg(UPDATED_WIN_LEG)
            .playerCallModeType(UPDATED_PLAYER_CALL_MODE_TYPE);

        restMatchTeamMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMatchTeam.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMatchTeam))
            )
            .andExpect(status().isOk());

        // Validate the MatchTeam in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMatchTeamUpdatableFieldsEquals(partialUpdatedMatchTeam, getPersistedMatchTeam(partialUpdatedMatchTeam));
    }

    @Test
    @Transactional
    void patchNonExistingMatchTeam() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        matchTeam.setId(UUID.randomUUID().toString());

        // Create the MatchTeam
        MatchTeamDTO matchTeamDTO = matchTeamMapper.toDto(matchTeam);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMatchTeamMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, matchTeamDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(matchTeamDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MatchTeam in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMatchTeam() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        matchTeam.setId(UUID.randomUUID().toString());

        // Create the MatchTeam
        MatchTeamDTO matchTeamDTO = matchTeamMapper.toDto(matchTeam);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMatchTeamMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(matchTeamDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MatchTeam in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMatchTeam() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        matchTeam.setId(UUID.randomUUID().toString());

        // Create the MatchTeam
        MatchTeamDTO matchTeamDTO = matchTeamMapper.toDto(matchTeam);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMatchTeamMockMvc
            .perform(
                patch(ENTITY_API_URL).with(csrf()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(matchTeamDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MatchTeam in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMatchTeam() throws Exception {
        // Initialize the database
        matchTeamRepository.saveAndFlush(matchTeam);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the matchTeam
        restMatchTeamMockMvc
            .perform(delete(ENTITY_API_URL_ID, matchTeam.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return matchTeamRepository.count();
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

    protected MatchTeam getPersistedMatchTeam(MatchTeam matchTeam) {
        return matchTeamRepository.findById(matchTeam.getId()).orElseThrow();
    }

    protected void assertPersistedMatchTeamToMatchAllProperties(MatchTeam expectedMatchTeam) {
        assertMatchTeamAllPropertiesEquals(expectedMatchTeam, getPersistedMatchTeam(expectedMatchTeam));
    }

    protected void assertPersistedMatchTeamToMatchUpdatableProperties(MatchTeam expectedMatchTeam) {
        assertMatchTeamAllUpdatablePropertiesEquals(expectedMatchTeam, getPersistedMatchTeam(expectedMatchTeam));
    }
}
