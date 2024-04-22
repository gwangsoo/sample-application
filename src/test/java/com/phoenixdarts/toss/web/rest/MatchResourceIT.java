package com.phoenixdarts.toss.web.rest;

import static com.phoenixdarts.toss.domain.MatchAsserts.*;
import static com.phoenixdarts.toss.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.phoenixdarts.toss.IntegrationTest;
import com.phoenixdarts.toss.domain.Match;
import com.phoenixdarts.toss.domain.enumeration.MatchStatus;
import com.phoenixdarts.toss.domain.enumeration.MatchType;
import com.phoenixdarts.toss.repository.MatchRepository;
import com.phoenixdarts.toss.service.dto.MatchDTO;
import com.phoenixdarts.toss.service.mapper.MatchMapper;
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
 * Integration tests for the {@link MatchResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MatchResourceIT {

    private static final String DEFAULT_MATCH_NO = "AAAAAAAAA";
    private static final String UPDATED_MATCH_NO = "BBBBBBBBB";

    private static final MatchType DEFAULT_MATCH_TYPE = MatchType.ROUND_ROBIN;
    private static final MatchType UPDATED_MATCH_TYPE = MatchType.WINNER_ELIMINATION;

    private static final Integer DEFAULT_GROUP_NO = 1;
    private static final Integer UPDATED_GROUP_NO = 2;

    private static final Integer DEFAULT_GROUP_MATCH_SEQ = 1;
    private static final Integer UPDATED_GROUP_MATCH_SEQ = 2;

    private static final Integer DEFAULT_ROUND_NUM = 1;
    private static final Integer UPDATED_ROUND_NUM = 2;

    private static final MatchStatus DEFAULT_MATCH_STATUS = MatchStatus.WAIT;
    private static final MatchStatus UPDATED_MATCH_STATUS = MatchStatus.PLAYING;

    private static final String DEFAULT_NEXT_MATCH_NO = "AAAAAAAAA";
    private static final String UPDATED_NEXT_MATCH_NO = "BBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/matches";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private MatchMapper matchMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMatchMockMvc;

    private Match match;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Match createEntity(EntityManager em) {
        Match match = new Match()
            .matchNo(DEFAULT_MATCH_NO)
            .matchType(DEFAULT_MATCH_TYPE)
            .groupNo(DEFAULT_GROUP_NO)
            .groupMatchSeq(DEFAULT_GROUP_MATCH_SEQ)
            .roundNum(DEFAULT_ROUND_NUM)
            .matchStatus(DEFAULT_MATCH_STATUS)
            .nextMatchNo(DEFAULT_NEXT_MATCH_NO);
        return match;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Match createUpdatedEntity(EntityManager em) {
        Match match = new Match()
            .matchNo(UPDATED_MATCH_NO)
            .matchType(UPDATED_MATCH_TYPE)
            .groupNo(UPDATED_GROUP_NO)
            .groupMatchSeq(UPDATED_GROUP_MATCH_SEQ)
            .roundNum(UPDATED_ROUND_NUM)
            .matchStatus(UPDATED_MATCH_STATUS)
            .nextMatchNo(UPDATED_NEXT_MATCH_NO);
        return match;
    }

    @BeforeEach
    public void initTest() {
        match = createEntity(em);
    }

    @Test
    @Transactional
    void createMatch() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Match
        MatchDTO matchDTO = matchMapper.toDto(match);
        var returnedMatchDTO = om.readValue(
            restMatchMockMvc
                .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(matchDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            MatchDTO.class
        );

        // Validate the Match in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedMatch = matchMapper.toEntity(returnedMatchDTO);
        assertMatchUpdatableFieldsEquals(returnedMatch, getPersistedMatch(returnedMatch));
    }

    @Test
    @Transactional
    void createMatchWithExistingId() throws Exception {
        // Create the Match with an existing ID
        match.setId("existing_id");
        MatchDTO matchDTO = matchMapper.toDto(match);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMatchMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(matchDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Match in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkMatchNoIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        match.setMatchNo(null);

        // Create the Match, which fails.
        MatchDTO matchDTO = matchMapper.toDto(match);

        restMatchMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(matchDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMatchTypeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        match.setMatchType(null);

        // Create the Match, which fails.
        MatchDTO matchDTO = matchMapper.toDto(match);

        restMatchMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(matchDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMatchStatusIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        match.setMatchStatus(null);

        // Create the Match, which fails.
        MatchDTO matchDTO = matchMapper.toDto(match);

        restMatchMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(matchDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllMatches() throws Exception {
        // Initialize the database
        matchRepository.saveAndFlush(match);

        // Get all the matchList
        restMatchMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(match.getId())))
            .andExpect(jsonPath("$.[*].matchNo").value(hasItem(DEFAULT_MATCH_NO)))
            .andExpect(jsonPath("$.[*].matchType").value(hasItem(DEFAULT_MATCH_TYPE.toString())))
            .andExpect(jsonPath("$.[*].groupNo").value(hasItem(DEFAULT_GROUP_NO)))
            .andExpect(jsonPath("$.[*].groupMatchSeq").value(hasItem(DEFAULT_GROUP_MATCH_SEQ)))
            .andExpect(jsonPath("$.[*].roundNum").value(hasItem(DEFAULT_ROUND_NUM)))
            .andExpect(jsonPath("$.[*].matchStatus").value(hasItem(DEFAULT_MATCH_STATUS.toString())))
            .andExpect(jsonPath("$.[*].nextMatchNo").value(hasItem(DEFAULT_NEXT_MATCH_NO)));
    }

    @Test
    @Transactional
    void getMatch() throws Exception {
        // Initialize the database
        matchRepository.saveAndFlush(match);

        // Get the match
        restMatchMockMvc
            .perform(get(ENTITY_API_URL_ID, match.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(match.getId()))
            .andExpect(jsonPath("$.matchNo").value(DEFAULT_MATCH_NO))
            .andExpect(jsonPath("$.matchType").value(DEFAULT_MATCH_TYPE.toString()))
            .andExpect(jsonPath("$.groupNo").value(DEFAULT_GROUP_NO))
            .andExpect(jsonPath("$.groupMatchSeq").value(DEFAULT_GROUP_MATCH_SEQ))
            .andExpect(jsonPath("$.roundNum").value(DEFAULT_ROUND_NUM))
            .andExpect(jsonPath("$.matchStatus").value(DEFAULT_MATCH_STATUS.toString()))
            .andExpect(jsonPath("$.nextMatchNo").value(DEFAULT_NEXT_MATCH_NO));
    }

    @Test
    @Transactional
    void getNonExistingMatch() throws Exception {
        // Get the match
        restMatchMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMatch() throws Exception {
        // Initialize the database
        matchRepository.saveAndFlush(match);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the match
        Match updatedMatch = matchRepository.findById(match.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedMatch are not directly saved in db
        em.detach(updatedMatch);
        updatedMatch
            .matchNo(UPDATED_MATCH_NO)
            .matchType(UPDATED_MATCH_TYPE)
            .groupNo(UPDATED_GROUP_NO)
            .groupMatchSeq(UPDATED_GROUP_MATCH_SEQ)
            .roundNum(UPDATED_ROUND_NUM)
            .matchStatus(UPDATED_MATCH_STATUS)
            .nextMatchNo(UPDATED_NEXT_MATCH_NO);
        MatchDTO matchDTO = matchMapper.toDto(updatedMatch);

        restMatchMockMvc
            .perform(
                put(ENTITY_API_URL_ID, matchDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(matchDTO))
            )
            .andExpect(status().isOk());

        // Validate the Match in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedMatchToMatchAllProperties(updatedMatch);
    }

    @Test
    @Transactional
    void putNonExistingMatch() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        match.setId(UUID.randomUUID().toString());

        // Create the Match
        MatchDTO matchDTO = matchMapper.toDto(match);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMatchMockMvc
            .perform(
                put(ENTITY_API_URL_ID, matchDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(matchDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Match in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMatch() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        match.setId(UUID.randomUUID().toString());

        // Create the Match
        MatchDTO matchDTO = matchMapper.toDto(match);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMatchMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(matchDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Match in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMatch() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        match.setId(UUID.randomUUID().toString());

        // Create the Match
        MatchDTO matchDTO = matchMapper.toDto(match);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMatchMockMvc
            .perform(put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(matchDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Match in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMatchWithPatch() throws Exception {
        // Initialize the database
        matchRepository.saveAndFlush(match);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the match using partial update
        Match partialUpdatedMatch = new Match();
        partialUpdatedMatch.setId(match.getId());

        partialUpdatedMatch.matchNo(UPDATED_MATCH_NO).groupNo(UPDATED_GROUP_NO).nextMatchNo(UPDATED_NEXT_MATCH_NO);

        restMatchMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMatch.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMatch))
            )
            .andExpect(status().isOk());

        // Validate the Match in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMatchUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedMatch, match), getPersistedMatch(match));
    }

    @Test
    @Transactional
    void fullUpdateMatchWithPatch() throws Exception {
        // Initialize the database
        matchRepository.saveAndFlush(match);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the match using partial update
        Match partialUpdatedMatch = new Match();
        partialUpdatedMatch.setId(match.getId());

        partialUpdatedMatch
            .matchNo(UPDATED_MATCH_NO)
            .matchType(UPDATED_MATCH_TYPE)
            .groupNo(UPDATED_GROUP_NO)
            .groupMatchSeq(UPDATED_GROUP_MATCH_SEQ)
            .roundNum(UPDATED_ROUND_NUM)
            .matchStatus(UPDATED_MATCH_STATUS)
            .nextMatchNo(UPDATED_NEXT_MATCH_NO);

        restMatchMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMatch.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMatch))
            )
            .andExpect(status().isOk());

        // Validate the Match in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMatchUpdatableFieldsEquals(partialUpdatedMatch, getPersistedMatch(partialUpdatedMatch));
    }

    @Test
    @Transactional
    void patchNonExistingMatch() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        match.setId(UUID.randomUUID().toString());

        // Create the Match
        MatchDTO matchDTO = matchMapper.toDto(match);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMatchMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, matchDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(matchDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Match in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMatch() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        match.setId(UUID.randomUUID().toString());

        // Create the Match
        MatchDTO matchDTO = matchMapper.toDto(match);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMatchMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(matchDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Match in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMatch() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        match.setId(UUID.randomUUID().toString());

        // Create the Match
        MatchDTO matchDTO = matchMapper.toDto(match);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMatchMockMvc
            .perform(patch(ENTITY_API_URL).with(csrf()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(matchDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Match in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMatch() throws Exception {
        // Initialize the database
        matchRepository.saveAndFlush(match);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the match
        restMatchMockMvc
            .perform(delete(ENTITY_API_URL_ID, match.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return matchRepository.count();
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

    protected Match getPersistedMatch(Match match) {
        return matchRepository.findById(match.getId()).orElseThrow();
    }

    protected void assertPersistedMatchToMatchAllProperties(Match expectedMatch) {
        assertMatchAllPropertiesEquals(expectedMatch, getPersistedMatch(expectedMatch));
    }

    protected void assertPersistedMatchToMatchUpdatableProperties(Match expectedMatch) {
        assertMatchAllUpdatablePropertiesEquals(expectedMatch, getPersistedMatch(expectedMatch));
    }
}
