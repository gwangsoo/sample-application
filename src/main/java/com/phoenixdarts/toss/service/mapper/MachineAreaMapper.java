package com.phoenixdarts.toss.service.mapper;

import com.phoenixdarts.toss.domain.Competition;
import com.phoenixdarts.toss.domain.MachineArea;
import com.phoenixdarts.toss.service.dto.CompetitionDTO;
import com.phoenixdarts.toss.service.dto.MachineAreaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link MachineArea} and its DTO {@link MachineAreaDTO}.
 */
@Mapper(componentModel = "spring")
public interface MachineAreaMapper extends EntityMapper<MachineAreaDTO, MachineArea> {
    @Mapping(target = "competition", source = "competition", qualifiedByName = "competitionId")
    MachineAreaDTO toDto(MachineArea s);

    @Named("competitionId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CompetitionDTO toDtoCompetitionId(Competition competition);
}
