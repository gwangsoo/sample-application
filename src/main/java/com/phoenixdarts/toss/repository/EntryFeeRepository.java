package com.phoenixdarts.toss.repository;

import com.phoenixdarts.toss.domain.EntryFee;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the EntryFee entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EntryFeeRepository extends JpaRepository<EntryFee, String> {}
