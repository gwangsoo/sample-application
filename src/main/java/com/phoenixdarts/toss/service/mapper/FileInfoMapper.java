package com.phoenixdarts.toss.backend.service.mapper;

import com.phoenixdarts.toss.backend.domain.FileInfo;
import com.phoenixdarts.toss.backend.service.dto.FileInfoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link FileInfo} and its DTO {@link FileInfoDTO}.
 */
@Mapper(componentModel = "spring")
public interface FileInfoMapper extends EntityMapper<FileInfoDTO, FileInfo> {}
