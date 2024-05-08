package com.phoenixdarts.toss.backend.web.rest;

import com.phoenixdarts.toss.backend.repository.MatchFormatRepository;
import com.phoenixdarts.toss.backend.service.MatchFormatService;
import com.phoenixdarts.toss.backend.service.dto.MatchFormatDTO;
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
 * REST controller for managing {@link com.phoenixdarts.toss.backend.domain.MatchFormat}.
 */
@RestController
@RequestMapping("/api/match-formats")
public class MatchFormatResource {

    private final Logger log = LoggerFactory.getLogger(MatchFormatResource.class);

    private static final String ENTITY_NAME = "matchFormat";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MatchFormatService matchFormatService;

    private final MatchFormatRepository matchFormatRepository;

    public MatchFormatResource(MatchFormatService matchFormatService, MatchFormatRepository matchFormatRepository) {
        this.matchFormatService = matchFormatService;
        this.matchFormatRepository = matchFormatRepository;
    }

    /**
     * {@code POST  /match-formats} : Create a new matchFormat.
     *
     * @param matchFormatDTO the matchFormatDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new matchFormatDTO, or with status {@code 400 (Bad Request)} if the matchFormat has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<MatchFormatDTO> createMatchFormat(@Valid @RequestBody MatchFormatDTO matchFormatDTO) throws URISyntaxException {
        log.debug("REST request to save MatchFormat : {}", matchFormatDTO);
        if (matchFormatDTO.getId() != null) {
            throw new BadRequestAlertException("A new matchFormat cannot already have an ID", ENTITY_NAME, "idexists");
        }
        matchFormatDTO = matchFormatService.save(matchFormatDTO);
        return ResponseEntity.created(new URI("/api/match-formats/" + matchFormatDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, matchFormatDTO.getId()))
            .body(matchFormatDTO);
    }

    /**
     * {@code PUT  /match-formats/:id} : Updates an existing matchFormat.
     *
     * @param id the id of the matchFormatDTO to save.
     * @param matchFormatDTO the matchFormatDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated matchFormatDTO,
     * or with status {@code 400 (Bad Request)} if the matchFormatDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the matchFormatDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<MatchFormatDTO> updateMatchFormat(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody MatchFormatDTO matchFormatDTO
    ) throws URISyntaxException {
        log.debug("REST request to update MatchFormat : {}, {}", id, matchFormatDTO);
        if (matchFormatDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, matchFormatDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!matchFormatRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        matchFormatDTO = matchFormatService.update(matchFormatDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, matchFormatDTO.getId()))
            .body(matchFormatDTO);
    }

    /**
     * {@code PATCH  /match-formats/:id} : Partial updates given fields of an existing matchFormat, field will ignore if it is null
     *
     * @param id the id of the matchFormatDTO to save.
     * @param matchFormatDTO the matchFormatDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated matchFormatDTO,
     * or with status {@code 400 (Bad Request)} if the matchFormatDTO is not valid,
     * or with status {@code 404 (Not Found)} if the matchFormatDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the matchFormatDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MatchFormatDTO> partialUpdateMatchFormat(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody MatchFormatDTO matchFormatDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update MatchFormat partially : {}, {}", id, matchFormatDTO);
        if (matchFormatDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, matchFormatDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!matchFormatRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MatchFormatDTO> result = matchFormatService.partialUpdate(matchFormatDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, matchFormatDTO.getId())
        );
    }

    /**
     * {@code GET  /match-formats} : get all the matchFormats.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of matchFormats in body.
     */
    @GetMapping("")
    public List<MatchFormatDTO> getAllMatchFormats() {
        log.debug("REST request to get all MatchFormats");
        return matchFormatService.findAll();
    }

    /**
     * {@code GET  /match-formats/:id} : get the "id" matchFormat.
     *
     * @param id the id of the matchFormatDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the matchFormatDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<MatchFormatDTO> getMatchFormat(@PathVariable("id") String id) {
        log.debug("REST request to get MatchFormat : {}", id);
        Optional<MatchFormatDTO> matchFormatDTO = matchFormatService.findOne(id);
        return ResponseUtil.wrapOrNotFound(matchFormatDTO);
    }

    /**
     * {@code DELETE  /match-formats/:id} : delete the "id" matchFormat.
     *
     * @param id the id of the matchFormatDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMatchFormat(@PathVariable("id") String id) {
        log.debug("REST request to delete MatchFormat : {}", id);
        matchFormatService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
