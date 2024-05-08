package com.phoenixdarts.toss.backend.service.mapper;

import com.phoenixdarts.toss.backend.domain.Division;
import com.phoenixdarts.toss.backend.domain.Machine;
import com.phoenixdarts.toss.backend.domain.Match;
import com.phoenixdarts.toss.backend.domain.MatchTeam;
import com.phoenixdarts.toss.backend.service.dto.DivisionDTO;
import com.phoenixdarts.toss.backend.service.dto.MachineDTO;
import com.phoenixdarts.toss.backend.service.dto.MatchDTO;
import com.phoenixdarts.toss.backend.service.dto.MatchTeamDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Match} and its DTO {@link MatchDTO}.
 */
@Mapper(componentModel = "spring")
public interface MatchMapper extends EntityMapper<MatchDTO, Match> {
    @Mapping(target = "home", source = "home", qualifiedByName = "matchTeamId")
    @Mapping(target = "away", source = "away", qualifiedByName = "matchTeamId")
    @Mapping(target = "tmatch", source = "tmatch", qualifiedByName = "machineId")
    @Mapping(target = "division", source = "division", qualifiedByName = "divisionId")
    MatchDTO toDto(Match s);

    @Named("matchTeamId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MatchTeamDTO toDtoMatchTeamId(MatchTeam matchTeam);

    @Named("machineId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MachineDTO toDtoMachineId(Machine machine);

    @Named("divisionId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DivisionDTO toDtoDivisionId(Division division);
}
