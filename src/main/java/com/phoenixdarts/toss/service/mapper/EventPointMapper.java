package com.phoenixdarts.toss.backend.service.mapper;

import com.phoenixdarts.toss.backend.domain.Division;
import com.phoenixdarts.toss.backend.domain.EventPoint;
import com.phoenixdarts.toss.backend.service.dto.DivisionDTO;
import com.phoenixdarts.toss.backend.service.dto.EventPointDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link EventPoint} and its DTO {@link EventPointDTO}.
 */
@Mapper(componentModel = "spring")
public interface EventPointMapper extends EntityMapper<EventPointDTO, EventPoint> {
    @Mapping(target = "division", source = "division", qualifiedByName = "divisionId")
    EventPointDTO toDto(EventPoint s);

    @Named("divisionId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DivisionDTO toDtoDivisionId(Division division);
}
