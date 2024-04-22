package com.phoenixdarts.toss.service.impl;

import com.phoenixdarts.toss.domain.MatchFormat;
import com.phoenixdarts.toss.repository.MatchFormatRepository;
import com.phoenixdarts.toss.service.MatchFormatService;
import com.phoenixdarts.toss.service.dto.MatchFormatDTO;
import com.phoenixdarts.toss.service.mapper.MatchFormatMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.phoenixdarts.toss.domain.MatchFormat}.
 */
@Service
@Transactional
public class MatchFormatServiceImpl implements MatchFormatService {

    private final Logger log = LoggerFactory.getLogger(MatchFormatServiceImpl.class);

    private final MatchFormatRepository matchFormatRepository;

    private final MatchFormatMapper matchFormatMapper;

    public MatchFormatServiceImpl(MatchFormatRepository matchFormatRepository, MatchFormatMapper matchFormatMapper) {
        this.matchFormatRepository = matchFormatRepository;
        this.matchFormatMapper = matchFormatMapper;
    }

    @Override
    public MatchFormatDTO save(MatchFormatDTO matchFormatDTO) {
        log.debug("Request to save MatchFormat : {}", matchFormatDTO);
        MatchFormat matchFormat = matchFormatMapper.toEntity(matchFormatDTO);
        matchFormat = matchFormatRepository.save(matchFormat);
        return matchFormatMapper.toDto(matchFormat);
    }

    @Override
    public MatchFormatDTO update(MatchFormatDTO matchFormatDTO) {
        log.debug("Request to update MatchFormat : {}", matchFormatDTO);
        MatchFormat matchFormat = matchFormatMapper.toEntity(matchFormatDTO);
        matchFormat = matchFormatRepository.save(matchFormat);
        return matchFormatMapper.toDto(matchFormat);
    }

    @Override
    public Optional<MatchFormatDTO> partialUpdate(MatchFormatDTO matchFormatDTO) {
        log.debug("Request to partially update MatchFormat : {}", matchFormatDTO);

        return matchFormatRepository
            .findById(matchFormatDTO.getId())
            .map(existingMatchFormat -> {
                matchFormatMapper.partialUpdate(existingMatchFormat, matchFormatDTO);

                return existingMatchFormat;
            })
            .map(matchFormatRepository::save)
            .map(matchFormatMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MatchFormatDTO> findAll() {
        log.debug("Request to get all MatchFormats");
        return matchFormatRepository.findAll().stream().map(matchFormatMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MatchFormatDTO> findOne(String id) {
        log.debug("Request to get MatchFormat : {}", id);
        return matchFormatRepository.findById(id).map(matchFormatMapper::toDto);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete MatchFormat : {}", id);
        matchFormatRepository.deleteById(id);
    }
}
