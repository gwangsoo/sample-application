package com.phoenixdarts.toss.backend.service.mapper;

import com.phoenixdarts.toss.backend.domain.MatchFormatLeg;
import com.phoenixdarts.toss.backend.domain.MatchFormatOption;
import com.phoenixdarts.toss.backend.domain.MatchFormatSet;
import com.phoenixdarts.toss.backend.service.dto.MatchFormatLegDTO;
import com.phoenixdarts.toss.backend.service.dto.MatchFormatOptionDTO;
import com.phoenixdarts.toss.backend.service.dto.MatchFormatSetDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link MatchFormatLeg} and its DTO {@link MatchFormatLegDTO}.
 */
@Mapper(componentModel = "spring")
public interface MatchFormatLegMapper extends EntityMapper<MatchFormatLegDTO, MatchFormatLeg> {
    @Mapping(target = "option", source = "option", qualifiedByName = "matchFormatOptionId")
    @Mapping(target = "matchFormatSet", source = "matchFormatSet", qualifiedByName = "matchFormatSetId")
    MatchFormatLegDTO toDto(MatchFormatLeg s);

    @Named("matchFormatOptionId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MatchFormatOptionDTO toDtoMatchFormatOptionId(MatchFormatOption matchFormatOption);

    @Named("matchFormatSetId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MatchFormatSetDTO toDtoMatchFormatSetId(MatchFormatSet matchFormatSet);
}
