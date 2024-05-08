package com.phoenixdarts.toss.backend.service;

import com.phoenixdarts.toss.backend.service.dto.MatchFormatSetDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.phoenixdarts.toss.backend.domain.MatchFormatSet}.
 */
public interface MatchFormatSetService {
    /**
     * Save a matchFormatSet.
     *
     * @param matchFormatSetDTO the entity to save.
     * @return the persisted entity.
     */
    MatchFormatSetDTO save(MatchFormatSetDTO matchFormatSetDTO);

    /**
     * Updates a matchFormatSet.
     *
     * @param matchFormatSetDTO the entity to update.
     * @return the persisted entity.
     */
    MatchFormatSetDTO update(MatchFormatSetDTO matchFormatSetDTO);

    /**
     * Partially updates a matchFormatSet.
     *
     * @param matchFormatSetDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<MatchFormatSetDTO> partialUpdate(MatchFormatSetDTO matchFormatSetDTO);

    /**
     * Get all the matchFormatSets.
     *
     * @return the list of entities.
     */
    List<MatchFormatSetDTO> findAll();

    /**
     * Get the "id" matchFormatSet.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MatchFormatSetDTO> findOne(String id);

    /**
     * Delete the "id" matchFormatSet.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
