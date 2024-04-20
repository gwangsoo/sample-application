package com.example.bfi.service.impl;

import com.example.bfi.domain.Evse;
import com.example.bfi.repository.EvseRepository;
import com.example.bfi.service.EvseService;
import com.example.bfi.service.dto.EvseDTO;
import com.example.bfi.service.mapper.EvseMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link com.example.bfi.domain.Evse}.
 */
@Service
public class EvseServiceImpl implements EvseService {

    private final Logger log = LoggerFactory.getLogger(EvseServiceImpl.class);

    private final EvseRepository evseRepository;

    private final EvseMapper evseMapper;

    public EvseServiceImpl(EvseRepository evseRepository, EvseMapper evseMapper) {
        this.evseRepository = evseRepository;
        this.evseMapper = evseMapper;
    }

    @Override
    public EvseDTO save(EvseDTO evseDTO) {
        log.debug("Request to save Evse : {}", evseDTO);
        Evse evse = evseMapper.toEntity(evseDTO);
        evse = evseRepository.save(evse);
        return evseMapper.toDto(evse);
    }

    @Override
    public EvseDTO update(EvseDTO evseDTO) {
        log.debug("Request to update Evse : {}", evseDTO);
        Evse evse = evseMapper.toEntity(evseDTO);
        evse = evseRepository.save(evse);
        return evseMapper.toDto(evse);
    }

    @Override
    public Optional<EvseDTO> partialUpdate(EvseDTO evseDTO) {
        log.debug("Request to partially update Evse : {}", evseDTO);

        return evseRepository
            .findById(evseDTO.getId())
            .map(existingEvse -> {
                evseMapper.partialUpdate(existingEvse, evseDTO);

                return existingEvse;
            })
            .map(evseRepository::save)
            .map(evseMapper::toDto);
    }

    @Override
    public List<EvseDTO> findAll() {
        log.debug("Request to get all Evses");
        return evseRepository.findAll().stream().map(evseMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public Optional<EvseDTO> findOne(String id) {
        log.debug("Request to get Evse : {}", id);
        return evseRepository.findById(id).map(evseMapper::toDto);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Evse : {}", id);
        evseRepository.deleteById(id);
    }
}
