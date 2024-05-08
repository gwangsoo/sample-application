package com.phoenixdarts.toss.backend.service;

import com.phoenixdarts.toss.backend.service.dto.RewardItemDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.phoenixdarts.toss.backend.domain.RewardItem}.
 */
public interface RewardItemService {
    /**
     * Save a rewardItem.
     *
     * @param rewardItemDTO the entity to save.
     * @return the persisted entity.
     */
    RewardItemDTO save(RewardItemDTO rewardItemDTO);

    /**
     * Updates a rewardItem.
     *
     * @param rewardItemDTO the entity to update.
     * @return the persisted entity.
     */
    RewardItemDTO update(RewardItemDTO rewardItemDTO);

    /**
     * Partially updates a rewardItem.
     *
     * @param rewardItemDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<RewardItemDTO> partialUpdate(RewardItemDTO rewardItemDTO);

    /**
     * Get all the rewardItems.
     *
     * @return the list of entities.
     */
    List<RewardItemDTO> findAll();

    /**
     * Get the "id" rewardItem.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RewardItemDTO> findOne(String id);

    /**
     * Delete the "id" rewardItem.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
