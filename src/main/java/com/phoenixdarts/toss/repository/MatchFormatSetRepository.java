package com.phoenixdarts.toss.backend.repository;

import com.phoenixdarts.toss.backend.domain.MatchFormatSet;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the MatchFormatSet entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MatchFormatSetRepository extends JpaRepository<MatchFormatSet, String> {}
