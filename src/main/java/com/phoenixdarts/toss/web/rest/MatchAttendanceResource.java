package com.phoenixdarts.toss.backend.web.rest;

import com.phoenixdarts.toss.backend.repository.MatchAttendanceRepository;
import com.phoenixdarts.toss.backend.service.MatchAttendanceService;
import com.phoenixdarts.toss.backend.service.dto.MatchAttendanceDTO;
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
 * REST controller for managing {@link com.phoenixdarts.toss.backend.domain.MatchAttendance}.
 */
@RestController
@RequestMapping("/api/match-attendances")
public class MatchAttendanceResource {

    private final Logger log = LoggerFactory.getLogger(MatchAttendanceResource.class);

    private static final String ENTITY_NAME = "matchAttendance";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MatchAttendanceService matchAttendanceService;

    private final MatchAttendanceRepository matchAttendanceRepository;

    public MatchAttendanceResource(MatchAttendanceService matchAttendanceService, MatchAttendanceRepository matchAttendanceRepository) {
        this.matchAttendanceService = matchAttendanceService;
        this.matchAttendanceRepository = matchAttendanceRepository;
    }

    /**
     * {@code POST  /match-attendances} : Create a new matchAttendance.
     *
     * @param matchAttendanceDTO the matchAttendanceDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new matchAttendanceDTO, or with status {@code 400 (Bad Request)} if the matchAttendance has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<MatchAttendanceDTO> createMatchAttendance(@Valid @RequestBody MatchAttendanceDTO matchAttendanceDTO)
        throws URISyntaxException {
        log.debug("REST request to save MatchAttendance : {}", matchAttendanceDTO);
        if (matchAttendanceDTO.getId() != null) {
            throw new BadRequestAlertException("A new matchAttendance cannot already have an ID", ENTITY_NAME, "idexists");
        }
        matchAttendanceDTO = matchAttendanceService.save(matchAttendanceDTO);
        return ResponseEntity.created(new URI("/api/match-attendances/" + matchAttendanceDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, matchAttendanceDTO.getId()))
            .body(matchAttendanceDTO);
    }

    /**
     * {@code PUT  /match-attendances/:id} : Updates an existing matchAttendance.
     *
     * @param id the id of the matchAttendanceDTO to save.
     * @param matchAttendanceDTO the matchAttendanceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated matchAttendanceDTO,
     * or with status {@code 400 (Bad Request)} if the matchAttendanceDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the matchAttendanceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<MatchAttendanceDTO> updateMatchAttendance(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody MatchAttendanceDTO matchAttendanceDTO
    ) throws URISyntaxException {
        log.debug("REST request to update MatchAttendance : {}, {}", id, matchAttendanceDTO);
        if (matchAttendanceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, matchAttendanceDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!matchAttendanceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        matchAttendanceDTO = matchAttendanceService.update(matchAttendanceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, matchAttendanceDTO.getId()))
            .body(matchAttendanceDTO);
    }

    /**
     * {@code PATCH  /match-attendances/:id} : Partial updates given fields of an existing matchAttendance, field will ignore if it is null
     *
     * @param id the id of the matchAttendanceDTO to save.
     * @param matchAttendanceDTO the matchAttendanceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated matchAttendanceDTO,
     * or with status {@code 400 (Bad Request)} if the matchAttendanceDTO is not valid,
     * or with status {@code 404 (Not Found)} if the matchAttendanceDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the matchAttendanceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MatchAttendanceDTO> partialUpdateMatchAttendance(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody MatchAttendanceDTO matchAttendanceDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update MatchAttendance partially : {}, {}", id, matchAttendanceDTO);
        if (matchAttendanceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, matchAttendanceDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!matchAttendanceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MatchAttendanceDTO> result = matchAttendanceService.partialUpdate(matchAttendanceDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, matchAttendanceDTO.getId())
        );
    }

    /**
     * {@code GET  /match-attendances} : get all the matchAttendances.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of matchAttendances in body.
     */
    @GetMapping("")
    public List<MatchAttendanceDTO> getAllMatchAttendances() {
        log.debug("REST request to get all MatchAttendances");
        return matchAttendanceService.findAll();
    }

    /**
     * {@code GET  /match-attendances/:id} : get the "id" matchAttendance.
     *
     * @param id the id of the matchAttendanceDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the matchAttendanceDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<MatchAttendanceDTO> getMatchAttendance(@PathVariable("id") String id) {
        log.debug("REST request to get MatchAttendance : {}", id);
        Optional<MatchAttendanceDTO> matchAttendanceDTO = matchAttendanceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(matchAttendanceDTO);
    }

    /**
     * {@code DELETE  /match-attendances/:id} : delete the "id" matchAttendance.
     *
     * @param id the id of the matchAttendanceDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMatchAttendance(@PathVariable("id") String id) {
        log.debug("REST request to delete MatchAttendance : {}", id);
        matchAttendanceService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
