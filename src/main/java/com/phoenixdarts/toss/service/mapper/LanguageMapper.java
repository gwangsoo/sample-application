package com.phoenixdarts.toss.service.mapper;

import com.phoenixdarts.toss.domain.Language;
import com.phoenixdarts.toss.service.dto.LanguageDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Language} and its DTO {@link LanguageDTO}.
 */
@Mapper(componentModel = "spring")
public interface LanguageMapper extends EntityMapper<LanguageDTO, Language> {}
