package com.phoenixdarts.toss.service.mapper;

import com.phoenixdarts.toss.domain.Game;
import com.phoenixdarts.toss.service.dto.GameDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Game} and its DTO {@link GameDTO}.
 */
@Mapper(componentModel = "spring")
public interface GameMapper extends EntityMapper<GameDTO, Game> {}
