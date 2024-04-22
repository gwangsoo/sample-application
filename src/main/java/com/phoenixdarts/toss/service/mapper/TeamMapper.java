package com.phoenixdarts.toss.service.mapper;

import com.phoenixdarts.toss.domain.AffiliatedInfo;
import com.phoenixdarts.toss.domain.Division;
import com.phoenixdarts.toss.domain.Entry;
import com.phoenixdarts.toss.domain.PaymentInfo;
import com.phoenixdarts.toss.domain.Team;
import com.phoenixdarts.toss.service.dto.AffiliatedInfoDTO;
import com.phoenixdarts.toss.service.dto.DivisionDTO;
import com.phoenixdarts.toss.service.dto.EntryDTO;
import com.phoenixdarts.toss.service.dto.PaymentInfoDTO;
import com.phoenixdarts.toss.service.dto.TeamDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Team} and its DTO {@link TeamDTO}.
 */
@Mapper(componentModel = "spring")
public interface TeamMapper extends EntityMapper<TeamDTO, Team> {
    @Mapping(target = "captain", source = "captain", qualifiedByName = "entryId")
    @Mapping(target = "tteam", source = "tteam", qualifiedByName = "divisionId")
    @Mapping(target = "affiliatedInfo", source = "affiliatedInfo", qualifiedByName = "affiliatedInfoId")
    @Mapping(target = "paymentInfo", source = "paymentInfo", qualifiedByName = "paymentInfoId")
    @Mapping(target = "division", source = "division", qualifiedByName = "divisionId")
    TeamDTO toDto(Team s);

    @Named("entryId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EntryDTO toDtoEntryId(Entry entry);

    @Named("divisionId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DivisionDTO toDtoDivisionId(Division division);

    @Named("affiliatedInfoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AffiliatedInfoDTO toDtoAffiliatedInfoId(AffiliatedInfo affiliatedInfo);

    @Named("paymentInfoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PaymentInfoDTO toDtoPaymentInfoId(PaymentInfo paymentInfo);
}
