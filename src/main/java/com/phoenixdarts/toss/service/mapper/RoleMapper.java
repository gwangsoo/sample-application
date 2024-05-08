package com.phoenixdarts.toss.backend.service.mapper;

import com.phoenixdarts.toss.backend.domain.OperatorRole;
import com.phoenixdarts.toss.backend.domain.Role;
import com.phoenixdarts.toss.backend.service.dto.OperatorRoleDTO;
import com.phoenixdarts.toss.backend.service.dto.RoleDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Role} and its DTO {@link RoleDTO}.
 */
@Mapper(componentModel = "spring")
public interface RoleMapper extends EntityMapper<RoleDTO, Role> {
    @Mapping(target = "operatorRole", source = "operatorRole", qualifiedByName = "operatorRoleId")
    RoleDTO toDto(Role s);

    @Named("operatorRoleId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    OperatorRoleDTO toDtoOperatorRoleId(OperatorRole operatorRole);
}
