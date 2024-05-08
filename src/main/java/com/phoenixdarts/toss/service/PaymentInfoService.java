package com.phoenixdarts.toss.backend.service;

import com.phoenixdarts.toss.backend.service.dto.PaymentInfoDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.phoenixdarts.toss.backend.domain.PaymentInfo}.
 */
public interface PaymentInfoService {
    /**
     * Save a paymentInfo.
     *
     * @param paymentInfoDTO the entity to save.
     * @return the persisted entity.
     */
    PaymentInfoDTO save(PaymentInfoDTO paymentInfoDTO);

    /**
     * Updates a paymentInfo.
     *
     * @param paymentInfoDTO the entity to update.
     * @return the persisted entity.
     */
    PaymentInfoDTO update(PaymentInfoDTO paymentInfoDTO);

    /**
     * Partially updates a paymentInfo.
     *
     * @param paymentInfoDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PaymentInfoDTO> partialUpdate(PaymentInfoDTO paymentInfoDTO);

    /**
     * Get all the paymentInfos.
     *
     * @return the list of entities.
     */
    List<PaymentInfoDTO> findAll();

    /**
     * Get the "id" paymentInfo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PaymentInfoDTO> findOne(String id);

    /**
     * Delete the "id" paymentInfo.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
