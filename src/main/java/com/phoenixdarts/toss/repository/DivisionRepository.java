package com.phoenixdarts.toss.backend.repository;

import com.phoenixdarts.toss.backend.domain.Division;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Division entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DivisionRepository extends JpaRepository<Division, String> {}
