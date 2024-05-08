package com.phoenixdarts.toss.backend.service.mapper;

import com.phoenixdarts.toss.backend.domain.Reward;
import com.phoenixdarts.toss.backend.service.dto.RewardDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Reward} and its DTO {@link RewardDTO}.
 */
@Mapper(componentModel = "spring")
public interface RewardMapper extends EntityMapper<RewardDTO, Reward> {}
