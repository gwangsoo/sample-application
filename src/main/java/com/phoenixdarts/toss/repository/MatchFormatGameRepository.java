package com.phoenixdarts.toss.backend.repository;

import com.phoenixdarts.toss.backend.domain.MatchFormatGame;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the MatchFormatGame entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MatchFormatGameRepository extends JpaRepository<MatchFormatGame, String> {}
