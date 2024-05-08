package com.phoenixdarts.toss.backend.service.impl;

import com.phoenixdarts.toss.backend.domain.Operator;
import com.phoenixdarts.toss.backend.repository.OperatorRepository;
import com.phoenixdarts.toss.backend.service.OperatorService;
import com.phoenixdarts.toss.backend.service.dto.OperatorDTO;
import com.phoenixdarts.toss.backend.service.mapper.OperatorMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.phoenixdarts.toss.backend.domain.Operator}.
 */
@Service
@Transactional
public class OperatorServiceImpl implements OperatorService {

    private final Logger log = LoggerFactory.getLogger(OperatorServiceImpl.class);

    private final OperatorRepository operatorRepository;

    private final OperatorMapper operatorMapper;

    public OperatorServiceImpl(OperatorRepository operatorRepository, OperatorMapper operatorMapper) {
        this.operatorRepository = operatorRepository;
        this.operatorMapper = operatorMapper;
    }

    @Override
    public OperatorDTO save(OperatorDTO operatorDTO) {
        log.debug("Request to save Operator : {}", operatorDTO);
        Operator operator = operatorMapper.toEntity(operatorDTO);
        operator = operatorRepository.save(operator);
        return operatorMapper.toDto(operator);
    }

    @Override
    public OperatorDTO update(OperatorDTO operatorDTO) {
        log.debug("Request to update Operator : {}", operatorDTO);
        Operator operator = operatorMapper.toEntity(operatorDTO);
        operator = operatorRepository.save(operator);
        return operatorMapper.toDto(operator);
    }

    @Override
    public Optional<OperatorDTO> partialUpdate(OperatorDTO operatorDTO) {
        log.debug("Request to partially update Operator : {}", operatorDTO);

        return operatorRepository
            .findById(operatorDTO.getId())
            .map(existingOperator -> {
                operatorMapper.partialUpdate(existingOperator, operatorDTO);

                return existingOperator;
            })
            .map(operatorRepository::save)
            .map(operatorMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OperatorDTO> findAll() {
        log.debug("Request to get all Operators");
        return operatorRepository.findAll().stream().map(operatorMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OperatorDTO> findOne(String id) {
        log.debug("Request to get Operator : {}", id);
        return operatorRepository.findById(id).map(operatorMapper::toDto);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Operator : {}", id);
        operatorRepository.deleteById(id);
    }
}
