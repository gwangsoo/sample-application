package com.phoenixdarts.toss.service.mapper;

import com.phoenixdarts.toss.domain.MatchFormat;
import com.phoenixdarts.toss.domain.MatchFormatOption;
import com.phoenixdarts.toss.service.dto.MatchFormatDTO;
import com.phoenixdarts.toss.service.dto.MatchFormatOptionDTO;
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
