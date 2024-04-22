package com.phoenixdarts.toss.service.mapper;

import com.phoenixdarts.toss.domain.FileInfo;
import com.phoenixdarts.toss.service.dto.FileInfoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link FileInfo} and its DTO {@link FileInfoDTO}.
 */
@Mapper(componentModel = "spring")
public interface FileInfoMapper extends EntityMapper<FileInfoDTO, FileInfo> {}
