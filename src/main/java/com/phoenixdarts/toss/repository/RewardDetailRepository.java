package com.phoenixdarts.toss.repository;

import com.phoenixdarts.toss.domain.RewardDetail;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the RewardDetail entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RewardDetailRepository extends JpaRepository<RewardDetail, String> {}
