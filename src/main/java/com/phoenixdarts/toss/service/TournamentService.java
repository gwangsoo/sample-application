package com.phoenixdarts.toss.backend.service;

import com.phoenixdarts.toss.backend.service.dto.TournamentDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.phoenixdarts.toss.backend.domain.Tournament}.
 */
public interface TournamentService {
    /**
     * Save a tournament.
     *
     * @param tournamentDTO the entity to save.
     * @return the persisted entity.
     */
    TournamentDTO save(TournamentDTO tournamentDTO);

    /**
     * Updates a tournament.
     *
     * @param tournamentDTO the entity to update.
     * @return the persisted entity.
     */
    TournamentDTO update(TournamentDTO tournamentDTO);

    /**
     * Partially updates a tournament.
     *
     * @param tournamentDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TournamentDTO> partialUpdate(TournamentDTO tournamentDTO);

    /**
     * Get all the tournaments.
     *
     * @return the list of entities.
     */
    List<TournamentDTO> findAll();

    /**
     * Get the "id" tournament.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TournamentDTO> findOne(String id);

    /**
     * Delete the "id" tournament.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
