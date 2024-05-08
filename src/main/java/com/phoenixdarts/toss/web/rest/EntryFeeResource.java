package com.phoenixdarts.toss.backend.web.rest;

import com.phoenixdarts.toss.backend.repository.EntryFeeRepository;
import com.phoenixdarts.toss.backend.service.EntryFeeService;
import com.phoenixdarts.toss.backend.service.dto.EntryFeeDTO;
import com.phoenixdarts.toss.backend.web.rest.errors.BadRequestAlertException;
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
import com.phoenixdarts.toss.backend.util.HeaderUtil;
import com.phoenixdarts.toss.backend.util.ResponseUtil;

/**
 * REST controller for managing {@link com.phoenixdarts.toss.backend.domain.EntryFee}.
 */
@RestController
@RequestMapping("/api/entry-fees")
public class EntryFeeResource {

    private final Logger log = LoggerFactory.getLogger(EntryFeeResource.class);

    private static final String ENTITY_NAME = "entryFee";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EntryFeeService entryFeeService;

    private final EntryFeeRepository entryFeeRepository;

    public EntryFeeResource(EntryFeeService entryFeeService, EntryFeeRepository entryFeeRepository) {
        this.entryFeeService = entryFeeService;
        this.entryFeeRepository = entryFeeRepository;
    }

    /**
     * {@code POST  /entry-fees} : Create a new entryFee.
     *
     * @param entryFeeDTO the entryFeeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new entryFeeDTO, or with status {@code 400 (Bad Request)} if the entryFee has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<EntryFeeDTO> createEntryFee(@Valid @RequestBody EntryFeeDTO entryFeeDTO) throws URISyntaxException {
        log.debug("REST request to save EntryFee : {}", entryFeeDTO);
        if (entryFeeDTO.getId() != null) {
            throw new BadRequestAlertException("A new entryFee cannot already have an ID", ENTITY_NAME, "idexists");
        }
        entryFeeDTO = entryFeeService.save(entryFeeDTO);
        return ResponseEntity.created(new URI("/api/entry-fees/" + entryFeeDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, entryFeeDTO.getId()))
            .body(entryFeeDTO);
    }

    /**
     * {@code PUT  /entry-fees/:id} : Updates an existing entryFee.
     *
     * @param id the id of the entryFeeDTO to save.
     * @param entryFeeDTO the entryFeeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated entryFeeDTO,
     * or with status {@code 400 (Bad Request)} if the entryFeeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the entryFeeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<EntryFeeDTO> updateEntryFee(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody EntryFeeDTO entryFeeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update EntryFee : {}, {}", id, entryFeeDTO);
        if (entryFeeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, entryFeeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!entryFeeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        entryFeeDTO = entryFeeService.update(entryFeeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, entryFeeDTO.getId()))
            .body(entryFeeDTO);
    }

    /**
     * {@code PATCH  /entry-fees/:id} : Partial updates given fields of an existing entryFee, field will ignore if it is null
     *
     * @param id the id of the entryFeeDTO to save.
     * @param entryFeeDTO the entryFeeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated entryFeeDTO,
     * or with status {@code 400 (Bad Request)} if the entryFeeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the entryFeeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the entryFeeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EntryFeeDTO> partialUpdateEntryFee(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody EntryFeeDTO entryFeeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update EntryFee partially : {}, {}", id, entryFeeDTO);
        if (entryFeeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, entryFeeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!entryFeeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EntryFeeDTO> result = entryFeeService.partialUpdate(entryFeeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, entryFeeDTO.getId())
        );
    }

    /**
     * {@code GET  /entry-fees} : get all the entryFees.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of entryFees in body.
     */
    @GetMapping("")
    public List<EntryFeeDTO> getAllEntryFees() {
        log.debug("REST request to get all EntryFees");
        return entryFeeService.findAll();
    }

    /**
     * {@code GET  /entry-fees/:id} : get the "id" entryFee.
     *
     * @param id the id of the entryFeeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the entryFeeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<EntryFeeDTO> getEntryFee(@PathVariable("id") String id) {
        log.debug("REST request to get EntryFee : {}", id);
        Optional<EntryFeeDTO> entryFeeDTO = entryFeeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(entryFeeDTO);
    }

    /**
     * {@code DELETE  /entry-fees/:id} : delete the "id" entryFee.
     *
     * @param id the id of the entryFeeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEntryFee(@PathVariable("id") String id) {
        log.debug("REST request to delete EntryFee : {}", id);
        entryFeeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
