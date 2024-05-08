package com.phoenixdarts.toss.backend.repository;

import com.phoenixdarts.toss.backend.domain.Competition;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Competition entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompetitionRepository extends JpaRepository<Competition, String> {}
