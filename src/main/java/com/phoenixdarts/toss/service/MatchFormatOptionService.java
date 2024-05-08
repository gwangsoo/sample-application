package com.phoenixdarts.toss.backend.service;

import com.phoenixdarts.toss.backend.service.dto.MatchFormatOptionDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.phoenixdarts.toss.backend.domain.MatchFormatOption}.
 */
public interface MatchFormatOptionService {
    /**
     * Save a matchFormatOption.
     *
     * @param matchFormatOptionDTO the entity to save.
     * @return the persisted entity.
     */
    MatchFormatOptionDTO save(MatchFormatOptionDTO matchFormatOptionDTO);

    /**
     * Updates a matchFormatOption.
     *
     * @param matchFormatOptionDTO the entity to update.
     * @return the persisted entity.
     */
    MatchFormatOptionDTO update(MatchFormatOptionDTO matchFormatOptionDTO);

    /**
     * Partially updates a matchFormatOption.
     *
     * @param matchFormatOptionDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<MatchFormatOptionDTO> partialUpdate(MatchFormatOptionDTO matchFormatOptionDTO);

    /**
     * Get all the matchFormatOptions.
     *
     * @return the list of entities.
     */
    List<MatchFormatOptionDTO> findAll();

    /**
     * Get the "id" matchFormatOption.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MatchFormatOptionDTO> findOne(String id);

    /**
     * Delete the "id" matchFormatOption.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
