package com.phoenixdarts.toss.service.mapper;

import com.phoenixdarts.toss.domain.Division;
import com.phoenixdarts.toss.domain.Tournament;
import com.phoenixdarts.toss.service.dto.DivisionDTO;
import com.phoenixdarts.toss.service.dto.TournamentDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Division} and its DTO {@link DivisionDTO}.
 */
@Mapper(componentModel = "spring")
public interface DivisionMapper extends EntityMapper<DivisionDTO, Division> {
    @Mapping(target = "tournament", source = "tournament", qualifiedByName = "tournamentId")
    DivisionDTO toDto(Division s);

    @Named("tournamentId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TournamentDTO toDtoTournamentId(Tournament tournament);
}
