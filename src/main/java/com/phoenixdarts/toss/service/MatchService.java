package com.phoenixdarts.toss.service;

import com.phoenixdarts.toss.service.dto.MatchDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.phoenixdarts.toss.domain.Match}.
 */
public interface MatchService {
    /**
     * Save a match.
     *
     * @param matchDTO the entity to save.
     * @return the persisted entity.
     */
    MatchDTO save(MatchDTO matchDTO);

    /**
     * Updates a match.
     *
     * @param matchDTO the entity to update.
     * @return the persisted entity.
     */
    MatchDTO update(MatchDTO matchDTO);

    /**
     * Partially updates a match.
     *
     * @param matchDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<MatchDTO> partialUpdate(MatchDTO matchDTO);

    /**
     * Get all the matches.
     *
     * @return the list of entities.
     */
    List<MatchDTO> findAll();

    /**
     * Get the "id" match.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MatchDTO> findOne(String id);

    /**
     * Delete the "id" match.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
