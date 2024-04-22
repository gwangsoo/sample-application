package com.phoenixdarts.toss.service.impl;

import com.phoenixdarts.toss.domain.Competition;
import com.phoenixdarts.toss.repository.CompetitionRepository;
import com.phoenixdarts.toss.service.CompetitionService;
import com.phoenixdarts.toss.service.dto.CompetitionDTO;
import com.phoenixdarts.toss.service.mapper.CompetitionMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.phoenixdarts.toss.domain.Competition}.
 */
@Service
@Transactional
public class CompetitionServiceImpl implements CompetitionService {

    private final Logger log = LoggerFactory.getLogger(CompetitionServiceImpl.class);

    private final CompetitionRepository competitionRepository;

    private final CompetitionMapper competitionMapper;

    public CompetitionServiceImpl(CompetitionRepository competitionRepository, CompetitionMapper competitionMapper) {
        this.competitionRepository = competitionRepository;
        this.competitionMapper = competitionMapper;
    }

    @Override
    public CompetitionDTO save(CompetitionDTO competitionDTO) {
        log.debug("Request to save Competition : {}", competitionDTO);
        Competition competition = competitionMapper.toEntity(competitionDTO);
        competition = competitionRepository.save(competition);
        return competitionMapper.toDto(competition);
    }

    @Override
    public CompetitionDTO update(CompetitionDTO competitionDTO) {
        log.debug("Request to update Competition : {}", competitionDTO);
        Competition competition = competitionMapper.toEntity(competitionDTO);
        competition = competitionRepository.save(competition);
        return competitionMapper.toDto(competition);
    }

    @Override
    public Optional<CompetitionDTO> partialUpdate(CompetitionDTO competitionDTO) {
        log.debug("Request to partially update Competition : {}", competitionDTO);

        return competitionRepository
            .findById(competitionDTO.getId())
            .map(existingCompetition -> {
                competitionMapper.partialUpdate(existingCompetition, competitionDTO);

                return existingCompetition;
            })
            .map(competitionRepository::save)
            .map(competitionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CompetitionDTO> findAll() {
        log.debug("Request to get all Competitions");
        return competitionRepository.findAll().stream().map(competitionMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CompetitionDTO> findOne(String id) {
        log.debug("Request to get Competition : {}", id);
        return competitionRepository.findById(id).map(competitionMapper::toDto);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Competition : {}", id);
        competitionRepository.deleteById(id);
    }
}
