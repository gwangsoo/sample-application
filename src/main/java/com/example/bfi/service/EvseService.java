package com.example.bfi.service;

import com.example.bfi.service.dto.EvseDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.example.bfi.domain.Evse}.
 */
public interface EvseService {
    /**
     * Save a evse.
     *
     * @param evseDTO the entity to save.
     * @return the persisted entity.
     */
    EvseDTO save(EvseDTO evseDTO);

    /**
     * Updates a evse.
     *
     * @param evseDTO the entity to update.
     * @return the persisted entity.
     */
    EvseDTO update(EvseDTO evseDTO);

    /**
     * Partially updates a evse.
     *
     * @param evseDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<EvseDTO> partialUpdate(EvseDTO evseDTO);

    /**
     * Get all the evses.
     *
     * @return the list of entities.
     */
    List<EvseDTO> findAll();

    /**
     * Get the "id" evse.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EvseDTO> findOne(String id);

    /**
     * Delete the "id" evse.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
