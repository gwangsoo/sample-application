package com.phoenixdarts.toss.backend.service.mapper;

import com.phoenixdarts.toss.backend.domain.OperatorRole;
import com.phoenixdarts.toss.backend.service.dto.OperatorRoleDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link OperatorRole} and its DTO {@link OperatorRoleDTO}.
 */
@Mapper(componentModel = "spring")
public interface OperatorRoleMapper extends EntityMapper<OperatorRoleDTO, OperatorRole> {}
