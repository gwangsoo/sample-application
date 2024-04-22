package com.phoenixdarts.toss.service;

import com.phoenixdarts.toss.service.dto.DivisionDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.phoenixdarts.toss.domain.Division}.
 */
public interface DivisionService {
    /**
     * Save a division.
     *
     * @param divisionDTO the entity to save.
     * @return the persisted entity.
     */
    DivisionDTO save(DivisionDTO divisionDTO);

    /**
     * Updates a division.
     *
     * @param divisionDTO the entity to update.
     * @return the persisted entity.
     */
    DivisionDTO update(DivisionDTO divisionDTO);

    /**
     * Partially updates a division.
     *
     * @param divisionDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DivisionDTO> partialUpdate(DivisionDTO divisionDTO);

    /**
     * Get all the divisions.
     *
     * @return the list of entities.
     */
    List<DivisionDTO> findAll();

    /**
     * Get the "id" division.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DivisionDTO> findOne(String id);

    /**
     * Delete the "id" division.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
