package com.phoenixdarts.toss.service;

import com.phoenixdarts.toss.service.dto.MatchFormatGameDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.phoenixdarts.toss.domain.MatchFormatGame}.
 */
public interface MatchFormatGameService {
    /**
     * Save a matchFormatGame.
     *
     * @param matchFormatGameDTO the entity to save.
     * @return the persisted entity.
     */
    MatchFormatGameDTO save(MatchFormatGameDTO matchFormatGameDTO);

    /**
     * Updates a matchFormatGame.
     *
     * @param matchFormatGameDTO the entity to update.
     * @return the persisted entity.
     */
    MatchFormatGameDTO update(MatchFormatGameDTO matchFormatGameDTO);

    /**
     * Partially updates a matchFormatGame.
     *
     * @param matchFormatGameDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<MatchFormatGameDTO> partialUpdate(MatchFormatGameDTO matchFormatGameDTO);

    /**
     * Get all the matchFormatGames.
     *
     * @return the list of entities.
     */
    List<MatchFormatGameDTO> findAll();

    /**
     * Get the "id" matchFormatGame.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MatchFormatGameDTO> findOne(String id);

    /**
     * Delete the "id" matchFormatGame.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
