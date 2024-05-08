package com.phoenixdarts.toss.backend.service;

import com.phoenixdarts.toss.backend.service.dto.MatchFormatDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.phoenixdarts.toss.backend.domain.MatchFormat}.
 */
public interface MatchFormatService {
    /**
     * Save a matchFormat.
     *
     * @param matchFormatDTO the entity to save.
     * @return the persisted entity.
     */
    MatchFormatDTO save(MatchFormatDTO matchFormatDTO);

    /**
     * Updates a matchFormat.
     *
     * @param matchFormatDTO the entity to update.
     * @return the persisted entity.
     */
    MatchFormatDTO update(MatchFormatDTO matchFormatDTO);

    /**
     * Partially updates a matchFormat.
     *
     * @param matchFormatDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<MatchFormatDTO> partialUpdate(MatchFormatDTO matchFormatDTO);

    /**
     * Get all the matchFormats.
     *
     * @return the list of entities.
     */
    List<MatchFormatDTO> findAll();

    /**
     * Get the "id" matchFormat.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MatchFormatDTO> findOne(String id);

    /**
     * Delete the "id" matchFormat.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
