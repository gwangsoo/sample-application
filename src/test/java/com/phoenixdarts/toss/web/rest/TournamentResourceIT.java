package com.phoenixdarts.toss.backend.web.rest;

import static com.phoenixdarts.toss.backend.domain.TournamentAsserts.*;
import static com.phoenixdarts.toss.backend.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.phoenixdarts.toss.IntegrationTest;
import com.phoenixdarts.toss.backend.domain.Tournament;
import com.phoenixdarts.toss.backend.domain.enumeration.DivisionAssignMethod;
import com.phoenixdarts.toss.backend.domain.enumeration.DivisionRuleType;
import com.phoenixdarts.toss.backend.domain.enumeration.EntryApprovalType;
import com.phoenixdarts.toss.backend.domain.enumeration.EntryGenderType;
import com.phoenixdarts.toss.backend.domain.enumeration.HandicapType;
import com.phoenixdarts.toss.backend.domain.enumeration.SeedingRuleType;
import com.phoenixdarts.toss.backend.domain.enumeration.TournamentPlayMode;
import com.phoenixdarts.toss.backend.domain.enumeration.TournamentType;
import com.phoenixdarts.toss.backend.repository.TournamentRepository;
import com.phoenixdarts.toss.backend.service.dto.TournamentDTO;
import com.phoenixdarts.toss.backend.service.mapper.TournamentMapper;
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
 * Integration tests for the {@link TournamentResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TournamentResourceIT {

    private static final Integer DEFAULT_SEQ = 1;
    private static final Integer UPDATED_SEQ = 2;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_DAY = 1;
    private static final Integer UPDATED_DAY = 2;

    private static final Boolean DEFAULT_EVENT_TOURNAMENT = false;
    private static final Boolean UPDATED_EVENT_TOURNAMENT = true;

    private static final Boolean DEFAULT_ENTRY_CLOSE = false;
    private static final Boolean UPDATED_ENTRY_CLOSE = true;

    private static final EntryApprovalType DEFAULT_ENTRY_APPROVAL_TYPE = EntryApprovalType.AUTO;
    private static final EntryApprovalType UPDATED_ENTRY_APPROVAL_TYPE = EntryApprovalType.MANUAL;

    private static final TournamentType DEFAULT_TOURNAMENT_TYPE = TournamentType.ROUNDROBIN;
    private static final TournamentType UPDATED_TOURNAMENT_TYPE = TournamentType.SINGLE_ELIMINATION;

    private static final SeedingRuleType DEFAULT_SEEDING_RULE = SeedingRuleType.RANDOM;
    private static final SeedingRuleType UPDATED_SEEDING_RULE = SeedingRuleType.RATING;

    private static final TournamentPlayMode DEFAULT_TOURNAMENT_PLAY_MODE = TournamentPlayMode.SINGLE;
    private static final TournamentPlayMode UPDATED_TOURNAMENT_PLAY_MODE = TournamentPlayMode.DOUBLE;

    private static final Boolean DEFAULT_THIRD_PLACE_DECISION = false;
    private static final Boolean UPDATED_THIRD_PLACE_DECISION = true;

    private static final DivisionRuleType DEFAULT_DIVISION_RULE = DivisionRuleType.CARD_RATING;
    private static final DivisionRuleType UPDATED_DIVISION_RULE = DivisionRuleType.GENERAL_RATING;

    private static final Boolean DEFAULT_ENTRY_LIMIT = false;
    private static final Boolean UPDATED_ENTRY_LIMIT = true;

    private static final Integer DEFAULT_NUM_OF_ENTRY = 0;
    private static final Integer UPDATED_NUM_OF_ENTRY = 1;

    private static final DivisionAssignMethod DEFAULT_DIVISION_ASSIGN_METHOD = DivisionAssignMethod.AUTO;
    private static final DivisionAssignMethod UPDATED_DIVISION_ASSIGN_METHOD = DivisionAssignMethod.PLAYER;

    private static final EntryGenderType DEFAULT_ENTRY_GENDER_TYPE = EntryGenderType.ALL;
    private static final EntryGenderType UPDATED_ENTRY_GENDER_TYPE = EntryGenderType.MALE;

    private static final HandicapType DEFAULT_HANDICAP = HandicapType.NO;
    private static final HandicapType UPDATED_HANDICAP = HandicapType.OFFICIAL;

    private static final String ENTITY_API_URL = "/api/tournaments";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TournamentRepository tournamentRepository;

    @Autowired
    private TournamentMapper tournamentMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTournamentMockMvc;

    private Tournament tournament;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tournament createEntity(EntityManager em) {
        Tournament tournament = new Tournament()
            .seq(DEFAULT_SEQ)
            .name(DEFAULT_NAME)
            .day(DEFAULT_DAY)
            .eventTournament(DEFAULT_EVENT_TOURNAMENT)
            .entryClose(DEFAULT_ENTRY_CLOSE)
            .entryApprovalType(DEFAULT_ENTRY_APPROVAL_TYPE)
            .tournamentType(DEFAULT_TOURNAMENT_TYPE)
            .seedingRule(DEFAULT_SEEDING_RULE)
            .tournamentPlayMode(DEFAULT_TOURNAMENT_PLAY_MODE)
            .thirdPlaceDecision(DEFAULT_THIRD_PLACE_DECISION)
            .divisionRule(DEFAULT_DIVISION_RULE)
            .entryLimit(DEFAULT_ENTRY_LIMIT)
            .numOfEntry(DEFAULT_NUM_OF_ENTRY)
            .divisionAssignMethod(DEFAULT_DIVISION_ASSIGN_METHOD)
            .entryGenderType(DEFAULT_ENTRY_GENDER_TYPE)
            .handicap(DEFAULT_HANDICAP);
        return tournament;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tournament createUpdatedEntity(EntityManager em) {
        Tournament tournament = new Tournament()
            .seq(UPDATED_SEQ)
            .name(UPDATED_NAME)
            .day(UPDATED_DAY)
            .eventTournament(UPDATED_EVENT_TOURNAMENT)
            .entryClose(UPDATED_ENTRY_CLOSE)
            .entryApprovalType(UPDATED_ENTRY_APPROVAL_TYPE)
            .tournamentType(UPDATED_TOURNAMENT_TYPE)
            .seedingRule(UPDATED_SEEDING_RULE)
            .tournamentPlayMode(UPDATED_TOURNAMENT_PLAY_MODE)
            .thirdPlaceDecision(UPDATED_THIRD_PLACE_DECISION)
            .divisionRule(UPDATED_DIVISION_RULE)
            .entryLimit(UPDATED_ENTRY_LIMIT)
            .numOfEntry(UPDATED_NUM_OF_ENTRY)
            .divisionAssignMethod(UPDATED_DIVISION_ASSIGN_METHOD)
            .entryGenderType(UPDATED_ENTRY_GENDER_TYPE)
            .handicap(UPDATED_HANDICAP);
        return tournament;
    }

    @BeforeEach
    public void initTest() {
        tournament = createEntity(em);
    }

    @Test
    @Transactional
    void createTournament() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Tournament
        TournamentDTO tournamentDTO = tournamentMapper.toDto(tournament);
        var returnedTournamentDTO = om.readValue(
            restTournamentMockMvc
                .perform(
                    post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tournamentDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            TournamentDTO.class
        );

        // Validate the Tournament in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedTournament = tournamentMapper.toEntity(returnedTournamentDTO);
        assertTournamentUpdatableFieldsEquals(returnedTournament, getPersistedTournament(returnedTournament));
    }

    @Test
    @Transactional
    void createTournamentWithExistingId() throws Exception {
        // Create the Tournament with an existing ID
        tournament.setId("existing_id");
        TournamentDTO tournamentDTO = tournamentMapper.toDto(tournament);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTournamentMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tournamentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Tournament in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkSeqIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        tournament.setSeq(null);

        // Create the Tournament, which fails.
        TournamentDTO tournamentDTO = tournamentMapper.toDto(tournament);

        restTournamentMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tournamentDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        tournament.setName(null);

        // Create the Tournament, which fails.
        TournamentDTO tournamentDTO = tournamentMapper.toDto(tournament);

        restTournamentMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tournamentDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDayIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        tournament.setDay(null);

        // Create the Tournament, which fails.
        TournamentDTO tournamentDTO = tournamentMapper.toDto(tournament);

        restTournamentMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tournamentDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEntryApprovalTypeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        tournament.setEntryApprovalType(null);

        // Create the Tournament, which fails.
        TournamentDTO tournamentDTO = tournamentMapper.toDto(tournament);

        restTournamentMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tournamentDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTournamentTypeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        tournament.setTournamentType(null);

        // Create the Tournament, which fails.
        TournamentDTO tournamentDTO = tournamentMapper.toDto(tournament);

        restTournamentMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tournamentDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSeedingRuleIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        tournament.setSeedingRule(null);

        // Create the Tournament, which fails.
        TournamentDTO tournamentDTO = tournamentMapper.toDto(tournament);

        restTournamentMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tournamentDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTournamentPlayModeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        tournament.setTournamentPlayMode(null);

        // Create the Tournament, which fails.
        TournamentDTO tournamentDTO = tournamentMapper.toDto(tournament);

        restTournamentMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tournamentDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDivisionRuleIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        tournament.setDivisionRule(null);

        // Create the Tournament, which fails.
        TournamentDTO tournamentDTO = tournamentMapper.toDto(tournament);

        restTournamentMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tournamentDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEntryLimitIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        tournament.setEntryLimit(null);

        // Create the Tournament, which fails.
        TournamentDTO tournamentDTO = tournamentMapper.toDto(tournament);

        restTournamentMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tournamentDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNumOfEntryIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        tournament.setNumOfEntry(null);

        // Create the Tournament, which fails.
        TournamentDTO tournamentDTO = tournamentMapper.toDto(tournament);

        restTournamentMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tournamentDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDivisionAssignMethodIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        tournament.setDivisionAssignMethod(null);

        // Create the Tournament, which fails.
        TournamentDTO tournamentDTO = tournamentMapper.toDto(tournament);

        restTournamentMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tournamentDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEntryGenderTypeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        tournament.setEntryGenderType(null);

        // Create the Tournament, which fails.
        TournamentDTO tournamentDTO = tournamentMapper.toDto(tournament);

        restTournamentMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tournamentDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkHandicapIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        tournament.setHandicap(null);

        // Create the Tournament, which fails.
        TournamentDTO tournamentDTO = tournamentMapper.toDto(tournament);

        restTournamentMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tournamentDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTournaments() throws Exception {
        // Initialize the database
        tournamentRepository.saveAndFlush(tournament);

        // Get all the tournamentList
        restTournamentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tournament.getId())))
            .andExpect(jsonPath("$.[*].seq").value(hasItem(DEFAULT_SEQ)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].day").value(hasItem(DEFAULT_DAY)))
            .andExpect(jsonPath("$.[*].eventTournament").value(hasItem(DEFAULT_EVENT_TOURNAMENT.booleanValue())))
            .andExpect(jsonPath("$.[*].entryClose").value(hasItem(DEFAULT_ENTRY_CLOSE.booleanValue())))
            .andExpect(jsonPath("$.[*].entryApprovalType").value(hasItem(DEFAULT_ENTRY_APPROVAL_TYPE.toString())))
            .andExpect(jsonPath("$.[*].tournamentType").value(hasItem(DEFAULT_TOURNAMENT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].seedingRule").value(hasItem(DEFAULT_SEEDING_RULE.toString())))
            .andExpect(jsonPath("$.[*].tournamentPlayMode").value(hasItem(DEFAULT_TOURNAMENT_PLAY_MODE.toString())))
            .andExpect(jsonPath("$.[*].thirdPlaceDecision").value(hasItem(DEFAULT_THIRD_PLACE_DECISION.booleanValue())))
            .andExpect(jsonPath("$.[*].divisionRule").value(hasItem(DEFAULT_DIVISION_RULE.toString())))
            .andExpect(jsonPath("$.[*].entryLimit").value(hasItem(DEFAULT_ENTRY_LIMIT.booleanValue())))
            .andExpect(jsonPath("$.[*].numOfEntry").value(hasItem(DEFAULT_NUM_OF_ENTRY)))
            .andExpect(jsonPath("$.[*].divisionAssignMethod").value(hasItem(DEFAULT_DIVISION_ASSIGN_METHOD.toString())))
            .andExpect(jsonPath("$.[*].entryGenderType").value(hasItem(DEFAULT_ENTRY_GENDER_TYPE.toString())))
            .andExpect(jsonPath("$.[*].handicap").value(hasItem(DEFAULT_HANDICAP.toString())));
    }

    @Test
    @Transactional
    void getTournament() throws Exception {
        // Initialize the database
        tournamentRepository.saveAndFlush(tournament);

        // Get the tournament
        restTournamentMockMvc
            .perform(get(ENTITY_API_URL_ID, tournament.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tournament.getId()))
            .andExpect(jsonPath("$.seq").value(DEFAULT_SEQ))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.day").value(DEFAULT_DAY))
            .andExpect(jsonPath("$.eventTournament").value(DEFAULT_EVENT_TOURNAMENT.booleanValue()))
            .andExpect(jsonPath("$.entryClose").value(DEFAULT_ENTRY_CLOSE.booleanValue()))
            .andExpect(jsonPath("$.entryApprovalType").value(DEFAULT_ENTRY_APPROVAL_TYPE.toString()))
            .andExpect(jsonPath("$.tournamentType").value(DEFAULT_TOURNAMENT_TYPE.toString()))
            .andExpect(jsonPath("$.seedingRule").value(DEFAULT_SEEDING_RULE.toString()))
            .andExpect(jsonPath("$.tournamentPlayMode").value(DEFAULT_TOURNAMENT_PLAY_MODE.toString()))
            .andExpect(jsonPath("$.thirdPlaceDecision").value(DEFAULT_THIRD_PLACE_DECISION.booleanValue()))
            .andExpect(jsonPath("$.divisionRule").value(DEFAULT_DIVISION_RULE.toString()))
            .andExpect(jsonPath("$.entryLimit").value(DEFAULT_ENTRY_LIMIT.booleanValue()))
            .andExpect(jsonPath("$.numOfEntry").value(DEFAULT_NUM_OF_ENTRY))
            .andExpect(jsonPath("$.divisionAssignMethod").value(DEFAULT_DIVISION_ASSIGN_METHOD.toString()))
            .andExpect(jsonPath("$.entryGenderType").value(DEFAULT_ENTRY_GENDER_TYPE.toString()))
            .andExpect(jsonPath("$.handicap").value(DEFAULT_HANDICAP.toString()));
    }

    @Test
    @Transactional
    void getNonExistingTournament() throws Exception {
        // Get the tournament
        restTournamentMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTournament() throws Exception {
        // Initialize the database
        tournamentRepository.saveAndFlush(tournament);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tournament
        Tournament updatedTournament = tournamentRepository.findById(tournament.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedTournament are not directly saved in db
        em.detach(updatedTournament);
        updatedTournament
            .seq(UPDATED_SEQ)
            .name(UPDATED_NAME)
            .day(UPDATED_DAY)
            .eventTournament(UPDATED_EVENT_TOURNAMENT)
            .entryClose(UPDATED_ENTRY_CLOSE)
            .entryApprovalType(UPDATED_ENTRY_APPROVAL_TYPE)
            .tournamentType(UPDATED_TOURNAMENT_TYPE)
            .seedingRule(UPDATED_SEEDING_RULE)
            .tournamentPlayMode(UPDATED_TOURNAMENT_PLAY_MODE)
            .thirdPlaceDecision(UPDATED_THIRD_PLACE_DECISION)
            .divisionRule(UPDATED_DIVISION_RULE)
            .entryLimit(UPDATED_ENTRY_LIMIT)
            .numOfEntry(UPDATED_NUM_OF_ENTRY)
            .divisionAssignMethod(UPDATED_DIVISION_ASSIGN_METHOD)
            .entryGenderType(UPDATED_ENTRY_GENDER_TYPE)
            .handicap(UPDATED_HANDICAP);
        TournamentDTO tournamentDTO = tournamentMapper.toDto(updatedTournament);

        restTournamentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tournamentDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(tournamentDTO))
            )
            .andExpect(status().isOk());

        // Validate the Tournament in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedTournamentToMatchAllProperties(updatedTournament);
    }

    @Test
    @Transactional
    void putNonExistingTournament() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tournament.setId(UUID.randomUUID().toString());

        // Create the Tournament
        TournamentDTO tournamentDTO = tournamentMapper.toDto(tournament);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTournamentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tournamentDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(tournamentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tournament in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTournament() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tournament.setId(UUID.randomUUID().toString());

        // Create the Tournament
        TournamentDTO tournamentDTO = tournamentMapper.toDto(tournament);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTournamentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(tournamentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tournament in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTournament() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tournament.setId(UUID.randomUUID().toString());

        // Create the Tournament
        TournamentDTO tournamentDTO = tournamentMapper.toDto(tournament);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTournamentMockMvc
            .perform(put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tournamentDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Tournament in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTournamentWithPatch() throws Exception {
        // Initialize the database
        tournamentRepository.saveAndFlush(tournament);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tournament using partial update
        Tournament partialUpdatedTournament = new Tournament();
        partialUpdatedTournament.setId(tournament.getId());

        partialUpdatedTournament
            .seq(UPDATED_SEQ)
            .entryClose(UPDATED_ENTRY_CLOSE)
            .tournamentType(UPDATED_TOURNAMENT_TYPE)
            .tournamentPlayMode(UPDATED_TOURNAMENT_PLAY_MODE)
            .thirdPlaceDecision(UPDATED_THIRD_PLACE_DECISION)
            .numOfEntry(UPDATED_NUM_OF_ENTRY)
            .divisionAssignMethod(UPDATED_DIVISION_ASSIGN_METHOD);

        restTournamentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTournament.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTournament))
            )
            .andExpect(status().isOk());

        // Validate the Tournament in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTournamentUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedTournament, tournament),
            getPersistedTournament(tournament)
        );
    }

    @Test
    @Transactional
    void fullUpdateTournamentWithPatch() throws Exception {
        // Initialize the database
        tournamentRepository.saveAndFlush(tournament);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tournament using partial update
        Tournament partialUpdatedTournament = new Tournament();
        partialUpdatedTournament.setId(tournament.getId());

        partialUpdatedTournament
            .seq(UPDATED_SEQ)
            .name(UPDATED_NAME)
            .day(UPDATED_DAY)
            .eventTournament(UPDATED_EVENT_TOURNAMENT)
            .entryClose(UPDATED_ENTRY_CLOSE)
            .entryApprovalType(UPDATED_ENTRY_APPROVAL_TYPE)
            .tournamentType(UPDATED_TOURNAMENT_TYPE)
            .seedingRule(UPDATED_SEEDING_RULE)
            .tournamentPlayMode(UPDATED_TOURNAMENT_PLAY_MODE)
            .thirdPlaceDecision(UPDATED_THIRD_PLACE_DECISION)
            .divisionRule(UPDATED_DIVISION_RULE)
            .entryLimit(UPDATED_ENTRY_LIMIT)
            .numOfEntry(UPDATED_NUM_OF_ENTRY)
            .divisionAssignMethod(UPDATED_DIVISION_ASSIGN_METHOD)
            .entryGenderType(UPDATED_ENTRY_GENDER_TYPE)
            .handicap(UPDATED_HANDICAP);

        restTournamentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTournament.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTournament))
            )
            .andExpect(status().isOk());

        // Validate the Tournament in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTournamentUpdatableFieldsEquals(partialUpdatedTournament, getPersistedTournament(partialUpdatedTournament));
    }

    @Test
    @Transactional
    void patchNonExistingTournament() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tournament.setId(UUID.randomUUID().toString());

        // Create the Tournament
        TournamentDTO tournamentDTO = tournamentMapper.toDto(tournament);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTournamentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tournamentDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(tournamentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tournament in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTournament() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tournament.setId(UUID.randomUUID().toString());

        // Create the Tournament
        TournamentDTO tournamentDTO = tournamentMapper.toDto(tournament);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTournamentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(tournamentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tournament in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTournament() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tournament.setId(UUID.randomUUID().toString());

        // Create the Tournament
        TournamentDTO tournamentDTO = tournamentMapper.toDto(tournament);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTournamentMockMvc
            .perform(
                patch(ENTITY_API_URL).with(csrf()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(tournamentDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Tournament in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTournament() throws Exception {
        // Initialize the database
        tournamentRepository.saveAndFlush(tournament);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the tournament
        restTournamentMockMvc
            .perform(delete(ENTITY_API_URL_ID, tournament.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return tournamentRepository.count();
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

    protected Tournament getPersistedTournament(Tournament tournament) {
        return tournamentRepository.findById(tournament.getId()).orElseThrow();
    }

    protected void assertPersistedTournamentToMatchAllProperties(Tournament expectedTournament) {
        assertTournamentAllPropertiesEquals(expectedTournament, getPersistedTournament(expectedTournament));
    }

    protected void assertPersistedTournamentToMatchUpdatableProperties(Tournament expectedTournament) {
        assertTournamentAllUpdatablePropertiesEquals(expectedTournament, getPersistedTournament(expectedTournament));
    }
}
