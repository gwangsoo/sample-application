package com.phoenixdarts.toss.web.rest;

import static com.phoenixdarts.toss.domain.EntryFeeAsserts.*;
import static com.phoenixdarts.toss.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.phoenixdarts.toss.IntegrationTest;
import com.phoenixdarts.toss.domain.EntryFee;
import com.phoenixdarts.toss.domain.enumeration.EntryFeeSubType;
import com.phoenixdarts.toss.domain.enumeration.EntryFeeType;
import com.phoenixdarts.toss.domain.enumeration.PaymentMethodType;
import com.phoenixdarts.toss.repository.EntryFeeRepository;
import com.phoenixdarts.toss.service.dto.EntryFeeDTO;
import com.phoenixdarts.toss.service.mapper.EntryFeeMapper;
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
 * Integration tests for the {@link EntryFeeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EntryFeeResourceIT {

    private static final EntryFeeType DEFAULT_ENTRY_FEE_TYPE = EntryFeeType.FREE;
    private static final EntryFeeType UPDATED_ENTRY_FEE_TYPE = EntryFeeType.PAY;

    private static final EntryFeeSubType DEFAULT_ENTRY_FEE_SUB_TYPE = EntryFeeSubType.DAY;
    private static final EntryFeeSubType UPDATED_ENTRY_FEE_SUB_TYPE = EntryFeeSubType.TOURNAMENT;

    private static final PaymentMethodType DEFAULT_PAYMENT_METHOD_TYPE = PaymentMethodType.PG;
    private static final PaymentMethodType UPDATED_PAYMENT_METHOD_TYPE = PaymentMethodType.CASH;

    private static final Integer DEFAULT_SCHEDULE_NUMBER = 1;
    private static final Integer UPDATED_SCHEDULE_NUMBER = 0;

    private static final Integer DEFAULT_FEE = 1;
    private static final Integer UPDATED_FEE = 2;

    private static final String ENTITY_API_URL = "/api/entry-fees";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private EntryFeeRepository entryFeeRepository;

    @Autowired
    private EntryFeeMapper entryFeeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEntryFeeMockMvc;

    private EntryFee entryFee;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EntryFee createEntity(EntityManager em) {
        EntryFee entryFee = new EntryFee()
            .entryFeeType(DEFAULT_ENTRY_FEE_TYPE)
            .entryFeeSubType(DEFAULT_ENTRY_FEE_SUB_TYPE)
            .paymentMethodType(DEFAULT_PAYMENT_METHOD_TYPE)
            .scheduleNumber(DEFAULT_SCHEDULE_NUMBER)
            .fee(DEFAULT_FEE);
        return entryFee;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EntryFee createUpdatedEntity(EntityManager em) {
        EntryFee entryFee = new EntryFee()
            .entryFeeType(UPDATED_ENTRY_FEE_TYPE)
            .entryFeeSubType(UPDATED_ENTRY_FEE_SUB_TYPE)
            .paymentMethodType(UPDATED_PAYMENT_METHOD_TYPE)
            .scheduleNumber(UPDATED_SCHEDULE_NUMBER)
            .fee(UPDATED_FEE);
        return entryFee;
    }

    @BeforeEach
    public void initTest() {
        entryFee = createEntity(em);
    }

    @Test
    @Transactional
    void createEntryFee() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the EntryFee
        EntryFeeDTO entryFeeDTO = entryFeeMapper.toDto(entryFee);
        var returnedEntryFeeDTO = om.readValue(
            restEntryFeeMockMvc
                .perform(
                    post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(entryFeeDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            EntryFeeDTO.class
        );

        // Validate the EntryFee in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedEntryFee = entryFeeMapper.toEntity(returnedEntryFeeDTO);
        assertEntryFeeUpdatableFieldsEquals(returnedEntryFee, getPersistedEntryFee(returnedEntryFee));
    }

    @Test
    @Transactional
    void createEntryFeeWithExistingId() throws Exception {
        // Create the EntryFee with an existing ID
        entryFee.setId("existing_id");
        EntryFeeDTO entryFeeDTO = entryFeeMapper.toDto(entryFee);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEntryFeeMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(entryFeeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EntryFee in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkEntryFeeTypeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        entryFee.setEntryFeeType(null);

        // Create the EntryFee, which fails.
        EntryFeeDTO entryFeeDTO = entryFeeMapper.toDto(entryFee);

        restEntryFeeMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(entryFeeDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkScheduleNumberIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        entryFee.setScheduleNumber(null);

        // Create the EntryFee, which fails.
        EntryFeeDTO entryFeeDTO = entryFeeMapper.toDto(entryFee);

        restEntryFeeMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(entryFeeDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllEntryFees() throws Exception {
        // Initialize the database
        entryFeeRepository.saveAndFlush(entryFee);

        // Get all the entryFeeList
        restEntryFeeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(entryFee.getId())))
            .andExpect(jsonPath("$.[*].entryFeeType").value(hasItem(DEFAULT_ENTRY_FEE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].entryFeeSubType").value(hasItem(DEFAULT_ENTRY_FEE_SUB_TYPE.toString())))
            .andExpect(jsonPath("$.[*].paymentMethodType").value(hasItem(DEFAULT_PAYMENT_METHOD_TYPE.toString())))
            .andExpect(jsonPath("$.[*].scheduleNumber").value(hasItem(DEFAULT_SCHEDULE_NUMBER)))
            .andExpect(jsonPath("$.[*].fee").value(hasItem(DEFAULT_FEE)));
    }

    @Test
    @Transactional
    void getEntryFee() throws Exception {
        // Initialize the database
        entryFeeRepository.saveAndFlush(entryFee);

        // Get the entryFee
        restEntryFeeMockMvc
            .perform(get(ENTITY_API_URL_ID, entryFee.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(entryFee.getId()))
            .andExpect(jsonPath("$.entryFeeType").value(DEFAULT_ENTRY_FEE_TYPE.toString()))
            .andExpect(jsonPath("$.entryFeeSubType").value(DEFAULT_ENTRY_FEE_SUB_TYPE.toString()))
            .andExpect(jsonPath("$.paymentMethodType").value(DEFAULT_PAYMENT_METHOD_TYPE.toString()))
            .andExpect(jsonPath("$.scheduleNumber").value(DEFAULT_SCHEDULE_NUMBER))
            .andExpect(jsonPath("$.fee").value(DEFAULT_FEE));
    }

    @Test
    @Transactional
    void getNonExistingEntryFee() throws Exception {
        // Get the entryFee
        restEntryFeeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEntryFee() throws Exception {
        // Initialize the database
        entryFeeRepository.saveAndFlush(entryFee);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the entryFee
        EntryFee updatedEntryFee = entryFeeRepository.findById(entryFee.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedEntryFee are not directly saved in db
        em.detach(updatedEntryFee);
        updatedEntryFee
            .entryFeeType(UPDATED_ENTRY_FEE_TYPE)
            .entryFeeSubType(UPDATED_ENTRY_FEE_SUB_TYPE)
            .paymentMethodType(UPDATED_PAYMENT_METHOD_TYPE)
            .scheduleNumber(UPDATED_SCHEDULE_NUMBER)
            .fee(UPDATED_FEE);
        EntryFeeDTO entryFeeDTO = entryFeeMapper.toDto(updatedEntryFee);

        restEntryFeeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, entryFeeDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(entryFeeDTO))
            )
            .andExpect(status().isOk());

        // Validate the EntryFee in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedEntryFeeToMatchAllProperties(updatedEntryFee);
    }

    @Test
    @Transactional
    void putNonExistingEntryFee() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        entryFee.setId(UUID.randomUUID().toString());

        // Create the EntryFee
        EntryFeeDTO entryFeeDTO = entryFeeMapper.toDto(entryFee);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEntryFeeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, entryFeeDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(entryFeeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EntryFee in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEntryFee() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        entryFee.setId(UUID.randomUUID().toString());

        // Create the EntryFee
        EntryFeeDTO entryFeeDTO = entryFeeMapper.toDto(entryFee);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEntryFeeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(entryFeeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EntryFee in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEntryFee() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        entryFee.setId(UUID.randomUUID().toString());

        // Create the EntryFee
        EntryFeeDTO entryFeeDTO = entryFeeMapper.toDto(entryFee);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEntryFeeMockMvc
            .perform(put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(entryFeeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the EntryFee in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEntryFeeWithPatch() throws Exception {
        // Initialize the database
        entryFeeRepository.saveAndFlush(entryFee);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the entryFee using partial update
        EntryFee partialUpdatedEntryFee = new EntryFee();
        partialUpdatedEntryFee.setId(entryFee.getId());

        partialUpdatedEntryFee
            .entryFeeType(UPDATED_ENTRY_FEE_TYPE)
            .entryFeeSubType(UPDATED_ENTRY_FEE_SUB_TYPE)
            .paymentMethodType(UPDATED_PAYMENT_METHOD_TYPE)
            .scheduleNumber(UPDATED_SCHEDULE_NUMBER)
            .fee(UPDATED_FEE);

        restEntryFeeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEntryFee.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEntryFee))
            )
            .andExpect(status().isOk());

        // Validate the EntryFee in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEntryFeeUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedEntryFee, entryFee), getPersistedEntryFee(entryFee));
    }

    @Test
    @Transactional
    void fullUpdateEntryFeeWithPatch() throws Exception {
        // Initialize the database
        entryFeeRepository.saveAndFlush(entryFee);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the entryFee using partial update
        EntryFee partialUpdatedEntryFee = new EntryFee();
        partialUpdatedEntryFee.setId(entryFee.getId());

        partialUpdatedEntryFee
            .entryFeeType(UPDATED_ENTRY_FEE_TYPE)
            .entryFeeSubType(UPDATED_ENTRY_FEE_SUB_TYPE)
            .paymentMethodType(UPDATED_PAYMENT_METHOD_TYPE)
            .scheduleNumber(UPDATED_SCHEDULE_NUMBER)
            .fee(UPDATED_FEE);

        restEntryFeeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEntryFee.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEntryFee))
            )
            .andExpect(status().isOk());

        // Validate the EntryFee in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEntryFeeUpdatableFieldsEquals(partialUpdatedEntryFee, getPersistedEntryFee(partialUpdatedEntryFee));
    }

    @Test
    @Transactional
    void patchNonExistingEntryFee() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        entryFee.setId(UUID.randomUUID().toString());

        // Create the EntryFee
        EntryFeeDTO entryFeeDTO = entryFeeMapper.toDto(entryFee);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEntryFeeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, entryFeeDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(entryFeeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EntryFee in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEntryFee() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        entryFee.setId(UUID.randomUUID().toString());

        // Create the EntryFee
        EntryFeeDTO entryFeeDTO = entryFeeMapper.toDto(entryFee);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEntryFeeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(entryFeeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EntryFee in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEntryFee() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        entryFee.setId(UUID.randomUUID().toString());

        // Create the EntryFee
        EntryFeeDTO entryFeeDTO = entryFeeMapper.toDto(entryFee);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEntryFeeMockMvc
            .perform(
                patch(ENTITY_API_URL).with(csrf()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(entryFeeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EntryFee in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEntryFee() throws Exception {
        // Initialize the database
        entryFeeRepository.saveAndFlush(entryFee);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the entryFee
        restEntryFeeMockMvc
            .perform(delete(ENTITY_API_URL_ID, entryFee.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return entryFeeRepository.count();
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

    protected EntryFee getPersistedEntryFee(EntryFee entryFee) {
        return entryFeeRepository.findById(entryFee.getId()).orElseThrow();
    }

    protected void assertPersistedEntryFeeToMatchAllProperties(EntryFee expectedEntryFee) {
        assertEntryFeeAllPropertiesEquals(expectedEntryFee, getPersistedEntryFee(expectedEntryFee));
    }

    protected void assertPersistedEntryFeeToMatchUpdatableProperties(EntryFee expectedEntryFee) {
        assertEntryFeeAllUpdatablePropertiesEquals(expectedEntryFee, getPersistedEntryFee(expectedEntryFee));
    }
}
