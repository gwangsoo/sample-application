package com.phoenixdarts.toss.web.rest;

import com.phoenixdarts.toss.repository.DivisionRepository;
import com.phoenixdarts.toss.service.DivisionService;
import com.phoenixdarts.toss.service.dto.DivisionDTO;
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
 * REST controller for managing {@link com.phoenixdarts.toss.domain.Division}.
 */
@RestController
@RequestMapping("/api/divisions")
public class DivisionResource {

    private final Logger log = LoggerFactory.getLogger(DivisionResource.class);

    private static final String ENTITY_NAME = "division";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DivisionService divisionService;

    private final DivisionRepository divisionRepository;

    public DivisionResource(DivisionService divisionService, DivisionRepository divisionRepository) {
        this.divisionService = divisionService;
        this.divisionRepository = divisionRepository;
    }

    /**
     * {@code POST  /divisions} : Create a new division.
     *
     * @param divisionDTO the divisionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new divisionDTO, or with status {@code 400 (Bad Request)} if the division has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<DivisionDTO> createDivision(@Valid @RequestBody DivisionDTO divisionDTO) throws URISyntaxException {
        log.debug("REST request to save Division : {}", divisionDTO);
        if (divisionDTO.getId() != null) {
            throw new BadRequestAlertException("A new division cannot already have an ID", ENTITY_NAME, "idexists");
        }
        divisionDTO = divisionService.save(divisionDTO);
        return ResponseEntity.created(new URI("/api/divisions/" + divisionDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, divisionDTO.getId()))
            .body(divisionDTO);
    }

    /**
     * {@code PUT  /divisions/:id} : Updates an existing division.
     *
     * @param id the id of the divisionDTO to save.
     * @param divisionDTO the divisionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated divisionDTO,
     * or with status {@code 400 (Bad Request)} if the divisionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the divisionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<DivisionDTO> updateDivision(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody DivisionDTO divisionDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Division : {}, {}", id, divisionDTO);
        if (divisionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, divisionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!divisionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        divisionDTO = divisionService.update(divisionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, divisionDTO.getId()))
            .body(divisionDTO);
    }

    /**
     * {@code PATCH  /divisions/:id} : Partial updates given fields of an existing division, field will ignore if it is null
     *
     * @param id the id of the divisionDTO to save.
     * @param divisionDTO the divisionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated divisionDTO,
     * or with status {@code 400 (Bad Request)} if the divisionDTO is not valid,
     * or with status {@code 404 (Not Found)} if the divisionDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the divisionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DivisionDTO> partialUpdateDivision(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody DivisionDTO divisionDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Division partially : {}, {}", id, divisionDTO);
        if (divisionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, divisionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!divisionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DivisionDTO> result = divisionService.partialUpdate(divisionDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, divisionDTO.getId())
        );
    }

    /**
     * {@code GET  /divisions} : get all the divisions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of divisions in body.
     */
    @GetMapping("")
    public List<DivisionDTO> getAllDivisions() {
        log.debug("REST request to get all Divisions");
        return divisionService.findAll();
    }

    /**
     * {@code GET  /divisions/:id} : get the "id" division.
     *
     * @param id the id of the divisionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the divisionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<DivisionDTO> getDivision(@PathVariable("id") String id) {
        log.debug("REST request to get Division : {}", id);
        Optional<DivisionDTO> divisionDTO = divisionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(divisionDTO);
    }

    /**
     * {@code DELETE  /divisions/:id} : delete the "id" division.
     *
     * @param id the id of the divisionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDivision(@PathVariable("id") String id) {
        log.debug("REST request to delete Division : {}", id);
        divisionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
