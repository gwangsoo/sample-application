package com.phoenixdarts.toss.service.impl;

import com.phoenixdarts.toss.domain.Machine;
import com.phoenixdarts.toss.repository.MachineRepository;
import com.phoenixdarts.toss.service.MachineService;
import com.phoenixdarts.toss.service.dto.MachineDTO;
import com.phoenixdarts.toss.service.mapper.MachineMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.phoenixdarts.toss.domain.Machine}.
 */
@Service
@Transactional
public class MachineServiceImpl implements MachineService {

    private final Logger log = LoggerFactory.getLogger(MachineServiceImpl.class);

    private final MachineRepository machineRepository;

    private final MachineMapper machineMapper;

    public MachineServiceImpl(MachineRepository machineRepository, MachineMapper machineMapper) {
        this.machineRepository = machineRepository;
        this.machineMapper = machineMapper;
    }

    @Override
    public MachineDTO save(MachineDTO machineDTO) {
        log.debug("Request to save Machine : {}", machineDTO);
        Machine machine = machineMapper.toEntity(machineDTO);
        machine = machineRepository.save(machine);
        return machineMapper.toDto(machine);
    }

    @Override
    public MachineDTO update(MachineDTO machineDTO) {
        log.debug("Request to update Machine : {}", machineDTO);
        Machine machine = machineMapper.toEntity(machineDTO);
        machine = machineRepository.save(machine);
        return machineMapper.toDto(machine);
    }

    @Override
    public Optional<MachineDTO> partialUpdate(MachineDTO machineDTO) {
        log.debug("Request to partially update Machine : {}", machineDTO);

        return machineRepository
            .findById(machineDTO.getId())
            .map(existingMachine -> {
                machineMapper.partialUpdate(existingMachine, machineDTO);

                return existingMachine;
            })
            .map(machineRepository::save)
            .map(machineMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MachineDTO> findAll() {
        log.debug("Request to get all Machines");
        return machineRepository.findAll().stream().map(machineMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MachineDTO> findOne(String id) {
        log.debug("Request to get Machine : {}", id);
        return machineRepository.findById(id).map(machineMapper::toDto);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Machine : {}", id);
        machineRepository.deleteById(id);
    }
}
