package com.phoenixdarts.toss.service.mapper;

import com.phoenixdarts.toss.domain.MatchTeam;
import com.phoenixdarts.toss.domain.Team;
import com.phoenixdarts.toss.service.dto.MatchTeamDTO;
import com.phoenixdarts.toss.service.dto.TeamDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link MatchTeam} and its DTO {@link MatchTeamDTO}.
 */
@Mapper(componentModel = "spring")
public interface MatchTeamMapper extends EntityMapper<MatchTeamDTO, MatchTeam> {
    @Mapping(target = "team", source = "team", qualifiedByName = "teamId")
    MatchTeamDTO toDto(MatchTeam s);

    @Named("teamId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TeamDTO toDtoTeamId(Team team);
}
