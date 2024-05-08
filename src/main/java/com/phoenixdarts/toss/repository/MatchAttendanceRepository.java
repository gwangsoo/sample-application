package com.phoenixdarts.toss.backend.repository;

import com.phoenixdarts.toss.backend.domain.MatchAttendance;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the MatchAttendance entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MatchAttendanceRepository extends JpaRepository<MatchAttendance, String> {}
