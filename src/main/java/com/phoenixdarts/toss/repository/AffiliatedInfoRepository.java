package com.phoenixdarts.toss.repository;

import com.phoenixdarts.toss.domain.AffiliatedInfo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AffiliatedInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AffiliatedInfoRepository extends JpaRepository<AffiliatedInfo, String> {}
