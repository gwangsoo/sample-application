package com.phoenixdarts.toss.service;

import com.phoenixdarts.toss.service.dto.AffiliatedInfoDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.phoenixdarts.toss.domain.AffiliatedInfo}.
 */
public interface AffiliatedInfoService {
    /**
     * Save a affiliatedInfo.
     *
     * @param affiliatedInfoDTO the entity to save.
     * @return the persisted entity.
     */
    AffiliatedInfoDTO save(AffiliatedInfoDTO affiliatedInfoDTO);

    /**
     * Updates a affiliatedInfo.
     *
     * @param affiliatedInfoDTO the entity to update.
     * @return the persisted entity.
     */
    AffiliatedInfoDTO update(AffiliatedInfoDTO affiliatedInfoDTO);

    /**
     * Partially updates a affiliatedInfo.
     *
     * @param affiliatedInfoDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AffiliatedInfoDTO> partialUpdate(AffiliatedInfoDTO affiliatedInfoDTO);

    /**
     * Get all the affiliatedInfos.
     *
     * @return the list of entities.
     */
    List<AffiliatedInfoDTO> findAll();

    /**
     * Get the "id" affiliatedInfo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AffiliatedInfoDTO> findOne(String id);

    /**
     * Delete the "id" affiliatedInfo.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
