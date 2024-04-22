package com.phoenixdarts.toss.repository;

import com.phoenixdarts.toss.domain.MatchScore;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the MatchScore entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MatchScoreRepository extends JpaRepository<MatchScore, String> {}
