package com.example.bfi.service.impl;

import com.example.bfi.domain.Connector;
import com.example.bfi.repository.ConnectorRepository;
import com.example.bfi.service.ConnectorService;
import com.example.bfi.service.dto.ConnectorDTO;
import com.example.bfi.service.mapper.ConnectorMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link com.example.bfi.domain.Connector}.
 */
@Service
public class ConnectorServiceImpl implements ConnectorService {

    private final Logger log = LoggerFactory.getLogger(ConnectorServiceImpl.class);

    private final ConnectorRepository connectorRepository;

    private final ConnectorMapper connectorMapper;

    public ConnectorServiceImpl(ConnectorRepository connectorRepository, ConnectorMapper connectorMapper) {
        this.connectorRepository = connectorRepository;
        this.connectorMapper = connectorMapper;
    }

    @Override
    public ConnectorDTO save(ConnectorDTO connectorDTO) {
        log.debug("Request to save Connector : {}", connectorDTO);
        Connector connector = connectorMapper.toEntity(connectorDTO);
        connector = connectorRepository.save(connector);
        return connectorMapper.toDto(connector);
    }

    @Override
    public ConnectorDTO update(ConnectorDTO connectorDTO) {
        log.debug("Request to update Connector : {}", connectorDTO);
        Connector connector = connectorMapper.toEntity(connectorDTO);
        connector = connectorRepository.save(connector);
        return connectorMapper.toDto(connector);
    }

    @Override
    public Optional<ConnectorDTO> partialUpdate(ConnectorDTO connectorDTO) {
        log.debug("Request to partially update Connector : {}", connectorDTO);

        return connectorRepository
            .findById(connectorDTO.getId())
            .map(existingConnector -> {
                connectorMapper.partialUpdate(existingConnector, connectorDTO);

                return existingConnector;
            })
            .map(connectorRepository::save)
            .map(connectorMapper::toDto);
    }

    @Override
    public List<ConnectorDTO> findAll() {
        log.debug("Request to get all Connectors");
        return connectorRepository.findAll().stream().map(connectorMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public Optional<ConnectorDTO> findOne(String id) {
        log.debug("Request to get Connector : {}", id);
        return connectorRepository.findById(id).map(connectorMapper::toDto);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Connector : {}", id);
        connectorRepository.deleteById(id);
    }
}
