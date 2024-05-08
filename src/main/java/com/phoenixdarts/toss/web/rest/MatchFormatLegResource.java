package com.phoenixdarts.toss.backend.web.rest;

import com.phoenixdarts.toss.backend.repository.MatchFormatLegRepository;
import com.phoenixdarts.toss.backend.service.MatchFormatLegService;
import com.phoenixdarts.toss.backend.service.dto.MatchFormatLegDTO;
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
 * REST controller for managing {@link com.phoenixdarts.toss.backend.domain.MatchFormatLeg}.
 */
@RestController
@RequestMapping("/api/match-format-legs")
public class MatchFormatLegResource {

    private final Logger log = LoggerFactory.getLogger(MatchFormatLegResource.class);

    private static final String ENTITY_NAME = "matchFormatLeg";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MatchFormatLegService matchFormatLegService;

    private final MatchFormatLegRepository matchFormatLegRepository;

    public MatchFormatLegResource(MatchFormatLegService matchFormatLegService, MatchFormatLegRepository matchFormatLegRepository) {
        this.matchFormatLegService = matchFormatLegService;
        this.matchFormatLegRepository = matchFormatLegRepository;
    }

    /**
     * {@code POST  /match-format-legs} : Create a new matchFormatLeg.
     *
     * @param matchFormatLegDTO the matchFormatLegDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new matchFormatLegDTO, or with status {@code 400 (Bad Request)} if the matchFormatLeg has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<MatchFormatLegDTO> createMatchFormatLeg(@Valid @RequestBody MatchFormatLegDTO matchFormatLegDTO)
        throws URISyntaxException {
        log.debug("REST request to save MatchFormatLeg : {}", matchFormatLegDTO);
        if (matchFormatLegDTO.getId() != null) {
            throw new BadRequestAlertException("A new matchFormatLeg cannot already have an ID", ENTITY_NAME, "idexists");
        }
        matchFormatLegDTO = matchFormatLegService.save(matchFormatLegDTO);
        return ResponseEntity.created(new URI("/api/match-format-legs/" + matchFormatLegDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, matchFormatLegDTO.getId()))
            .body(matchFormatLegDTO);
    }

    /**
     * {@code PUT  /match-format-legs/:id} : Updates an existing matchFormatLeg.
     *
     * @param id the id of the matchFormatLegDTO to save.
     * @param matchFormatLegDTO the matchFormatLegDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated matchFormatLegDTO,
     * or with status {@code 400 (Bad Request)} if the matchFormatLegDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the matchFormatLegDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<MatchFormatLegDTO> updateMatchFormatLeg(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody MatchFormatLegDTO matchFormatLegDTO
    ) throws URISyntaxException {
        log.debug("REST request to update MatchFormatLeg : {}, {}", id, matchFormatLegDTO);
        if (matchFormatLegDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, matchFormatLegDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!matchFormatLegRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        matchFormatLegDTO = matchFormatLegService.update(matchFormatLegDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, matchFormatLegDTO.getId()))
            .body(matchFormatLegDTO);
    }

    /**
     * {@code PATCH  /match-format-legs/:id} : Partial updates given fields of an existing matchFormatLeg, field will ignore if it is null
     *
     * @param id the id of the matchFormatLegDTO to save.
     * @param matchFormatLegDTO the matchFormatLegDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated matchFormatLegDTO,
     * or with status {@code 400 (Bad Request)} if the matchFormatLegDTO is not valid,
     * or with status {@code 404 (Not Found)} if the matchFormatLegDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the matchFormatLegDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MatchFormatLegDTO> partialUpdateMatchFormatLeg(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody MatchFormatLegDTO matchFormatLegDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update MatchFormatLeg partially : {}, {}", id, matchFormatLegDTO);
        if (matchFormatLegDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, matchFormatLegDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!matchFormatLegRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MatchFormatLegDTO> result = matchFormatLegService.partialUpdate(matchFormatLegDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, matchFormatLegDTO.getId())
        );
    }

    /**
     * {@code GET  /match-format-legs} : get all the matchFormatLegs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of matchFormatLegs in body.
     */
    @GetMapping("")
    public List<MatchFormatLegDTO> getAllMatchFormatLegs() {
        log.debug("REST request to get all MatchFormatLegs");
        return matchFormatLegService.findAll();
    }

    /**
     * {@code GET  /match-format-legs/:id} : get the "id" matchFormatLeg.
     *
     * @param id the id of the matchFormatLegDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the matchFormatLegDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<MatchFormatLegDTO> getMatchFormatLeg(@PathVariable("id") String id) {
        log.debug("REST request to get MatchFormatLeg : {}", id);
        Optional<MatchFormatLegDTO> matchFormatLegDTO = matchFormatLegService.findOne(id);
        return ResponseUtil.wrapOrNotFound(matchFormatLegDTO);
    }

    /**
     * {@code DELETE  /match-format-legs/:id} : delete the "id" matchFormatLeg.
     *
     * @param id the id of the matchFormatLegDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMatchFormatLeg(@PathVariable("id") String id) {
        log.debug("REST request to delete MatchFormatLeg : {}", id);
        matchFormatLegService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
