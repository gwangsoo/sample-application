package com.phoenixdarts.toss.backend.repository;

import com.phoenixdarts.toss.backend.domain.EntryFee;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the EntryFee entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EntryFeeRepository extends JpaRepository<EntryFee, String> {}
