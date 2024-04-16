package com.example.bfi.service;

import com.example.bfi.service.dto.ConnectorDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.example.bfi.domain.Connector}.
 */
public interface ConnectorService {
    /**
     * Save a connector.
     *
     * @param connectorDTO the entity to save.
     * @return the persisted entity.
     */
    ConnectorDTO save(ConnectorDTO connectorDTO);

    /**
     * Updates a connector.
     *
     * @param connectorDTO the entity to update.
     * @return the persisted entity.
     */
    ConnectorDTO update(ConnectorDTO connectorDTO);

    /**
     * Partially updates a connector.
     *
     * @param connectorDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ConnectorDTO> partialUpdate(ConnectorDTO connectorDTO);

    /**
     * Get all the connectors.
     *
     * @return the list of entities.
     */
    List<ConnectorDTO> findAll();

    /**
     * Get the "id" connector.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ConnectorDTO> findOne(String id);

    /**
     * Delete the "id" connector.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
