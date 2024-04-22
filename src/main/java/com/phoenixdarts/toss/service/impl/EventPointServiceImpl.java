package com.phoenixdarts.toss.service.impl;

import com.phoenixdarts.toss.domain.EventPoint;
import com.phoenixdarts.toss.repository.EventPointRepository;
import com.phoenixdarts.toss.service.EventPointService;
import com.phoenixdarts.toss.service.dto.EventPointDTO;
import com.phoenixdarts.toss.service.mapper.EventPointMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.phoenixdarts.toss.domain.EventPoint}.
 */
@Service
@Transactional
public class EventPointServiceImpl implements EventPointService {

    private final Logger log = LoggerFactory.getLogger(EventPointServiceImpl.class);

    private final EventPointRepository eventPointRepository;

    private final EventPointMapper eventPointMapper;

    public EventPointServiceImpl(EventPointRepository eventPointRepository, EventPointMapper eventPointMapper) {
        this.eventPointRepository = eventPointRepository;
        this.eventPointMapper = eventPointMapper;
    }

    @Override
    public EventPointDTO save(EventPointDTO eventPointDTO) {
        log.debug("Request to save EventPoint : {}", eventPointDTO);
        EventPoint eventPoint = eventPointMapper.toEntity(eventPointDTO);
        eventPoint = eventPointRepository.save(eventPoint);
        return eventPointMapper.toDto(eventPoint);
    }

    @Override
    public EventPointDTO update(EventPointDTO eventPointDTO) {
        log.debug("Request to update EventPoint : {}", eventPointDTO);
        EventPoint eventPoint = eventPointMapper.toEntity(eventPointDTO);
        eventPoint = eventPointRepository.save(eventPoint);
        return eventPointMapper.toDto(eventPoint);
    }

    @Override
    public Optional<EventPointDTO> partialUpdate(EventPointDTO eventPointDTO) {
        log.debug("Request to partially update EventPoint : {}", eventPointDTO);

        return eventPointRepository
            .findById(eventPointDTO.getId())
            .map(existingEventPoint -> {
                eventPointMapper.partialUpdate(existingEventPoint, eventPointDTO);

                return existingEventPoint;
            })
            .map(eventPointRepository::save)
            .map(eventPointMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EventPointDTO> findAll() {
        log.debug("Request to get all EventPoints");
        return eventPointRepository.findAll().stream().map(eventPointMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EventPointDTO> findOne(String id) {
        log.debug("Request to get EventPoint : {}", id);
        return eventPointRepository.findById(id).map(eventPointMapper::toDto);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete EventPoint : {}", id);
        eventPointRepository.deleteById(id);
    }
}
