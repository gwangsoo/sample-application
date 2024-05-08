package com.phoenixdarts.toss.backend.service;

import com.phoenixdarts.toss.backend.service.dto.EntryFeeDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.phoenixdarts.toss.backend.domain.EntryFee}.
 */
public interface EntryFeeService {
    /**
     * Save a entryFee.
     *
     * @param entryFeeDTO the entity to save.
     * @return the persisted entity.
     */
    EntryFeeDTO save(EntryFeeDTO entryFeeDTO);

    /**
     * Updates a entryFee.
     *
     * @param entryFeeDTO the entity to update.
     * @return the persisted entity.
     */
    EntryFeeDTO update(EntryFeeDTO entryFeeDTO);

    /**
     * Partially updates a entryFee.
     *
     * @param entryFeeDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<EntryFeeDTO> partialUpdate(EntryFeeDTO entryFeeDTO);

    /**
     * Get all the entryFees.
     *
     * @return the list of entities.
     */
    List<EntryFeeDTO> findAll();

    /**
     * Get the "id" entryFee.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EntryFeeDTO> findOne(String id);

    /**
     * Delete the "id" entryFee.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
