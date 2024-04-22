package com.phoenixdarts.toss.repository;

import com.phoenixdarts.toss.domain.MachineArea;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the MachineArea entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MachineAreaRepository extends JpaRepository<MachineArea, String> {}
