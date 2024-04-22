package com.phoenixdarts.toss.service.impl;

import com.phoenixdarts.toss.domain.MatchFormatLeg;
import com.phoenixdarts.toss.repository.MatchFormatLegRepository;
import com.phoenixdarts.toss.service.MatchFormatLegService;
import com.phoenixdarts.toss.service.dto.MatchFormatLegDTO;
import com.phoenixdarts.toss.service.mapper.MatchFormatLegMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.phoenixdarts.toss.domain.MatchFormatLeg}.
 */
@Service
@Transactional
public class MatchFormatLegServiceImpl implements MatchFormatLegService {

    private final Logger log = LoggerFactory.getLogger(MatchFormatLegServiceImpl.class);

    private final MatchFormatLegRepository matchFormatLegRepository;

    private final MatchFormatLegMapper matchFormatLegMapper;

    public MatchFormatLegServiceImpl(MatchFormatLegRepository matchFormatLegRepository, MatchFormatLegMapper matchFormatLegMapper) {
        this.matchFormatLegRepository = matchFormatLegRepository;
        this.matchFormatLegMapper = matchFormatLegMapper;
    }

    @Override
    public MatchFormatLegDTO save(MatchFormatLegDTO matchFormatLegDTO) {
        log.debug("Request to save MatchFormatLeg : {}", matchFormatLegDTO);
        MatchFormatLeg matchFormatLeg = matchFormatLegMapper.toEntity(matchFormatLegDTO);
        matchFormatLeg = matchFormatLegRepository.save(matchFormatLeg);
        return matchFormatLegMapper.toDto(matchFormatLeg);
    }

    @Override
    public MatchFormatLegDTO update(MatchFormatLegDTO matchFormatLegDTO) {
        log.debug("Request to update MatchFormatLeg : {}", matchFormatLegDTO);
        MatchFormatLeg matchFormatLeg = matchFormatLegMapper.toEntity(matchFormatLegDTO);
        matchFormatLeg = matchFormatLegRepository.save(matchFormatLeg);
        return matchFormatLegMapper.toDto(matchFormatLeg);
    }

    @Override
    public Optional<MatchFormatLegDTO> partialUpdate(MatchFormatLegDTO matchFormatLegDTO) {
        log.debug("Request to partially update MatchFormatLeg : {}", matchFormatLegDTO);

        return matchFormatLegRepository
            .findById(matchFormatLegDTO.getId())
            .map(existingMatchFormatLeg -> {
                matchFormatLegMapper.partialUpdate(existingMatchFormatLeg, matchFormatLegDTO);

                return existingMatchFormatLeg;
            })
            .map(matchFormatLegRepository::save)
            .map(matchFormatLegMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MatchFormatLegDTO> findAll() {
        log.debug("Request to get all MatchFormatLegs");
        return matchFormatLegRepository
            .findAll()
            .stream()
            .map(matchFormatLegMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MatchFormatLegDTO> findOne(String id) {
        log.debug("Request to get MatchFormatLeg : {}", id);
        return matchFormatLegRepository.findById(id).map(matchFormatLegMapper::toDto);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete MatchFormatLeg : {}", id);
        matchFormatLegRepository.deleteById(id);
    }
}
