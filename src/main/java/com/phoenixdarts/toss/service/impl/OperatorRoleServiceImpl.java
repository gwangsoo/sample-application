package com.phoenixdarts.toss.service.impl;

import com.phoenixdarts.toss.domain.OperatorRole;
import com.phoenixdarts.toss.repository.OperatorRoleRepository;
import com.phoenixdarts.toss.service.OperatorRoleService;
import com.phoenixdarts.toss.service.dto.OperatorRoleDTO;
import com.phoenixdarts.toss.service.mapper.OperatorRoleMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.phoenixdarts.toss.domain.OperatorRole}.
 */
@Service
@Transactional
public class OperatorRoleServiceImpl implements OperatorRoleService {

    private final Logger log = LoggerFactory.getLogger(OperatorRoleServiceImpl.class);

    private final OperatorRoleRepository operatorRoleRepository;

    private final OperatorRoleMapper operatorRoleMapper;

    public OperatorRoleServiceImpl(OperatorRoleRepository operatorRoleRepository, OperatorRoleMapper operatorRoleMapper) {
        this.operatorRoleRepository = operatorRoleRepository;
        this.operatorRoleMapper = operatorRoleMapper;
    }

    @Override
    public OperatorRoleDTO save(OperatorRoleDTO operatorRoleDTO) {
        log.debug("Request to save OperatorRole : {}", operatorRoleDTO);
        OperatorRole operatorRole = operatorRoleMapper.toEntity(operatorRoleDTO);
        operatorRole = operatorRoleRepository.save(operatorRole);
        return operatorRoleMapper.toDto(operatorRole);
    }

    @Override
    public OperatorRoleDTO update(OperatorRoleDTO operatorRoleDTO) {
        log.debug("Request to update OperatorRole : {}", operatorRoleDTO);
        OperatorRole operatorRole = operatorRoleMapper.toEntity(operatorRoleDTO);
        operatorRole = operatorRoleRepository.save(operatorRole);
        return operatorRoleMapper.toDto(operatorRole);
    }

    @Override
    public Optional<OperatorRoleDTO> partialUpdate(OperatorRoleDTO operatorRoleDTO) {
        log.debug("Request to partially update OperatorRole : {}", operatorRoleDTO);

        return operatorRoleRepository
            .findById(operatorRoleDTO.getId())
            .map(existingOperatorRole -> {
                operatorRoleMapper.partialUpdate(existingOperatorRole, operatorRoleDTO);

                return existingOperatorRole;
            })
            .map(operatorRoleRepository::save)
            .map(operatorRoleMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OperatorRoleDTO> findAll() {
        log.debug("Request to get all OperatorRoles");
        return operatorRoleRepository.findAll().stream().map(operatorRoleMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OperatorRoleDTO> findOne(String id) {
        log.debug("Request to get OperatorRole : {}", id);
        return operatorRoleRepository.findById(id).map(operatorRoleMapper::toDto);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete OperatorRole : {}", id);
        operatorRoleRepository.deleteById(id);
    }
}
