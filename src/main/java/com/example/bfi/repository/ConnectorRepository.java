package com.example.bfi.repository;

import com.example.bfi.domain.Connector;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Connector entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConnectorRepository extends MongoRepository<Connector, String> {}
