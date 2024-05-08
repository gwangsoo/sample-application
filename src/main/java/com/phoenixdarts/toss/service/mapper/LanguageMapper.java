package com.phoenixdarts.toss.backend.service.mapper;

import com.phoenixdarts.toss.backend.domain.Language;
import com.phoenixdarts.toss.backend.service.dto.LanguageDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Language} and its DTO {@link LanguageDTO}.
 */
@Mapper(componentModel = "spring")
public interface LanguageMapper extends EntityMapper<LanguageDTO, Language> {}
