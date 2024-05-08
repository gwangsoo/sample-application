package com.phoenixdarts.toss.backend.repository;

import com.phoenixdarts.toss.backend.domain.MatchFormat;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the MatchFormat entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MatchFormatRepository extends JpaRepository<MatchFormat, String> {}
