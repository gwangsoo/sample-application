package com.example.bfi.repository;

import com.example.bfi.domain.Evse;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Evse entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EvseRepository extends MongoRepository<Evse, String> {}
