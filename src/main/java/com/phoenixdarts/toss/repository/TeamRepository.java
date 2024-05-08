package com.phoenixdarts.toss.backend.repository;

import com.phoenixdarts.toss.backend.domain.Team;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Team entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TeamRepository extends JpaRepository<Team, String> {}
