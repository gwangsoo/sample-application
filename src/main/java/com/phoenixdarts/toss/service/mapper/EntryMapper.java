package com.phoenixdarts.toss.backend.service.mapper;

import com.phoenixdarts.toss.backend.domain.Entry;
import com.phoenixdarts.toss.backend.domain.Team;
import com.phoenixdarts.toss.backend.service.dto.EntryDTO;
import com.phoenixdarts.toss.backend.service.dto.TeamDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Entry} and its DTO {@link EntryDTO}.
 */
@Mapper(componentModel = "spring")
public interface EntryMapper extends EntityMapper<EntryDTO, Entry> {
    @Mapping(target = "team", source = "team", qualifiedByName = "teamId")
    EntryDTO toDto(Entry s);

    @Named("teamId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TeamDTO toDtoTeamId(Team team);
}
