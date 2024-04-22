package com.phoenixdarts.toss.service;

import com.phoenixdarts.toss.service.dto.RewardDetailDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.phoenixdarts.toss.domain.RewardDetail}.
 */
public interface RewardDetailService {
    /**
     * Save a rewardDetail.
     *
     * @param rewardDetailDTO the entity to save.
     * @return the persisted entity.
     */
    RewardDetailDTO save(RewardDetailDTO rewardDetailDTO);

    /**
     * Updates a rewardDetail.
     *
     * @param rewardDetailDTO the entity to update.
     * @return the persisted entity.
     */
    RewardDetailDTO update(RewardDetailDTO rewardDetailDTO);

    /**
     * Partially updates a rewardDetail.
     *
     * @param rewardDetailDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<RewardDetailDTO> partialUpdate(RewardDetailDTO rewardDetailDTO);

    /**
     * Get all the rewardDetails.
     *
     * @return the list of entities.
     */
    List<RewardDetailDTO> findAll();

    /**
     * Get the "id" rewardDetail.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RewardDetailDTO> findOne(String id);

    /**
     * Delete the "id" rewardDetail.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
