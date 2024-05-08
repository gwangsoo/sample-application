package com.phoenixdarts.toss.backend.service.mapper;

import com.phoenixdarts.toss.backend.domain.Division;
import com.phoenixdarts.toss.backend.domain.MatchFormat;
import com.phoenixdarts.toss.backend.service.dto.DivisionDTO;
import com.phoenixdarts.toss.backend.service.dto.MatchFormatDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link MatchFormat} and its DTO {@link MatchFormatDTO}.
 */
@Mapper(componentModel = "spring")
public interface MatchFormatMapper extends EntityMapper<MatchFormatDTO, MatchFormat> {
    @Mapping(target = "division", source = "division", qualifiedByName = "divisionId")
    MatchFormatDTO toDto(MatchFormat s);

    @Named("divisionId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DivisionDTO toDtoDivisionId(Division division);
}
