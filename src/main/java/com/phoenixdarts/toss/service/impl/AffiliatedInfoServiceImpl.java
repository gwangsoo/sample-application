package com.phoenixdarts.toss.service.impl;

import com.phoenixdarts.toss.domain.AffiliatedInfo;
import com.phoenixdarts.toss.repository.AffiliatedInfoRepository;
import com.phoenixdarts.toss.service.AffiliatedInfoService;
import com.phoenixdarts.toss.service.dto.AffiliatedInfoDTO;
import com.phoenixdarts.toss.service.mapper.AffiliatedInfoMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.phoenixdarts.toss.domain.AffiliatedInfo}.
 */
@Service
@Transactional
public class AffiliatedInfoServiceImpl implements AffiliatedInfoService {

    private final Logger log = LoggerFactory.getLogger(AffiliatedInfoServiceImpl.class);

    private final AffiliatedInfoRepository affiliatedInfoRepository;

    private final AffiliatedInfoMapper affiliatedInfoMapper;

    public AffiliatedInfoServiceImpl(AffiliatedInfoRepository affiliatedInfoRepository, AffiliatedInfoMapper affiliatedInfoMapper) {
        this.affiliatedInfoRepository = affiliatedInfoRepository;
        this.affiliatedInfoMapper = affiliatedInfoMapper;
    }

    @Override
    public AffiliatedInfoDTO save(AffiliatedInfoDTO affiliatedInfoDTO) {
        log.debug("Request to save AffiliatedInfo : {}", affiliatedInfoDTO);
        AffiliatedInfo affiliatedInfo = affiliatedInfoMapper.toEntity(affiliatedInfoDTO);
        affiliatedInfo = affiliatedInfoRepository.save(affiliatedInfo);
        return affiliatedInfoMapper.toDto(affiliatedInfo);
    }

    @Override
    public AffiliatedInfoDTO update(AffiliatedInfoDTO affiliatedInfoDTO) {
        log.debug("Request to update AffiliatedInfo : {}", affiliatedInfoDTO);
        AffiliatedInfo affiliatedInfo = affiliatedInfoMapper.toEntity(affiliatedInfoDTO);
        affiliatedInfo = affiliatedInfoRepository.save(affiliatedInfo);
        return affiliatedInfoMapper.toDto(affiliatedInfo);
    }

    @Override
    public Optional<AffiliatedInfoDTO> partialUpdate(AffiliatedInfoDTO affiliatedInfoDTO) {
        log.debug("Request to partially update AffiliatedInfo : {}", affiliatedInfoDTO);

        return affiliatedInfoRepository
            .findById(affiliatedInfoDTO.getId())
            .map(existingAffiliatedInfo -> {
                affiliatedInfoMapper.partialUpdate(existingAffiliatedInfo, affiliatedInfoDTO);

                return existingAffiliatedInfo;
            })
            .map(affiliatedInfoRepository::save)
            .map(affiliatedInfoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AffiliatedInfoDTO> findAll() {
        log.debug("Request to get all AffiliatedInfos");
        return affiliatedInfoRepository
            .findAll()
            .stream()
            .map(affiliatedInfoMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AffiliatedInfoDTO> findOne(String id) {
        log.debug("Request to get AffiliatedInfo : {}", id);
        return affiliatedInfoRepository.findById(id).map(affiliatedInfoMapper::toDto);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete AffiliatedInfo : {}", id);
        affiliatedInfoRepository.deleteById(id);
    }
}
