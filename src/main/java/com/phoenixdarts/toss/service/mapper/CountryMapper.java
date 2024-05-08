package com.phoenixdarts.toss.backend.service.mapper;

import com.phoenixdarts.toss.backend.domain.Country;
import com.phoenixdarts.toss.backend.domain.FileInfo;
import com.phoenixdarts.toss.backend.service.dto.CountryDTO;
import com.phoenixdarts.toss.backend.service.dto.FileInfoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Country} and its DTO {@link CountryDTO}.
 */
@Mapper(componentModel = "spring")
public interface CountryMapper extends EntityMapper<CountryDTO, Country> {
    @Mapping(target = "image", source = "image", qualifiedByName = "fileInfoId")
    CountryDTO toDto(Country s);

    @Named("fileInfoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FileInfoDTO toDtoFileInfoId(FileInfo fileInfo);
}
