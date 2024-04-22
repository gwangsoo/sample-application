package com.phoenixdarts.toss.service.impl;

import com.phoenixdarts.toss.domain.MachineArea;
import com.phoenixdarts.toss.repository.MachineAreaRepository;
import com.phoenixdarts.toss.service.MachineAreaService;
import com.phoenixdarts.toss.service.dto.MachineAreaDTO;
import com.phoenixdarts.toss.service.mapper.MachineAreaMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.phoenixdarts.toss.domain.MachineArea}.
 */
@Service
@Transactional
public class MachineAreaServiceImpl implements MachineAreaService {

    private final Logger log = LoggerFactory.getLogger(MachineAreaServiceImpl.class);

    private final MachineAreaRepository machineAreaRepository;

    private final MachineAreaMapper machineAreaMapper;

    public MachineAreaServiceImpl(MachineAreaRepository machineAreaRepository, MachineAreaMapper machineAreaMapper) {
        this.machineAreaRepository = machineAreaRepository;
        this.machineAreaMapper = machineAreaMapper;
    }

    @Override
    public MachineAreaDTO save(MachineAreaDTO machineAreaDTO) {
        log.debug("Request to save MachineArea : {}", machineAreaDTO);
        MachineArea machineArea = machineAreaMapper.toEntity(machineAreaDTO);
        machineArea = machineAreaRepository.save(machineArea);
        return machineAreaMapper.toDto(machineArea);
    }

    @Override
    public MachineAreaDTO update(MachineAreaDTO machineAreaDTO) {
        log.debug("Request to update MachineArea : {}", machineAreaDTO);
        MachineArea machineArea = machineAreaMapper.toEntity(machineAreaDTO);
        machineArea = machineAreaRepository.save(machineArea);
        return machineAreaMapper.toDto(machineArea);
    }

    @Override
    public Optional<MachineAreaDTO> partialUpdate(MachineAreaDTO machineAreaDTO) {
        log.debug("Request to partially update MachineArea : {}", machineAreaDTO);

        return machineAreaRepository
            .findById(machineAreaDTO.getId())
            .map(existingMachineArea -> {
                machineAreaMapper.partialUpdate(existingMachineArea, machineAreaDTO);

                return existingMachineArea;
            })
            .map(machineAreaRepository::save)
            .map(machineAreaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MachineAreaDTO> findAll() {
        log.debug("Request to get all MachineAreas");
        return machineAreaRepository.findAll().stream().map(machineAreaMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MachineAreaDTO> findOne(String id) {
        log.debug("Request to get MachineArea : {}", id);
        return machineAreaRepository.findById(id).map(machineAreaMapper::toDto);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete MachineArea : {}", id);
        machineAreaRepository.deleteById(id);
    }
}
