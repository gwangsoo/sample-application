package com.phoenixdarts.toss.backend.repository;

import com.phoenixdarts.toss.backend.domain.Reward;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Reward entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RewardRepository extends JpaRepository<Reward, String> {}
