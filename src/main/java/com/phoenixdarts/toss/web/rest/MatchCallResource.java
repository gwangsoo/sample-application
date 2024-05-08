package com.phoenixdarts.toss.backend.web.rest;

import com.phoenixdarts.toss.backend.repository.MatchCallRepository;
import com.phoenixdarts.toss.backend.service.MatchCallService;
import com.phoenixdarts.toss.backend.service.dto.MatchCallDTO;
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
 * REST controller for managing {@link com.phoenixdarts.toss.backend.domain.MatchCall}.
 */
@RestController
@RequestMapping("/api/match-calls")
public class MatchCallResource {

    private final Logger log = LoggerFactory.getLogger(MatchCallResource.class);

    private static final String ENTITY_NAME = "matchCall";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MatchCallService matchCallService;

    private final MatchCallRepository matchCallRepository;

    public MatchCallResource(MatchCallService matchCallService, MatchCallRepository matchCallRepository) {
        this.matchCallService = matchCallService;
        this.matchCallRepository = matchCallRepository;
    }

    /**
     * {@code POST  /match-calls} : Create a new matchCall.
     *
     * @param matchCallDTO the matchCallDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new matchCallDTO, or with status {@code 400 (Bad Request)} if the matchCall has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<MatchCallDTO> createMatchCall(@Valid @RequestBody MatchCallDTO matchCallDTO) throws URISyntaxException {
        log.debug("REST request to save MatchCall : {}", matchCallDTO);
        if (matchCallDTO.getId() != null) {
            throw new BadRequestAlertException("A new matchCall cannot already have an ID", ENTITY_NAME, "idexists");
        }
        matchCallDTO = matchCallService.save(matchCallDTO);
        return ResponseEntity.created(new URI("/api/match-calls/" + matchCallDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, matchCallDTO.getId()))
            .body(matchCallDTO);
    }

    /**
     * {@code PUT  /match-calls/:id} : Updates an existing matchCall.
     *
     * @param id the id of the matchCallDTO to save.
     * @param matchCallDTO the matchCallDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated matchCallDTO,
     * or with status {@code 400 (Bad Request)} if the matchCallDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the matchCallDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<MatchCallDTO> updateMatchCall(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody MatchCallDTO matchCallDTO
    ) throws URISyntaxException {
        log.debug("REST request to update MatchCall : {}, {}", id, matchCallDTO);
        if (matchCallDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, matchCallDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!matchCallRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        matchCallDTO = matchCallService.update(matchCallDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, matchCallDTO.getId()))
            .body(matchCallDTO);
    }

    /**
     * {@code PATCH  /match-calls/:id} : Partial updates given fields of an existing matchCall, field will ignore if it is null
     *
     * @param id the id of the matchCallDTO to save.
     * @param matchCallDTO the matchCallDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated matchCallDTO,
     * or with status {@code 400 (Bad Request)} if the matchCallDTO is not valid,
     * or with status {@code 404 (Not Found)} if the matchCallDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the matchCallDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MatchCallDTO> partialUpdateMatchCall(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody MatchCallDTO matchCallDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update MatchCall partially : {}, {}", id, matchCallDTO);
        if (matchCallDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, matchCallDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!matchCallRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MatchCallDTO> result = matchCallService.partialUpdate(matchCallDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, matchCallDTO.getId())
        );
    }

    /**
     * {@code GET  /match-calls} : get all the matchCalls.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of matchCalls in body.
     */
    @GetMapping("")
    public List<MatchCallDTO> getAllMatchCalls() {
        log.debug("REST request to get all MatchCalls");
        return matchCallService.findAll();
    }

    /**
     * {@code GET  /match-calls/:id} : get the "id" matchCall.
     *
     * @param id the id of the matchCallDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the matchCallDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<MatchCallDTO> getMatchCall(@PathVariable("id") String id) {
        log.debug("REST request to get MatchCall : {}", id);
        Optional<MatchCallDTO> matchCallDTO = matchCallService.findOne(id);
        return ResponseUtil.wrapOrNotFound(matchCallDTO);
    }

    /**
     * {@code DELETE  /match-calls/:id} : delete the "id" matchCall.
     *
     * @param id the id of the matchCallDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMatchCall(@PathVariable("id") String id) {
        log.debug("REST request to delete MatchCall : {}", id);
        matchCallService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
