package com.phoenixdarts.toss.backend.service.mapper;

import com.phoenixdarts.toss.backend.domain.MatchCall;
import com.phoenixdarts.toss.backend.domain.MatchTeam;
import com.phoenixdarts.toss.backend.service.dto.MatchCallDTO;
import com.phoenixdarts.toss.backend.service.dto.MatchTeamDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link MatchCall} and its DTO {@link MatchCallDTO}.
 */
@Mapper(componentModel = "spring")
public interface MatchCallMapper extends EntityMapper<MatchCallDTO, MatchCall> {
    @Mapping(target = "matchTeam", source = "matchTeam", qualifiedByName = "matchTeamId")
    MatchCallDTO toDto(MatchCall s);

    @Named("matchTeamId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MatchTeamDTO toDtoMatchTeamId(MatchTeam matchTeam);
}
