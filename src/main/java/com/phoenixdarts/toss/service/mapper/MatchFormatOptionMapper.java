package com.phoenixdarts.toss.backend.service.mapper;

import com.phoenixdarts.toss.backend.domain.MatchFormat;
import com.phoenixdarts.toss.backend.domain.MatchFormatOption;
import com.phoenixdarts.toss.backend.service.dto.MatchFormatDTO;
import com.phoenixdarts.toss.backend.service.dto.MatchFormatOptionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link MatchFormatOption} and its DTO {@link MatchFormatOptionDTO}.
 */
@Mapper(componentModel = "spring")
public interface MatchFormatOptionMapper extends EntityMapper<MatchFormatOptionDTO, MatchFormatOption> {
    @Mapping(target = "matchFormat", source = "matchFormat", qualifiedByName = "matchFormatId")
    MatchFormatOptionDTO toDto(MatchFormatOption s);

    @Named("matchFormatId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MatchFormatDTO toDtoMatchFormatId(MatchFormat matchFormat);
}
