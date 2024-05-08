package com.phoenixdarts.toss.backend.repository;

import com.phoenixdarts.toss.backend.domain.Machine;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Machine entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MachineRepository extends JpaRepository<Machine, String> {}
