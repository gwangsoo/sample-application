package com.phoenixdarts.toss.backend.web.rest;

import com.phoenixdarts.toss.backend.repository.EntryRepository;
import com.phoenixdarts.toss.backend.service.EntryService;
import com.phoenixdarts.toss.backend.service.dto.EntryDTO;
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
 * REST controller for managing {@link com.phoenixdarts.toss.backend.domain.Entry}.
 */
@RestController
@RequestMapping("/api/entries")
public class EntryResource {

    private final Logger log = LoggerFactory.getLogger(EntryResource.class);

    private static final String ENTITY_NAME = "entry";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EntryService entryService;

    private final EntryRepository entryRepository;

    public EntryResource(EntryService entryService, EntryRepository entryRepository) {
        this.entryService = entryService;
        this.entryRepository = entryRepository;
    }

    /**
     * {@code POST  /entries} : Create a new entry.
     *
     * @param entryDTO the entryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new entryDTO, or with status {@code 400 (Bad Request)} if the entry has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<EntryDTO> createEntry(@Valid @RequestBody EntryDTO entryDTO) throws URISyntaxException {
        log.debug("REST request to save Entry : {}", entryDTO);
        if (entryDTO.getId() != null) {
            throw new BadRequestAlertException("A new entry cannot already have an ID", ENTITY_NAME, "idexists");
        }
        entryDTO = entryService.save(entryDTO);
        return ResponseEntity.created(new URI("/api/entries/" + entryDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, entryDTO.getId()))
            .body(entryDTO);
    }

    /**
     * {@code PUT  /entries/:id} : Updates an existing entry.
     *
     * @param id the id of the entryDTO to save.
     * @param entryDTO the entryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated entryDTO,
     * or with status {@code 400 (Bad Request)} if the entryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the entryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<EntryDTO> updateEntry(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody EntryDTO entryDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Entry : {}, {}", id, entryDTO);
        if (entryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, entryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!entryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        entryDTO = entryService.update(entryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, entryDTO.getId()))
            .body(entryDTO);
    }

    /**
     * {@code PATCH  /entries/:id} : Partial updates given fields of an existing entry, field will ignore if it is null
     *
     * @param id the id of the entryDTO to save.
     * @param entryDTO the entryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated entryDTO,
     * or with status {@code 400 (Bad Request)} if the entryDTO is not valid,
     * or with status {@code 404 (Not Found)} if the entryDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the entryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EntryDTO> partialUpdateEntry(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody EntryDTO entryDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Entry partially : {}, {}", id, entryDTO);
        if (entryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, entryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!entryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EntryDTO> result = entryService.partialUpdate(entryDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, entryDTO.getId())
        );
    }

    /**
     * {@code GET  /entries} : get all the entries.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of entries in body.
     */
    @GetMapping("")
    public List<EntryDTO> getAllEntries() {
        log.debug("REST request to get all Entries");
        return entryService.findAll();
    }

    /**
     * {@code GET  /entries/:id} : get the "id" entry.
     *
     * @param id the id of the entryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the entryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<EntryDTO> getEntry(@PathVariable("id") String id) {
        log.debug("REST request to get Entry : {}", id);
        Optional<EntryDTO> entryDTO = entryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(entryDTO);
    }

    /**
     * {@code DELETE  /entries/:id} : delete the "id" entry.
     *
     * @param id the id of the entryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEntry(@PathVariable("id") String id) {
        log.debug("REST request to delete Entry : {}", id);
        entryService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
