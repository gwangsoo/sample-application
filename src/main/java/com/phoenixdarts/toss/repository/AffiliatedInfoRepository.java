package com.phoenixdarts.toss.backend.repository;

import com.phoenixdarts.toss.backend.domain.AffiliatedInfo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AffiliatedInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AffiliatedInfoRepository extends JpaRepository<AffiliatedInfo, String> {}
