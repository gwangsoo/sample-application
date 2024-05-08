package com.phoenixdarts.toss.backend.service;

import com.phoenixdarts.toss.backend.service.dto.MatchAttendanceDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.phoenixdarts.toss.backend.domain.MatchAttendance}.
 */
public interface MatchAttendanceService {
    /**
     * Save a matchAttendance.
     *
     * @param matchAttendanceDTO the entity to save.
     * @return the persisted entity.
     */
    MatchAttendanceDTO save(MatchAttendanceDTO matchAttendanceDTO);

    /**
     * Updates a matchAttendance.
     *
     * @param matchAttendanceDTO the entity to update.
     * @return the persisted entity.
     */
    MatchAttendanceDTO update(MatchAttendanceDTO matchAttendanceDTO);

    /**
     * Partially updates a matchAttendance.
     *
     * @param matchAttendanceDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<MatchAttendanceDTO> partialUpdate(MatchAttendanceDTO matchAttendanceDTO);

    /**
     * Get all the matchAttendances.
     *
     * @return the list of entities.
     */
    List<MatchAttendanceDTO> findAll();

    /**
     * Get the "id" matchAttendance.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MatchAttendanceDTO> findOne(String id);

    /**
     * Delete the "id" matchAttendance.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
