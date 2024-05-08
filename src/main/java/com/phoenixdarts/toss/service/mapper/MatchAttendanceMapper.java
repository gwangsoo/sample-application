package com.phoenixdarts.toss.backend.service.mapper;

import com.phoenixdarts.toss.backend.domain.Entry;
import com.phoenixdarts.toss.backend.domain.MatchAttendance;
import com.phoenixdarts.toss.backend.domain.MatchTeam;
import com.phoenixdarts.toss.backend.service.dto.EntryDTO;
import com.phoenixdarts.toss.backend.service.dto.MatchAttendanceDTO;
import com.phoenixdarts.toss.backend.service.dto.MatchTeamDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link MatchAttendance} and its DTO {@link MatchAttendanceDTO}.
 */
@Mapper(componentModel = "spring")
public interface MatchAttendanceMapper extends EntityMapper<MatchAttendanceDTO, MatchAttendance> {
    @Mapping(target = "entry", source = "entry", qualifiedByName = "entryId")
    @Mapping(target = "matchTeam", source = "matchTeam", qualifiedByName = "matchTeamId")
    MatchAttendanceDTO toDto(MatchAttendance s);

    @Named("entryId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EntryDTO toDtoEntryId(Entry entry);

    @Named("matchTeamId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MatchTeamDTO toDtoMatchTeamId(MatchTeam matchTeam);
}
