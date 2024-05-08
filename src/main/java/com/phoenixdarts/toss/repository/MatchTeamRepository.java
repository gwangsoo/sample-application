package com.phoenixdarts.toss.backend.repository;

import com.phoenixdarts.toss.backend.domain.MatchTeam;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the MatchTeam entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MatchTeamRepository extends JpaRepository<MatchTeam, String> {}
