package com.phoenixdarts.toss.service;

import com.phoenixdarts.toss.service.dto.MachineAreaDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.phoenixdarts.toss.domain.MachineArea}.
 */
public interface MachineAreaService {
    /**
     * Save a machineArea.
     *
     * @param machineAreaDTO the entity to save.
     * @return the persisted entity.
     */
    MachineAreaDTO save(MachineAreaDTO machineAreaDTO);

    /**
     * Updates a machineArea.
     *
     * @param machineAreaDTO the entity to update.
     * @return the persisted entity.
     */
    MachineAreaDTO update(MachineAreaDTO machineAreaDTO);

    /**
     * Partially updates a machineArea.
     *
     * @param machineAreaDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<MachineAreaDTO> partialUpdate(MachineAreaDTO machineAreaDTO);

    /**
     * Get all the machineAreas.
     *
     * @return the list of entities.
     */
    List<MachineAreaDTO> findAll();

    /**
     * Get the "id" machineArea.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MachineAreaDTO> findOne(String id);

    /**
     * Delete the "id" machineArea.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
