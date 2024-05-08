package com.phoenixdarts.toss.backend.service.mapper;

import com.phoenixdarts.toss.backend.domain.Country;
import com.phoenixdarts.toss.backend.domain.Region;
import com.phoenixdarts.toss.backend.service.dto.CountryDTO;
import com.phoenixdarts.toss.backend.service.dto.RegionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Region} and its DTO {@link RegionDTO}.
 */
@Mapper(componentModel = "spring")
public interface RegionMapper extends EntityMapper<RegionDTO, Region> {
    @Mapping(target = "country", source = "country", qualifiedByName = "countryId")
    RegionDTO toDto(Region s);

    @Named("countryId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CountryDTO toDtoCountryId(Country country);
}
