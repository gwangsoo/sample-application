package com.phoenixdarts.toss.backend.service.impl;

import com.phoenixdarts.toss.backend.domain.MatchTeam;
import com.phoenixdarts.toss.backend.repository.MatchTeamRepository;
import com.phoenixdarts.toss.backend.service.MatchTeamService;
import com.phoenixdarts.toss.backend.service.dto.MatchTeamDTO;
import com.phoenixdarts.toss.backend.service.mapper.MatchTeamMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.phoenixdarts.toss.backend.domain.MatchTeam}.
 */
@Service
@Transactional
public class MatchTeamServiceImpl implements MatchTeamService {

    private final Logger log = LoggerFactory.getLogger(MatchTeamServiceImpl.class);

    private final MatchTeamRepository matchTeamRepository;

    private final MatchTeamMapper matchTeamMapper;

    public MatchTeamServiceImpl(MatchTeamRepository matchTeamRepository, MatchTeamMapper matchTeamMapper) {
        this.matchTeamRepository = matchTeamRepository;
        this.matchTeamMapper = matchTeamMapper;
    }

    @Override
    public MatchTeamDTO save(MatchTeamDTO matchTeamDTO) {
        log.debug("Request to save MatchTeam : {}", matchTeamDTO);
        MatchTeam matchTeam = matchTeamMapper.toEntity(matchTeamDTO);
        matchTeam = matchTeamRepository.save(matchTeam);
        return matchTeamMapper.toDto(matchTeam);
    }

    @Override
    public MatchTeamDTO update(MatchTeamDTO matchTeamDTO) {
        log.debug("Request to update MatchTeam : {}", matchTeamDTO);
        MatchTeam matchTeam = matchTeamMapper.toEntity(matchTeamDTO);
        matchTeam = matchTeamRepository.save(matchTeam);
        return matchTeamMapper.toDto(matchTeam);
    }

    @Override
    public Optional<MatchTeamDTO> partialUpdate(MatchTeamDTO matchTeamDTO) {
        log.debug("Request to partially update MatchTeam : {}", matchTeamDTO);

        return matchTeamRepository
            .findById(matchTeamDTO.getId())
            .map(existingMatchTeam -> {
                matchTeamMapper.partialUpdate(existingMatchTeam, matchTeamDTO);

                return existingMatchTeam;
            })
            .map(matchTeamRepository::save)
            .map(matchTeamMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MatchTeamDTO> findAll() {
        log.debug("Request to get all MatchTeams");
        return matchTeamRepository.findAll().stream().map(matchTeamMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MatchTeamDTO> findOne(String id) {
        log.debug("Request to get MatchTeam : {}", id);
        return matchTeamRepository.findById(id).map(matchTeamMapper::toDto);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete MatchTeam : {}", id);
        matchTeamRepository.deleteById(id);
    }
}
