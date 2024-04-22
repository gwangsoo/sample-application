package com.phoenixdarts.toss.web.rest;

import static com.phoenixdarts.toss.domain.RewardAsserts.*;
import static com.phoenixdarts.toss.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.phoenixdarts.toss.IntegrationTest;
import com.phoenixdarts.toss.domain.Reward;
import com.phoenixdarts.toss.domain.enumeration.MachineKindType;
import com.phoenixdarts.toss.domain.enumeration.RewardMethodSubType;
import com.phoenixdarts.toss.domain.enumeration.RewardMethodType;
import com.phoenixdarts.toss.repository.RewardRepository;
import com.phoenixdarts.toss.service.dto.RewardDTO;
import com.phoenixdarts.toss.service.mapper.RewardMapper;
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
 * Integration tests for the {@link RewardResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RewardResourceIT {

    private static final RewardMethodType DEFAULT_REWARD_METHOD_TYPE = RewardMethodType.DAY;
    private static final RewardMethodType UPDATED_REWARD_METHOD_TYPE = RewardMethodType.TOURNAMENT;

    private static final RewardMethodSubType DEFAULT_REWARD_METHOD_SUB_TYPE = RewardMethodSubType.ALL;
    private static final RewardMethodSubType UPDATED_REWARD_METHOD_SUB_TYPE = RewardMethodSubType.DIVISION;

    private static final Integer DEFAULT_REWARD_CATEGORY_NUM = 1;
    private static final Integer UPDATED_REWARD_CATEGORY_NUM = 2;

    private static final MachineKindType DEFAULT_MACHINE_KIND_TYPE = MachineKindType.VSS_S4;
    private static final MachineKindType UPDATED_MACHINE_KIND_TYPE = MachineKindType.VSX;

    private static final String ENTITY_API_URL = "/api/rewards";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private RewardRepository rewardRepository;

    @Autowired
    private RewardMapper rewardMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRewardMockMvc;

    private Reward reward;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Reward createEntity(EntityManager em) {
        Reward reward = new Reward()
            .rewardMethodType(DEFAULT_REWARD_METHOD_TYPE)
            .rewardMethodSubType(DEFAULT_REWARD_METHOD_SUB_TYPE)
            .rewardCategoryNum(DEFAULT_REWARD_CATEGORY_NUM)
            .machineKindType(DEFAULT_MACHINE_KIND_TYPE);
        return reward;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Reward createUpdatedEntity(EntityManager em) {
        Reward reward = new Reward()
            .rewardMethodType(UPDATED_REWARD_METHOD_TYPE)
            .rewardMethodSubType(UPDATED_REWARD_METHOD_SUB_TYPE)
            .rewardCategoryNum(UPDATED_REWARD_CATEGORY_NUM)
            .machineKindType(UPDATED_MACHINE_KIND_TYPE);
        return reward;
    }

    @BeforeEach
    public void initTest() {
        reward = createEntity(em);
    }

    @Test
    @Transactional
    void createReward() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Reward
        RewardDTO rewardDTO = rewardMapper.toDto(reward);
        var returnedRewardDTO = om.readValue(
            restRewardMockMvc
                .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rewardDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            RewardDTO.class
        );

        // Validate the Reward in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedReward = rewardMapper.toEntity(returnedRewardDTO);
        assertRewardUpdatableFieldsEquals(returnedReward, getPersistedReward(returnedReward));
    }

    @Test
    @Transactional
    void createRewardWithExistingId() throws Exception {
        // Create the Reward with an existing ID
        reward.setId("existing_id");
        RewardDTO rewardDTO = rewardMapper.toDto(reward);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRewardMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rewardDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Reward in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkRewardMethodTypeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        reward.setRewardMethodType(null);

        // Create the Reward, which fails.
        RewardDTO rewardDTO = rewardMapper.toDto(reward);

        restRewardMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rewardDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkRewardMethodSubTypeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        reward.setRewardMethodSubType(null);

        // Create the Reward, which fails.
        RewardDTO rewardDTO = rewardMapper.toDto(reward);

        restRewardMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rewardDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkRewardCategoryNumIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        reward.setRewardCategoryNum(null);

        // Create the Reward, which fails.
        RewardDTO rewardDTO = rewardMapper.toDto(reward);

        restRewardMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rewardDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllRewards() throws Exception {
        // Initialize the database
        rewardRepository.saveAndFlush(reward);

        // Get all the rewardList
        restRewardMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reward.getId())))
            .andExpect(jsonPath("$.[*].rewardMethodType").value(hasItem(DEFAULT_REWARD_METHOD_TYPE.toString())))
            .andExpect(jsonPath("$.[*].rewardMethodSubType").value(hasItem(DEFAULT_REWARD_METHOD_SUB_TYPE.toString())))
            .andExpect(jsonPath("$.[*].rewardCategoryNum").value(hasItem(DEFAULT_REWARD_CATEGORY_NUM)))
            .andExpect(jsonPath("$.[*].machineKindType").value(hasItem(DEFAULT_MACHINE_KIND_TYPE.toString())));
    }

    @Test
    @Transactional
    void getReward() throws Exception {
        // Initialize the database
        rewardRepository.saveAndFlush(reward);

        // Get the reward
        restRewardMockMvc
            .perform(get(ENTITY_API_URL_ID, reward.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(reward.getId()))
            .andExpect(jsonPath("$.rewardMethodType").value(DEFAULT_REWARD_METHOD_TYPE.toString()))
            .andExpect(jsonPath("$.rewardMethodSubType").value(DEFAULT_REWARD_METHOD_SUB_TYPE.toString()))
            .andExpect(jsonPath("$.rewardCategoryNum").value(DEFAULT_REWARD_CATEGORY_NUM))
            .andExpect(jsonPath("$.machineKindType").value(DEFAULT_MACHINE_KIND_TYPE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingReward() throws Exception {
        // Get the reward
        restRewardMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingReward() throws Exception {
        // Initialize the database
        rewardRepository.saveAndFlush(reward);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the reward
        Reward updatedReward = rewardRepository.findById(reward.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedReward are not directly saved in db
        em.detach(updatedReward);
        updatedReward
            .rewardMethodType(UPDATED_REWARD_METHOD_TYPE)
            .rewardMethodSubType(UPDATED_REWARD_METHOD_SUB_TYPE)
            .rewardCategoryNum(UPDATED_REWARD_CATEGORY_NUM)
            .machineKindType(UPDATED_MACHINE_KIND_TYPE);
        RewardDTO rewardDTO = rewardMapper.toDto(updatedReward);

        restRewardMockMvc
            .perform(
                put(ENTITY_API_URL_ID, rewardDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(rewardDTO))
            )
            .andExpect(status().isOk());

        // Validate the Reward in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedRewardToMatchAllProperties(updatedReward);
    }

    @Test
    @Transactional
    void putNonExistingReward() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        reward.setId(UUID.randomUUID().toString());

        // Create the Reward
        RewardDTO rewardDTO = rewardMapper.toDto(reward);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRewardMockMvc
            .perform(
                put(ENTITY_API_URL_ID, rewardDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(rewardDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Reward in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchReward() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        reward.setId(UUID.randomUUID().toString());

        // Create the Reward
        RewardDTO rewardDTO = rewardMapper.toDto(reward);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRewardMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(rewardDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Reward in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamReward() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        reward.setId(UUID.randomUUID().toString());

        // Create the Reward
        RewardDTO rewardDTO = rewardMapper.toDto(reward);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRewardMockMvc
            .perform(put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rewardDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Reward in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRewardWithPatch() throws Exception {
        // Initialize the database
        rewardRepository.saveAndFlush(reward);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the reward using partial update
        Reward partialUpdatedReward = new Reward();
        partialUpdatedReward.setId(reward.getId());

        partialUpdatedReward
            .rewardMethodType(UPDATED_REWARD_METHOD_TYPE)
            .rewardCategoryNum(UPDATED_REWARD_CATEGORY_NUM)
            .machineKindType(UPDATED_MACHINE_KIND_TYPE);

        restRewardMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedReward.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedReward))
            )
            .andExpect(status().isOk());

        // Validate the Reward in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRewardUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedReward, reward), getPersistedReward(reward));
    }

    @Test
    @Transactional
    void fullUpdateRewardWithPatch() throws Exception {
        // Initialize the database
        rewardRepository.saveAndFlush(reward);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the reward using partial update
        Reward partialUpdatedReward = new Reward();
        partialUpdatedReward.setId(reward.getId());

        partialUpdatedReward
            .rewardMethodType(UPDATED_REWARD_METHOD_TYPE)
            .rewardMethodSubType(UPDATED_REWARD_METHOD_SUB_TYPE)
            .rewardCategoryNum(UPDATED_REWARD_CATEGORY_NUM)
            .machineKindType(UPDATED_MACHINE_KIND_TYPE);

        restRewardMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedReward.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedReward))
            )
            .andExpect(status().isOk());

        // Validate the Reward in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRewardUpdatableFieldsEquals(partialUpdatedReward, getPersistedReward(partialUpdatedReward));
    }

    @Test
    @Transactional
    void patchNonExistingReward() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        reward.setId(UUID.randomUUID().toString());

        // Create the Reward
        RewardDTO rewardDTO = rewardMapper.toDto(reward);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRewardMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, rewardDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(rewardDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Reward in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchReward() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        reward.setId(UUID.randomUUID().toString());

        // Create the Reward
        RewardDTO rewardDTO = rewardMapper.toDto(reward);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRewardMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(rewardDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Reward in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamReward() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        reward.setId(UUID.randomUUID().toString());

        // Create the Reward
        RewardDTO rewardDTO = rewardMapper.toDto(reward);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRewardMockMvc
            .perform(
                patch(ENTITY_API_URL).with(csrf()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(rewardDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Reward in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteReward() throws Exception {
        // Initialize the database
        rewardRepository.saveAndFlush(reward);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the reward
        restRewardMockMvc
            .perform(delete(ENTITY_API_URL_ID, reward.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return rewardRepository.count();
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

    protected Reward getPersistedReward(Reward reward) {
        return rewardRepository.findById(reward.getId()).orElseThrow();
    }

    protected void assertPersistedRewardToMatchAllProperties(Reward expectedReward) {
        assertRewardAllPropertiesEquals(expectedReward, getPersistedReward(expectedReward));
    }

    protected void assertPersistedRewardToMatchUpdatableProperties(Reward expectedReward) {
        assertRewardAllUpdatablePropertiesEquals(expectedReward, getPersistedReward(expectedReward));
    }
}
