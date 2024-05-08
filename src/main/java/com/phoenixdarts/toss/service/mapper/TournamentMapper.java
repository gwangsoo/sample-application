package com.phoenixdarts.toss.backend.service.mapper;

import com.phoenixdarts.toss.backend.domain.Competition;
import com.phoenixdarts.toss.backend.domain.EntryFee;
import com.phoenixdarts.toss.backend.domain.Tournament;
import com.phoenixdarts.toss.backend.service.dto.CompetitionDTO;
import com.phoenixdarts.toss.backend.service.dto.EntryFeeDTO;
import com.phoenixdarts.toss.backend.service.dto.TournamentDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Tournament} and its DTO {@link TournamentDTO}.
 */
@Mapper(componentModel = "spring")
public interface TournamentMapper extends EntityMapper<TournamentDTO, Tournament> {
    @Mapping(target = "entryFee", source = "entryFee", qualifiedByName = "entryFeeId")
    @Mapping(target = "competition", source = "competition", qualifiedByName = "competitionId")
    TournamentDTO toDto(Tournament s);

    @Named("entryFeeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EntryFeeDTO toDtoEntryFeeId(EntryFee entryFee);

    @Named("competitionId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CompetitionDTO toDtoCompetitionId(Competition competition);
}
