package com.phoenixdarts.toss.service.impl;

import com.phoenixdarts.toss.domain.Entry;
import com.phoenixdarts.toss.repository.EntryRepository;
import com.phoenixdarts.toss.service.EntryService;
import com.phoenixdarts.toss.service.dto.EntryDTO;
import com.phoenixdarts.toss.service.mapper.EntryMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.phoenixdarts.toss.domain.Entry}.
 */
@Service
@Transactional
public class EntryServiceImpl implements EntryService {

    private final Logger log = LoggerFactory.getLogger(EntryServiceImpl.class);

    private final EntryRepository entryRepository;

    private final EntryMapper entryMapper;

    public EntryServiceImpl(EntryRepository entryRepository, EntryMapper entryMapper) {
        this.entryRepository = entryRepository;
        this.entryMapper = entryMapper;
    }

    @Override
    public EntryDTO save(EntryDTO entryDTO) {
        log.debug("Request to save Entry : {}", entryDTO);
        Entry entry = entryMapper.toEntity(entryDTO);
        entry = entryRepository.save(entry);
        return entryMapper.toDto(entry);
    }

    @Override
    public EntryDTO update(EntryDTO entryDTO) {
        log.debug("Request to update Entry : {}", entryDTO);
        Entry entry = entryMapper.toEntity(entryDTO);
        entry = entryRepository.save(entry);
        return entryMapper.toDto(entry);
    }

    @Override
    public Optional<EntryDTO> partialUpdate(EntryDTO entryDTO) {
        log.debug("Request to partially update Entry : {}", entryDTO);

        return entryRepository
            .findById(entryDTO.getId())
            .map(existingEntry -> {
                entryMapper.partialUpdate(existingEntry, entryDTO);

                return existingEntry;
            })
            .map(entryRepository::save)
            .map(entryMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EntryDTO> findAll() {
        log.debug("Request to get all Entries");
        return entryRepository.findAll().stream().map(entryMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EntryDTO> findOne(String id) {
        log.debug("Request to get Entry : {}", id);
        return entryRepository.findById(id).map(entryMapper::toDto);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Entry : {}", id);
        entryRepository.deleteById(id);
    }
}
