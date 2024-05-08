package com.phoenixdarts.toss.backend.service.mapper;

import com.phoenixdarts.toss.backend.domain.Currency;
import com.phoenixdarts.toss.backend.domain.EntryFee;
import com.phoenixdarts.toss.backend.service.dto.CurrencyDTO;
import com.phoenixdarts.toss.backend.service.dto.EntryFeeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link EntryFee} and its DTO {@link EntryFeeDTO}.
 */
@Mapper(componentModel = "spring")
public interface EntryFeeMapper extends EntityMapper<EntryFeeDTO, EntryFee> {
    @Mapping(target = "currency", source = "currency", qualifiedByName = "currencyId")
    EntryFeeDTO toDto(EntryFee s);

    @Named("currencyId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CurrencyDTO toDtoCurrencyId(Currency currency);
}
