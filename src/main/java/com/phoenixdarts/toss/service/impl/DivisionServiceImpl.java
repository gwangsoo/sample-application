package com.phoenixdarts.toss.backend.service.impl;

import com.phoenixdarts.toss.backend.domain.Division;
import com.phoenixdarts.toss.backend.repository.DivisionRepository;
import com.phoenixdarts.toss.backend.service.DivisionService;
import com.phoenixdarts.toss.backend.service.dto.DivisionDTO;
import com.phoenixdarts.toss.backend.service.mapper.DivisionMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.phoenixdarts.toss.backend.domain.Division}.
 */
@Service
@Transactional
public class DivisionServiceImpl implements DivisionService {

    private final Logger log = LoggerFactory.getLogger(DivisionServiceImpl.class);

    private final DivisionRepository divisionRepository;

    private final DivisionMapper divisionMapper;

    public DivisionServiceImpl(DivisionRepository divisionRepository, DivisionMapper divisionMapper) {
        this.divisionRepository = divisionRepository;
        this.divisionMapper = divisionMapper;
    }

    @Override
    public DivisionDTO save(DivisionDTO divisionDTO) {
        log.debug("Request to save Division : {}", divisionDTO);
        Division division = divisionMapper.toEntity(divisionDTO);
        division = divisionRepository.save(division);
        return divisionMapper.toDto(division);
    }

    @Override
    public DivisionDTO update(DivisionDTO divisionDTO) {
        log.debug("Request to update Division : {}", divisionDTO);
        Division division = divisionMapper.toEntity(divisionDTO);
        division = divisionRepository.save(division);
        return divisionMapper.toDto(division);
    }

    @Override
    public Optional<DivisionDTO> partialUpdate(DivisionDTO divisionDTO) {
        log.debug("Request to partially update Division : {}", divisionDTO);

        return divisionRepository
            .findById(divisionDTO.getId())
            .map(existingDivision -> {
                divisionMapper.partialUpdate(existingDivision, divisionDTO);

                return existingDivision;
            })
            .map(divisionRepository::save)
            .map(divisionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DivisionDTO> findAll() {
        log.debug("Request to get all Divisions");
        return divisionRepository.findAll().stream().map(divisionMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DivisionDTO> findOne(String id) {
        log.debug("Request to get Division : {}", id);
        return divisionRepository.findById(id).map(divisionMapper::toDto);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Division : {}", id);
        divisionRepository.deleteById(id);
    }
}
