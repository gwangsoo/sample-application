package com.phoenixdarts.toss.repository;

import com.phoenixdarts.toss.domain.MatchFormatOption;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the MatchFormatOption entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MatchFormatOptionRepository extends JpaRepository<MatchFormatOption, String> {}
