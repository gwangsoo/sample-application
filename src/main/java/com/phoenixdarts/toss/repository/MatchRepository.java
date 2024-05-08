package com.phoenixdarts.toss.backend.repository;

import com.phoenixdarts.toss.backend.domain.Match;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Match entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MatchRepository extends JpaRepository<Match, String> {}
