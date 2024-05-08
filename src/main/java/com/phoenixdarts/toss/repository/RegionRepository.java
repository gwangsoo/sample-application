package com.phoenixdarts.toss.backend.repository;

import com.phoenixdarts.toss.backend.domain.Region;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Region entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RegionRepository extends JpaRepository<Region, String> {}
