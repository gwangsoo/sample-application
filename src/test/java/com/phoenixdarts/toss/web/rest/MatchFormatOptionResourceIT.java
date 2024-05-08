package com.phoenixdarts.toss.backend.web.rest;

import static com.phoenixdarts.toss.backend.domain.MatchFormatOptionAsserts.*;
import static com.phoenixdarts.toss.backend.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.phoenixdarts.toss.IntegrationTest;
import com.phoenixdarts.toss.backend.domain.MatchFormatOption;
import com.phoenixdarts.toss.backend.domain.enumeration.MatchFormatBullOptionType;
import com.phoenixdarts.toss.backend.domain.enumeration.MatchFormatFreezeOptionType;
import com.phoenixdarts.toss.backend.domain.enumeration.MatchFormatInOptionType;
import com.phoenixdarts.toss.backend.domain.enumeration.MatchFormatOutOptionType;
import com.phoenixdarts.toss.backend.domain.enumeration.MatchFormatTeamFinishOptionType;
import com.phoenixdarts.toss.backend.repository.MatchFormatOptionRepository;
import com.phoenixdarts.toss.backend.service.dto.MatchFormatOptionDTO;
import com.phoenixdarts.toss.backend.service.mapper.MatchFormatOptionMapper;
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
 * Integration tests for the {@link MatchFormatOptionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MatchFormatOptionResourceIT {

    private static final MatchFormatInOptionType DEFAULT_GAME_01_IN_OPTION_TYPE = MatchFormatInOptionType.OPEN;
    private static final MatchFormatInOptionType UPDATED_GAME_01_IN_OPTION_TYPE = MatchFormatInOptionType.DOUBLE;

    private static final MatchFormatOutOptionType DEFAULT_GAME_01_OUT_OPTION_TYPE = MatchFormatOutOptionType.OPEN;
    private static final MatchFormatOutOptionType UPDATED_GAME_01_OUT_OPTION_TYPE = MatchFormatOutOptionType.DOUBLE;

    private static final MatchFormatBullOptionType DEFAULT_GAME_01_BULL_OPTION_TYPE = MatchFormatBullOptionType.BULL_25_50;
    private static final MatchFormatBullOptionType UPDATED_GAME_01_BULL_OPTION_TYPE = MatchFormatBullOptionType.BULL_50_50;

    private static final Boolean DEFAULT_GAME_01_ARRANGE = false;
    private static final Boolean UPDATED_GAME_01_ARRANGE = true;

    private static final Boolean DEFAULT_CRICKET_OVER_KILL = false;
    private static final Boolean UPDATED_CRICKET_OVER_KILL = true;

    private static final Integer DEFAULT_CRICKET_SCORE = 1;
    private static final Integer UPDATED_CRICKET_SCORE = 2;

    private static final MatchFormatFreezeOptionType DEFAULT_TEAM_GAME_01_FREEZE_VIEW = MatchFormatFreezeOptionType.BUST;
    private static final MatchFormatFreezeOptionType UPDATED_TEAM_GAME_01_FREEZE_VIEW = MatchFormatFreezeOptionType.LOSE;

    private static final Boolean DEFAULT_TEAM_GAME_01_POINT = false;
    private static final Boolean UPDATED_TEAM_GAME_01_POINT = true;

    private static final Boolean DEFAULT_TEAM_CRICKET_MARK = false;
    private static final Boolean UPDATED_TEAM_CRICKET_MARK = true;

    private static final MatchFormatTeamFinishOptionType DEFAULT_TEAM_CRICKET_FINISH = MatchFormatTeamFinishOptionType.ONE_PLAYER_ONLY;
    private static final MatchFormatTeamFinishOptionType UPDATED_TEAM_CRICKET_FINISH = MatchFormatTeamFinishOptionType.ALL_PLAYER;

    private static final Boolean DEFAULT_TEAM_CRICKET_POINT = false;
    private static final Boolean UPDATED_TEAM_CRICKET_POINT = true;

    private static final String ENTITY_API_URL = "/api/match-format-options";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private MatchFormatOptionRepository matchFormatOptionRepository;

    @Autowired
    private MatchFormatOptionMapper matchFormatOptionMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMatchFormatOptionMockMvc;

    private MatchFormatOption matchFormatOption;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MatchFormatOption createEntity(EntityManager em) {
        MatchFormatOption matchFormatOption = new MatchFormatOption()
            .game01InOptionType(DEFAULT_GAME_01_IN_OPTION_TYPE)
            .game01OutOptionType(DEFAULT_GAME_01_OUT_OPTION_TYPE)
            .game01BullOptionType(DEFAULT_GAME_01_BULL_OPTION_TYPE)
            .game01Arrange(DEFAULT_GAME_01_ARRANGE)
            .cricketOverKill(DEFAULT_CRICKET_OVER_KILL)
            .cricketScore(DEFAULT_CRICKET_SCORE)
            .teamGame01FreezeView(DEFAULT_TEAM_GAME_01_FREEZE_VIEW)
            .teamGame01Point(DEFAULT_TEAM_GAME_01_POINT)
            .teamCricketMark(DEFAULT_TEAM_CRICKET_MARK)
            .teamCricketFinish(DEFAULT_TEAM_CRICKET_FINISH)
            .teamCricketPoint(DEFAULT_TEAM_CRICKET_POINT);
        return matchFormatOption;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MatchFormatOption createUpdatedEntity(EntityManager em) {
        MatchFormatOption matchFormatOption = new MatchFormatOption()
            .game01InOptionType(UPDATED_GAME_01_IN_OPTION_TYPE)
            .game01OutOptionType(UPDATED_GAME_01_OUT_OPTION_TYPE)
            .game01BullOptionType(UPDATED_GAME_01_BULL_OPTION_TYPE)
            .game01Arrange(UPDATED_GAME_01_ARRANGE)
            .cricketOverKill(UPDATED_CRICKET_OVER_KILL)
            .cricketScore(UPDATED_CRICKET_SCORE)
            .teamGame01FreezeView(UPDATED_TEAM_GAME_01_FREEZE_VIEW)
            .teamGame01Point(UPDATED_TEAM_GAME_01_POINT)
            .teamCricketMark(UPDATED_TEAM_CRICKET_MARK)
            .teamCricketFinish(UPDATED_TEAM_CRICKET_FINISH)
            .teamCricketPoint(UPDATED_TEAM_CRICKET_POINT);
        return matchFormatOption;
    }

    @BeforeEach
    public void initTest() {
        matchFormatOption = createEntity(em);
    }

    @Test
    @Transactional
    void createMatchFormatOption() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the MatchFormatOption
        MatchFormatOptionDTO matchFormatOptionDTO = matchFormatOptionMapper.toDto(matchFormatOption);
        var returnedMatchFormatOptionDTO = om.readValue(
            restMatchFormatOptionMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(matchFormatOptionDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            MatchFormatOptionDTO.class
        );

        // Validate the MatchFormatOption in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedMatchFormatOption = matchFormatOptionMapper.toEntity(returnedMatchFormatOptionDTO);
        assertMatchFormatOptionUpdatableFieldsEquals(returnedMatchFormatOption, getPersistedMatchFormatOption(returnedMatchFormatOption));
    }

    @Test
    @Transactional
    void createMatchFormatOptionWithExistingId() throws Exception {
        // Create the MatchFormatOption with an existing ID
        matchFormatOption.setId("existing_id");
        MatchFormatOptionDTO matchFormatOptionDTO = matchFormatOptionMapper.toDto(matchFormatOption);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMatchFormatOptionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(matchFormatOptionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MatchFormatOption in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMatchFormatOptions() throws Exception {
        // Initialize the database
        matchFormatOptionRepository.saveAndFlush(matchFormatOption);

        // Get all the matchFormatOptionList
        restMatchFormatOptionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(matchFormatOption.getId())))
            .andExpect(jsonPath("$.[*].game01InOptionType").value(hasItem(DEFAULT_GAME_01_IN_OPTION_TYPE.toString())))
            .andExpect(jsonPath("$.[*].game01OutOptionType").value(hasItem(DEFAULT_GAME_01_OUT_OPTION_TYPE.toString())))
            .andExpect(jsonPath("$.[*].game01BullOptionType").value(hasItem(DEFAULT_GAME_01_BULL_OPTION_TYPE.toString())))
            .andExpect(jsonPath("$.[*].game01Arrange").value(hasItem(DEFAULT_GAME_01_ARRANGE.booleanValue())))
            .andExpect(jsonPath("$.[*].cricketOverKill").value(hasItem(DEFAULT_CRICKET_OVER_KILL.booleanValue())))
            .andExpect(jsonPath("$.[*].cricketScore").value(hasItem(DEFAULT_CRICKET_SCORE)))
            .andExpect(jsonPath("$.[*].teamGame01FreezeView").value(hasItem(DEFAULT_TEAM_GAME_01_FREEZE_VIEW.toString())))
            .andExpect(jsonPath("$.[*].teamGame01Point").value(hasItem(DEFAULT_TEAM_GAME_01_POINT.booleanValue())))
            .andExpect(jsonPath("$.[*].teamCricketMark").value(hasItem(DEFAULT_TEAM_CRICKET_MARK.booleanValue())))
            .andExpect(jsonPath("$.[*].teamCricketFinish").value(hasItem(DEFAULT_TEAM_CRICKET_FINISH.toString())))
            .andExpect(jsonPath("$.[*].teamCricketPoint").value(hasItem(DEFAULT_TEAM_CRICKET_POINT.booleanValue())));
    }

    @Test
    @Transactional
    void getMatchFormatOption() throws Exception {
        // Initialize the database
        matchFormatOptionRepository.saveAndFlush(matchFormatOption);

        // Get the matchFormatOption
        restMatchFormatOptionMockMvc
            .perform(get(ENTITY_API_URL_ID, matchFormatOption.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(matchFormatOption.getId()))
            .andExpect(jsonPath("$.game01InOptionType").value(DEFAULT_GAME_01_IN_OPTION_TYPE.toString()))
            .andExpect(jsonPath("$.game01OutOptionType").value(DEFAULT_GAME_01_OUT_OPTION_TYPE.toString()))
            .andExpect(jsonPath("$.game01BullOptionType").value(DEFAULT_GAME_01_BULL_OPTION_TYPE.toString()))
            .andExpect(jsonPath("$.game01Arrange").value(DEFAULT_GAME_01_ARRANGE.booleanValue()))
            .andExpect(jsonPath("$.cricketOverKill").value(DEFAULT_CRICKET_OVER_KILL.booleanValue()))
            .andExpect(jsonPath("$.cricketScore").value(DEFAULT_CRICKET_SCORE))
            .andExpect(jsonPath("$.teamGame01FreezeView").value(DEFAULT_TEAM_GAME_01_FREEZE_VIEW.toString()))
            .andExpect(jsonPath("$.teamGame01Point").value(DEFAULT_TEAM_GAME_01_POINT.booleanValue()))
            .andExpect(jsonPath("$.teamCricketMark").value(DEFAULT_TEAM_CRICKET_MARK.booleanValue()))
            .andExpect(jsonPath("$.teamCricketFinish").value(DEFAULT_TEAM_CRICKET_FINISH.toString()))
            .andExpect(jsonPath("$.teamCricketPoint").value(DEFAULT_TEAM_CRICKET_POINT.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingMatchFormatOption() throws Exception {
        // Get the matchFormatOption
        restMatchFormatOptionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMatchFormatOption() throws Exception {
        // Initialize the database
        matchFormatOptionRepository.saveAndFlush(matchFormatOption);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the matchFormatOption
        MatchFormatOption updatedMatchFormatOption = matchFormatOptionRepository.findById(matchFormatOption.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedMatchFormatOption are not directly saved in db
        em.detach(updatedMatchFormatOption);
        updatedMatchFormatOption
            .game01InOptionType(UPDATED_GAME_01_IN_OPTION_TYPE)
            .game01OutOptionType(UPDATED_GAME_01_OUT_OPTION_TYPE)
            .game01BullOptionType(UPDATED_GAME_01_BULL_OPTION_TYPE)
            .game01Arrange(UPDATED_GAME_01_ARRANGE)
            .cricketOverKill(UPDATED_CRICKET_OVER_KILL)
            .cricketScore(UPDATED_CRICKET_SCORE)
            .teamGame01FreezeView(UPDATED_TEAM_GAME_01_FREEZE_VIEW)
            .teamGame01Point(UPDATED_TEAM_GAME_01_POINT)
            .teamCricketMark(UPDATED_TEAM_CRICKET_MARK)
            .teamCricketFinish(UPDATED_TEAM_CRICKET_FINISH)
            .teamCricketPoint(UPDATED_TEAM_CRICKET_POINT);
        MatchFormatOptionDTO matchFormatOptionDTO = matchFormatOptionMapper.toDto(updatedMatchFormatOption);

        restMatchFormatOptionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, matchFormatOptionDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(matchFormatOptionDTO))
            )
            .andExpect(status().isOk());

        // Validate the MatchFormatOption in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedMatchFormatOptionToMatchAllProperties(updatedMatchFormatOption);
    }

    @Test
    @Transactional
    void putNonExistingMatchFormatOption() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        matchFormatOption.setId(UUID.randomUUID().toString());

        // Create the MatchFormatOption
        MatchFormatOptionDTO matchFormatOptionDTO = matchFormatOptionMapper.toDto(matchFormatOption);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMatchFormatOptionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, matchFormatOptionDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(matchFormatOptionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MatchFormatOption in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMatchFormatOption() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        matchFormatOption.setId(UUID.randomUUID().toString());

        // Create the MatchFormatOption
        MatchFormatOptionDTO matchFormatOptionDTO = matchFormatOptionMapper.toDto(matchFormatOption);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMatchFormatOptionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(matchFormatOptionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MatchFormatOption in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMatchFormatOption() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        matchFormatOption.setId(UUID.randomUUID().toString());

        // Create the MatchFormatOption
        MatchFormatOptionDTO matchFormatOptionDTO = matchFormatOptionMapper.toDto(matchFormatOption);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMatchFormatOptionMockMvc
            .perform(
                put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(matchFormatOptionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MatchFormatOption in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMatchFormatOptionWithPatch() throws Exception {
        // Initialize the database
        matchFormatOptionRepository.saveAndFlush(matchFormatOption);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the matchFormatOption using partial update
        MatchFormatOption partialUpdatedMatchFormatOption = new MatchFormatOption();
        partialUpdatedMatchFormatOption.setId(matchFormatOption.getId());

        partialUpdatedMatchFormatOption
            .game01Arrange(UPDATED_GAME_01_ARRANGE)
            .teamGame01FreezeView(UPDATED_TEAM_GAME_01_FREEZE_VIEW)
            .teamGame01Point(UPDATED_TEAM_GAME_01_POINT)
            .teamCricketFinish(UPDATED_TEAM_CRICKET_FINISH);

        restMatchFormatOptionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMatchFormatOption.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMatchFormatOption))
            )
            .andExpect(status().isOk());

        // Validate the MatchFormatOption in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMatchFormatOptionUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedMatchFormatOption, matchFormatOption),
            getPersistedMatchFormatOption(matchFormatOption)
        );
    }

    @Test
    @Transactional
    void fullUpdateMatchFormatOptionWithPatch() throws Exception {
        // Initialize the database
        matchFormatOptionRepository.saveAndFlush(matchFormatOption);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the matchFormatOption using partial update
        MatchFormatOption partialUpdatedMatchFormatOption = new MatchFormatOption();
        partialUpdatedMatchFormatOption.setId(matchFormatOption.getId());

        partialUpdatedMatchFormatOption
            .game01InOptionType(UPDATED_GAME_01_IN_OPTION_TYPE)
            .game01OutOptionType(UPDATED_GAME_01_OUT_OPTION_TYPE)
            .game01BullOptionType(UPDATED_GAME_01_BULL_OPTION_TYPE)
            .game01Arrange(UPDATED_GAME_01_ARRANGE)
            .cricketOverKill(UPDATED_CRICKET_OVER_KILL)
            .cricketScore(UPDATED_CRICKET_SCORE)
            .teamGame01FreezeView(UPDATED_TEAM_GAME_01_FREEZE_VIEW)
            .teamGame01Point(UPDATED_TEAM_GAME_01_POINT)
            .teamCricketMark(UPDATED_TEAM_CRICKET_MARK)
            .teamCricketFinish(UPDATED_TEAM_CRICKET_FINISH)
            .teamCricketPoint(UPDATED_TEAM_CRICKET_POINT);

        restMatchFormatOptionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMatchFormatOption.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMatchFormatOption))
            )
            .andExpect(status().isOk());

        // Validate the MatchFormatOption in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMatchFormatOptionUpdatableFieldsEquals(
            partialUpdatedMatchFormatOption,
            getPersistedMatchFormatOption(partialUpdatedMatchFormatOption)
        );
    }

    @Test
    @Transactional
    void patchNonExistingMatchFormatOption() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        matchFormatOption.setId(UUID.randomUUID().toString());

        // Create the MatchFormatOption
        MatchFormatOptionDTO matchFormatOptionDTO = matchFormatOptionMapper.toDto(matchFormatOption);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMatchFormatOptionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, matchFormatOptionDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(matchFormatOptionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MatchFormatOption in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMatchFormatOption() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        matchFormatOption.setId(UUID.randomUUID().toString());

        // Create the MatchFormatOption
        MatchFormatOptionDTO matchFormatOptionDTO = matchFormatOptionMapper.toDto(matchFormatOption);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMatchFormatOptionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(matchFormatOptionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MatchFormatOption in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMatchFormatOption() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        matchFormatOption.setId(UUID.randomUUID().toString());

        // Create the MatchFormatOption
        MatchFormatOptionDTO matchFormatOptionDTO = matchFormatOptionMapper.toDto(matchFormatOption);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMatchFormatOptionMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(matchFormatOptionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MatchFormatOption in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMatchFormatOption() throws Exception {
        // Initialize the database
        matchFormatOptionRepository.saveAndFlush(matchFormatOption);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the matchFormatOption
        restMatchFormatOptionMockMvc
            .perform(delete(ENTITY_API_URL_ID, matchFormatOption.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return matchFormatOptionRepository.count();
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

    protected MatchFormatOption getPersistedMatchFormatOption(MatchFormatOption matchFormatOption) {
        return matchFormatOptionRepository.findById(matchFormatOption.getId()).orElseThrow();
    }

    protected void assertPersistedMatchFormatOptionToMatchAllProperties(MatchFormatOption expectedMatchFormatOption) {
        assertMatchFormatOptionAllPropertiesEquals(expectedMatchFormatOption, getPersistedMatchFormatOption(expectedMatchFormatOption));
    }

    protected void assertPersistedMatchFormatOptionToMatchUpdatableProperties(MatchFormatOption expectedMatchFormatOption) {
        assertMatchFormatOptionAllUpdatablePropertiesEquals(
            expectedMatchFormatOption,
            getPersistedMatchFormatOption(expectedMatchFormatOption)
        );
    }
}
