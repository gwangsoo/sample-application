package com.phoenixdarts.toss.backend.service.mapper;

import com.phoenixdarts.toss.backend.domain.Game;
import com.phoenixdarts.toss.backend.service.dto.GameDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Game} and its DTO {@link GameDTO}.
 */
@Mapper(componentModel = "spring")
public interface GameMapper extends EntityMapper<GameDTO, Game> {}
