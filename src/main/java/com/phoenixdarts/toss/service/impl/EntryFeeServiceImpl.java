package com.phoenixdarts.toss.service.impl;

import com.phoenixdarts.toss.domain.EntryFee;
import com.phoenixdarts.toss.repository.EntryFeeRepository;
import com.phoenixdarts.toss.service.EntryFeeService;
import com.phoenixdarts.toss.service.dto.EntryFeeDTO;
import com.phoenixdarts.toss.service.mapper.EntryFeeMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.phoenixdarts.toss.domain.EntryFee}.
 */
@Service
@Transactional
public class EntryFeeServiceImpl implements EntryFeeService {

    private final Logger log = LoggerFactory.getLogger(EntryFeeServiceImpl.class);

    private final EntryFeeRepository entryFeeRepository;

    private final EntryFeeMapper entryFeeMapper;

    public EntryFeeServiceImpl(EntryFeeRepository entryFeeRepository, EntryFeeMapper entryFeeMapper) {
        this.entryFeeRepository = entryFeeRepository;
        this.entryFeeMapper = entryFeeMapper;
    }

    @Override
    public EntryFeeDTO save(EntryFeeDTO entryFeeDTO) {
        log.debug("Request to save EntryFee : {}", entryFeeDTO);
        EntryFee entryFee = entryFeeMapper.toEntity(entryFeeDTO);
        entryFee = entryFeeRepository.save(entryFee);
        return entryFeeMapper.toDto(entryFee);
    }

    @Override
    public EntryFeeDTO update(EntryFeeDTO entryFeeDTO) {
        log.debug("Request to update EntryFee : {}", entryFeeDTO);
        EntryFee entryFee = entryFeeMapper.toEntity(entryFeeDTO);
        entryFee = entryFeeRepository.save(entryFee);
        return entryFeeMapper.toDto(entryFee);
    }

    @Override
    public Optional<EntryFeeDTO> partialUpdate(EntryFeeDTO entryFeeDTO) {
        log.debug("Request to partially update EntryFee : {}", entryFeeDTO);

        return entryFeeRepository
            .findById(entryFeeDTO.getId())
            .map(existingEntryFee -> {
                entryFeeMapper.partialUpdate(existingEntryFee, entryFeeDTO);

                return existingEntryFee;
            })
            .map(entryFeeRepository::save)
            .map(entryFeeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EntryFeeDTO> findAll() {
        log.debug("Request to get all EntryFees");
        return entryFeeRepository.findAll().stream().map(entryFeeMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EntryFeeDTO> findOne(String id) {
        log.debug("Request to get EntryFee : {}", id);
        return entryFeeRepository.findById(id).map(entryFeeMapper::toDto);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete EntryFee : {}", id);
        entryFeeRepository.deleteById(id);
    }
}
