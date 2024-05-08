package com.phoenixdarts.toss.backend.web.rest;

import static com.phoenixdarts.toss.backend.domain.OperatorAsserts.*;
import static com.phoenixdarts.toss.backend.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.phoenixdarts.toss.IntegrationTest;
import com.phoenixdarts.toss.backend.domain.Operator;
import com.phoenixdarts.toss.backend.repository.OperatorRepository;
import com.phoenixdarts.toss.backend.service.dto.OperatorDTO;
import com.phoenixdarts.toss.backend.service.mapper.OperatorMapper;
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
 * Integration tests for the {@link OperatorResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OperatorResourceIT {

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_USER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_USER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_APPROVAL_STATUS = false;
    private static final Boolean UPDATED_APPROVAL_STATUS = true;

    private static final String ENTITY_API_URL = "/api/operators";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private OperatorRepository operatorRepository;

    @Autowired
    private OperatorMapper operatorMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOperatorMockMvc;

    private Operator operator;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Operator createEntity(EntityManager em) {
        Operator operator = new Operator()
            .userId(DEFAULT_USER_ID)
            .userName(DEFAULT_USER_NAME)
            .phone(DEFAULT_PHONE)
            .email(DEFAULT_EMAIL)
            .address(DEFAULT_ADDRESS)
            .approvalStatus(DEFAULT_APPROVAL_STATUS);
        return operator;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Operator createUpdatedEntity(EntityManager em) {
        Operator operator = new Operator()
            .userId(UPDATED_USER_ID)
            .userName(UPDATED_USER_NAME)
            .phone(UPDATED_PHONE)
            .email(UPDATED_EMAIL)
            .address(UPDATED_ADDRESS)
            .approvalStatus(UPDATED_APPROVAL_STATUS);
        return operator;
    }

    @BeforeEach
    public void initTest() {
        operator = createEntity(em);
    }

    @Test
    @Transactional
    void createOperator() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Operator
        OperatorDTO operatorDTO = operatorMapper.toDto(operator);
        var returnedOperatorDTO = om.readValue(
            restOperatorMockMvc
                .perform(
                    post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(operatorDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            OperatorDTO.class
        );

        // Validate the Operator in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedOperator = operatorMapper.toEntity(returnedOperatorDTO);
        assertOperatorUpdatableFieldsEquals(returnedOperator, getPersistedOperator(returnedOperator));
    }

    @Test
    @Transactional
    void createOperatorWithExistingId() throws Exception {
        // Create the Operator with an existing ID
        operator.setId("existing_id");
        OperatorDTO operatorDTO = operatorMapper.toDto(operator);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOperatorMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(operatorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Operator in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkUserIdIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        operator.setUserId(null);

        // Create the Operator, which fails.
        OperatorDTO operatorDTO = operatorMapper.toDto(operator);

        restOperatorMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(operatorDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUserNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        operator.setUserName(null);

        // Create the Operator, which fails.
        OperatorDTO operatorDTO = operatorMapper.toDto(operator);

        restOperatorMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(operatorDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkApprovalStatusIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        operator.setApprovalStatus(null);

        // Create the Operator, which fails.
        OperatorDTO operatorDTO = operatorMapper.toDto(operator);

        restOperatorMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(operatorDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllOperators() throws Exception {
        // Initialize the database
        operatorRepository.saveAndFlush(operator);

        // Get all the operatorList
        restOperatorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(operator.getId())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].userName").value(hasItem(DEFAULT_USER_NAME)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].approvalStatus").value(hasItem(DEFAULT_APPROVAL_STATUS.booleanValue())));
    }

    @Test
    @Transactional
    void getOperator() throws Exception {
        // Initialize the database
        operatorRepository.saveAndFlush(operator);

        // Get the operator
        restOperatorMockMvc
            .perform(get(ENTITY_API_URL_ID, operator.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(operator.getId()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.userName").value(DEFAULT_USER_NAME))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.approvalStatus").value(DEFAULT_APPROVAL_STATUS.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingOperator() throws Exception {
        // Get the operator
        restOperatorMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingOperator() throws Exception {
        // Initialize the database
        operatorRepository.saveAndFlush(operator);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the operator
        Operator updatedOperator = operatorRepository.findById(operator.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedOperator are not directly saved in db
        em.detach(updatedOperator);
        updatedOperator
            .userId(UPDATED_USER_ID)
            .userName(UPDATED_USER_NAME)
            .phone(UPDATED_PHONE)
            .email(UPDATED_EMAIL)
            .address(UPDATED_ADDRESS)
            .approvalStatus(UPDATED_APPROVAL_STATUS);
        OperatorDTO operatorDTO = operatorMapper.toDto(updatedOperator);

        restOperatorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, operatorDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(operatorDTO))
            )
            .andExpect(status().isOk());

        // Validate the Operator in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedOperatorToMatchAllProperties(updatedOperator);
    }

    @Test
    @Transactional
    void putNonExistingOperator() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        operator.setId(UUID.randomUUID().toString());

        // Create the Operator
        OperatorDTO operatorDTO = operatorMapper.toDto(operator);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOperatorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, operatorDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(operatorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Operator in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOperator() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        operator.setId(UUID.randomUUID().toString());

        // Create the Operator
        OperatorDTO operatorDTO = operatorMapper.toDto(operator);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOperatorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(operatorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Operator in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOperator() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        operator.setId(UUID.randomUUID().toString());

        // Create the Operator
        OperatorDTO operatorDTO = operatorMapper.toDto(operator);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOperatorMockMvc
            .perform(put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(operatorDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Operator in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOperatorWithPatch() throws Exception {
        // Initialize the database
        operatorRepository.saveAndFlush(operator);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the operator using partial update
        Operator partialUpdatedOperator = new Operator();
        partialUpdatedOperator.setId(operator.getId());

        partialUpdatedOperator.userId(UPDATED_USER_ID).userName(UPDATED_USER_NAME);

        restOperatorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOperator.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOperator))
            )
            .andExpect(status().isOk());

        // Validate the Operator in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOperatorUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedOperator, operator), getPersistedOperator(operator));
    }

    @Test
    @Transactional
    void fullUpdateOperatorWithPatch() throws Exception {
        // Initialize the database
        operatorRepository.saveAndFlush(operator);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the operator using partial update
        Operator partialUpdatedOperator = new Operator();
        partialUpdatedOperator.setId(operator.getId());

        partialUpdatedOperator
            .userId(UPDATED_USER_ID)
            .userName(UPDATED_USER_NAME)
            .phone(UPDATED_PHONE)
            .email(UPDATED_EMAIL)
            .address(UPDATED_ADDRESS)
            .approvalStatus(UPDATED_APPROVAL_STATUS);

        restOperatorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOperator.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOperator))
            )
            .andExpect(status().isOk());

        // Validate the Operator in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOperatorUpdatableFieldsEquals(partialUpdatedOperator, getPersistedOperator(partialUpdatedOperator));
    }

    @Test
    @Transactional
    void patchNonExistingOperator() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        operator.setId(UUID.randomUUID().toString());

        // Create the Operator
        OperatorDTO operatorDTO = operatorMapper.toDto(operator);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOperatorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, operatorDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(operatorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Operator in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOperator() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        operator.setId(UUID.randomUUID().toString());

        // Create the Operator
        OperatorDTO operatorDTO = operatorMapper.toDto(operator);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOperatorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(operatorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Operator in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOperator() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        operator.setId(UUID.randomUUID().toString());

        // Create the Operator
        OperatorDTO operatorDTO = operatorMapper.toDto(operator);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOperatorMockMvc
            .perform(
                patch(ENTITY_API_URL).with(csrf()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(operatorDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Operator in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOperator() throws Exception {
        // Initialize the database
        operatorRepository.saveAndFlush(operator);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the operator
        restOperatorMockMvc
            .perform(delete(ENTITY_API_URL_ID, operator.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return operatorRepository.count();
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

    protected Operator getPersistedOperator(Operator operator) {
        return operatorRepository.findById(operator.getId()).orElseThrow();
    }

    protected void assertPersistedOperatorToMatchAllProperties(Operator expectedOperator) {
        assertOperatorAllPropertiesEquals(expectedOperator, getPersistedOperator(expectedOperator));
    }

    protected void assertPersistedOperatorToMatchUpdatableProperties(Operator expectedOperator) {
        assertOperatorAllUpdatablePropertiesEquals(expectedOperator, getPersistedOperator(expectedOperator));
    }
}
