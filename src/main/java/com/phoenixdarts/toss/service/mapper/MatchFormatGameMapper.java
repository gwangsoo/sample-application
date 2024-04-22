package com.phoenixdarts.toss.service.mapper;

import com.phoenixdarts.toss.domain.Game;
import com.phoenixdarts.toss.domain.MatchFormat;
import com.phoenixdarts.toss.domain.MatchFormatGame;
import com.phoenixdarts.toss.service.dto.GameDTO;
import com.phoenixdarts.toss.service.dto.MatchFormatDTO;
import com.phoenixdarts.toss.service.dto.MatchFormatGameDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link MatchFormatGame} and its DTO {@link MatchFormatGameDTO}.
 */
@Mapper(componentModel = "spring")
public interface MatchFormatGameMapper extends EntityMapper<MatchFormatGameDTO, MatchFormatGame> {
    @Mapping(target = "game", source = "game", qualifiedByName = "gameId")
    @Mapping(target = "matchFormat", source = "matchFormat", qualifiedByName = "matchFormatId")
    MatchFormatGameDTO toDto(MatchFormatGame s);

    @Named("gameId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    GameDTO toDtoGameId(Game game);

    @Named("matchFormatId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MatchFormatDTO toDtoMatchFormatId(MatchFormat matchFormat);
}
