package com.phoenixdarts.toss.service.impl;

import com.phoenixdarts.toss.domain.Region;
import com.phoenixdarts.toss.repository.RegionRepository;
import com.phoenixdarts.toss.service.RegionService;
import com.phoenixdarts.toss.service.dto.RegionDTO;
import com.phoenixdarts.toss.service.mapper.RegionMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.phoenixdarts.toss.domain.Region}.
 */
@Service
@Transactional
public class RegionServiceImpl implements RegionService {

    private final Logger log = LoggerFactory.getLogger(RegionServiceImpl.class);

    private final RegionRepository regionRepository;

    private final RegionMapper regionMapper;

    public RegionServiceImpl(RegionRepository regionRepository, RegionMapper regionMapper) {
        this.regionRepository = regionRepository;
        this.regionMapper = regionMapper;
    }

    @Override
    public RegionDTO save(RegionDTO regionDTO) {
        log.debug("Request to save Region : {}", regionDTO);
        Region region = regionMapper.toEntity(regionDTO);
        region = regionRepository.save(region);
        return regionMapper.toDto(region);
    }

    @Override
    public RegionDTO update(RegionDTO regionDTO) {
        log.debug("Request to update Region : {}", regionDTO);
        Region region = regionMapper.toEntity(regionDTO);
        region = regionRepository.save(region);
        return regionMapper.toDto(region);
    }

    @Override
    public Optional<RegionDTO> partialUpdate(RegionDTO regionDTO) {
        log.debug("Request to partially update Region : {}", regionDTO);

        return regionRepository
            .findById(regionDTO.getId())
            .map(existingRegion -> {
                regionMapper.partialUpdate(existingRegion, regionDTO);

                return existingRegion;
            })
            .map(regionRepository::save)
            .map(regionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RegionDTO> findAll() {
        log.debug("Request to get all Regions");
        return regionRepository.findAll().stream().map(regionMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RegionDTO> findOne(String id) {
        log.debug("Request to get Region : {}", id);
        return regionRepository.findById(id).map(regionMapper::toDto);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Region : {}", id);
        regionRepository.deleteById(id);
    }
}
