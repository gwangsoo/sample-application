package com.example.bfi.web.rest;

import com.example.bfi.repository.ConnectorRepository;
import com.example.bfi.service.ConnectorService;
import com.example.bfi.service.dto.ConnectorDTO;
import com.example.bfi.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.example.bfi.domain.Connector}.
 */
@RestController
@RequestMapping("/api/connectors")
public class ConnectorResource {

    private final Logger log = LoggerFactory.getLogger(ConnectorResource.class);

    private static final String ENTITY_NAME = "connector";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ConnectorService connectorService;

    private final ConnectorRepository connectorRepository;

    public ConnectorResource(ConnectorService connectorService, ConnectorRepository connectorRepository) {
        this.connectorService = connectorService;
        this.connectorRepository = connectorRepository;
    }

    /**
     * {@code POST  /connectors} : Create a new connector.
     *
     * @param connectorDTO the connectorDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new connectorDTO, or with status {@code 400 (Bad Request)} if the connector has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ConnectorDTO> createConnector(@Valid @RequestBody ConnectorDTO connectorDTO) throws URISyntaxException {
        log.debug("REST request to save Connector : {}", connectorDTO);
        if (connectorDTO.getId() != null) {
            throw new BadRequestAlertException("A new connector cannot already have an ID", ENTITY_NAME, "idexists");
        }
        connectorDTO = connectorService.save(connectorDTO);
        return ResponseEntity.created(new URI("/api/connectors/" + connectorDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, connectorDTO.getId()))
            .body(connectorDTO);
    }

    /**
     * {@code PUT  /connectors/:id} : Updates an existing connector.
     *
     * @param id the id of the connectorDTO to save.
     * @param connectorDTO the connectorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated connectorDTO,
     * or with status {@code 400 (Bad Request)} if the connectorDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the connectorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ConnectorDTO> updateConnector(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody ConnectorDTO connectorDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Connector : {}, {}", id, connectorDTO);
        if (connectorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, connectorDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!connectorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        connectorDTO = connectorService.update(connectorDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, connectorDTO.getId()))
            .body(connectorDTO);
    }

    /**
     * {@code PATCH  /connectors/:id} : Partial updates given fields of an existing connector, field will ignore if it is null
     *
     * @param id the id of the connectorDTO to save.
     * @param connectorDTO the connectorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated connectorDTO,
     * or with status {@code 400 (Bad Request)} if the connectorDTO is not valid,
     * or with status {@code 404 (Not Found)} if the connectorDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the connectorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ConnectorDTO> partialUpdateConnector(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody ConnectorDTO connectorDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Connector partially : {}, {}", id, connectorDTO);
        if (connectorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, connectorDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!connectorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ConnectorDTO> result = connectorService.partialUpdate(connectorDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, connectorDTO.getId())
        );
    }

    /**
     * {@code GET  /connectors} : get all the connectors.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of connectors in body.
     */
    @GetMapping("")
    public List<ConnectorDTO> getAllConnectors() {
        log.debug("REST request to get all Connectors");
        return connectorService.findAll();
    }

    /**
     * {@code GET  /connectors/:id} : get the "id" connector.
     *
     * @param id the id of the connectorDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the connectorDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ConnectorDTO> getConnector(@PathVariable("id") String id) {
        log.debug("REST request to get Connector : {}", id);
        Optional<ConnectorDTO> connectorDTO = connectorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(connectorDTO);
    }

    /**
     * {@code DELETE  /connectors/:id} : delete the "id" connector.
     *
     * @param id the id of the connectorDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConnector(@PathVariable("id") String id) {
        log.debug("REST request to delete Connector : {}", id);
        connectorService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
