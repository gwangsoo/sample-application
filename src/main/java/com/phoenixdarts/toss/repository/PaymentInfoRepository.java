package com.phoenixdarts.toss.backend.repository;

import com.phoenixdarts.toss.backend.domain.PaymentInfo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PaymentInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PaymentInfoRepository extends JpaRepository<PaymentInfo, String> {}
