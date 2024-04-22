package com.phoenixdarts.toss.service;

import com.phoenixdarts.toss.service.dto.RewardDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.phoenixdarts.toss.domain.Reward}.
 */
public interface RewardService {
    /**
     * Save a reward.
     *
     * @param rewardDTO the entity to save.
     * @return the persisted entity.
     */
    RewardDTO save(RewardDTO rewardDTO);

    /**
     * Updates a reward.
     *
     * @param rewardDTO the entity to update.
     * @return the persisted entity.
     */
    RewardDTO update(RewardDTO rewardDTO);

    /**
     * Partially updates a reward.
     *
     * @param rewardDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<RewardDTO> partialUpdate(RewardDTO rewardDTO);

    /**
     * Get all the rewards.
     *
     * @return the list of entities.
     */
    List<RewardDTO> findAll();

    /**
     * Get the "id" reward.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RewardDTO> findOne(String id);

    /**
     * Delete the "id" reward.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
