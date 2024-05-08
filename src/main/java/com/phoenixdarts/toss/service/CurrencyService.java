package com.phoenixdarts.toss.backend.service;

import com.phoenixdarts.toss.backend.service.dto.CurrencyDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.phoenixdarts.toss.backend.domain.Currency}.
 */
public interface CurrencyService {
    /**
     * Save a currency.
     *
     * @param currencyDTO the entity to save.
     * @return the persisted entity.
     */
    CurrencyDTO save(CurrencyDTO currencyDTO);

    /**
     * Updates a currency.
     *
     * @param currencyDTO the entity to update.
     * @return the persisted entity.
     */
    CurrencyDTO update(CurrencyDTO currencyDTO);

    /**
     * Partially updates a currency.
     *
     * @param currencyDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CurrencyDTO> partialUpdate(CurrencyDTO currencyDTO);

    /**
     * Get all the currencies.
     *
     * @return the list of entities.
     */
    List<CurrencyDTO> findAll();

    /**
     * Get the "id" currency.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CurrencyDTO> findOne(String id);

    /**
     * Delete the "id" currency.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
