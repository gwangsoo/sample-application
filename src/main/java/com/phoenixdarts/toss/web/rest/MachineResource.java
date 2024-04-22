package com.phoenixdarts.toss.web.rest;

import com.phoenixdarts.toss.repository.MachineRepository;
import com.phoenixdarts.toss.service.MachineService;
import com.phoenixdarts.toss.service.dto.MachineDTO;
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
 * REST controller for managing {@link com.phoenixdarts.toss.domain.Machine}.
 */
@RestController
@RequestMapping("/api/machines")
public class MachineResource {

    private final Logger log = LoggerFactory.getLogger(MachineResource.class);

    private static final String ENTITY_NAME = "machine";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MachineService machineService;

    private final MachineRepository machineRepository;

    public MachineResource(MachineService machineService, MachineRepository machineRepository) {
        this.machineService = machineService;
        this.machineRepository = machineRepository;
    }

    /**
     * {@code POST  /machines} : Create a new machine.
     *
     * @param machineDTO the machineDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new machineDTO, or with status {@code 400 (Bad Request)} if the machine has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<MachineDTO> createMachine(@Valid @RequestBody MachineDTO machineDTO) throws URISyntaxException {
        log.debug("REST request to save Machine : {}", machineDTO);
        if (machineDTO.getId() != null) {
            throw new BadRequestAlertException("A new machine cannot already have an ID", ENTITY_NAME, "idexists");
        }
        machineDTO = machineService.save(machineDTO);
        return ResponseEntity.created(new URI("/api/machines/" + machineDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, machineDTO.getId()))
            .body(machineDTO);
    }

    /**
     * {@code PUT  /machines/:id} : Updates an existing machine.
     *
     * @param id the id of the machineDTO to save.
     * @param machineDTO the machineDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated machineDTO,
     * or with status {@code 400 (Bad Request)} if the machineDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the machineDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<MachineDTO> updateMachine(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody MachineDTO machineDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Machine : {}, {}", id, machineDTO);
        if (machineDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, machineDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!machineRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        machineDTO = machineService.update(machineDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, machineDTO.getId()))
            .body(machineDTO);
    }

    /**
     * {@code PATCH  /machines/:id} : Partial updates given fields of an existing machine, field will ignore if it is null
     *
     * @param id the id of the machineDTO to save.
     * @param machineDTO the machineDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated machineDTO,
     * or with status {@code 400 (Bad Request)} if the machineDTO is not valid,
     * or with status {@code 404 (Not Found)} if the machineDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the machineDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MachineDTO> partialUpdateMachine(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody MachineDTO machineDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Machine partially : {}, {}", id, machineDTO);
        if (machineDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, machineDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!machineRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MachineDTO> result = machineService.partialUpdate(machineDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, machineDTO.getId())
        );
    }

    /**
     * {@code GET  /machines} : get all the machines.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of machines in body.
     */
    @GetMapping("")
    public List<MachineDTO> getAllMachines() {
        log.debug("REST request to get all Machines");
        return machineService.findAll();
    }

    /**
     * {@code GET  /machines/:id} : get the "id" machine.
     *
     * @param id the id of the machineDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the machineDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<MachineDTO> getMachine(@PathVariable("id") String id) {
        log.debug("REST request to get Machine : {}", id);
        Optional<MachineDTO> machineDTO = machineService.findOne(id);
        return ResponseUtil.wrapOrNotFound(machineDTO);
    }

    /**
     * {@code DELETE  /machines/:id} : delete the "id" machine.
     *
     * @param id the id of the machineDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMachine(@PathVariable("id") String id) {
        log.debug("REST request to delete Machine : {}", id);
        machineService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
