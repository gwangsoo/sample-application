package com.phoenixdarts.toss.service;

import com.phoenixdarts.toss.service.dto.EntryDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.phoenixdarts.toss.domain.Entry}.
 */
public interface EntryService {
    /**
     * Save a entry.
     *
     * @param entryDTO the entity to save.
     * @return the persisted entity.
     */
    EntryDTO save(EntryDTO entryDTO);

    /**
     * Updates a entry.
     *
     * @param entryDTO the entity to update.
     * @return the persisted entity.
     */
    EntryDTO update(EntryDTO entryDTO);

    /**
     * Partially updates a entry.
     *
     * @param entryDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<EntryDTO> partialUpdate(EntryDTO entryDTO);

    /**
     * Get all the entries.
     *
     * @return the list of entities.
     */
    List<EntryDTO> findAll();

    /**
     * Get the "id" entry.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EntryDTO> findOne(String id);

    /**
     * Delete the "id" entry.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
