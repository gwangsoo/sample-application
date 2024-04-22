package com.phoenixdarts.toss.web.rest;

import com.phoenixdarts.toss.repository.MachineAreaRepository;
import com.phoenixdarts.toss.service.MachineAreaService;
import com.phoenixdarts.toss.service.dto.MachineAreaDTO;
import com.phoenixdarts.toss.web.rest.errors.BadRequestAlertException;
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
 * REST controller for managing {@link com.phoenixdarts.toss.domain.MachineArea}.
 */
@RestController
@RequestMapping("/api/machine-areas")
public class MachineAreaResource {

    private final Logger log = LoggerFactory.getLogger(MachineAreaResource.class);

    private static final String ENTITY_NAME = "machineArea";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MachineAreaService machineAreaService;

    private final MachineAreaRepository machineAreaRepository;

    public MachineAreaResource(MachineAreaService machineAreaService, MachineAreaRepository machineAreaRepository) {
        this.machineAreaService = machineAreaService;
        this.machineAreaRepository = machineAreaRepository;
    }

    /**
     * {@code POST  /machine-areas} : Create a new machineArea.
     *
     * @param machineAreaDTO the machineAreaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new machineAreaDTO, or with status {@code 400 (Bad Request)} if the machineArea has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<MachineAreaDTO> createMachineArea(@Valid @RequestBody MachineAreaDTO machineAreaDTO) throws URISyntaxException {
        log.debug("REST request to save MachineArea : {}", machineAreaDTO);
        if (machineAreaDTO.getId() != null) {
            throw new BadRequestAlertException("A new machineArea cannot already have an ID", ENTITY_NAME, "idexists");
        }
        machineAreaDTO = machineAreaService.save(machineAreaDTO);
        return ResponseEntity.created(new URI("/api/machine-areas/" + machineAreaDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, machineAreaDTO.getId()))
            .body(machineAreaDTO);
    }

    /**
     * {@code PUT  /machine-areas/:id} : Updates an existing machineArea.
     *
     * @param id the id of the machineAreaDTO to save.
     * @param machineAreaDTO the machineAreaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated machineAreaDTO,
     * or with status {@code 400 (Bad Request)} if the machineAreaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the machineAreaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<MachineAreaDTO> updateMachineArea(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody MachineAreaDTO machineAreaDTO
    ) throws URISyntaxException {
        log.debug("REST request to update MachineArea : {}, {}", id, machineAreaDTO);
        if (machineAreaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, machineAreaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!machineAreaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        machineAreaDTO = machineAreaService.update(machineAreaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, machineAreaDTO.getId()))
            .body(machineAreaDTO);
    }

    /**
     * {@code PATCH  /machine-areas/:id} : Partial updates given fields of an existing machineArea, field will ignore if it is null
     *
     * @param id the id of the machineAreaDTO to save.
     * @param machineAreaDTO the machineAreaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated machineAreaDTO,
     * or with status {@code 400 (Bad Request)} if the machineAreaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the machineAreaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the machineAreaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MachineAreaDTO> partialUpdateMachineArea(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody MachineAreaDTO machineAreaDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update MachineArea partially : {}, {}", id, machineAreaDTO);
        if (machineAreaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, machineAreaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!machineAreaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MachineAreaDTO> result = machineAreaService.partialUpdate(machineAreaDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, machineAreaDTO.getId())
        );
    }

    /**
     * {@code GET  /machine-areas} : get all the machineAreas.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of machineAreas in body.
     */
    @GetMapping("")
    public List<MachineAreaDTO> getAllMachineAreas() {
        log.debug("REST request to get all MachineAreas");
        return machineAreaService.findAll();
    }

    /**
     * {@code GET  /machine-areas/:id} : get the "id" machineArea.
     *
     * @param id the id of the machineAreaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the machineAreaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<MachineAreaDTO> getMachineArea(@PathVariable("id") String id) {
        log.debug("REST request to get MachineArea : {}", id);
        Optional<MachineAreaDTO> machineAreaDTO = machineAreaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(machineAreaDTO);
    }

    /**
     * {@code DELETE  /machine-areas/:id} : delete the "id" machineArea.
     *
     * @param id the id of the machineAreaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMachineArea(@PathVariable("id") String id) {
        log.debug("REST request to delete MachineArea : {}", id);
        machineAreaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
