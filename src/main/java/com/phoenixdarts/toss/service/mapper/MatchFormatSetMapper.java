package com.phoenixdarts.toss.backend.service.mapper;

import com.phoenixdarts.toss.backend.domain.MatchFormat;
import com.phoenixdarts.toss.backend.domain.MatchFormatSet;
import com.phoenixdarts.toss.backend.service.dto.MatchFormatDTO;
import com.phoenixdarts.toss.backend.service.dto.MatchFormatSetDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link MatchFormatSet} and its DTO {@link MatchFormatSetDTO}.
 */
@Mapper(componentModel = "spring")
public interface MatchFormatSetMapper extends EntityMapper<MatchFormatSetDTO, MatchFormatSet> {
    @Mapping(target = "matchFormat", source = "matchFormat", qualifiedByName = "matchFormatId")
    MatchFormatSetDTO toDto(MatchFormatSet s);

    @Named("matchFormatId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MatchFormatDTO toDtoMatchFormatId(MatchFormat matchFormat);
}
