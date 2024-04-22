package com.phoenixdarts.toss.service.impl;

import com.phoenixdarts.toss.domain.MatchFormatSet;
import com.phoenixdarts.toss.repository.MatchFormatSetRepository;
import com.phoenixdarts.toss.service.MatchFormatSetService;
import com.phoenixdarts.toss.service.dto.MatchFormatSetDTO;
import com.phoenixdarts.toss.service.mapper.MatchFormatSetMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.phoenixdarts.toss.domain.MatchFormatSet}.
 */
@Service
@Transactional
public class MatchFormatSetServiceImpl implements MatchFormatSetService {

    private final Logger log = LoggerFactory.getLogger(MatchFormatSetServiceImpl.class);

    private final MatchFormatSetRepository matchFormatSetRepository;

    private final MatchFormatSetMapper matchFormatSetMapper;

    public MatchFormatSetServiceImpl(MatchFormatSetRepository matchFormatSetRepository, MatchFormatSetMapper matchFormatSetMapper) {
        this.matchFormatSetRepository = matchFormatSetRepository;
        this.matchFormatSetMapper = matchFormatSetMapper;
    }

    @Override
    public MatchFormatSetDTO save(MatchFormatSetDTO matchFormatSetDTO) {
        log.debug("Request to save MatchFormatSet : {}", matchFormatSetDTO);
        MatchFormatSet matchFormatSet = matchFormatSetMapper.toEntity(matchFormatSetDTO);
        matchFormatSet = matchFormatSetRepository.save(matchFormatSet);
        return matchFormatSetMapper.toDto(matchFormatSet);
    }

    @Override
    public MatchFormatSetDTO update(MatchFormatSetDTO matchFormatSetDTO) {
        log.debug("Request to update MatchFormatSet : {}", matchFormatSetDTO);
        MatchFormatSet matchFormatSet = matchFormatSetMapper.toEntity(matchFormatSetDTO);
        matchFormatSet = matchFormatSetRepository.save(matchFormatSet);
        return matchFormatSetMapper.toDto(matchFormatSet);
    }

    @Override
    public Optional<MatchFormatSetDTO> partialUpdate(MatchFormatSetDTO matchFormatSetDTO) {
        log.debug("Request to partially update MatchFormatSet : {}", matchFormatSetDTO);

        return matchFormatSetRepository
            .findById(matchFormatSetDTO.getId())
            .map(existingMatchFormatSet -> {
                matchFormatSetMapper.partialUpdate(existingMatchFormatSet, matchFormatSetDTO);

                return existingMatchFormatSet;
            })
            .map(matchFormatSetRepository::save)
            .map(matchFormatSetMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MatchFormatSetDTO> findAll() {
        log.debug("Request to get all MatchFormatSets");
        return matchFormatSetRepository
            .findAll()
            .stream()
            .map(matchFormatSetMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MatchFormatSetDTO> findOne(String id) {
        log.debug("Request to get MatchFormatSet : {}", id);
        return matchFormatSetRepository.findById(id).map(matchFormatSetMapper::toDto);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete MatchFormatSet : {}", id);
        matchFormatSetRepository.deleteById(id);
    }
}
