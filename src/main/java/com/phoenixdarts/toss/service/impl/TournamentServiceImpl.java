package com.phoenixdarts.toss.backend.service.impl;

import com.phoenixdarts.toss.backend.domain.Tournament;
import com.phoenixdarts.toss.backend.repository.TournamentRepository;
import com.phoenixdarts.toss.backend.service.TournamentService;
import com.phoenixdarts.toss.backend.service.dto.TournamentDTO;
import com.phoenixdarts.toss.backend.service.mapper.TournamentMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.phoenixdarts.toss.backend.domain.Tournament}.
 */
@Service
@Transactional
public class TournamentServiceImpl implements TournamentService {

    private final Logger log = LoggerFactory.getLogger(TournamentServiceImpl.class);

    private final TournamentRepository tournamentRepository;

    private final TournamentMapper tournamentMapper;

    public TournamentServiceImpl(TournamentRepository tournamentRepository, TournamentMapper tournamentMapper) {
        this.tournamentRepository = tournamentRepository;
        this.tournamentMapper = tournamentMapper;
    }

    @Override
    public TournamentDTO save(TournamentDTO tournamentDTO) {
        log.debug("Request to save Tournament : {}", tournamentDTO);
        Tournament tournament = tournamentMapper.toEntity(tournamentDTO);
        tournament = tournamentRepository.save(tournament);
        return tournamentMapper.toDto(tournament);
    }

    @Override
    public TournamentDTO update(TournamentDTO tournamentDTO) {
        log.debug("Request to update Tournament : {}", tournamentDTO);
        Tournament tournament = tournamentMapper.toEntity(tournamentDTO);
        tournament = tournamentRepository.save(tournament);
        return tournamentMapper.toDto(tournament);
    }

    @Override
    public Optional<TournamentDTO> partialUpdate(TournamentDTO tournamentDTO) {
        log.debug("Request to partially update Tournament : {}", tournamentDTO);

        return tournamentRepository
            .findById(tournamentDTO.getId())
            .map(existingTournament -> {
                tournamentMapper.partialUpdate(existingTournament, tournamentDTO);

                return existingTournament;
            })
            .map(tournamentRepository::save)
            .map(tournamentMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TournamentDTO> findAll() {
        log.debug("Request to get all Tournaments");
        return tournamentRepository.findAll().stream().map(tournamentMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TournamentDTO> findOne(String id) {
        log.debug("Request to get Tournament : {}", id);
        return tournamentRepository.findById(id).map(tournamentMapper::toDto);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Tournament : {}", id);
        tournamentRepository.deleteById(id);
    }
}
