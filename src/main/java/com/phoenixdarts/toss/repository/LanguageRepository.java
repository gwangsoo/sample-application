package com.phoenixdarts.toss.backend.repository;

import com.phoenixdarts.toss.backend.domain.Language;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Language entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LanguageRepository extends JpaRepository<Language, String> {}
