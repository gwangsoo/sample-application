package com.phoenixdarts.toss.web.rest;

import static com.phoenixdarts.toss.domain.CompetitionAsserts.*;
import static com.phoenixdarts.toss.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.phoenixdarts.toss.IntegrationTest;
import com.phoenixdarts.toss.domain.Competition;
import com.phoenixdarts.toss.domain.enumeration.CompetitionStatus;
import com.phoenixdarts.toss.domain.enumeration.EntryApplyType;
import com.phoenixdarts.toss.domain.enumeration.EntryRatingType;
import com.phoenixdarts.toss.repository.CompetitionRepository;
import com.phoenixdarts.toss.service.dto.CompetitionDTO;
import com.phoenixdarts.toss.service.mapper.CompetitionMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Integration tests for the {@link CompetitionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CompetitionResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Instant DEFAULT_START_DATE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_DATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_DATE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_DATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_ENTRY_START_DATE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ENTRY_START_DATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_ENTRY_END_DATE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ENTRY_END_DATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final CompetitionStatus DEFAULT_STATUS = CompetitionStatus.PREPARE;
    private static final CompetitionStatus UPDATED_STATUS = CompetitionStatus.OPENING;

    private static final Boolean DEFAULT_APPROVAL = false;
    private static final Boolean UPDATED_APPROVAL = true;

    private static final EntryApplyType DEFAULT_ENTRY_APPLY_TYPE = EntryApplyType.APP;
    private static final EntryApplyType UPDATED_ENTRY_APPLY_TYPE = EntryApplyType.DIRECT;

    private static final EntryRatingType DEFAULT_ENTRY_RATING_TYPE = EntryRatingType.AUTO;
    private static final EntryRatingType UPDATED_ENTRY_RATING_TYPE = EntryRatingType.DIRECT;

    private static final String ENTITY_API_URL = "/api/competitions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CompetitionRepository competitionRepository;

    @Autowired
    private CompetitionMapper competitionMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCompetitionMockMvc;

    private Competition competition;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Competition createEntity(EntityManager em) {
        Competition competition = new Competition()
            .name(DEFAULT_NAME)
            .startDateTime(DEFAULT_START_DATE_TIME)
            .endDateTime(DEFAULT_END_DATE_TIME)
            .entryStartDateTime(DEFAULT_ENTRY_START_DATE_TIME)
            .entryEndDateTime(DEFAULT_ENTRY_END_DATE_TIME)
            .status(DEFAULT_STATUS)
            .approval(DEFAULT_APPROVAL)
            .entryApplyType(DEFAULT_ENTRY_APPLY_TYPE)
            .entryRatingType(DEFAULT_ENTRY_RATING_TYPE);
        return competition;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Competition createUpdatedEntity(EntityManager em) {
        Competition competition = new Competition()
            .name(UPDATED_NAME)
            .startDateTime(UPDATED_START_DATE_TIME)
            .endDateTime(UPDATED_END_DATE_TIME)
            .entryStartDateTime(UPDATED_ENTRY_START_DATE_TIME)
            .entryEndDateTime(UPDATED_ENTRY_END_DATE_TIME)
            .status(UPDATED_STATUS)
            .approval(UPDATED_APPROVAL)
            .entryApplyType(UPDATED_ENTRY_APPLY_TYPE)
            .entryRatingType(UPDATED_ENTRY_RATING_TYPE);
        return competition;
    }

    @BeforeEach
    public void initTest() {
        competition = createEntity(em);
    }

    @Test
    @Transactional
    void createCompetition() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Competition
        CompetitionDTO competitionDTO = competitionMapper.toDto(competition);
        var returnedCompetitionDTO = om.readValue(
            restCompetitionMockMvc
                .perform(
                    post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(competitionDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            CompetitionDTO.class
        );

        // Validate the Competition in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedCompetition = competitionMapper.toEntity(returnedCompetitionDTO);
        assertCompetitionUpdatableFieldsEquals(returnedCompetition, getPersistedCompetition(returnedCompetition));
    }

    @Test
    @Transactional
    void createCompetitionWithExistingId() throws Exception {
        // Create the Competition with an existing ID
        competition.setId("existing_id");
        CompetitionDTO competitionDTO = competitionMapper.toDto(competition);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompetitionMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(competitionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Competition in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        competition.setName(null);

        // Create the Competition, which fails.
        CompetitionDTO competitionDTO = competitionMapper.toDto(competition);

        restCompetitionMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(competitionDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStartDateTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        competition.setStartDateTime(null);

        // Create the Competition, which fails.
        CompetitionDTO competitionDTO = competitionMapper.toDto(competition);

        restCompetitionMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(competitionDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEndDateTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        competition.setEndDateTime(null);

        // Create the Competition, which fails.
        CompetitionDTO competitionDTO = competitionMapper.toDto(competition);

        restCompetitionMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(competitionDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEntryStartDateTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        competition.setEntryStartDateTime(null);

        // Create the Competition, which fails.
        CompetitionDTO competitionDTO = competitionMapper.toDto(competition);

        restCompetitionMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(competitionDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEntryEndDateTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        competition.setEntryEndDateTime(null);

        // Create the Competition, which fails.
        CompetitionDTO competitionDTO = competitionMapper.toDto(competition);

        restCompetitionMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(competitionDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStatusIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        competition.setStatus(null);

        // Create the Competition, which fails.
        CompetitionDTO competitionDTO = competitionMapper.toDto(competition);

        restCompetitionMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(competitionDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkApprovalIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        competition.setApproval(null);

        // Create the Competition, which fails.
        CompetitionDTO competitionDTO = competitionMapper.toDto(competition);

        restCompetitionMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(competitionDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEntryApplyTypeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        competition.setEntryApplyType(null);

        // Create the Competition, which fails.
        CompetitionDTO competitionDTO = competitionMapper.toDto(competition);

        restCompetitionMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(competitionDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEntryRatingTypeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        competition.setEntryRatingType(null);

        // Create the Competition, which fails.
        CompetitionDTO competitionDTO = competitionMapper.toDto(competition);

        restCompetitionMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(competitionDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCompetitions() throws Exception {
        // Initialize the database
        competitionRepository.saveAndFlush(competition);

        // Get all the competitionList
        restCompetitionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(competition.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].startDateTime").value(hasItem(DEFAULT_START_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].endDateTime").value(hasItem(DEFAULT_END_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].entryStartDateTime").value(hasItem(DEFAULT_ENTRY_START_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].entryEndDateTime").value(hasItem(DEFAULT_ENTRY_END_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].approval").value(hasItem(DEFAULT_APPROVAL.booleanValue())))
            .andExpect(jsonPath("$.[*].entryApplyType").value(hasItem(DEFAULT_ENTRY_APPLY_TYPE.toString())))
            .andExpect(jsonPath("$.[*].entryRatingType").value(hasItem(DEFAULT_ENTRY_RATING_TYPE.toString())));
    }

    @Test
    @Transactional
    void getCompetition() throws Exception {
        // Initialize the database
        competitionRepository.saveAndFlush(competition);

        // Get the competition
        restCompetitionMockMvc
            .perform(get(ENTITY_API_URL_ID, competition.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(competition.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.startDateTime").value(DEFAULT_START_DATE_TIME.toString()))
            .andExpect(jsonPath("$.endDateTime").value(DEFAULT_END_DATE_TIME.toString()))
            .andExpect(jsonPath("$.entryStartDateTime").value(DEFAULT_ENTRY_START_DATE_TIME.toString()))
            .andExpect(jsonPath("$.entryEndDateTime").value(DEFAULT_ENTRY_END_DATE_TIME.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.approval").value(DEFAULT_APPROVAL.booleanValue()))
            .andExpect(jsonPath("$.entryApplyType").value(DEFAULT_ENTRY_APPLY_TYPE.toString()))
            .andExpect(jsonPath("$.entryRatingType").value(DEFAULT_ENTRY_RATING_TYPE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingCompetition() throws Exception {
        // Get the competition
        restCompetitionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCompetition() throws Exception {
        // Initialize the database
        competitionRepository.saveAndFlush(competition);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the competition
        Competition updatedCompetition = competitionRepository.findById(competition.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCompetition are not directly saved in db
        em.detach(updatedCompetition);
        updatedCompetition
            .name(UPDATED_NAME)
            .startDateTime(UPDATED_START_DATE_TIME)
            .endDateTime(UPDATED_END_DATE_TIME)
            .entryStartDateTime(UPDATED_ENTRY_START_DATE_TIME)
            .entryEndDateTime(UPDATED_ENTRY_END_DATE_TIME)
            .status(UPDATED_STATUS)
            .approval(UPDATED_APPROVAL)
            .entryApplyType(UPDATED_ENTRY_APPLY_TYPE)
            .entryRatingType(UPDATED_ENTRY_RATING_TYPE);
        CompetitionDTO competitionDTO = competitionMapper.toDto(updatedCompetition);

        restCompetitionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, competitionDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(competitionDTO))
            )
            .andExpect(status().isOk());

        // Validate the Competition in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCompetitionToMatchAllProperties(updatedCompetition);
    }

    @Test
    @Transactional
    void putNonExistingCompetition() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        competition.setId(UUID.randomUUID().toString());

        // Create the Competition
        CompetitionDTO competitionDTO = competitionMapper.toDto(competition);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompetitionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, competitionDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(competitionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Competition in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCompetition() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        competition.setId(UUID.randomUUID().toString());

        // Create the Competition
        CompetitionDTO competitionDTO = competitionMapper.toDto(competition);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompetitionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(competitionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Competition in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCompetition() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        competition.setId(UUID.randomUUID().toString());

        // Create the Competition
        CompetitionDTO competitionDTO = competitionMapper.toDto(competition);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompetitionMockMvc
            .perform(put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(competitionDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Competition in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCompetitionWithPatch() throws Exception {
        // Initialize the database
        competitionRepository.saveAndFlush(competition);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the competition using partial update
        Competition partialUpdatedCompetition = new Competition();
        partialUpdatedCompetition.setId(competition.getId());

        partialUpdatedCompetition
            .name(UPDATED_NAME)
            .entryStartDateTime(UPDATED_ENTRY_START_DATE_TIME)
            .entryEndDateTime(UPDATED_ENTRY_END_DATE_TIME)
            .status(UPDATED_STATUS)
            .entryRatingType(UPDATED_ENTRY_RATING_TYPE);

        restCompetitionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCompetition.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCompetition))
            )
            .andExpect(status().isOk());

        // Validate the Competition in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCompetitionUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedCompetition, competition),
            getPersistedCompetition(competition)
        );
    }

    @Test
    @Transactional
    void fullUpdateCompetitionWithPatch() throws Exception {
        // Initialize the database
        competitionRepository.saveAndFlush(competition);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the competition using partial update
        Competition partialUpdatedCompetition = new Competition();
        partialUpdatedCompetition.setId(competition.getId());

        partialUpdatedCompetition
            .name(UPDATED_NAME)
            .startDateTime(UPDATED_START_DATE_TIME)
            .endDateTime(UPDATED_END_DATE_TIME)
            .entryStartDateTime(UPDATED_ENTRY_START_DATE_TIME)
            .entryEndDateTime(UPDATED_ENTRY_END_DATE_TIME)
            .status(UPDATED_STATUS)
            .approval(UPDATED_APPROVAL)
            .entryApplyType(UPDATED_ENTRY_APPLY_TYPE)
            .entryRatingType(UPDATED_ENTRY_RATING_TYPE);

        restCompetitionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCompetition.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCompetition))
            )
            .andExpect(status().isOk());

        // Validate the Competition in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCompetitionUpdatableFieldsEquals(partialUpdatedCompetition, getPersistedCompetition(partialUpdatedCompetition));
    }

    @Test
    @Transactional
    void patchNonExistingCompetition() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        competition.setId(UUID.randomUUID().toString());

        // Create the Competition
        CompetitionDTO competitionDTO = competitionMapper.toDto(competition);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompetitionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, competitionDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(competitionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Competition in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCompetition() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        competition.setId(UUID.randomUUID().toString());

        // Create the Competition
        CompetitionDTO competitionDTO = competitionMapper.toDto(competition);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompetitionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(competitionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Competition in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCompetition() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        competition.setId(UUID.randomUUID().toString());

        // Create the Competition
        CompetitionDTO competitionDTO = competitionMapper.toDto(competition);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompetitionMockMvc
            .perform(
                patch(ENTITY_API_URL).with(csrf()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(competitionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Competition in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCompetition() throws Exception {
        // Initialize the database
        competitionRepository.saveAndFlush(competition);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the competition
        restCompetitionMockMvc
            .perform(delete(ENTITY_API_URL_ID, competition.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return competitionRepository.count();
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

    protected Competition getPersistedCompetition(Competition competition) {
        return competitionRepository.findById(competition.getId()).orElseThrow();
    }

    protected void assertPersistedCompetitionToMatchAllProperties(Competition expectedCompetition) {
        assertCompetitionAllPropertiesEquals(expectedCompetition, getPersistedCompetition(expectedCompetition));
    }

    protected void assertPersistedCompetitionToMatchUpdatableProperties(Competition expectedCompetition) {
        assertCompetitionAllUpdatablePropertiesEquals(expectedCompetition, getPersistedCompetition(expectedCompetition));
    }
}
