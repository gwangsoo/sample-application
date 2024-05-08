package com.phoenixdarts.toss.backend.repository;

import com.phoenixdarts.toss.backend.domain.EventPoint;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the EventPoint entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EventPointRepository extends JpaRepository<EventPoint, String> {}
