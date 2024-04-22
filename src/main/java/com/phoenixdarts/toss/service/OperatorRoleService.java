package com.phoenixdarts.toss.service;

import com.phoenixdarts.toss.service.dto.OperatorRoleDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.phoenixdarts.toss.domain.OperatorRole}.
 */
public interface OperatorRoleService {
    /**
     * Save a operatorRole.
     *
     * @param operatorRoleDTO the entity to save.
     * @return the persisted entity.
     */
    OperatorRoleDTO save(OperatorRoleDTO operatorRoleDTO);

    /**
     * Updates a operatorRole.
     *
     * @param operatorRoleDTO the entity to update.
     * @return the persisted entity.
     */
    OperatorRoleDTO update(OperatorRoleDTO operatorRoleDTO);

    /**
     * Partially updates a operatorRole.
     *
     * @param operatorRoleDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<OperatorRoleDTO> partialUpdate(OperatorRoleDTO operatorRoleDTO);

    /**
     * Get all the operatorRoles.
     *
     * @return the list of entities.
     */
    List<OperatorRoleDTO> findAll();

    /**
     * Get the "id" operatorRole.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OperatorRoleDTO> findOne(String id);

    /**
     * Delete the "id" operatorRole.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
