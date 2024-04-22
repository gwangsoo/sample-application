package com.phoenixdarts.toss.web.rest;

import static com.phoenixdarts.toss.domain.MatchCallAsserts.*;
import static com.phoenixdarts.toss.web.rest.TestUtil.createUpdateProxyForBean;
import static com.phoenixdarts.toss.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.phoenixdarts.toss.IntegrationTest;
import com.phoenixdarts.toss.domain.MatchCall;
import com.phoenixdarts.toss.repository.MatchCallRepository;
import com.phoenixdarts.toss.service.dto.MatchCallDTO;
import com.phoenixdarts.toss.service.mapper.MatchCallMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
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
 * Integration tests for the {@link MatchCallResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MatchCallResourceIT {

    private static final ZonedDateTime DEFAULT_CALL_DATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CALL_DATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_CALL_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_CALL_MESSAGE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/match-calls";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private MatchCallRepository matchCallRepository;

    @Autowired
    private MatchCallMapper matchCallMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMatchCallMockMvc;

    private MatchCall matchCall;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MatchCall createEntity(EntityManager em) {
        MatchCall matchCall = new MatchCall().callDateTime(DEFAULT_CALL_DATE_TIME).callMessage(DEFAULT_CALL_MESSAGE);
        return matchCall;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MatchCall createUpdatedEntity(EntityManager em) {
        MatchCall matchCall = new MatchCall().callDateTime(UPDATED_CALL_DATE_TIME).callMessage(UPDATED_CALL_MESSAGE);
        return matchCall;
    }

    @BeforeEach
    public void initTest() {
        matchCall = createEntity(em);
    }

    @Test
    @Transactional
    void createMatchCall() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the MatchCall
        MatchCallDTO matchCallDTO = matchCallMapper.toDto(matchCall);
        var returnedMatchCallDTO = om.readValue(
            restMatchCallMockMvc
                .perform(
                    post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(matchCallDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            MatchCallDTO.class
        );

        // Validate the MatchCall in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedMatchCall = matchCallMapper.toEntity(returnedMatchCallDTO);
        assertMatchCallUpdatableFieldsEquals(returnedMatchCall, getPersistedMatchCall(returnedMatchCall));
    }

    @Test
    @Transactional
    void createMatchCallWithExistingId() throws Exception {
        // Create the MatchCall with an existing ID
        matchCall.setId("existing_id");
        MatchCallDTO matchCallDTO = matchCallMapper.toDto(matchCall);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMatchCallMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(matchCallDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MatchCall in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMatchCalls() throws Exception {
        // Initialize the database
        matchCallRepository.saveAndFlush(matchCall);

        // Get all the matchCallList
        restMatchCallMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(matchCall.getId())))
            .andExpect(jsonPath("$.[*].callDateTime").value(hasItem(sameInstant(DEFAULT_CALL_DATE_TIME))))
            .andExpect(jsonPath("$.[*].callMessage").value(hasItem(DEFAULT_CALL_MESSAGE)));
    }

    @Test
    @Transactional
    void getMatchCall() throws Exception {
        // Initialize the database
        matchCallRepository.saveAndFlush(matchCall);

        // Get the matchCall
        restMatchCallMockMvc
            .perform(get(ENTITY_API_URL_ID, matchCall.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(matchCall.getId()))
            .andExpect(jsonPath("$.callDateTime").value(sameInstant(DEFAULT_CALL_DATE_TIME)))
            .andExpect(jsonPath("$.callMessage").value(DEFAULT_CALL_MESSAGE));
    }

    @Test
    @Transactional
    void getNonExistingMatchCall() throws Exception {
        // Get the matchCall
        restMatchCallMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMatchCall() throws Exception {
        // Initialize the database
        matchCallRepository.saveAndFlush(matchCall);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the matchCall
        MatchCall updatedMatchCall = matchCallRepository.findById(matchCall.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedMatchCall are not directly saved in db
        em.detach(updatedMatchCall);
        updatedMatchCall.callDateTime(UPDATED_CALL_DATE_TIME).callMessage(UPDATED_CALL_MESSAGE);
        MatchCallDTO matchCallDTO = matchCallMapper.toDto(updatedMatchCall);

        restMatchCallMockMvc
            .perform(
                put(ENTITY_API_URL_ID, matchCallDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(matchCallDTO))
            )
            .andExpect(status().isOk());

        // Validate the MatchCall in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedMatchCallToMatchAllProperties(updatedMatchCall);
    }

    @Test
    @Transactional
    void putNonExistingMatchCall() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        matchCall.setId(UUID.randomUUID().toString());

        // Create the MatchCall
        MatchCallDTO matchCallDTO = matchCallMapper.toDto(matchCall);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMatchCallMockMvc
            .perform(
                put(ENTITY_API_URL_ID, matchCallDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(matchCallDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MatchCall in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMatchCall() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        matchCall.setId(UUID.randomUUID().toString());

        // Create the MatchCall
        MatchCallDTO matchCallDTO = matchCallMapper.toDto(matchCall);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMatchCallMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(matchCallDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MatchCall in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMatchCall() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        matchCall.setId(UUID.randomUUID().toString());

        // Create the MatchCall
        MatchCallDTO matchCallDTO = matchCallMapper.toDto(matchCall);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMatchCallMockMvc
            .perform(put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(matchCallDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the MatchCall in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMatchCallWithPatch() throws Exception {
        // Initialize the database
        matchCallRepository.saveAndFlush(matchCall);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the matchCall using partial update
        MatchCall partialUpdatedMatchCall = new MatchCall();
        partialUpdatedMatchCall.setId(matchCall.getId());

        restMatchCallMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMatchCall.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMatchCall))
            )
            .andExpect(status().isOk());

        // Validate the MatchCall in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMatchCallUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedMatchCall, matchCall),
            getPersistedMatchCall(matchCall)
        );
    }

    @Test
    @Transactional
    void fullUpdateMatchCallWithPatch() throws Exception {
        // Initialize the database
        matchCallRepository.saveAndFlush(matchCall);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the matchCall using partial update
        MatchCall partialUpdatedMatchCall = new MatchCall();
        partialUpdatedMatchCall.setId(matchCall.getId());

        partialUpdatedMatchCall.callDateTime(UPDATED_CALL_DATE_TIME).callMessage(UPDATED_CALL_MESSAGE);

        restMatchCallMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMatchCall.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMatchCall))
            )
            .andExpect(status().isOk());

        // Validate the MatchCall in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMatchCallUpdatableFieldsEquals(partialUpdatedMatchCall, getPersistedMatchCall(partialUpdatedMatchCall));
    }

    @Test
    @Transactional
    void patchNonExistingMatchCall() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        matchCall.setId(UUID.randomUUID().toString());

        // Create the MatchCall
        MatchCallDTO matchCallDTO = matchCallMapper.toDto(matchCall);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMatchCallMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, matchCallDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(matchCallDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MatchCall in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMatchCall() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        matchCall.setId(UUID.randomUUID().toString());

        // Create the MatchCall
        MatchCallDTO matchCallDTO = matchCallMapper.toDto(matchCall);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMatchCallMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(matchCallDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MatchCall in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMatchCall() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        matchCall.setId(UUID.randomUUID().toString());

        // Create the MatchCall
        MatchCallDTO matchCallDTO = matchCallMapper.toDto(matchCall);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMatchCallMockMvc
            .perform(
                patch(ENTITY_API_URL).with(csrf()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(matchCallDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MatchCall in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMatchCall() throws Exception {
        // Initialize the database
        matchCallRepository.saveAndFlush(matchCall);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the matchCall
        restMatchCallMockMvc
            .perform(delete(ENTITY_API_URL_ID, matchCall.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return matchCallRepository.count();
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

    protected MatchCall getPersistedMatchCall(MatchCall matchCall) {
        return matchCallRepository.findById(matchCall.getId()).orElseThrow();
    }

    protected void assertPersistedMatchCallToMatchAllProperties(MatchCall expectedMatchCall) {
        assertMatchCallAllPropertiesEquals(expectedMatchCall, getPersistedMatchCall(expectedMatchCall));
    }

    protected void assertPersistedMatchCallToMatchUpdatableProperties(MatchCall expectedMatchCall) {
        assertMatchCallAllUpdatablePropertiesEquals(expectedMatchCall, getPersistedMatchCall(expectedMatchCall));
    }
}
