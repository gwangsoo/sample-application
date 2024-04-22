package com.phoenixdarts.toss.web.rest;

import static com.phoenixdarts.toss.domain.DivisionAsserts.*;
import static com.phoenixdarts.toss.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.phoenixdarts.toss.IntegrationTest;
import com.phoenixdarts.toss.domain.Division;
import com.phoenixdarts.toss.domain.enumeration.EventRangeType;
import com.phoenixdarts.toss.domain.enumeration.NextRoundDecisionType;
import com.phoenixdarts.toss.domain.enumeration.RoundRobinGroupType;
import com.phoenixdarts.toss.domain.enumeration.RoundRobinRankingDecisionType;
import com.phoenixdarts.toss.domain.enumeration.ThirdDecisionRankingRule;
import com.phoenixdarts.toss.repository.DivisionRepository;
import com.phoenixdarts.toss.service.dto.DivisionDTO;
import com.phoenixdarts.toss.service.mapper.DivisionMapper;
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
 * Integration tests for the {@link DivisionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DivisionResourceIT {

    private static final Integer DEFAULT_SEQ = 1;
    private static final Integer UPDATED_SEQ = 2;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Float DEFAULT_RATING_RULE_TEAM_MIN = 0F;
    private static final Float UPDATED_RATING_RULE_TEAM_MIN = 1F;

    private static final Float DEFAULT_RATING_RULE_TEAM_MAX = 0F;
    private static final Float UPDATED_RATING_RULE_TEAM_MAX = 1F;

    private static final Boolean DEFAULT_RATING_RULE_INDIVIDUAL_LIMIT = false;
    private static final Boolean UPDATED_RATING_RULE_INDIVIDUAL_LIMIT = true;

    private static final Float DEFAULT_RATING_RULE_INDIVIDUAL_MIN = 0F;
    private static final Float UPDATED_RATING_RULE_INDIVIDUAL_MIN = 1F;

    private static final Float DEFAULT_RATING_RULE_INDIVIDUAL_MAX = 0F;
    private static final Float UPDATED_RATING_RULE_INDIVIDUAL_MAX = 1F;

    private static final Boolean DEFAULT_ENTRY_LIMIT = false;
    private static final Boolean UPDATED_ENTRY_LIMIT = true;

    private static final RoundRobinRankingDecisionType DEFAULT_ROUND_ROBIN_RANKING_DECISION_TYPE = RoundRobinRankingDecisionType.PERFECT;
    private static final RoundRobinRankingDecisionType UPDATED_ROUND_ROBIN_RANKING_DECISION_TYPE = RoundRobinRankingDecisionType.AMATEUR1;

    private static final RoundRobinGroupType DEFAULT_ROUND_ROBIN_GROUP_TYPE = RoundRobinGroupType.TEAM4;
    private static final RoundRobinGroupType UPDATED_ROUND_ROBIN_GROUP_TYPE = RoundRobinGroupType.TEAM5;

    private static final NextRoundDecisionType DEFAULT_NEXT_ROUND_DECISION_TYPE = NextRoundDecisionType.FIRST;
    private static final NextRoundDecisionType UPDATED_NEXT_ROUND_DECISION_TYPE = NextRoundDecisionType.FIRST_SECOND;

    private static final Boolean DEFAULT_ROUND_ROGIN_THIRD_DECISION = false;
    private static final Boolean UPDATED_ROUND_ROGIN_THIRD_DECISION = true;

    private static final ThirdDecisionRankingRule DEFAULT_THIRD_DECISION_RANKING_RULE = ThirdDecisionRankingRule.RATING;
    private static final ThirdDecisionRankingRule UPDATED_THIRD_DECISION_RANKING_RULE = ThirdDecisionRankingRule.PPD;

    private static final Boolean DEFAULT_USE_ALL_ROUND_SAME_FORMAT = false;
    private static final Boolean UPDATED_USE_ALL_ROUND_SAME_FORMAT = true;

    private static final EventRangeType DEFAULT_EVENT_RANGE_TYPE = EventRangeType.CARD_RATING;
    private static final EventRangeType UPDATED_EVENT_RANGE_TYPE = EventRangeType.GENERAL_RATING;

    private static final Integer DEFAULT_ELIMINATION_TEAM_COUNT = 1;
    private static final Integer UPDATED_ELIMINATION_TEAM_COUNT = 2;

    private static final String ENTITY_API_URL = "/api/divisions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private DivisionRepository divisionRepository;

    @Autowired
    private DivisionMapper divisionMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDivisionMockMvc;

    private Division division;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Division createEntity(EntityManager em) {
        Division division = new Division()
            .seq(DEFAULT_SEQ)
            .name(DEFAULT_NAME)
            .ratingRuleTeamMin(DEFAULT_RATING_RULE_TEAM_MIN)
            .ratingRuleTeamMax(DEFAULT_RATING_RULE_TEAM_MAX)
            .ratingRuleIndividualLimit(DEFAULT_RATING_RULE_INDIVIDUAL_LIMIT)
            .ratingRuleIndividualMin(DEFAULT_RATING_RULE_INDIVIDUAL_MIN)
            .ratingRuleIndividualMax(DEFAULT_RATING_RULE_INDIVIDUAL_MAX)
            .entryLimit(DEFAULT_ENTRY_LIMIT)
            .roundRobinRankingDecisionType(DEFAULT_ROUND_ROBIN_RANKING_DECISION_TYPE)
            .roundRobinGroupType(DEFAULT_ROUND_ROBIN_GROUP_TYPE)
            .nextRoundDecisionType(DEFAULT_NEXT_ROUND_DECISION_TYPE)
            .roundRoginThirdDecision(DEFAULT_ROUND_ROGIN_THIRD_DECISION)
            .thirdDecisionRankingRule(DEFAULT_THIRD_DECISION_RANKING_RULE)
            .useAllRoundSameFormat(DEFAULT_USE_ALL_ROUND_SAME_FORMAT)
            .eventRangeType(DEFAULT_EVENT_RANGE_TYPE)
            .eliminationTeamCount(DEFAULT_ELIMINATION_TEAM_COUNT);
        return division;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Division createUpdatedEntity(EntityManager em) {
        Division division = new Division()
            .seq(UPDATED_SEQ)
            .name(UPDATED_NAME)
            .ratingRuleTeamMin(UPDATED_RATING_RULE_TEAM_MIN)
            .ratingRuleTeamMax(UPDATED_RATING_RULE_TEAM_MAX)
            .ratingRuleIndividualLimit(UPDATED_RATING_RULE_INDIVIDUAL_LIMIT)
            .ratingRuleIndividualMin(UPDATED_RATING_RULE_INDIVIDUAL_MIN)
            .ratingRuleIndividualMax(UPDATED_RATING_RULE_INDIVIDUAL_MAX)
            .entryLimit(UPDATED_ENTRY_LIMIT)
            .roundRobinRankingDecisionType(UPDATED_ROUND_ROBIN_RANKING_DECISION_TYPE)
            .roundRobinGroupType(UPDATED_ROUND_ROBIN_GROUP_TYPE)
            .nextRoundDecisionType(UPDATED_NEXT_ROUND_DECISION_TYPE)
            .roundRoginThirdDecision(UPDATED_ROUND_ROGIN_THIRD_DECISION)
            .thirdDecisionRankingRule(UPDATED_THIRD_DECISION_RANKING_RULE)
            .useAllRoundSameFormat(UPDATED_USE_ALL_ROUND_SAME_FORMAT)
            .eventRangeType(UPDATED_EVENT_RANGE_TYPE)
            .eliminationTeamCount(UPDATED_ELIMINATION_TEAM_COUNT);
        return division;
    }

    @BeforeEach
    public void initTest() {
        division = createEntity(em);
    }

    @Test
    @Transactional
    void createDivision() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Division
        DivisionDTO divisionDTO = divisionMapper.toDto(division);
        var returnedDivisionDTO = om.readValue(
            restDivisionMockMvc
                .perform(
                    post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(divisionDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            DivisionDTO.class
        );

        // Validate the Division in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedDivision = divisionMapper.toEntity(returnedDivisionDTO);
        assertDivisionUpdatableFieldsEquals(returnedDivision, getPersistedDivision(returnedDivision));
    }

    @Test
    @Transactional
    void createDivisionWithExistingId() throws Exception {
        // Create the Division with an existing ID
        division.setId("existing_id");
        DivisionDTO divisionDTO = divisionMapper.toDto(division);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDivisionMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(divisionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Division in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkSeqIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        division.setSeq(null);

        // Create the Division, which fails.
        DivisionDTO divisionDTO = divisionMapper.toDto(division);

        restDivisionMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(divisionDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        division.setName(null);

        // Create the Division, which fails.
        DivisionDTO divisionDTO = divisionMapper.toDto(division);

        restDivisionMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(divisionDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEntryLimitIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        division.setEntryLimit(null);

        // Create the Division, which fails.
        DivisionDTO divisionDTO = divisionMapper.toDto(division);

        restDivisionMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(divisionDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDivisions() throws Exception {
        // Initialize the database
        divisionRepository.saveAndFlush(division);

        // Get all the divisionList
        restDivisionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(division.getId())))
            .andExpect(jsonPath("$.[*].seq").value(hasItem(DEFAULT_SEQ)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].ratingRuleTeamMin").value(hasItem(DEFAULT_RATING_RULE_TEAM_MIN.doubleValue())))
            .andExpect(jsonPath("$.[*].ratingRuleTeamMax").value(hasItem(DEFAULT_RATING_RULE_TEAM_MAX.doubleValue())))
            .andExpect(jsonPath("$.[*].ratingRuleIndividualLimit").value(hasItem(DEFAULT_RATING_RULE_INDIVIDUAL_LIMIT.booleanValue())))
            .andExpect(jsonPath("$.[*].ratingRuleIndividualMin").value(hasItem(DEFAULT_RATING_RULE_INDIVIDUAL_MIN.doubleValue())))
            .andExpect(jsonPath("$.[*].ratingRuleIndividualMax").value(hasItem(DEFAULT_RATING_RULE_INDIVIDUAL_MAX.doubleValue())))
            .andExpect(jsonPath("$.[*].entryLimit").value(hasItem(DEFAULT_ENTRY_LIMIT.booleanValue())))
            .andExpect(jsonPath("$.[*].roundRobinRankingDecisionType").value(hasItem(DEFAULT_ROUND_ROBIN_RANKING_DECISION_TYPE.toString())))
            .andExpect(jsonPath("$.[*].roundRobinGroupType").value(hasItem(DEFAULT_ROUND_ROBIN_GROUP_TYPE.toString())))
            .andExpect(jsonPath("$.[*].nextRoundDecisionType").value(hasItem(DEFAULT_NEXT_ROUND_DECISION_TYPE.toString())))
            .andExpect(jsonPath("$.[*].roundRoginThirdDecision").value(hasItem(DEFAULT_ROUND_ROGIN_THIRD_DECISION.booleanValue())))
            .andExpect(jsonPath("$.[*].thirdDecisionRankingRule").value(hasItem(DEFAULT_THIRD_DECISION_RANKING_RULE.toString())))
            .andExpect(jsonPath("$.[*].useAllRoundSameFormat").value(hasItem(DEFAULT_USE_ALL_ROUND_SAME_FORMAT.booleanValue())))
            .andExpect(jsonPath("$.[*].eventRangeType").value(hasItem(DEFAULT_EVENT_RANGE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].eliminationTeamCount").value(hasItem(DEFAULT_ELIMINATION_TEAM_COUNT)));
    }

    @Test
    @Transactional
    void getDivision() throws Exception {
        // Initialize the database
        divisionRepository.saveAndFlush(division);

        // Get the division
        restDivisionMockMvc
            .perform(get(ENTITY_API_URL_ID, division.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(division.getId()))
            .andExpect(jsonPath("$.seq").value(DEFAULT_SEQ))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.ratingRuleTeamMin").value(DEFAULT_RATING_RULE_TEAM_MIN.doubleValue()))
            .andExpect(jsonPath("$.ratingRuleTeamMax").value(DEFAULT_RATING_RULE_TEAM_MAX.doubleValue()))
            .andExpect(jsonPath("$.ratingRuleIndividualLimit").value(DEFAULT_RATING_RULE_INDIVIDUAL_LIMIT.booleanValue()))
            .andExpect(jsonPath("$.ratingRuleIndividualMin").value(DEFAULT_RATING_RULE_INDIVIDUAL_MIN.doubleValue()))
            .andExpect(jsonPath("$.ratingRuleIndividualMax").value(DEFAULT_RATING_RULE_INDIVIDUAL_MAX.doubleValue()))
            .andExpect(jsonPath("$.entryLimit").value(DEFAULT_ENTRY_LIMIT.booleanValue()))
            .andExpect(jsonPath("$.roundRobinRankingDecisionType").value(DEFAULT_ROUND_ROBIN_RANKING_DECISION_TYPE.toString()))
            .andExpect(jsonPath("$.roundRobinGroupType").value(DEFAULT_ROUND_ROBIN_GROUP_TYPE.toString()))
            .andExpect(jsonPath("$.nextRoundDecisionType").value(DEFAULT_NEXT_ROUND_DECISION_TYPE.toString()))
            .andExpect(jsonPath("$.roundRoginThirdDecision").value(DEFAULT_ROUND_ROGIN_THIRD_DECISION.booleanValue()))
            .andExpect(jsonPath("$.thirdDecisionRankingRule").value(DEFAULT_THIRD_DECISION_RANKING_RULE.toString()))
            .andExpect(jsonPath("$.useAllRoundSameFormat").value(DEFAULT_USE_ALL_ROUND_SAME_FORMAT.booleanValue()))
            .andExpect(jsonPath("$.eventRangeType").value(DEFAULT_EVENT_RANGE_TYPE.toString()))
            .andExpect(jsonPath("$.eliminationTeamCount").value(DEFAULT_ELIMINATION_TEAM_COUNT));
    }

    @Test
    @Transactional
    void getNonExistingDivision() throws Exception {
        // Get the division
        restDivisionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDivision() throws Exception {
        // Initialize the database
        divisionRepository.saveAndFlush(division);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the division
        Division updatedDivision = divisionRepository.findById(division.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedDivision are not directly saved in db
        em.detach(updatedDivision);
        updatedDivision
            .seq(UPDATED_SEQ)
            .name(UPDATED_NAME)
            .ratingRuleTeamMin(UPDATED_RATING_RULE_TEAM_MIN)
            .ratingRuleTeamMax(UPDATED_RATING_RULE_TEAM_MAX)
            .ratingRuleIndividualLimit(UPDATED_RATING_RULE_INDIVIDUAL_LIMIT)
            .ratingRuleIndividualMin(UPDATED_RATING_RULE_INDIVIDUAL_MIN)
            .ratingRuleIndividualMax(UPDATED_RATING_RULE_INDIVIDUAL_MAX)
            .entryLimit(UPDATED_ENTRY_LIMIT)
            .roundRobinRankingDecisionType(UPDATED_ROUND_ROBIN_RANKING_DECISION_TYPE)
            .roundRobinGroupType(UPDATED_ROUND_ROBIN_GROUP_TYPE)
            .nextRoundDecisionType(UPDATED_NEXT_ROUND_DECISION_TYPE)
            .roundRoginThirdDecision(UPDATED_ROUND_ROGIN_THIRD_DECISION)
            .thirdDecisionRankingRule(UPDATED_THIRD_DECISION_RANKING_RULE)
            .useAllRoundSameFormat(UPDATED_USE_ALL_ROUND_SAME_FORMAT)
            .eventRangeType(UPDATED_EVENT_RANGE_TYPE)
            .eliminationTeamCount(UPDATED_ELIMINATION_TEAM_COUNT);
        DivisionDTO divisionDTO = divisionMapper.toDto(updatedDivision);

        restDivisionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, divisionDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(divisionDTO))
            )
            .andExpect(status().isOk());

        // Validate the Division in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedDivisionToMatchAllProperties(updatedDivision);
    }

    @Test
    @Transactional
    void putNonExistingDivision() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        division.setId(UUID.randomUUID().toString());

        // Create the Division
        DivisionDTO divisionDTO = divisionMapper.toDto(division);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDivisionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, divisionDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(divisionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Division in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDivision() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        division.setId(UUID.randomUUID().toString());

        // Create the Division
        DivisionDTO divisionDTO = divisionMapper.toDto(division);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDivisionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(divisionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Division in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDivision() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        division.setId(UUID.randomUUID().toString());

        // Create the Division
        DivisionDTO divisionDTO = divisionMapper.toDto(division);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDivisionMockMvc
            .perform(put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(divisionDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Division in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDivisionWithPatch() throws Exception {
        // Initialize the database
        divisionRepository.saveAndFlush(division);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the division using partial update
        Division partialUpdatedDivision = new Division();
        partialUpdatedDivision.setId(division.getId());

        partialUpdatedDivision
            .seq(UPDATED_SEQ)
            .name(UPDATED_NAME)
            .ratingRuleTeamMin(UPDATED_RATING_RULE_TEAM_MIN)
            .ratingRuleIndividualLimit(UPDATED_RATING_RULE_INDIVIDUAL_LIMIT)
            .ratingRuleIndividualMin(UPDATED_RATING_RULE_INDIVIDUAL_MIN)
            .ratingRuleIndividualMax(UPDATED_RATING_RULE_INDIVIDUAL_MAX)
            .roundRobinRankingDecisionType(UPDATED_ROUND_ROBIN_RANKING_DECISION_TYPE)
            .nextRoundDecisionType(UPDATED_NEXT_ROUND_DECISION_TYPE)
            .roundRoginThirdDecision(UPDATED_ROUND_ROGIN_THIRD_DECISION)
            .thirdDecisionRankingRule(UPDATED_THIRD_DECISION_RANKING_RULE)
            .eventRangeType(UPDATED_EVENT_RANGE_TYPE)
            .eliminationTeamCount(UPDATED_ELIMINATION_TEAM_COUNT);

        restDivisionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDivision.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDivision))
            )
            .andExpect(status().isOk());

        // Validate the Division in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDivisionUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedDivision, division), getPersistedDivision(division));
    }

    @Test
    @Transactional
    void fullUpdateDivisionWithPatch() throws Exception {
        // Initialize the database
        divisionRepository.saveAndFlush(division);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the division using partial update
        Division partialUpdatedDivision = new Division();
        partialUpdatedDivision.setId(division.getId());

        partialUpdatedDivision
            .seq(UPDATED_SEQ)
            .name(UPDATED_NAME)
            .ratingRuleTeamMin(UPDATED_RATING_RULE_TEAM_MIN)
            .ratingRuleTeamMax(UPDATED_RATING_RULE_TEAM_MAX)
            .ratingRuleIndividualLimit(UPDATED_RATING_RULE_INDIVIDUAL_LIMIT)
            .ratingRuleIndividualMin(UPDATED_RATING_RULE_INDIVIDUAL_MIN)
            .ratingRuleIndividualMax(UPDATED_RATING_RULE_INDIVIDUAL_MAX)
            .entryLimit(UPDATED_ENTRY_LIMIT)
            .roundRobinRankingDecisionType(UPDATED_ROUND_ROBIN_RANKING_DECISION_TYPE)
            .roundRobinGroupType(UPDATED_ROUND_ROBIN_GROUP_TYPE)
            .nextRoundDecisionType(UPDATED_NEXT_ROUND_DECISION_TYPE)
            .roundRoginThirdDecision(UPDATED_ROUND_ROGIN_THIRD_DECISION)
            .thirdDecisionRankingRule(UPDATED_THIRD_DECISION_RANKING_RULE)
            .useAllRoundSameFormat(UPDATED_USE_ALL_ROUND_SAME_FORMAT)
            .eventRangeType(UPDATED_EVENT_RANGE_TYPE)
            .eliminationTeamCount(UPDATED_ELIMINATION_TEAM_COUNT);

        restDivisionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDivision.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDivision))
            )
            .andExpect(status().isOk());

        // Validate the Division in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDivisionUpdatableFieldsEquals(partialUpdatedDivision, getPersistedDivision(partialUpdatedDivision));
    }

    @Test
    @Transactional
    void patchNonExistingDivision() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        division.setId(UUID.randomUUID().toString());

        // Create the Division
        DivisionDTO divisionDTO = divisionMapper.toDto(division);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDivisionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, divisionDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(divisionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Division in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDivision() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        division.setId(UUID.randomUUID().toString());

        // Create the Division
        DivisionDTO divisionDTO = divisionMapper.toDto(division);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDivisionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(divisionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Division in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDivision() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        division.setId(UUID.randomUUID().toString());

        // Create the Division
        DivisionDTO divisionDTO = divisionMapper.toDto(division);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDivisionMockMvc
            .perform(
                patch(ENTITY_API_URL).with(csrf()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(divisionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Division in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDivision() throws Exception {
        // Initialize the database
        divisionRepository.saveAndFlush(division);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the division
        restDivisionMockMvc
            .perform(delete(ENTITY_API_URL_ID, division.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return divisionRepository.count();
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

    protected Division getPersistedDivision(Division division) {
        return divisionRepository.findById(division.getId()).orElseThrow();
    }

    protected void assertPersistedDivisionToMatchAllProperties(Division expectedDivision) {
        assertDivisionAllPropertiesEquals(expectedDivision, getPersistedDivision(expectedDivision));
    }

    protected void assertPersistedDivisionToMatchUpdatableProperties(Division expectedDivision) {
        assertDivisionAllUpdatablePropertiesEquals(expectedDivision, getPersistedDivision(expectedDivision));
    }
}
