package com.phoenixdarts.toss.backend.web.rest;

import static com.phoenixdarts.toss.backend.domain.GameAsserts.*;
import static com.phoenixdarts.toss.backend.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.phoenixdarts.toss.IntegrationTest;
import com.phoenixdarts.toss.backend.domain.Game;
import com.phoenixdarts.toss.backend.domain.enumeration.GameCategoryType;
import com.phoenixdarts.toss.backend.repository.GameRepository;
import com.phoenixdarts.toss.backend.service.dto.GameDTO;
import com.phoenixdarts.toss.backend.service.mapper.GameMapper;
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
 * Integration tests for the {@link GameResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class GameResourceIT {

    private static final GameCategoryType DEFAULT_GAME_CATEGORY_TYPE = GameCategoryType.GAME_01;
    private static final GameCategoryType UPDATED_GAME_CATEGORY_TYPE = GameCategoryType.CRICKET;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_ROUND_NUM_DEFAULT = 1;
    private static final Integer UPDATED_ROUND_NUM_DEFAULT = 2;

    private static final Integer DEFAULT_ROUND_NUM_MIN = 1;
    private static final Integer UPDATED_ROUND_NUM_MIN = 2;

    private static final Integer DEFAULT_ROUND_NUM_MAX = 1;
    private static final Integer UPDATED_ROUND_NUM_MAX = 2;

    private static final Integer DEFAULT_ROUND_NUM_UNLIMIT = 1;
    private static final Integer UPDATED_ROUND_NUM_UNLIMIT = 2;

    private static final String ENTITY_API_URL = "/api/games";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private GameMapper gameMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGameMockMvc;

    private Game game;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Game createEntity(EntityManager em) {
        Game game = new Game()
            .gameCategoryType(DEFAULT_GAME_CATEGORY_TYPE)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .roundNumDefault(DEFAULT_ROUND_NUM_DEFAULT)
            .roundNumMin(DEFAULT_ROUND_NUM_MIN)
            .roundNumMax(DEFAULT_ROUND_NUM_MAX)
            .roundNumUnlimit(DEFAULT_ROUND_NUM_UNLIMIT);
        return game;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Game createUpdatedEntity(EntityManager em) {
        Game game = new Game()
            .gameCategoryType(UPDATED_GAME_CATEGORY_TYPE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .roundNumDefault(UPDATED_ROUND_NUM_DEFAULT)
            .roundNumMin(UPDATED_ROUND_NUM_MIN)
            .roundNumMax(UPDATED_ROUND_NUM_MAX)
            .roundNumUnlimit(UPDATED_ROUND_NUM_UNLIMIT);
        return game;
    }

    @BeforeEach
    public void initTest() {
        game = createEntity(em);
    }

    @Test
    @Transactional
    void createGame() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Game
        GameDTO gameDTO = gameMapper.toDto(game);
        var returnedGameDTO = om.readValue(
            restGameMockMvc
                .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(gameDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            GameDTO.class
        );

        // Validate the Game in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedGame = gameMapper.toEntity(returnedGameDTO);
        assertGameUpdatableFieldsEquals(returnedGame, getPersistedGame(returnedGame));
    }

    @Test
    @Transactional
    void createGameWithExistingId() throws Exception {
        // Create the Game with an existing ID
        game.setId("existing_id");
        GameDTO gameDTO = gameMapper.toDto(game);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restGameMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(gameDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Game in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkGameCategoryTypeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        game.setGameCategoryType(null);

        // Create the Game, which fails.
        GameDTO gameDTO = gameMapper.toDto(game);

        restGameMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(gameDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        game.setName(null);

        // Create the Game, which fails.
        GameDTO gameDTO = gameMapper.toDto(game);

        restGameMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(gameDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDescriptionIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        game.setDescription(null);

        // Create the Game, which fails.
        GameDTO gameDTO = gameMapper.toDto(game);

        restGameMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(gameDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllGames() throws Exception {
        // Initialize the database
        gameRepository.saveAndFlush(game);

        // Get all the gameList
        restGameMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(game.getId())))
            .andExpect(jsonPath("$.[*].gameCategoryType").value(hasItem(DEFAULT_GAME_CATEGORY_TYPE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].roundNumDefault").value(hasItem(DEFAULT_ROUND_NUM_DEFAULT)))
            .andExpect(jsonPath("$.[*].roundNumMin").value(hasItem(DEFAULT_ROUND_NUM_MIN)))
            .andExpect(jsonPath("$.[*].roundNumMax").value(hasItem(DEFAULT_ROUND_NUM_MAX)))
            .andExpect(jsonPath("$.[*].roundNumUnlimit").value(hasItem(DEFAULT_ROUND_NUM_UNLIMIT)));
    }

    @Test
    @Transactional
    void getGame() throws Exception {
        // Initialize the database
        gameRepository.saveAndFlush(game);

        // Get the game
        restGameMockMvc
            .perform(get(ENTITY_API_URL_ID, game.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(game.getId()))
            .andExpect(jsonPath("$.gameCategoryType").value(DEFAULT_GAME_CATEGORY_TYPE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.roundNumDefault").value(DEFAULT_ROUND_NUM_DEFAULT))
            .andExpect(jsonPath("$.roundNumMin").value(DEFAULT_ROUND_NUM_MIN))
            .andExpect(jsonPath("$.roundNumMax").value(DEFAULT_ROUND_NUM_MAX))
            .andExpect(jsonPath("$.roundNumUnlimit").value(DEFAULT_ROUND_NUM_UNLIMIT));
    }

    @Test
    @Transactional
    void getNonExistingGame() throws Exception {
        // Get the game
        restGameMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingGame() throws Exception {
        // Initialize the database
        gameRepository.saveAndFlush(game);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the game
        Game updatedGame = gameRepository.findById(game.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedGame are not directly saved in db
        em.detach(updatedGame);
        updatedGame
            .gameCategoryType(UPDATED_GAME_CATEGORY_TYPE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .roundNumDefault(UPDATED_ROUND_NUM_DEFAULT)
            .roundNumMin(UPDATED_ROUND_NUM_MIN)
            .roundNumMax(UPDATED_ROUND_NUM_MAX)
            .roundNumUnlimit(UPDATED_ROUND_NUM_UNLIMIT);
        GameDTO gameDTO = gameMapper.toDto(updatedGame);

        restGameMockMvc
            .perform(
                put(ENTITY_API_URL_ID, gameDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(gameDTO))
            )
            .andExpect(status().isOk());

        // Validate the Game in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedGameToMatchAllProperties(updatedGame);
    }

    @Test
    @Transactional
    void putNonExistingGame() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        game.setId(UUID.randomUUID().toString());

        // Create the Game
        GameDTO gameDTO = gameMapper.toDto(game);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGameMockMvc
            .perform(
                put(ENTITY_API_URL_ID, gameDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(gameDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Game in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchGame() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        game.setId(UUID.randomUUID().toString());

        // Create the Game
        GameDTO gameDTO = gameMapper.toDto(game);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGameMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(gameDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Game in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamGame() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        game.setId(UUID.randomUUID().toString());

        // Create the Game
        GameDTO gameDTO = gameMapper.toDto(game);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGameMockMvc
            .perform(put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(gameDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Game in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateGameWithPatch() throws Exception {
        // Initialize the database
        gameRepository.saveAndFlush(game);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the game using partial update
        Game partialUpdatedGame = new Game();
        partialUpdatedGame.setId(game.getId());

        partialUpdatedGame.roundNumUnlimit(UPDATED_ROUND_NUM_UNLIMIT);

        restGameMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGame.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedGame))
            )
            .andExpect(status().isOk());

        // Validate the Game in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertGameUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedGame, game), getPersistedGame(game));
    }

    @Test
    @Transactional
    void fullUpdateGameWithPatch() throws Exception {
        // Initialize the database
        gameRepository.saveAndFlush(game);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the game using partial update
        Game partialUpdatedGame = new Game();
        partialUpdatedGame.setId(game.getId());

        partialUpdatedGame
            .gameCategoryType(UPDATED_GAME_CATEGORY_TYPE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .roundNumDefault(UPDATED_ROUND_NUM_DEFAULT)
            .roundNumMin(UPDATED_ROUND_NUM_MIN)
            .roundNumMax(UPDATED_ROUND_NUM_MAX)
            .roundNumUnlimit(UPDATED_ROUND_NUM_UNLIMIT);

        restGameMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGame.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedGame))
            )
            .andExpect(status().isOk());

        // Validate the Game in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertGameUpdatableFieldsEquals(partialUpdatedGame, getPersistedGame(partialUpdatedGame));
    }

    @Test
    @Transactional
    void patchNonExistingGame() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        game.setId(UUID.randomUUID().toString());

        // Create the Game
        GameDTO gameDTO = gameMapper.toDto(game);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGameMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, gameDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(gameDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Game in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchGame() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        game.setId(UUID.randomUUID().toString());

        // Create the Game
        GameDTO gameDTO = gameMapper.toDto(game);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGameMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(gameDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Game in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamGame() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        game.setId(UUID.randomUUID().toString());

        // Create the Game
        GameDTO gameDTO = gameMapper.toDto(game);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGameMockMvc
            .perform(patch(ENTITY_API_URL).with(csrf()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(gameDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Game in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteGame() throws Exception {
        // Initialize the database
        gameRepository.saveAndFlush(game);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the game
        restGameMockMvc
            .perform(delete(ENTITY_API_URL_ID, game.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return gameRepository.count();
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

    protected Game getPersistedGame(Game game) {
        return gameRepository.findById(game.getId()).orElseThrow();
    }

    protected void assertPersistedGameToMatchAllProperties(Game expectedGame) {
        assertGameAllPropertiesEquals(expectedGame, getPersistedGame(expectedGame));
    }

    protected void assertPersistedGameToMatchUpdatableProperties(Game expectedGame) {
        assertGameAllUpdatablePropertiesEquals(expectedGame, getPersistedGame(expectedGame));
    }
}
