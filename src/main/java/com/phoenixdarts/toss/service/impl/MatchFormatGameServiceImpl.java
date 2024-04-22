package com.phoenixdarts.toss.service.impl;

import com.phoenixdarts.toss.domain.MatchFormatGame;
import com.phoenixdarts.toss.repository.MatchFormatGameRepository;
import com.phoenixdarts.toss.service.MatchFormatGameService;
import com.phoenixdarts.toss.service.dto.MatchFormatGameDTO;
import com.phoenixdarts.toss.service.mapper.MatchFormatGameMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.phoenixdarts.toss.domain.MatchFormatGame}.
 */
@Service
@Transactional
public class MatchFormatGameServiceImpl implements MatchFormatGameService {

    private final Logger log = LoggerFactory.getLogger(MatchFormatGameServiceImpl.class);

    private final MatchFormatGameRepository matchFormatGameRepository;

    private final MatchFormatGameMapper matchFormatGameMapper;

    public MatchFormatGameServiceImpl(MatchFormatGameRepository matchFormatGameRepository, MatchFormatGameMapper matchFormatGameMapper) {
        this.matchFormatGameRepository = matchFormatGameRepository;
        this.matchFormatGameMapper = matchFormatGameMapper;
    }

    @Override
    public MatchFormatGameDTO save(MatchFormatGameDTO matchFormatGameDTO) {
        log.debug("Request to save MatchFormatGame : {}", matchFormatGameDTO);
        MatchFormatGame matchFormatGame = matchFormatGameMapper.toEntity(matchFormatGameDTO);
        matchFormatGame = matchFormatGameRepository.save(matchFormatGame);
        return matchFormatGameMapper.toDto(matchFormatGame);
    }

    @Override
    public MatchFormatGameDTO update(MatchFormatGameDTO matchFormatGameDTO) {
        log.debug("Request to update MatchFormatGame : {}", matchFormatGameDTO);
        MatchFormatGame matchFormatGame = matchFormatGameMapper.toEntity(matchFormatGameDTO);
        matchFormatGame = matchFormatGameRepository.save(matchFormatGame);
        return matchFormatGameMapper.toDto(matchFormatGame);
    }

    @Override
    public Optional<MatchFormatGameDTO> partialUpdate(MatchFormatGameDTO matchFormatGameDTO) {
        log.debug("Request to partially update MatchFormatGame : {}", matchFormatGameDTO);

        return matchFormatGameRepository
            .findById(matchFormatGameDTO.getId())
            .map(existingMatchFormatGame -> {
                matchFormatGameMapper.partialUpdate(existingMatchFormatGame, matchFormatGameDTO);

                return existingMatchFormatGame;
            })
            .map(matchFormatGameRepository::save)
            .map(matchFormatGameMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MatchFormatGameDTO> findAll() {
        log.debug("Request to get all MatchFormatGames");
        return matchFormatGameRepository
            .findAll()
            .stream()
            .map(matchFormatGameMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MatchFormatGameDTO> findOne(String id) {
        log.debug("Request to get MatchFormatGame : {}", id);
        return matchFormatGameRepository.findById(id).map(matchFormatGameMapper::toDto);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete MatchFormatGame : {}", id);
        matchFormatGameRepository.deleteById(id);
    }
}
