package com.phoenixdarts.toss.service;

import com.phoenixdarts.toss.service.dto.RegionDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.phoenixdarts.toss.domain.Region}.
 */
public interface RegionService {
    /**
     * Save a region.
     *
     * @param regionDTO the entity to save.
     * @return the persisted entity.
     */
    RegionDTO save(RegionDTO regionDTO);

    /**
     * Updates a region.
     *
     * @param regionDTO the entity to update.
     * @return the persisted entity.
     */
    RegionDTO update(RegionDTO regionDTO);

    /**
     * Partially updates a region.
     *
     * @param regionDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<RegionDTO> partialUpdate(RegionDTO regionDTO);

    /**
     * Get all the regions.
     *
     * @return the list of entities.
     */
    List<RegionDTO> findAll();

    /**
     * Get the "id" region.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RegionDTO> findOne(String id);

    /**
     * Delete the "id" region.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
