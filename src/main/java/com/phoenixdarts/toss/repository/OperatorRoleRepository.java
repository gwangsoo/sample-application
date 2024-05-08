package com.phoenixdarts.toss.backend.repository;

import com.phoenixdarts.toss.backend.domain.OperatorRole;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the OperatorRole entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OperatorRoleRepository extends JpaRepository<OperatorRole, String> {}
