package com.phoenixdarts.toss.backend.service.impl;

import com.phoenixdarts.toss.backend.domain.MatchScore;
import com.phoenixdarts.toss.backend.repository.MatchScoreRepository;
import com.phoenixdarts.toss.backend.service.MatchScoreService;
import com.phoenixdarts.toss.backend.service.dto.MatchScoreDTO;
import com.phoenixdarts.toss.backend.service.mapper.MatchScoreMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.phoenixdarts.toss.backend.domain.MatchScore}.
 */
@Service
@Transactional
public class MatchScoreServiceImpl implements MatchScoreService {

    private final Logger log = LoggerFactory.getLogger(MatchScoreServiceImpl.class);

    private final MatchScoreRepository matchScoreRepository;

    private final MatchScoreMapper matchScoreMapper;

    public MatchScoreServiceImpl(MatchScoreRepository matchScoreRepository, MatchScoreMapper matchScoreMapper) {
        this.matchScoreRepository = matchScoreRepository;
        this.matchScoreMapper = matchScoreMapper;
    }

    @Override
    public MatchScoreDTO save(MatchScoreDTO matchScoreDTO) {
        log.debug("Request to save MatchScore : {}", matchScoreDTO);
        MatchScore matchScore = matchScoreMapper.toEntity(matchScoreDTO);
        matchScore = matchScoreRepository.save(matchScore);
        return matchScoreMapper.toDto(matchScore);
    }

    @Override
    public MatchScoreDTO update(MatchScoreDTO matchScoreDTO) {
        log.debug("Request to update MatchScore : {}", matchScoreDTO);
        MatchScore matchScore = matchScoreMapper.toEntity(matchScoreDTO);
        matchScore = matchScoreRepository.save(matchScore);
        return matchScoreMapper.toDto(matchScore);
    }

    @Override
    public Optional<MatchScoreDTO> partialUpdate(MatchScoreDTO matchScoreDTO) {
        log.debug("Request to partially update MatchScore : {}", matchScoreDTO);

        return matchScoreRepository
            .findById(matchScoreDTO.getId())
            .map(existingMatchScore -> {
                matchScoreMapper.partialUpdate(existingMatchScore, matchScoreDTO);

                return existingMatchScore;
            })
            .map(matchScoreRepository::save)
            .map(matchScoreMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MatchScoreDTO> findAll() {
        log.debug("Request to get all MatchScores");
        return matchScoreRepository.findAll().stream().map(matchScoreMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MatchScoreDTO> findOne(String id) {
        log.debug("Request to get MatchScore : {}", id);
        return matchScoreRepository.findById(id).map(matchScoreMapper::toDto);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete MatchScore : {}", id);
        matchScoreRepository.deleteById(id);
    }
}
