package com.phoenixdarts.toss.repository;

import com.phoenixdarts.toss.domain.RewardItem;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the RewardItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RewardItemRepository extends JpaRepository<RewardItem, String> {}
