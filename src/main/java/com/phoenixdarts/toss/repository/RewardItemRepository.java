package com.phoenixdarts.toss.backend.repository;

import com.phoenixdarts.toss.backend.domain.RewardItem;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the RewardItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RewardItemRepository extends JpaRepository<RewardItem, String> {}
