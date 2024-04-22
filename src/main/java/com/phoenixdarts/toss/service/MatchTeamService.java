package com.phoenixdarts.toss.service;

import com.phoenixdarts.toss.service.dto.MatchTeamDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.phoenixdarts.toss.domain.MatchTeam}.
 */
public interface MatchTeamService {
    /**
     * Save a matchTeam.
     *
     * @param matchTeamDTO the entity to save.
     * @return the persisted entity.
     */
    MatchTeamDTO save(MatchTeamDTO matchTeamDTO);

    /**
     * Updates a matchTeam.
     *
     * @param matchTeamDTO the entity to update.
     * @return the persisted entity.
     */
    MatchTeamDTO update(MatchTeamDTO matchTeamDTO);

    /**
     * Partially updates a matchTeam.
     *
     * @param matchTeamDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<MatchTeamDTO> partialUpdate(MatchTeamDTO matchTeamDTO);

    /**
     * Get all the matchTeams.
     *
     * @return the list of entities.
     */
    List<MatchTeamDTO> findAll();

    /**
     * Get the "id" matchTeam.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MatchTeamDTO> findOne(String id);

    /**
     * Delete the "id" matchTeam.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
