package com.phoenixdarts.toss.backend.service;

import com.phoenixdarts.toss.backend.service.dto.LanguageDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.phoenixdarts.toss.backend.domain.Language}.
 */
public interface LanguageService {
    /**
     * Save a language.
     *
     * @param languageDTO the entity to save.
     * @return the persisted entity.
     */
    LanguageDTO save(LanguageDTO languageDTO);

    /**
     * Updates a language.
     *
     * @param languageDTO the entity to update.
     * @return the persisted entity.
     */
    LanguageDTO update(LanguageDTO languageDTO);

    /**
     * Partially updates a language.
     *
     * @param languageDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<LanguageDTO> partialUpdate(LanguageDTO languageDTO);

    /**
     * Get all the languages.
     *
     * @return the list of entities.
     */
    List<LanguageDTO> findAll();

    /**
     * Get the "id" language.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LanguageDTO> findOne(String id);

    /**
     * Delete the "id" language.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
