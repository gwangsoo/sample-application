package com.phoenixdarts.toss.service.impl;

import com.phoenixdarts.toss.domain.MatchFormatOption;
import com.phoenixdarts.toss.repository.MatchFormatOptionRepository;
import com.phoenixdarts.toss.service.MatchFormatOptionService;
import com.phoenixdarts.toss.service.dto.MatchFormatOptionDTO;
import com.phoenixdarts.toss.service.mapper.MatchFormatOptionMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.phoenixdarts.toss.domain.MatchFormatOption}.
 */
@Service
@Transactional
public class MatchFormatOptionServiceImpl implements MatchFormatOptionService {

    private final Logger log = LoggerFactory.getLogger(MatchFormatOptionServiceImpl.class);

    private final MatchFormatOptionRepository matchFormatOptionRepository;

    private final MatchFormatOptionMapper matchFormatOptionMapper;

    public MatchFormatOptionServiceImpl(
        MatchFormatOptionRepository matchFormatOptionRepository,
        MatchFormatOptionMapper matchFormatOptionMapper
    ) {
        this.matchFormatOptionRepository = matchFormatOptionRepository;
        this.matchFormatOptionMapper = matchFormatOptionMapper;
    }

    @Override
    public MatchFormatOptionDTO save(MatchFormatOptionDTO matchFormatOptionDTO) {
        log.debug("Request to save MatchFormatOption : {}", matchFormatOptionDTO);
        MatchFormatOption matchFormatOption = matchFormatOptionMapper.toEntity(matchFormatOptionDTO);
        matchFormatOption = matchFormatOptionRepository.save(matchFormatOption);
        return matchFormatOptionMapper.toDto(matchFormatOption);
    }

    @Override
    public MatchFormatOptionDTO update(MatchFormatOptionDTO matchFormatOptionDTO) {
        log.debug("Request to update MatchFormatOption : {}", matchFormatOptionDTO);
        MatchFormatOption matchFormatOption = matchFormatOptionMapper.toEntity(matchFormatOptionDTO);
        matchFormatOption = matchFormatOptionRepository.save(matchFormatOption);
        return matchFormatOptionMapper.toDto(matchFormatOption);
    }

    @Override
    public Optional<MatchFormatOptionDTO> partialUpdate(MatchFormatOptionDTO matchFormatOptionDTO) {
        log.debug("Request to partially update MatchFormatOption : {}", matchFormatOptionDTO);

        return matchFormatOptionRepository
            .findById(matchFormatOptionDTO.getId())
            .map(existingMatchFormatOption -> {
                matchFormatOptionMapper.partialUpdate(existingMatchFormatOption, matchFormatOptionDTO);

                return existingMatchFormatOption;
            })
            .map(matchFormatOptionRepository::save)
            .map(matchFormatOptionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MatchFormatOptionDTO> findAll() {
        log.debug("Request to get all MatchFormatOptions");
        return matchFormatOptionRepository
            .findAll()
            .stream()
            .map(matchFormatOptionMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MatchFormatOptionDTO> findOne(String id) {
        log.debug("Request to get MatchFormatOption : {}", id);
        return matchFormatOptionRepository.findById(id).map(matchFormatOptionMapper::toDto);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete MatchFormatOption : {}", id);
        matchFormatOptionRepository.deleteById(id);
    }
}
