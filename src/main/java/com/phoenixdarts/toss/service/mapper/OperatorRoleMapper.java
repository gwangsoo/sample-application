package com.phoenixdarts.toss.service.mapper;

import com.phoenixdarts.toss.domain.OperatorRole;
import com.phoenixdarts.toss.service.dto.OperatorRoleDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link OperatorRole} and its DTO {@link OperatorRoleDTO}.
 */
@Mapper(componentModel = "spring")
public interface OperatorRoleMapper extends EntityMapper<OperatorRoleDTO, OperatorRole> {}
