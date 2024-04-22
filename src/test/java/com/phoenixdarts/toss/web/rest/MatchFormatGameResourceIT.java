package com.phoenixdarts.toss.web.rest;

import static com.phoenixdarts.toss.domain.MatchFormatGameAsserts.*;
import static com.phoenixdarts.toss.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.phoenixdarts.toss.IntegrationTest;
import com.phoenixdarts.toss.domain.MatchFormatGame;
import com.phoenixdarts.toss.domain.enumeration.GameCategoryType;
import com.phoenixdarts.toss.domain.enumeration.GameType;
import com.phoenixdarts.toss.domain.enumeration.MachineCreditType;
import com.phoenixdarts.toss.repository.MatchFormatGameRepository;
import com.phoenixdarts.toss.service.dto.MatchFormatGameDTO;
import com.phoenixdarts.toss.service.mapper.MatchFormatGameMapper;
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
 * Integration tests for the {@link MatchFormatGameResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MatchFormatGameResourceIT {

    private static final GameCategoryType DEFAULT_GAME_CATEGORY_TYPE = GameCategoryType.GAME_01;
    private static final GameCategoryType UPDATED_GAME_CATEGORY_TYPE = GameCategoryType.CRICKET;

    private static final GameType DEFAULT_GAME_TYPE = GameType.GAME_301;
    private static final GameType UPDATED_GAME_TYPE = GameType.GAME_501;

    private static final Integer DEFAULT_ROUND_NUM = 1;
    private static final Integer UPDATED_ROUND_NUM = 2;

    private static final MachineCreditType DEFAULT_MACHINE_CREDIT_TYPE = MachineCreditType.MACHINE_DEFAULT;
    private static final MachineCreditType UPDATED_MACHINE_CREDIT_TYPE = MachineCreditType.FREE;

    private static final Boolean DEFAULT_INCLUDING_CHOICE_GAME = false;
    private static final Boolean UPDATED_INCLUDING_CHOICE_GAME = true;

    private static final String ENTITY_API_URL = "/api/match-format-games";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private MatchFormatGameRepository matchFormatGameRepository;

    @Autowired
    private MatchFormatGameMapper matchFormatGameMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMatchFormatGameMockMvc;

    private MatchFormatGame matchFormatGame;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MatchFormatGame createEntity(EntityManager em) {
        MatchFormatGame matchFormatGame = new MatchFormatGame()
            .gameCategoryType(DEFAULT_GAME_CATEGORY_TYPE)
            .gameType(DEFAULT_GAME_TYPE)
            .roundNum(DEFAULT_ROUND_NUM)
            .machineCreditType(DEFAULT_MACHINE_CREDIT_TYPE)
            .includingChoiceGame(DEFAULT_INCLUDING_CHOICE_GAME);
        return matchFormatGame;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MatchFormatGame createUpdatedEntity(EntityManager em) {
        MatchFormatGame matchFormatGame = new MatchFormatGame()
            .gameCategoryType(UPDATED_GAME_CATEGORY_TYPE)
            .gameType(UPDATED_GAME_TYPE)
            .roundNum(UPDATED_ROUND_NUM)
            .machineCreditType(UPDATED_MACHINE_CREDIT_TYPE)
            .includingChoiceGame(UPDATED_INCLUDING_CHOICE_GAME);
        return matchFormatGame;
    }

    @BeforeEach
    public void initTest() {
        matchFormatGame = createEntity(em);
    }

    @Test
    @Transactional
    void createMatchFormatGame() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the MatchFormatGame
        MatchFormatGameDTO matchFormatGameDTO = matchFormatGameMapper.toDto(matchFormatGame);
        var returnedMatchFormatGameDTO = om.readValue(
            restMatchFormatGameMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(matchFormatGameDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            MatchFormatGameDTO.class
        );

        // Validate the MatchFormatGame in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedMatchFormatGame = matchFormatGameMapper.toEntity(returnedMatchFormatGameDTO);
        assertMatchFormatGameUpdatableFieldsEquals(returnedMatchFormatGame, getPersistedMatchFormatGame(returnedMatchFormatGame));
    }

    @Test
    @Transactional
    void createMatchFormatGameWithExistingId() throws Exception {
        // Create the MatchFormatGame with an existing ID
        matchFormatGame.setId("existing_id");
        MatchFormatGameDTO matchFormatGameDTO = matchFormatGameMapper.toDto(matchFormatGame);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMatchFormatGameMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(matchFormatGameDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MatchFormatGame in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkGameCategoryTypeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        matchFormatGame.setGameCategoryType(null);

        // Create the MatchFormatGame, which fails.
        MatchFormatGameDTO matchFormatGameDTO = matchFormatGameMapper.toDto(matchFormatGame);

        restMatchFormatGameMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(matchFormatGameDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkGameTypeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        matchFormatGame.setGameType(null);

        // Create the MatchFormatGame, which fails.
        MatchFormatGameDTO matchFormatGameDTO = matchFormatGameMapper.toDto(matchFormatGame);

        restMatchFormatGameMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(matchFormatGameDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkRoundNumIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        matchFormatGame.setRoundNum(null);

        // Create the MatchFormatGame, which fails.
        MatchFormatGameDTO matchFormatGameDTO = matchFormatGameMapper.toDto(matchFormatGame);

        restMatchFormatGameMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(matchFormatGameDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMachineCreditTypeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        matchFormatGame.setMachineCreditType(null);

        // Create the MatchFormatGame, which fails.
        MatchFormatGameDTO matchFormatGameDTO = matchFormatGameMapper.toDto(matchFormatGame);

        restMatchFormatGameMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(matchFormatGameDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllMatchFormatGames() throws Exception {
        // Initialize the database
        matchFormatGameRepository.saveAndFlush(matchFormatGame);

        // Get all the matchFormatGameList
        restMatchFormatGameMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(matchFormatGame.getId())))
            .andExpect(jsonPath("$.[*].gameCategoryType").value(hasItem(DEFAULT_GAME_CATEGORY_TYPE.toString())))
            .andExpect(jsonPath("$.[*].gameType").value(hasItem(DEFAULT_GAME_TYPE.toString())))
            .andExpect(jsonPath("$.[*].roundNum").value(hasItem(DEFAULT_ROUND_NUM)))
            .andExpect(jsonPath("$.[*].machineCreditType").value(hasItem(DEFAULT_MACHINE_CREDIT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].includingChoiceGame").value(hasItem(DEFAULT_INCLUDING_CHOICE_GAME.booleanValue())));
    }

    @Test
    @Transactional
    void getMatchFormatGame() throws Exception {
        // Initialize the database
        matchFormatGameRepository.saveAndFlush(matchFormatGame);

        // Get the matchFormatGame
        restMatchFormatGameMockMvc
            .perform(get(ENTITY_API_URL_ID, matchFormatGame.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(matchFormatGame.getId()))
            .andExpect(jsonPath("$.gameCategoryType").value(DEFAULT_GAME_CATEGORY_TYPE.toString()))
            .andExpect(jsonPath("$.gameType").value(DEFAULT_GAME_TYPE.toString()))
            .andExpect(jsonPath("$.roundNum").value(DEFAULT_ROUND_NUM))
            .andExpect(jsonPath("$.machineCreditType").value(DEFAULT_MACHINE_CREDIT_TYPE.toString()))
            .andExpect(jsonPath("$.includingChoiceGame").value(DEFAULT_INCLUDING_CHOICE_GAME.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingMatchFormatGame() throws Exception {
        // Get the matchFormatGame
        restMatchFormatGameMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMatchFormatGame() throws Exception {
        // Initialize the database
        matchFormatGameRepository.saveAndFlush(matchFormatGame);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the matchFormatGame
        MatchFormatGame updatedMatchFormatGame = matchFormatGameRepository.findById(matchFormatGame.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedMatchFormatGame are not directly saved in db
        em.detach(updatedMatchFormatGame);
        updatedMatchFormatGame
            .gameCategoryType(UPDATED_GAME_CATEGORY_TYPE)
            .gameType(UPDATED_GAME_TYPE)
            .roundNum(UPDATED_ROUND_NUM)
            .machineCreditType(UPDATED_MACHINE_CREDIT_TYPE)
            .includingChoiceGame(UPDATED_INCLUDING_CHOICE_GAME);
        MatchFormatGameDTO matchFormatGameDTO = matchFormatGameMapper.toDto(updatedMatchFormatGame);

        restMatchFormatGameMockMvc
            .perform(
                put(ENTITY_API_URL_ID, matchFormatGameDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(matchFormatGameDTO))
            )
            .andExpect(status().isOk());

        // Validate the MatchFormatGame in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedMatchFormatGameToMatchAllProperties(updatedMatchFormatGame);
    }

    @Test
    @Transactional
    void putNonExistingMatchFormatGame() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        matchFormatGame.setId(UUID.randomUUID().toString());

        // Create the MatchFormatGame
        MatchFormatGameDTO matchFormatGameDTO = matchFormatGameMapper.toDto(matchFormatGame);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMatchFormatGameMockMvc
            .perform(
                put(ENTITY_API_URL_ID, matchFormatGameDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(matchFormatGameDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MatchFormatGame in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMatchFormatGame() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        matchFormatGame.setId(UUID.randomUUID().toString());

        // Create the MatchFormatGame
        MatchFormatGameDTO matchFormatGameDTO = matchFormatGameMapper.toDto(matchFormatGame);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMatchFormatGameMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(matchFormatGameDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MatchFormatGame in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMatchFormatGame() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        matchFormatGame.setId(UUID.randomUUID().toString());

        // Create the MatchFormatGame
        MatchFormatGameDTO matchFormatGameDTO = matchFormatGameMapper.toDto(matchFormatGame);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMatchFormatGameMockMvc
            .perform(
                put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(matchFormatGameDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MatchFormatGame in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMatchFormatGameWithPatch() throws Exception {
        // Initialize the database
        matchFormatGameRepository.saveAndFlush(matchFormatGame);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the matchFormatGame using partial update
        MatchFormatGame partialUpdatedMatchFormatGame = new MatchFormatGame();
        partialUpdatedMatchFormatGame.setId(matchFormatGame.getId());

        partialUpdatedMatchFormatGame
            .gameCategoryType(UPDATED_GAME_CATEGORY_TYPE)
            .gameType(UPDATED_GAME_TYPE)
            .roundNum(UPDATED_ROUND_NUM)
            .includingChoiceGame(UPDATED_INCLUDING_CHOICE_GAME);

        restMatchFormatGameMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMatchFormatGame.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMatchFormatGame))
            )
            .andExpect(status().isOk());

        // Validate the MatchFormatGame in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMatchFormatGameUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedMatchFormatGame, matchFormatGame),
            getPersistedMatchFormatGame(matchFormatGame)
        );
    }

    @Test
    @Transactional
    void fullUpdateMatchFormatGameWithPatch() throws Exception {
        // Initialize the database
        matchFormatGameRepository.saveAndFlush(matchFormatGame);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the matchFormatGame using partial update
        MatchFormatGame partialUpdatedMatchFormatGame = new MatchFormatGame();
        partialUpdatedMatchFormatGame.setId(matchFormatGame.getId());

        partialUpdatedMatchFormatGame
            .gameCategoryType(UPDATED_GAME_CATEGORY_TYPE)
            .gameType(UPDATED_GAME_TYPE)
            .roundNum(UPDATED_ROUND_NUM)
            .machineCreditType(UPDATED_MACHINE_CREDIT_TYPE)
            .includingChoiceGame(UPDATED_INCLUDING_CHOICE_GAME);

        restMatchFormatGameMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMatchFormatGame.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMatchFormatGame))
            )
            .andExpect(status().isOk());

        // Validate the MatchFormatGame in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMatchFormatGameUpdatableFieldsEquals(
            partialUpdatedMatchFormatGame,
            getPersistedMatchFormatGame(partialUpdatedMatchFormatGame)
        );
    }

    @Test
    @Transactional
    void patchNonExistingMatchFormatGame() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        matchFormatGame.setId(UUID.randomUUID().toString());

        // Create the MatchFormatGame
        MatchFormatGameDTO matchFormatGameDTO = matchFormatGameMapper.toDto(matchFormatGame);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMatchFormatGameMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, matchFormatGameDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(matchFormatGameDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MatchFormatGame in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMatchFormatGame() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        matchFormatGame.setId(UUID.randomUUID().toString());

        // Create the MatchFormatGame
        MatchFormatGameDTO matchFormatGameDTO = matchFormatGameMapper.toDto(matchFormatGame);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMatchFormatGameMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(matchFormatGameDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MatchFormatGame in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMatchFormatGame() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        matchFormatGame.setId(UUID.randomUUID().toString());

        // Create the MatchFormatGame
        MatchFormatGameDTO matchFormatGameDTO = matchFormatGameMapper.toDto(matchFormatGame);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMatchFormatGameMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(matchFormatGameDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MatchFormatGame in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMatchFormatGame() throws Exception {
        // Initialize the database
        matchFormatGameRepository.saveAndFlush(matchFormatGame);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the matchFormatGame
        restMatchFormatGameMockMvc
            .perform(delete(ENTITY_API_URL_ID, matchFormatGame.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return matchFormatGameRepository.count();
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

    protected MatchFormatGame getPersistedMatchFormatGame(MatchFormatGame matchFormatGame) {
        return matchFormatGameRepository.findById(matchFormatGame.getId()).orElseThrow();
    }

    protected void assertPersistedMatchFormatGameToMatchAllProperties(MatchFormatGame expectedMatchFormatGame) {
        assertMatchFormatGameAllPropertiesEquals(expectedMatchFormatGame, getPersistedMatchFormatGame(expectedMatchFormatGame));
    }

    protected void assertPersistedMatchFormatGameToMatchUpdatableProperties(MatchFormatGame expectedMatchFormatGame) {
        assertMatchFormatGameAllUpdatablePropertiesEquals(expectedMatchFormatGame, getPersistedMatchFormatGame(expectedMatchFormatGame));
    }
}
