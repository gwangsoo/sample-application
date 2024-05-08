package com.phoenixdarts.toss.backend.web.rest;

import static com.phoenixdarts.toss.backend.domain.OperatorRoleAsserts.*;
import static com.phoenixdarts.toss.backend.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.phoenixdarts.toss.IntegrationTest;
import com.phoenixdarts.toss.backend.domain.OperatorRole;
import com.phoenixdarts.toss.backend.repository.OperatorRoleRepository;
import com.phoenixdarts.toss.backend.service.dto.OperatorRoleDTO;
import com.phoenixdarts.toss.backend.service.mapper.OperatorRoleMapper;
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
 * Integration tests for the {@link OperatorRoleResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OperatorRoleResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_DISPLAY_ORDER = 1;
    private static final Integer UPDATED_DISPLAY_ORDER = 2;

    private static final String ENTITY_API_URL = "/api/operator-roles";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private OperatorRoleRepository operatorRoleRepository;

    @Autowired
    private OperatorRoleMapper operatorRoleMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOperatorRoleMockMvc;

    private OperatorRole operatorRole;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OperatorRole createEntity(EntityManager em) {
        OperatorRole operatorRole = new OperatorRole().name(DEFAULT_NAME).displayOrder(DEFAULT_DISPLAY_ORDER);
        return operatorRole;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OperatorRole createUpdatedEntity(EntityManager em) {
        OperatorRole operatorRole = new OperatorRole().name(UPDATED_NAME).displayOrder(UPDATED_DISPLAY_ORDER);
        return operatorRole;
    }

    @BeforeEach
    public void initTest() {
        operatorRole = createEntity(em);
    }

    @Test
    @Transactional
    void createOperatorRole() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the OperatorRole
        OperatorRoleDTO operatorRoleDTO = operatorRoleMapper.toDto(operatorRole);
        var returnedOperatorRoleDTO = om.readValue(
            restOperatorRoleMockMvc
                .perform(
                    post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(operatorRoleDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            OperatorRoleDTO.class
        );

        // Validate the OperatorRole in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedOperatorRole = operatorRoleMapper.toEntity(returnedOperatorRoleDTO);
        assertOperatorRoleUpdatableFieldsEquals(returnedOperatorRole, getPersistedOperatorRole(returnedOperatorRole));
    }

    @Test
    @Transactional
    void createOperatorRoleWithExistingId() throws Exception {
        // Create the OperatorRole with an existing ID
        operatorRole.setId("existing_id");
        OperatorRoleDTO operatorRoleDTO = operatorRoleMapper.toDto(operatorRole);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOperatorRoleMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(operatorRoleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OperatorRole in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        operatorRole.setName(null);

        // Create the OperatorRole, which fails.
        OperatorRoleDTO operatorRoleDTO = operatorRoleMapper.toDto(operatorRole);

        restOperatorRoleMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(operatorRoleDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDisplayOrderIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        operatorRole.setDisplayOrder(null);

        // Create the OperatorRole, which fails.
        OperatorRoleDTO operatorRoleDTO = operatorRoleMapper.toDto(operatorRole);

        restOperatorRoleMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(operatorRoleDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllOperatorRoles() throws Exception {
        // Initialize the database
        operatorRoleRepository.saveAndFlush(operatorRole);

        // Get all the operatorRoleList
        restOperatorRoleMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(operatorRole.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].displayOrder").value(hasItem(DEFAULT_DISPLAY_ORDER)));
    }

    @Test
    @Transactional
    void getOperatorRole() throws Exception {
        // Initialize the database
        operatorRoleRepository.saveAndFlush(operatorRole);

        // Get the operatorRole
        restOperatorRoleMockMvc
            .perform(get(ENTITY_API_URL_ID, operatorRole.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(operatorRole.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.displayOrder").value(DEFAULT_DISPLAY_ORDER));
    }

    @Test
    @Transactional
    void getNonExistingOperatorRole() throws Exception {
        // Get the operatorRole
        restOperatorRoleMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingOperatorRole() throws Exception {
        // Initialize the database
        operatorRoleRepository.saveAndFlush(operatorRole);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the operatorRole
        OperatorRole updatedOperatorRole = operatorRoleRepository.findById(operatorRole.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedOperatorRole are not directly saved in db
        em.detach(updatedOperatorRole);
        updatedOperatorRole.name(UPDATED_NAME).displayOrder(UPDATED_DISPLAY_ORDER);
        OperatorRoleDTO operatorRoleDTO = operatorRoleMapper.toDto(updatedOperatorRole);

        restOperatorRoleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, operatorRoleDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(operatorRoleDTO))
            )
            .andExpect(status().isOk());

        // Validate the OperatorRole in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedOperatorRoleToMatchAllProperties(updatedOperatorRole);
    }

    @Test
    @Transactional
    void putNonExistingOperatorRole() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        operatorRole.setId(UUID.randomUUID().toString());

        // Create the OperatorRole
        OperatorRoleDTO operatorRoleDTO = operatorRoleMapper.toDto(operatorRole);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOperatorRoleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, operatorRoleDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(operatorRoleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OperatorRole in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOperatorRole() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        operatorRole.setId(UUID.randomUUID().toString());

        // Create the OperatorRole
        OperatorRoleDTO operatorRoleDTO = operatorRoleMapper.toDto(operatorRole);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOperatorRoleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(operatorRoleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OperatorRole in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOperatorRole() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        operatorRole.setId(UUID.randomUUID().toString());

        // Create the OperatorRole
        OperatorRoleDTO operatorRoleDTO = operatorRoleMapper.toDto(operatorRole);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOperatorRoleMockMvc
            .perform(
                put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(operatorRoleDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OperatorRole in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOperatorRoleWithPatch() throws Exception {
        // Initialize the database
        operatorRoleRepository.saveAndFlush(operatorRole);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the operatorRole using partial update
        OperatorRole partialUpdatedOperatorRole = new OperatorRole();
        partialUpdatedOperatorRole.setId(operatorRole.getId());

        restOperatorRoleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOperatorRole.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOperatorRole))
            )
            .andExpect(status().isOk());

        // Validate the OperatorRole in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOperatorRoleUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedOperatorRole, operatorRole),
            getPersistedOperatorRole(operatorRole)
        );
    }

    @Test
    @Transactional
    void fullUpdateOperatorRoleWithPatch() throws Exception {
        // Initialize the database
        operatorRoleRepository.saveAndFlush(operatorRole);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the operatorRole using partial update
        OperatorRole partialUpdatedOperatorRole = new OperatorRole();
        partialUpdatedOperatorRole.setId(operatorRole.getId());

        partialUpdatedOperatorRole.name(UPDATED_NAME).displayOrder(UPDATED_DISPLAY_ORDER);

        restOperatorRoleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOperatorRole.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOperatorRole))
            )
            .andExpect(status().isOk());

        // Validate the OperatorRole in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOperatorRoleUpdatableFieldsEquals(partialUpdatedOperatorRole, getPersistedOperatorRole(partialUpdatedOperatorRole));
    }

    @Test
    @Transactional
    void patchNonExistingOperatorRole() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        operatorRole.setId(UUID.randomUUID().toString());

        // Create the OperatorRole
        OperatorRoleDTO operatorRoleDTO = operatorRoleMapper.toDto(operatorRole);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOperatorRoleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, operatorRoleDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(operatorRoleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OperatorRole in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOperatorRole() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        operatorRole.setId(UUID.randomUUID().toString());

        // Create the OperatorRole
        OperatorRoleDTO operatorRoleDTO = operatorRoleMapper.toDto(operatorRole);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOperatorRoleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(operatorRoleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OperatorRole in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOperatorRole() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        operatorRole.setId(UUID.randomUUID().toString());

        // Create the OperatorRole
        OperatorRoleDTO operatorRoleDTO = operatorRoleMapper.toDto(operatorRole);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOperatorRoleMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(operatorRoleDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OperatorRole in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOperatorRole() throws Exception {
        // Initialize the database
        operatorRoleRepository.saveAndFlush(operatorRole);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the operatorRole
        restOperatorRoleMockMvc
            .perform(delete(ENTITY_API_URL_ID, operatorRole.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return operatorRoleRepository.count();
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

    protected OperatorRole getPersistedOperatorRole(OperatorRole operatorRole) {
        return operatorRoleRepository.findById(operatorRole.getId()).orElseThrow();
    }

    protected void assertPersistedOperatorRoleToMatchAllProperties(OperatorRole expectedOperatorRole) {
        assertOperatorRoleAllPropertiesEquals(expectedOperatorRole, getPersistedOperatorRole(expectedOperatorRole));
    }

    protected void assertPersistedOperatorRoleToMatchUpdatableProperties(OperatorRole expectedOperatorRole) {
        assertOperatorRoleAllUpdatablePropertiesEquals(expectedOperatorRole, getPersistedOperatorRole(expectedOperatorRole));
    }
}
