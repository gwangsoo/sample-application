package com.phoenixdarts.toss.backend.repository;

import com.phoenixdarts.toss.backend.domain.MatchFormatLeg;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the MatchFormatLeg entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MatchFormatLegRepository extends JpaRepository<MatchFormatLeg, String> {}
