package com.phoenixdarts.toss.service;

import com.phoenixdarts.toss.service.dto.MatchFormatLegDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.phoenixdarts.toss.domain.MatchFormatLeg}.
 */
public interface MatchFormatLegService {
    /**
     * Save a matchFormatLeg.
     *
     * @param matchFormatLegDTO the entity to save.
     * @return the persisted entity.
     */
    MatchFormatLegDTO save(MatchFormatLegDTO matchFormatLegDTO);

    /**
     * Updates a matchFormatLeg.
     *
     * @param matchFormatLegDTO the entity to update.
     * @return the persisted entity.
     */
    MatchFormatLegDTO update(MatchFormatLegDTO matchFormatLegDTO);

    /**
     * Partially updates a matchFormatLeg.
     *
     * @param matchFormatLegDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<MatchFormatLegDTO> partialUpdate(MatchFormatLegDTO matchFormatLegDTO);

    /**
     * Get all the matchFormatLegs.
     *
     * @return the list of entities.
     */
    List<MatchFormatLegDTO> findAll();

    /**
     * Get the "id" matchFormatLeg.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MatchFormatLegDTO> findOne(String id);

    /**
     * Delete the "id" matchFormatLeg.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
