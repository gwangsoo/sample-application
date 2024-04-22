package com.phoenixdarts.toss.service.mapper;

import com.phoenixdarts.toss.domain.Machine;
import com.phoenixdarts.toss.domain.MachineArea;
import com.phoenixdarts.toss.domain.Match;
import com.phoenixdarts.toss.service.dto.MachineAreaDTO;
import com.phoenixdarts.toss.service.dto.MachineDTO;
import com.phoenixdarts.toss.service.dto.MatchDTO;
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
