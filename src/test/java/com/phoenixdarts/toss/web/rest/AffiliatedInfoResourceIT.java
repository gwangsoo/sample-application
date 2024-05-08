package com.phoenixdarts.toss.backend.web.rest;

import static com.phoenixdarts.toss.backend.domain.AffiliatedInfoAsserts.*;
import static com.phoenixdarts.toss.backend.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.phoenixdarts.toss.IntegrationTest;
import com.phoenixdarts.toss.backend.domain.AffiliatedInfo;
import com.phoenixdarts.toss.backend.domain.enumeration.AffiliatedType;
import com.phoenixdarts.toss.backend.repository.AffiliatedInfoRepository;
import com.phoenixdarts.toss.backend.service.dto.AffiliatedInfoDTO;
import com.phoenixdarts.toss.backend.service.mapper.AffiliatedInfoMapper;
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
 * Integration tests for the {@link AffiliatedInfoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AffiliatedInfoResourceIT {

    private static final AffiliatedType DEFAULT_AFFILIATED_TYPE = AffiliatedType.SHOP;
    private static final AffiliatedType UPDATED_AFFILIATED_TYPE = AffiliatedType.CLUB;

    private static final String DEFAULT_SEQ = "AAAAAAAAAA";
    private static final String UPDATED_SEQ = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_TEL_NO = "AAAAAAAAAA";
    private static final String UPDATED_TEL_NO = "BBBBBBBBBB";

    private static final String DEFAULT_HOMEPAGE_URL = "AAAAAAAAAA";
    private static final String UPDATED_HOMEPAGE_URL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/affiliated-infos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AffiliatedInfoRepository affiliatedInfoRepository;

    @Autowired
    private AffiliatedInfoMapper affiliatedInfoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAffiliatedInfoMockMvc;

    private AffiliatedInfo affiliatedInfo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AffiliatedInfo createEntity(EntityManager em) {
        AffiliatedInfo affiliatedInfo = new AffiliatedInfo()
            .affiliatedType(DEFAULT_AFFILIATED_TYPE)
            .seq(DEFAULT_SEQ)
            .name(DEFAULT_NAME)
            .address(DEFAULT_ADDRESS)
            .telNo(DEFAULT_TEL_NO)
            .homepageUrl(DEFAULT_HOMEPAGE_URL);
        return affiliatedInfo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AffiliatedInfo createUpdatedEntity(EntityManager em) {
        AffiliatedInfo affiliatedInfo = new AffiliatedInfo()
            .affiliatedType(UPDATED_AFFILIATED_TYPE)
            .seq(UPDATED_SEQ)
            .name(UPDATED_NAME)
            .address(UPDATED_ADDRESS)
            .telNo(UPDATED_TEL_NO)
            .homepageUrl(UPDATED_HOMEPAGE_URL);
        return affiliatedInfo;
    }

    @BeforeEach
    public void initTest() {
        affiliatedInfo = createEntity(em);
    }

    @Test
    @Transactional
    void createAffiliatedInfo() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the AffiliatedInfo
        AffiliatedInfoDTO affiliatedInfoDTO = affiliatedInfoMapper.toDto(affiliatedInfo);
        var returnedAffiliatedInfoDTO = om.readValue(
            restAffiliatedInfoMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(affiliatedInfoDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            AffiliatedInfoDTO.class
        );

        // Validate the AffiliatedInfo in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedAffiliatedInfo = affiliatedInfoMapper.toEntity(returnedAffiliatedInfoDTO);
        assertAffiliatedInfoUpdatableFieldsEquals(returnedAffiliatedInfo, getPersistedAffiliatedInfo(returnedAffiliatedInfo));
    }

    @Test
    @Transactional
    void createAffiliatedInfoWithExistingId() throws Exception {
        // Create the AffiliatedInfo with an existing ID
        affiliatedInfo.setId("existing_id");
        AffiliatedInfoDTO affiliatedInfoDTO = affiliatedInfoMapper.toDto(affiliatedInfo);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAffiliatedInfoMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(affiliatedInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AffiliatedInfo in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkAffiliatedTypeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        affiliatedInfo.setAffiliatedType(null);

        // Create the AffiliatedInfo, which fails.
        AffiliatedInfoDTO affiliatedInfoDTO = affiliatedInfoMapper.toDto(affiliatedInfo);

        restAffiliatedInfoMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(affiliatedInfoDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        affiliatedInfo.setName(null);

        // Create the AffiliatedInfo, which fails.
        AffiliatedInfoDTO affiliatedInfoDTO = affiliatedInfoMapper.toDto(affiliatedInfo);

        restAffiliatedInfoMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(affiliatedInfoDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAffiliatedInfos() throws Exception {
        // Initialize the database
        affiliatedInfoRepository.saveAndFlush(affiliatedInfo);

        // Get all the affiliatedInfoList
        restAffiliatedInfoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(affiliatedInfo.getId())))
            .andExpect(jsonPath("$.[*].affiliatedType").value(hasItem(DEFAULT_AFFILIATED_TYPE.toString())))
            .andExpect(jsonPath("$.[*].seq").value(hasItem(DEFAULT_SEQ)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].telNo").value(hasItem(DEFAULT_TEL_NO)))
            .andExpect(jsonPath("$.[*].homepageUrl").value(hasItem(DEFAULT_HOMEPAGE_URL)));
    }

    @Test
    @Transactional
    void getAffiliatedInfo() throws Exception {
        // Initialize the database
        affiliatedInfoRepository.saveAndFlush(affiliatedInfo);

        // Get the affiliatedInfo
        restAffiliatedInfoMockMvc
            .perform(get(ENTITY_API_URL_ID, affiliatedInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(affiliatedInfo.getId()))
            .andExpect(jsonPath("$.affiliatedType").value(DEFAULT_AFFILIATED_TYPE.toString()))
            .andExpect(jsonPath("$.seq").value(DEFAULT_SEQ))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.telNo").value(DEFAULT_TEL_NO))
            .andExpect(jsonPath("$.homepageUrl").value(DEFAULT_HOMEPAGE_URL));
    }

    @Test
    @Transactional
    void getNonExistingAffiliatedInfo() throws Exception {
        // Get the affiliatedInfo
        restAffiliatedInfoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAffiliatedInfo() throws Exception {
        // Initialize the database
        affiliatedInfoRepository.saveAndFlush(affiliatedInfo);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the affiliatedInfo
        AffiliatedInfo updatedAffiliatedInfo = affiliatedInfoRepository.findById(affiliatedInfo.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedAffiliatedInfo are not directly saved in db
        em.detach(updatedAffiliatedInfo);
        updatedAffiliatedInfo
            .affiliatedType(UPDATED_AFFILIATED_TYPE)
            .seq(UPDATED_SEQ)
            .name(UPDATED_NAME)
            .address(UPDATED_ADDRESS)
            .telNo(UPDATED_TEL_NO)
            .homepageUrl(UPDATED_HOMEPAGE_URL);
        AffiliatedInfoDTO affiliatedInfoDTO = affiliatedInfoMapper.toDto(updatedAffiliatedInfo);

        restAffiliatedInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, affiliatedInfoDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(affiliatedInfoDTO))
            )
            .andExpect(status().isOk());

        // Validate the AffiliatedInfo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAffiliatedInfoToMatchAllProperties(updatedAffiliatedInfo);
    }

    @Test
    @Transactional
    void putNonExistingAffiliatedInfo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        affiliatedInfo.setId(UUID.randomUUID().toString());

        // Create the AffiliatedInfo
        AffiliatedInfoDTO affiliatedInfoDTO = affiliatedInfoMapper.toDto(affiliatedInfo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAffiliatedInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, affiliatedInfoDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(affiliatedInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AffiliatedInfo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAffiliatedInfo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        affiliatedInfo.setId(UUID.randomUUID().toString());

        // Create the AffiliatedInfo
        AffiliatedInfoDTO affiliatedInfoDTO = affiliatedInfoMapper.toDto(affiliatedInfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAffiliatedInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(affiliatedInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AffiliatedInfo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAffiliatedInfo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        affiliatedInfo.setId(UUID.randomUUID().toString());

        // Create the AffiliatedInfo
        AffiliatedInfoDTO affiliatedInfoDTO = affiliatedInfoMapper.toDto(affiliatedInfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAffiliatedInfoMockMvc
            .perform(
                put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(affiliatedInfoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AffiliatedInfo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAffiliatedInfoWithPatch() throws Exception {
        // Initialize the database
        affiliatedInfoRepository.saveAndFlush(affiliatedInfo);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the affiliatedInfo using partial update
        AffiliatedInfo partialUpdatedAffiliatedInfo = new AffiliatedInfo();
        partialUpdatedAffiliatedInfo.setId(affiliatedInfo.getId());

        partialUpdatedAffiliatedInfo.affiliatedType(UPDATED_AFFILIATED_TYPE).seq(UPDATED_SEQ).homepageUrl(UPDATED_HOMEPAGE_URL);

        restAffiliatedInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAffiliatedInfo.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAffiliatedInfo))
            )
            .andExpect(status().isOk());

        // Validate the AffiliatedInfo in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAffiliatedInfoUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedAffiliatedInfo, affiliatedInfo),
            getPersistedAffiliatedInfo(affiliatedInfo)
        );
    }

    @Test
    @Transactional
    void fullUpdateAffiliatedInfoWithPatch() throws Exception {
        // Initialize the database
        affiliatedInfoRepository.saveAndFlush(affiliatedInfo);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the affiliatedInfo using partial update
        AffiliatedInfo partialUpdatedAffiliatedInfo = new AffiliatedInfo();
        partialUpdatedAffiliatedInfo.setId(affiliatedInfo.getId());

        partialUpdatedAffiliatedInfo
            .affiliatedType(UPDATED_AFFILIATED_TYPE)
            .seq(UPDATED_SEQ)
            .name(UPDATED_NAME)
            .address(UPDATED_ADDRESS)
            .telNo(UPDATED_TEL_NO)
            .homepageUrl(UPDATED_HOMEPAGE_URL);

        restAffiliatedInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAffiliatedInfo.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAffiliatedInfo))
            )
            .andExpect(status().isOk());

        // Validate the AffiliatedInfo in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAffiliatedInfoUpdatableFieldsEquals(partialUpdatedAffiliatedInfo, getPersistedAffiliatedInfo(partialUpdatedAffiliatedInfo));
    }

    @Test
    @Transactional
    void patchNonExistingAffiliatedInfo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        affiliatedInfo.setId(UUID.randomUUID().toString());

        // Create the AffiliatedInfo
        AffiliatedInfoDTO affiliatedInfoDTO = affiliatedInfoMapper.toDto(affiliatedInfo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAffiliatedInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, affiliatedInfoDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(affiliatedInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AffiliatedInfo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAffiliatedInfo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        affiliatedInfo.setId(UUID.randomUUID().toString());

        // Create the AffiliatedInfo
        AffiliatedInfoDTO affiliatedInfoDTO = affiliatedInfoMapper.toDto(affiliatedInfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAffiliatedInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(affiliatedInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AffiliatedInfo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAffiliatedInfo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        affiliatedInfo.setId(UUID.randomUUID().toString());

        // Create the AffiliatedInfo
        AffiliatedInfoDTO affiliatedInfoDTO = affiliatedInfoMapper.toDto(affiliatedInfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAffiliatedInfoMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(affiliatedInfoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AffiliatedInfo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAffiliatedInfo() throws Exception {
        // Initialize the database
        affiliatedInfoRepository.saveAndFlush(affiliatedInfo);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the affiliatedInfo
        restAffiliatedInfoMockMvc
            .perform(delete(ENTITY_API_URL_ID, affiliatedInfo.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return affiliatedInfoRepository.count();
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

    protected AffiliatedInfo getPersistedAffiliatedInfo(AffiliatedInfo affiliatedInfo) {
        return affiliatedInfoRepository.findById(affiliatedInfo.getId()).orElseThrow();
    }

    protected void assertPersistedAffiliatedInfoToMatchAllProperties(AffiliatedInfo expectedAffiliatedInfo) {
        assertAffiliatedInfoAllPropertiesEquals(expectedAffiliatedInfo, getPersistedAffiliatedInfo(expectedAffiliatedInfo));
    }

    protected void assertPersistedAffiliatedInfoToMatchUpdatableProperties(AffiliatedInfo expectedAffiliatedInfo) {
        assertAffiliatedInfoAllUpdatablePropertiesEquals(expectedAffiliatedInfo, getPersistedAffiliatedInfo(expectedAffiliatedInfo));
    }
}
