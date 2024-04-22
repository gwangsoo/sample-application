package com.phoenixdarts.toss.service;

import com.phoenixdarts.toss.service.dto.MatchCallDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.phoenixdarts.toss.domain.MatchCall}.
 */
public interface MatchCallService {
    /**
     * Save a matchCall.
     *
     * @param matchCallDTO the entity to save.
     * @return the persisted entity.
     */
    MatchCallDTO save(MatchCallDTO matchCallDTO);

    /**
     * Updates a matchCall.
     *
     * @param matchCallDTO the entity to update.
     * @return the persisted entity.
     */
    MatchCallDTO update(MatchCallDTO matchCallDTO);

    /**
     * Partially updates a matchCall.
     *
     * @param matchCallDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<MatchCallDTO> partialUpdate(MatchCallDTO matchCallDTO);

    /**
     * Get all the matchCalls.
     *
     * @return the list of entities.
     */
    List<MatchCallDTO> findAll();

    /**
     * Get the "id" matchCall.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MatchCallDTO> findOne(String id);

    /**
     * Delete the "id" matchCall.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
