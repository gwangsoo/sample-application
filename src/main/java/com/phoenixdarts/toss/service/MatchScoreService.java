package com.phoenixdarts.toss.service;

import com.phoenixdarts.toss.service.dto.MatchScoreDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.phoenixdarts.toss.domain.MatchScore}.
 */
public interface MatchScoreService {
    /**
     * Save a matchScore.
     *
     * @param matchScoreDTO the entity to save.
     * @return the persisted entity.
     */
    MatchScoreDTO save(MatchScoreDTO matchScoreDTO);

    /**
     * Updates a matchScore.
     *
     * @param matchScoreDTO the entity to update.
     * @return the persisted entity.
     */
    MatchScoreDTO update(MatchScoreDTO matchScoreDTO);

    /**
     * Partially updates a matchScore.
     *
     * @param matchScoreDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<MatchScoreDTO> partialUpdate(MatchScoreDTO matchScoreDTO);

    /**
     * Get all the matchScores.
     *
     * @return the list of entities.
     */
    List<MatchScoreDTO> findAll();

    /**
     * Get the "id" matchScore.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MatchScoreDTO> findOne(String id);

    /**
     * Delete the "id" matchScore.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
