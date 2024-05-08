package com.phoenixdarts.toss.backend.web.rest;

import static com.phoenixdarts.toss.backend.domain.RewardItemAsserts.*;
import static com.phoenixdarts.toss.backend.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.phoenixdarts.toss.IntegrationTest;
import com.phoenixdarts.toss.backend.domain.RewardItem;
import com.phoenixdarts.toss.backend.repository.RewardItemRepository;
import com.phoenixdarts.toss.backend.service.dto.RewardItemDTO;
import com.phoenixdarts.toss.backend.service.mapper.RewardItemMapper;
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
 * Integration tests for the {@link RewardItemResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RewardItemResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/reward-items";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private RewardItemRepository rewardItemRepository;

    @Autowired
    private RewardItemMapper rewardItemMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRewardItemMockMvc;

    private RewardItem rewardItem;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RewardItem createEntity(EntityManager em) {
        RewardItem rewardItem = new RewardItem().name(DEFAULT_NAME);
        return rewardItem;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RewardItem createUpdatedEntity(EntityManager em) {
        RewardItem rewardItem = new RewardItem().name(UPDATED_NAME);
        return rewardItem;
    }

    @BeforeEach
    public void initTest() {
        rewardItem = createEntity(em);
    }

    @Test
    @Transactional
    void createRewardItem() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the RewardItem
        RewardItemDTO rewardItemDTO = rewardItemMapper.toDto(rewardItem);
        var returnedRewardItemDTO = om.readValue(
            restRewardItemMockMvc
                .perform(
                    post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rewardItemDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            RewardItemDTO.class
        );

        // Validate the RewardItem in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedRewardItem = rewardItemMapper.toEntity(returnedRewardItemDTO);
        assertRewardItemUpdatableFieldsEquals(returnedRewardItem, getPersistedRewardItem(returnedRewardItem));
    }

    @Test
    @Transactional
    void createRewardItemWithExistingId() throws Exception {
        // Create the RewardItem with an existing ID
        rewardItem.setId("existing_id");
        RewardItemDTO rewardItemDTO = rewardItemMapper.toDto(rewardItem);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRewardItemMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rewardItemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RewardItem in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        rewardItem.setName(null);

        // Create the RewardItem, which fails.
        RewardItemDTO rewardItemDTO = rewardItemMapper.toDto(rewardItem);

        restRewardItemMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rewardItemDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllRewardItems() throws Exception {
        // Initialize the database
        rewardItemRepository.saveAndFlush(rewardItem);

        // Get all the rewardItemList
        restRewardItemMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rewardItem.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getRewardItem() throws Exception {
        // Initialize the database
        rewardItemRepository.saveAndFlush(rewardItem);

        // Get the rewardItem
        restRewardItemMockMvc
            .perform(get(ENTITY_API_URL_ID, rewardItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(rewardItem.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getNonExistingRewardItem() throws Exception {
        // Get the rewardItem
        restRewardItemMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRewardItem() throws Exception {
        // Initialize the database
        rewardItemRepository.saveAndFlush(rewardItem);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the rewardItem
        RewardItem updatedRewardItem = rewardItemRepository.findById(rewardItem.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedRewardItem are not directly saved in db
        em.detach(updatedRewardItem);
        updatedRewardItem.name(UPDATED_NAME);
        RewardItemDTO rewardItemDTO = rewardItemMapper.toDto(updatedRewardItem);

        restRewardItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, rewardItemDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(rewardItemDTO))
            )
            .andExpect(status().isOk());

        // Validate the RewardItem in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedRewardItemToMatchAllProperties(updatedRewardItem);
    }

    @Test
    @Transactional
    void putNonExistingRewardItem() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rewardItem.setId(UUID.randomUUID().toString());

        // Create the RewardItem
        RewardItemDTO rewardItemDTO = rewardItemMapper.toDto(rewardItem);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRewardItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, rewardItemDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(rewardItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RewardItem in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRewardItem() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rewardItem.setId(UUID.randomUUID().toString());

        // Create the RewardItem
        RewardItemDTO rewardItemDTO = rewardItemMapper.toDto(rewardItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRewardItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(rewardItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RewardItem in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRewardItem() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rewardItem.setId(UUID.randomUUID().toString());

        // Create the RewardItem
        RewardItemDTO rewardItemDTO = rewardItemMapper.toDto(rewardItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRewardItemMockMvc
            .perform(put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rewardItemDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the RewardItem in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRewardItemWithPatch() throws Exception {
        // Initialize the database
        rewardItemRepository.saveAndFlush(rewardItem);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the rewardItem using partial update
        RewardItem partialUpdatedRewardItem = new RewardItem();
        partialUpdatedRewardItem.setId(rewardItem.getId());

        partialUpdatedRewardItem.name(UPDATED_NAME);

        restRewardItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRewardItem.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRewardItem))
            )
            .andExpect(status().isOk());

        // Validate the RewardItem in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRewardItemUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedRewardItem, rewardItem),
            getPersistedRewardItem(rewardItem)
        );
    }

    @Test
    @Transactional
    void fullUpdateRewardItemWithPatch() throws Exception {
        // Initialize the database
        rewardItemRepository.saveAndFlush(rewardItem);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the rewardItem using partial update
        RewardItem partialUpdatedRewardItem = new RewardItem();
        partialUpdatedRewardItem.setId(rewardItem.getId());

        partialUpdatedRewardItem.name(UPDATED_NAME);

        restRewardItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRewardItem.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRewardItem))
            )
            .andExpect(status().isOk());

        // Validate the RewardItem in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRewardItemUpdatableFieldsEquals(partialUpdatedRewardItem, getPersistedRewardItem(partialUpdatedRewardItem));
    }

    @Test
    @Transactional
    void patchNonExistingRewardItem() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rewardItem.setId(UUID.randomUUID().toString());

        // Create the RewardItem
        RewardItemDTO rewardItemDTO = rewardItemMapper.toDto(rewardItem);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRewardItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, rewardItemDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(rewardItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RewardItem in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRewardItem() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rewardItem.setId(UUID.randomUUID().toString());

        // Create the RewardItem
        RewardItemDTO rewardItemDTO = rewardItemMapper.toDto(rewardItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRewardItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(rewardItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RewardItem in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRewardItem() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rewardItem.setId(UUID.randomUUID().toString());

        // Create the RewardItem
        RewardItemDTO rewardItemDTO = rewardItemMapper.toDto(rewardItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRewardItemMockMvc
            .perform(
                patch(ENTITY_API_URL).with(csrf()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(rewardItemDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RewardItem in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRewardItem() throws Exception {
        // Initialize the database
        rewardItemRepository.saveAndFlush(rewardItem);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the rewardItem
        restRewardItemMockMvc
            .perform(delete(ENTITY_API_URL_ID, rewardItem.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return rewardItemRepository.count();
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

    protected RewardItem getPersistedRewardItem(RewardItem rewardItem) {
        return rewardItemRepository.findById(rewardItem.getId()).orElseThrow();
    }

    protected void assertPersistedRewardItemToMatchAllProperties(RewardItem expectedRewardItem) {
        assertRewardItemAllPropertiesEquals(expectedRewardItem, getPersistedRewardItem(expectedRewardItem));
    }

    protected void assertPersistedRewardItemToMatchUpdatableProperties(RewardItem expectedRewardItem) {
        assertRewardItemAllUpdatablePropertiesEquals(expectedRewardItem, getPersistedRewardItem(expectedRewardItem));
    }
}
