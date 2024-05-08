package com.phoenixdarts.toss.backend.service.impl;

import com.phoenixdarts.toss.backend.domain.Match;
import com.phoenixdarts.toss.backend.repository.MatchRepository;
import com.phoenixdarts.toss.backend.service.MatchService;
import com.phoenixdarts.toss.backend.service.dto.MatchDTO;
import com.phoenixdarts.toss.backend.service.mapper.MatchMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.phoenixdarts.toss.backend.domain.Match}.
 */
@Service
@Transactional
public class MatchServiceImpl implements MatchService {

    private final Logger log = LoggerFactory.getLogger(MatchServiceImpl.class);

    private final MatchRepository matchRepository;

    private final MatchMapper matchMapper;

    public MatchServiceImpl(MatchRepository matchRepository, MatchMapper matchMapper) {
        this.matchRepository = matchRepository;
        this.matchMapper = matchMapper;
    }

    @Override
    public MatchDTO save(MatchDTO matchDTO) {
        log.debug("Request to save Match : {}", matchDTO);
        Match match = matchMapper.toEntity(matchDTO);
        match = matchRepository.save(match);
        return matchMapper.toDto(match);
    }

    @Override
    public MatchDTO update(MatchDTO matchDTO) {
        log.debug("Request to update Match : {}", matchDTO);
        Match match = matchMapper.toEntity(matchDTO);
        match = matchRepository.save(match);
        return matchMapper.toDto(match);
    }

    @Override
    public Optional<MatchDTO> partialUpdate(MatchDTO matchDTO) {
        log.debug("Request to partially update Match : {}", matchDTO);

        return matchRepository
            .findById(matchDTO.getId())
            .map(existingMatch -> {
                matchMapper.partialUpdate(existingMatch, matchDTO);

                return existingMatch;
            })
            .map(matchRepository::save)
            .map(matchMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MatchDTO> findAll() {
        log.debug("Request to get all Matches");
        return matchRepository.findAll().stream().map(matchMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MatchDTO> findOne(String id) {
        log.debug("Request to get Match : {}", id);
        return matchRepository.findById(id).map(matchMapper::toDto);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Match : {}", id);
        matchRepository.deleteById(id);
    }
}
