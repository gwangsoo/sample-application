package com.phoenixdarts.toss.backend.repository;

import com.phoenixdarts.toss.backend.domain.Operator;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Operator entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OperatorRepository extends JpaRepository<Operator, String> {}
