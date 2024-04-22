package com.phoenixdarts.toss.service.mapper;

import com.phoenixdarts.toss.domain.Reward;
import com.phoenixdarts.toss.domain.RewardDetail;
import com.phoenixdarts.toss.service.dto.RewardDTO;
import com.phoenixdarts.toss.service.dto.RewardDetailDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link RewardDetail} and its DTO {@link RewardDetailDTO}.
 */
@Mapper(componentModel = "spring")
public interface RewardDetailMapper extends EntityMapper<RewardDetailDTO, RewardDetail> {
    @Mapping(target = "reward", source = "reward", qualifiedByName = "rewardId")
    RewardDetailDTO toDto(RewardDetail s);

    @Named("rewardId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    RewardDTO toDtoRewardId(Reward reward);
}
