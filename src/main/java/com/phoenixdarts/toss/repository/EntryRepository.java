package com.phoenixdarts.toss.backend.repository;

import com.phoenixdarts.toss.backend.domain.Entry;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Entry entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EntryRepository extends JpaRepository<Entry, String> {}
