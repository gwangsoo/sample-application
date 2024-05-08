package com.phoenixdarts.toss.backend.service.mapper;

import com.phoenixdarts.toss.backend.domain.AffiliatedInfo;
import com.phoenixdarts.toss.backend.domain.FileInfo;
import com.phoenixdarts.toss.backend.service.dto.AffiliatedInfoDTO;
import com.phoenixdarts.toss.backend.service.dto.FileInfoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AffiliatedInfo} and its DTO {@link AffiliatedInfoDTO}.
 */
@Mapper(componentModel = "spring")
public interface AffiliatedInfoMapper extends EntityMapper<AffiliatedInfoDTO, AffiliatedInfo> {
    @Mapping(target = "fileInfo", source = "fileInfo", qualifiedByName = "fileInfoId")
    AffiliatedInfoDTO toDto(AffiliatedInfo s);

    @Named("fileInfoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FileInfoDTO toDtoFileInfoId(FileInfo fileInfo);
}
