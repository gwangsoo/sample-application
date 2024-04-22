package com.phoenixdarts.toss.service.mapper;

import com.phoenixdarts.toss.domain.Operator;
import com.phoenixdarts.toss.domain.OperatorRole;
import com.phoenixdarts.toss.domain.Region;
import com.phoenixdarts.toss.service.dto.OperatorDTO;
import com.phoenixdarts.toss.service.dto.OperatorRoleDTO;
import com.phoenixdarts.toss.service.dto.RegionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Operator} and its DTO {@link OperatorDTO}.
 */
@Mapper(componentModel = "spring")
public interface OperatorMapper extends EntityMapper<OperatorDTO, Operator> {
    @Mapping(target = "operatorRole", source = "operatorRole", qualifiedByName = "operatorRoleId")
    @Mapping(target = "region", source = "region", qualifiedByName = "regionId")
    OperatorDTO toDto(Operator s);

    @Named("operatorRoleId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    OperatorRoleDTO toDtoOperatorRoleId(OperatorRole operatorRole);

    @Named("regionId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    RegionDTO toDtoRegionId(Region region);
}
