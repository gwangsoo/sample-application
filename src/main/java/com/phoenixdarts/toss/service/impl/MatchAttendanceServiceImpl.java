package com.phoenixdarts.toss.backend.service.impl;

import com.phoenixdarts.toss.backend.domain.MatchAttendance;
import com.phoenixdarts.toss.backend.repository.MatchAttendanceRepository;
import com.phoenixdarts.toss.backend.service.MatchAttendanceService;
import com.phoenixdarts.toss.backend.service.dto.MatchAttendanceDTO;
import com.phoenixdarts.toss.backend.service.mapper.MatchAttendanceMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.phoenixdarts.toss.backend.domain.MatchAttendance}.
 */
@Service
@Transactional
public class MatchAttendanceServiceImpl implements MatchAttendanceService {

    private final Logger log = LoggerFactory.getLogger(MatchAttendanceServiceImpl.class);

    private final MatchAttendanceRepository matchAttendanceRepository;

    private final MatchAttendanceMapper matchAttendanceMapper;

    public MatchAttendanceServiceImpl(MatchAttendanceRepository matchAttendanceRepository, MatchAttendanceMapper matchAttendanceMapper) {
        this.matchAttendanceRepository = matchAttendanceRepository;
        this.matchAttendanceMapper = matchAttendanceMapper;
    }

    @Override
    public MatchAttendanceDTO save(MatchAttendanceDTO matchAttendanceDTO) {
        log.debug("Request to save MatchAttendance : {}", matchAttendanceDTO);
        MatchAttendance matchAttendance = matchAttendanceMapper.toEntity(matchAttendanceDTO);
        matchAttendance = matchAttendanceRepository.save(matchAttendance);
        return matchAttendanceMapper.toDto(matchAttendance);
    }

    @Override
    public MatchAttendanceDTO update(MatchAttendanceDTO matchAttendanceDTO) {
        log.debug("Request to update MatchAttendance : {}", matchAttendanceDTO);
        MatchAttendance matchAttendance = matchAttendanceMapper.toEntity(matchAttendanceDTO);
        matchAttendance = matchAttendanceRepository.save(matchAttendance);
        return matchAttendanceMapper.toDto(matchAttendance);
    }

    @Override
    public Optional<MatchAttendanceDTO> partialUpdate(MatchAttendanceDTO matchAttendanceDTO) {
        log.debug("Request to partially update MatchAttendance : {}", matchAttendanceDTO);

        return matchAttendanceRepository
            .findById(matchAttendanceDTO.getId())
            .map(existingMatchAttendance -> {
                matchAttendanceMapper.partialUpdate(existingMatchAttendance, matchAttendanceDTO);

                return existingMatchAttendance;
            })
            .map(matchAttendanceRepository::save)
            .map(matchAttendanceMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MatchAttendanceDTO> findAll() {
        log.debug("Request to get all MatchAttendances");
        return matchAttendanceRepository
            .findAll()
            .stream()
            .map(matchAttendanceMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MatchAttendanceDTO> findOne(String id) {
        log.debug("Request to get MatchAttendance : {}", id);
        return matchAttendanceRepository.findById(id).map(matchAttendanceMapper::toDto);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete MatchAttendance : {}", id);
        matchAttendanceRepository.deleteById(id);
    }
}
