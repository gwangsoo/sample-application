package com.phoenixdarts.toss.backend.service.mapper;

import com.phoenixdarts.toss.backend.domain.Match;
import com.phoenixdarts.toss.backend.domain.MatchScore;
import com.phoenixdarts.toss.backend.service.dto.MatchDTO;
import com.phoenixdarts.toss.backend.service.dto.MatchScoreDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link MatchScore} and its DTO {@link MatchScoreDTO}.
 */
@Mapper(componentModel = "spring")
public interface MatchScoreMapper extends EntityMapper<MatchScoreDTO, MatchScore> {
    @Mapping(target = "match", source = "match", qualifiedByName = "matchId")
    MatchScoreDTO toDto(MatchScore s);

    @Named("matchId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MatchDTO toDtoMatchId(Match match);
}
