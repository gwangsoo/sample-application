package com.phoenixdarts.toss.backend.service.mapper;

import com.phoenixdarts.toss.backend.domain.FileInfo;
import com.phoenixdarts.toss.backend.domain.RewardDetail;
import com.phoenixdarts.toss.backend.domain.RewardItem;
import com.phoenixdarts.toss.backend.service.dto.FileInfoDTO;
import com.phoenixdarts.toss.backend.service.dto.RewardDetailDTO;
import com.phoenixdarts.toss.backend.service.dto.RewardItemDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link RewardItem} and its DTO {@link RewardItemDTO}.
 */
@Mapper(componentModel = "spring")
public interface RewardItemMapper extends EntityMapper<RewardItemDTO, RewardItem> {
    @Mapping(target = "itemImage", source = "itemImage", qualifiedByName = "fileInfoId")
    @Mapping(target = "rewardDetail", source = "rewardDetail", qualifiedByName = "rewardDetailId")
    RewardItemDTO toDto(RewardItem s);

    @Named("fileInfoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FileInfoDTO toDtoFileInfoId(FileInfo fileInfo);

    @Named("rewardDetailId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    RewardDetailDTO toDtoRewardDetailId(RewardDetail rewardDetail);
}
