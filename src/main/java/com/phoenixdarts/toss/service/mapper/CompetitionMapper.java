package com.phoenixdarts.toss.backend.service.mapper;

import com.phoenixdarts.toss.backend.domain.Competition;
import com.phoenixdarts.toss.backend.domain.Country;
import com.phoenixdarts.toss.backend.domain.EntryFee;
import com.phoenixdarts.toss.backend.domain.FileInfo;
import com.phoenixdarts.toss.backend.domain.Operator;
import com.phoenixdarts.toss.backend.domain.Reward;
import com.phoenixdarts.toss.backend.service.dto.CompetitionDTO;
import com.phoenixdarts.toss.backend.service.dto.CountryDTO;
import com.phoenixdarts.toss.backend.service.dto.EntryFeeDTO;
import com.phoenixdarts.toss.backend.service.dto.FileInfoDTO;
import com.phoenixdarts.toss.backend.service.dto.OperatorDTO;
import com.phoenixdarts.toss.backend.service.dto.RewardDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Competition} and its DTO {@link CompetitionDTO}.
 */
@Mapper(componentModel = "spring")
public interface CompetitionMapper extends EntityMapper<CompetitionDTO, Competition> {
    @Mapping(target = "competitionImage", source = "competitionImage", qualifiedByName = "fileInfoId")
    @Mapping(target = "reward", source = "reward", qualifiedByName = "rewardId")
    @Mapping(target = "country", source = "country", qualifiedByName = "countryId")
    @Mapping(target = "operator", source = "operator", qualifiedByName = "operatorId")
    @Mapping(target = "entryFee", source = "entryFee", qualifiedByName = "entryFeeId")
    CompetitionDTO toDto(Competition s);

    @Named("fileInfoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FileInfoDTO toDtoFileInfoId(FileInfo fileInfo);

    @Named("rewardId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    RewardDTO toDtoRewardId(Reward reward);

    @Named("countryId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CountryDTO toDtoCountryId(Country country);

    @Named("operatorId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    OperatorDTO toDtoOperatorId(Operator operator);

    @Named("entryFeeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EntryFeeDTO toDtoEntryFeeId(EntryFee entryFee);
}
