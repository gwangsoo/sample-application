package com.phoenixdarts.toss.service.mapper;

import com.phoenixdarts.toss.domain.PaymentInfo;
import com.phoenixdarts.toss.service.dto.PaymentInfoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PaymentInfo} and its DTO {@link PaymentInfoDTO}.
 */
@Mapper(componentModel = "spring")
public interface PaymentInfoMapper extends EntityMapper<PaymentInfoDTO, PaymentInfo> {}
