package com.phoenixdarts.toss.backend.service.mapper;

import com.phoenixdarts.toss.backend.domain.Machine;
import com.phoenixdarts.toss.backend.domain.MachineArea;
import com.phoenixdarts.toss.backend.domain.Match;
import com.phoenixdarts.toss.backend.service.dto.MachineAreaDTO;
import com.phoenixdarts.toss.backend.service.dto.MachineDTO;
import com.phoenixdarts.toss.backend.service.dto.MatchDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Machine} and its DTO {@link MachineDTO}.
 */
@Mapper(componentModel = "spring")
public interface MachineMapper extends EntityMapper<MachineDTO, Machine> {
    @Mapping(target = "match", source = "match", qualifiedByName = "matchId")
    @Mapping(target = "machineArea", source = "machineArea", qualifiedByName = "machineAreaId")
    MachineDTO toDto(Machine s);

    @Named("matchId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MatchDTO toDtoMatchId(Match match);

    @Named("machineAreaId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MachineAreaDTO toDtoMachineAreaId(MachineArea machineArea);
}
