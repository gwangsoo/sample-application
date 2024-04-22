package com.phoenixdarts.toss.service;

import com.phoenixdarts.toss.service.dto.TeamDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.phoenixdarts.toss.domain.Team}.
 */
public interface TeamService {
    /**
     * Save a team.
     *
     * @param teamDTO the entity to save.
     * @return the persisted entity.
     */
    TeamDTO save(TeamDTO teamDTO);

    /**
     * Updates a team.
     *
     * @param teamDTO the entity to update.
     * @return the persisted entity.
     */
    TeamDTO update(TeamDTO teamDTO);

    /**
     * Partially updates a team.
     *
     * @param teamDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TeamDTO> partialUpdate(TeamDTO teamDTO);

    /**
     * Get all the teams.
     *
     * @return the list of entities.
     */
    List<TeamDTO> findAll();

    /**
     * Get the "id" team.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TeamDTO> findOne(String id);

    /**
     * Delete the "id" team.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
