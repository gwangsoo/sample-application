package com.phoenixdarts.toss.backend.repository;

import com.phoenixdarts.toss.backend.domain.MatchCall;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the MatchCall entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MatchCallRepository extends JpaRepository<MatchCall, String> {}
