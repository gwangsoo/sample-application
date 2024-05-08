package com.phoenixdarts.toss.backend.service.mapper;

import com.phoenixdarts.toss.backend.domain.PaymentInfo;
import com.phoenixdarts.toss.backend.service.dto.PaymentInfoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PaymentInfo} and its DTO {@link PaymentInfoDTO}.
 */
@Mapper(componentModel = "spring")
public interface PaymentInfoMapper extends EntityMapper<PaymentInfoDTO, PaymentInfo> {}
