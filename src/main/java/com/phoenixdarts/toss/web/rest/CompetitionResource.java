package com.phoenixdarts.toss.backend.web.rest;

import com.phoenixdarts.toss.backend.repository.CompetitionRepository;
import com.phoenixdarts.toss.backend.service.CompetitionService;
import com.phoenixdarts.toss.backend.service.dto.CompetitionDTO;
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
 * REST controller for managing {@link com.phoenixdarts.toss.backend.domain.Competition}.
 */
@RestController
@RequestMapping("/api/competitions")
public class CompetitionResource {

    private final Logger log = LoggerFactory.getLogger(CompetitionResource.class);

    private static final String ENTITY_NAME = "competition";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CompetitionService competitionService;

    private final CompetitionRepository competitionRepository;

    public CompetitionResource(CompetitionService competitionService, CompetitionRepository competitionRepository) {
        this.competitionService = competitionService;
        this.competitionRepository = competitionRepository;
    }

    /**
     * {@code POST  /competitions} : Create a new competition.
     *
     * @param competitionDTO the competitionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new competitionDTO, or with status {@code 400 (Bad Request)} if the competition has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<CompetitionDTO> createCompetition(@Valid @RequestBody CompetitionDTO competitionDTO) throws URISyntaxException {
        log.debug("REST request to save Competition : {}", competitionDTO);
        if (competitionDTO.getId() != null) {
            throw new BadRequestAlertException("A new competition cannot already have an ID", ENTITY_NAME, "idexists");
        }
        competitionDTO = competitionService.save(competitionDTO);
        return ResponseEntity.created(new URI("/api/competitions/" + competitionDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, competitionDTO.getId()))
            .body(competitionDTO);
    }

    /**
     * {@code PUT  /competitions/:id} : Updates an existing competition.
     *
     * @param id the id of the competitionDTO to save.
     * @param competitionDTO the competitionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated competitionDTO,
     * or with status {@code 400 (Bad Request)} if the competitionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the competitionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CompetitionDTO> updateCompetition(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody CompetitionDTO competitionDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Competition : {}, {}", id, competitionDTO);
        if (competitionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, competitionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!competitionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        competitionDTO = competitionService.update(competitionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, competitionDTO.getId()))
            .body(competitionDTO);
    }

    /**
     * {@code PATCH  /competitions/:id} : Partial updates given fields of an existing competition, field will ignore if it is null
     *
     * @param id the id of the competitionDTO to save.
     * @param competitionDTO the competitionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated competitionDTO,
     * or with status {@code 400 (Bad Request)} if the competitionDTO is not valid,
     * or with status {@code 404 (Not Found)} if the competitionDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the competitionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CompetitionDTO> partialUpdateCompetition(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody CompetitionDTO competitionDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Competition partially : {}, {}", id, competitionDTO);
        if (competitionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, competitionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!competitionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CompetitionDTO> result = competitionService.partialUpdate(competitionDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, competitionDTO.getId())
        );
    }

    /**
     * {@code GET  /competitions} : get all the competitions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of competitions in body.
     */
    @GetMapping("")
    public List<CompetitionDTO> getAllCompetitions() {
        log.debug("REST request to get all Competitions");
        return competitionService.findAll();
    }

    /**
     * {@code GET  /competitions/:id} : get the "id" competition.
     *
     * @param id the id of the competitionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the competitionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CompetitionDTO> getCompetition(@PathVariable("id") String id) {
        log.debug("REST request to get Competition : {}", id);
        Optional<CompetitionDTO> competitionDTO = competitionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(competitionDTO);
    }

    /**
     * {@code DELETE  /competitions/:id} : delete the "id" competition.
     *
     * @param id the id of the competitionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompetition(@PathVariable("id") String id) {
        log.debug("REST request to delete Competition : {}", id);
        competitionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
