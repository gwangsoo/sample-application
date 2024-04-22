package com.phoenixdarts.toss.service.impl;

import com.phoenixdarts.toss.domain.MatchCall;
import com.phoenixdarts.toss.repository.MatchCallRepository;
import com.phoenixdarts.toss.service.MatchCallService;
import com.phoenixdarts.toss.service.dto.MatchCallDTO;
import com.phoenixdarts.toss.service.mapper.MatchCallMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.phoenixdarts.toss.domain.MatchCall}.
 */
@Service
@Transactional
public class MatchCallServiceImpl implements MatchCallService {

    private final Logger log = LoggerFactory.getLogger(MatchCallServiceImpl.class);

    private final MatchCallRepository matchCallRepository;

    private final MatchCallMapper matchCallMapper;

    public MatchCallServiceImpl(MatchCallRepository matchCallRepository, MatchCallMapper matchCallMapper) {
        this.matchCallRepository = matchCallRepository;
        this.matchCallMapper = matchCallMapper;
    }

    @Override
    public MatchCallDTO save(MatchCallDTO matchCallDTO) {
        log.debug("Request to save MatchCall : {}", matchCallDTO);
        MatchCall matchCall = matchCallMapper.toEntity(matchCallDTO);
        matchCall = matchCallRepository.save(matchCall);
        return matchCallMapper.toDto(matchCall);
    }

    @Override
    public MatchCallDTO update(MatchCallDTO matchCallDTO) {
        log.debug("Request to update MatchCall : {}", matchCallDTO);
        MatchCall matchCall = matchCallMapper.toEntity(matchCallDTO);
        matchCall = matchCallRepository.save(matchCall);
        return matchCallMapper.toDto(matchCall);
    }

    @Override
    public Optional<MatchCallDTO> partialUpdate(MatchCallDTO matchCallDTO) {
        log.debug("Request to partially update MatchCall : {}", matchCallDTO);

        return matchCallRepository
            .findById(matchCallDTO.getId())
            .map(existingMatchCall -> {
                matchCallMapper.partialUpdate(existingMatchCall, matchCallDTO);

                return existingMatchCall;
            })
            .map(matchCallRepository::save)
            .map(matchCallMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MatchCallDTO> findAll() {
        log.debug("Request to get all MatchCalls");
        return matchCallRepository.findAll().stream().map(matchCallMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MatchCallDTO> findOne(String id) {
        log.debug("Request to get MatchCall : {}", id);
        return matchCallRepository.findById(id).map(matchCallMapper::toDto);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete MatchCall : {}", id);
        matchCallRepository.deleteById(id);
    }
}
