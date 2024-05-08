package com.phoenixdarts.toss.backend.repository;

import com.phoenixdarts.toss.backend.domain.RewardDetail;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the RewardDetail entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RewardDetailRepository extends JpaRepository<RewardDetail, String> {}
