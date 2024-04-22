package com.phoenixdarts.toss.service.mapper;

import com.phoenixdarts.toss.domain.Currency;
import com.phoenixdarts.toss.service.dto.CurrencyDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Currency} and its DTO {@link CurrencyDTO}.
 */
@Mapper(componentModel = "spring")
public interface CurrencyMapper extends EntityMapper<CurrencyDTO, Currency> {}
