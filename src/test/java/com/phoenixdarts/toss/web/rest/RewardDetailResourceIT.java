package com.phoenixdarts.toss.backend.web.rest;

import static com.phoenixdarts.toss.backend.domain.RewardDetailAsserts.*;
import static com.phoenixdarts.toss.backend.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.phoenixdarts.toss.IntegrationTest;
import com.phoenixdarts.toss.backend.domain.RewardDetail;
import com.phoenixdarts.toss.backend.repository.RewardDetailRepository;
import com.phoenixdarts.toss.backend.service.dto.RewardDetailDTO;
import com.phoenixdarts.toss.backend.service.mapper.RewardDetailMapper;
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
 * Integration tests for the {@link RewardDetailResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RewardDetailResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TOURNAMENT_ID = "AAAAAAAAAA";
    private static final String UPDATED_TOURNAMENT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_DIVISION_ID = "AAAAAAAAAA";
    private static final String UPDATED_DIVISION_ID = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/reward-details";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private RewardDetailRepository rewardDetailRepository;

    @Autowired
    private RewardDetailMapper rewardDetailMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRewardDetailMockMvc;

    private RewardDetail rewardDetail;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RewardDetail createEntity(EntityManager em) {
        RewardDetail rewardDetail = new RewardDetail()
            .name(DEFAULT_NAME)
            .tournamentId(DEFAULT_TOURNAMENT_ID)
            .divisionId(DEFAULT_DIVISION_ID);
        return rewardDetail;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RewardDetail createUpdatedEntity(EntityManager em) {
        RewardDetail rewardDetail = new RewardDetail()
            .name(UPDATED_NAME)
            .tournamentId(UPDATED_TOURNAMENT_ID)
            .divisionId(UPDATED_DIVISION_ID);
        return rewardDetail;
    }

    @BeforeEach
    public void initTest() {
        rewardDetail = createEntity(em);
    }

    @Test
    @Transactional
    void createRewardDetail() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the RewardDetail
        RewardDetailDTO rewardDetailDTO = rewardDetailMapper.toDto(rewardDetail);
        var returnedRewardDetailDTO = om.readValue(
            restRewardDetailMockMvc
                .perform(
                    post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rewardDetailDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            RewardDetailDTO.class
        );

        // Validate the RewardDetail in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedRewardDetail = rewardDetailMapper.toEntity(returnedRewardDetailDTO);
        assertRewardDetailUpdatableFieldsEquals(returnedRewardDetail, getPersistedRewardDetail(returnedRewardDetail));
    }

    @Test
    @Transactional
    void createRewardDetailWithExistingId() throws Exception {
        // Create the RewardDetail with an existing ID
        rewardDetail.setId("existing_id");
        RewardDetailDTO rewardDetailDTO = rewardDetailMapper.toDto(rewardDetail);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRewardDetailMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rewardDetailDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RewardDetail in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRewardDetails() throws Exception {
        // Initialize the database
        rewardDetailRepository.saveAndFlush(rewardDetail);

        // Get all the rewardDetailList
        restRewardDetailMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rewardDetail.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].tournamentId").value(hasItem(DEFAULT_TOURNAMENT_ID)))
            .andExpect(jsonPath("$.[*].divisionId").value(hasItem(DEFAULT_DIVISION_ID)));
    }

    @Test
    @Transactional
    void getRewardDetail() throws Exception {
        // Initialize the database
        rewardDetailRepository.saveAndFlush(rewardDetail);

        // Get the rewardDetail
        restRewardDetailMockMvc
            .perform(get(ENTITY_API_URL_ID, rewardDetail.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(rewardDetail.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.tournamentId").value(DEFAULT_TOURNAMENT_ID))
            .andExpect(jsonPath("$.divisionId").value(DEFAULT_DIVISION_ID));
    }

    @Test
    @Transactional
    void getNonExistingRewardDetail() throws Exception {
        // Get the rewardDetail
        restRewardDetailMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRewardDetail() throws Exception {
        // Initialize the database
        rewardDetailRepository.saveAndFlush(rewardDetail);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the rewardDetail
        RewardDetail updatedRewardDetail = rewardDetailRepository.findById(rewardDetail.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedRewardDetail are not directly saved in db
        em.detach(updatedRewardDetail);
        updatedRewardDetail.name(UPDATED_NAME).tournamentId(UPDATED_TOURNAMENT_ID).divisionId(UPDATED_DIVISION_ID);
        RewardDetailDTO rewardDetailDTO = rewardDetailMapper.toDto(updatedRewardDetail);

        restRewardDetailMockMvc
            .perform(
                put(ENTITY_API_URL_ID, rewardDetailDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(rewardDetailDTO))
            )
            .andExpect(status().isOk());

        // Validate the RewardDetail in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedRewardDetailToMatchAllProperties(updatedRewardDetail);
    }

    @Test
    @Transactional
    void putNonExistingRewardDetail() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rewardDetail.setId(UUID.randomUUID().toString());

        // Create the RewardDetail
        RewardDetailDTO rewardDetailDTO = rewardDetailMapper.toDto(rewardDetail);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRewardDetailMockMvc
            .perform(
                put(ENTITY_API_URL_ID, rewardDetailDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(rewardDetailDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RewardDetail in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRewardDetail() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rewardDetail.setId(UUID.randomUUID().toString());

        // Create the RewardDetail
        RewardDetailDTO rewardDetailDTO = rewardDetailMapper.toDto(rewardDetail);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRewardDetailMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(rewardDetailDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RewardDetail in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRewardDetail() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rewardDetail.setId(UUID.randomUUID().toString());

        // Create the RewardDetail
        RewardDetailDTO rewardDetailDTO = rewardDetailMapper.toDto(rewardDetail);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRewardDetailMockMvc
            .perform(
                put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rewardDetailDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RewardDetail in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRewardDetailWithPatch() throws Exception {
        // Initialize the database
        rewardDetailRepository.saveAndFlush(rewardDetail);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the rewardDetail using partial update
        RewardDetail partialUpdatedRewardDetail = new RewardDetail();
        partialUpdatedRewardDetail.setId(rewardDetail.getId());

        partialUpdatedRewardDetail.name(UPDATED_NAME);

        restRewardDetailMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRewardDetail.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRewardDetail))
            )
            .andExpect(status().isOk());

        // Validate the RewardDetail in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRewardDetailUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedRewardDetail, rewardDetail),
            getPersistedRewardDetail(rewardDetail)
        );
    }

    @Test
    @Transactional
    void fullUpdateRewardDetailWithPatch() throws Exception {
        // Initialize the database
        rewardDetailRepository.saveAndFlush(rewardDetail);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the rewardDetail using partial update
        RewardDetail partialUpdatedRewardDetail = new RewardDetail();
        partialUpdatedRewardDetail.setId(rewardDetail.getId());

        partialUpdatedRewardDetail.name(UPDATED_NAME).tournamentId(UPDATED_TOURNAMENT_ID).divisionId(UPDATED_DIVISION_ID);

        restRewardDetailMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRewardDetail.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRewardDetail))
            )
            .andExpect(status().isOk());

        // Validate the RewardDetail in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRewardDetailUpdatableFieldsEquals(partialUpdatedRewardDetail, getPersistedRewardDetail(partialUpdatedRewardDetail));
    }

    @Test
    @Transactional
    void patchNonExistingRewardDetail() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rewardDetail.setId(UUID.randomUUID().toString());

        // Create the RewardDetail
        RewardDetailDTO rewardDetailDTO = rewardDetailMapper.toDto(rewardDetail);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRewardDetailMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, rewardDetailDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(rewardDetailDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RewardDetail in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRewardDetail() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rewardDetail.setId(UUID.randomUUID().toString());

        // Create the RewardDetail
        RewardDetailDTO rewardDetailDTO = rewardDetailMapper.toDto(rewardDetail);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRewardDetailMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(rewardDetailDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RewardDetail in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRewardDetail() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rewardDetail.setId(UUID.randomUUID().toString());

        // Create the RewardDetail
        RewardDetailDTO rewardDetailDTO = rewardDetailMapper.toDto(rewardDetail);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRewardDetailMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(rewardDetailDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RewardDetail in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRewardDetail() throws Exception {
        // Initialize the database
        rewardDetailRepository.saveAndFlush(rewardDetail);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the rewardDetail
        restRewardDetailMockMvc
            .perform(delete(ENTITY_API_URL_ID, rewardDetail.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return rewardDetailRepository.count();
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

    protected RewardDetail getPersistedRewardDetail(RewardDetail rewardDetail) {
        return rewardDetailRepository.findById(rewardDetail.getId()).orElseThrow();
    }

    protected void assertPersistedRewardDetailToMatchAllProperties(RewardDetail expectedRewardDetail) {
        assertRewardDetailAllPropertiesEquals(expectedRewardDetail, getPersistedRewardDetail(expectedRewardDetail));
    }

    protected void assertPersistedRewardDetailToMatchUpdatableProperties(RewardDetail expectedRewardDetail) {
        assertRewardDetailAllUpdatablePropertiesEquals(expectedRewardDetail, getPersistedRewardDetail(expectedRewardDetail));
    }
}
