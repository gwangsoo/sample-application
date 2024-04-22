package com.phoenixdarts.toss.service;

import com.phoenixdarts.toss.service.dto.CompetitionDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.phoenixdarts.toss.domain.Competition}.
 */
public interface CompetitionService {
    /**
     * Save a competition.
     *
     * @param competitionDTO the entity to save.
     * @return the persisted entity.
     */
    CompetitionDTO save(CompetitionDTO competitionDTO);

    /**
     * Updates a competition.
     *
     * @param competitionDTO the entity to update.
     * @return the persisted entity.
     */
    CompetitionDTO update(CompetitionDTO competitionDTO);

    /**
     * Partially updates a competition.
     *
     * @param competitionDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CompetitionDTO> partialUpdate(CompetitionDTO competitionDTO);

    /**
     * Get all the competitions.
     *
     * @return the list of entities.
     */
    List<CompetitionDTO> findAll();

    /**
     * Get the "id" competition.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CompetitionDTO> findOne(String id);

    /**
     * Delete the "id" competition.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
