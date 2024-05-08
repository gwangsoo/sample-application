package com.phoenixdarts.toss.backend.service;

import com.phoenixdarts.toss.backend.service.dto.EventPointDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.phoenixdarts.toss.backend.domain.EventPoint}.
 */
public interface EventPointService {
    /**
     * Save a eventPoint.
     *
     * @param eventPointDTO the entity to save.
     * @return the persisted entity.
     */
    EventPointDTO save(EventPointDTO eventPointDTO);

    /**
     * Updates a eventPoint.
     *
     * @param eventPointDTO the entity to update.
     * @return the persisted entity.
     */
    EventPointDTO update(EventPointDTO eventPointDTO);

    /**
     * Partially updates a eventPoint.
     *
     * @param eventPointDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<EventPointDTO> partialUpdate(EventPointDTO eventPointDTO);

    /**
     * Get all the eventPoints.
     *
     * @return the list of entities.
     */
    List<EventPointDTO> findAll();

    /**
     * Get the "id" eventPoint.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EventPointDTO> findOne(String id);

    /**
     * Delete the "id" eventPoint.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
