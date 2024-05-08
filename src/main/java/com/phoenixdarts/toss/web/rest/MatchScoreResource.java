package com.phoenixdarts.toss.backend.web.rest;

import com.phoenixdarts.toss.backend.repository.MatchScoreRepository;
import com.phoenixdarts.toss.backend.service.MatchScoreService;
import com.phoenixdarts.toss.backend.service.dto.MatchScoreDTO;
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
 * REST controller for managing {@link com.phoenixdarts.toss.backend.domain.MatchScore}.
 */
@RestController
@RequestMapping("/api/match-scores")
public class MatchScoreResource {

    private final Logger log = LoggerFactory.getLogger(MatchScoreResource.class);

    private static final String ENTITY_NAME = "matchScore";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MatchScoreService matchScoreService;

    private final MatchScoreRepository matchScoreRepository;

    public MatchScoreResource(MatchScoreService matchScoreService, MatchScoreRepository matchScoreRepository) {
        this.matchScoreService = matchScoreService;
        this.matchScoreRepository = matchScoreRepository;
    }

    /**
     * {@code POST  /match-scores} : Create a new matchScore.
     *
     * @param matchScoreDTO the matchScoreDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new matchScoreDTO, or with status {@code 400 (Bad Request)} if the matchScore has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<MatchScoreDTO> createMatchScore(@Valid @RequestBody MatchScoreDTO matchScoreDTO) throws URISyntaxException {
        log.debug("REST request to save MatchScore : {}", matchScoreDTO);
        if (matchScoreDTO.getId() != null) {
            throw new BadRequestAlertException("A new matchScore cannot already have an ID", ENTITY_NAME, "idexists");
        }
        matchScoreDTO = matchScoreService.save(matchScoreDTO);
        return ResponseEntity.created(new URI("/api/match-scores/" + matchScoreDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, matchScoreDTO.getId()))
            .body(matchScoreDTO);
    }

    /**
     * {@code PUT  /match-scores/:id} : Updates an existing matchScore.
     *
     * @param id the id of the matchScoreDTO to save.
     * @param matchScoreDTO the matchScoreDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated matchScoreDTO,
     * or with status {@code 400 (Bad Request)} if the matchScoreDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the matchScoreDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<MatchScoreDTO> updateMatchScore(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody MatchScoreDTO matchScoreDTO
    ) throws URISyntaxException {
        log.debug("REST request to update MatchScore : {}, {}", id, matchScoreDTO);
        if (matchScoreDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, matchScoreDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!matchScoreRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        matchScoreDTO = matchScoreService.update(matchScoreDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, matchScoreDTO.getId()))
            .body(matchScoreDTO);
    }

    /**
     * {@code PATCH  /match-scores/:id} : Partial updates given fields of an existing matchScore, field will ignore if it is null
     *
     * @param id the id of the matchScoreDTO to save.
     * @param matchScoreDTO the matchScoreDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated matchScoreDTO,
     * or with status {@code 400 (Bad Request)} if the matchScoreDTO is not valid,
     * or with status {@code 404 (Not Found)} if the matchScoreDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the matchScoreDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MatchScoreDTO> partialUpdateMatchScore(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody MatchScoreDTO matchScoreDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update MatchScore partially : {}, {}", id, matchScoreDTO);
        if (matchScoreDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, matchScoreDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!matchScoreRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MatchScoreDTO> result = matchScoreService.partialUpdate(matchScoreDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, matchScoreDTO.getId())
        );
    }

    /**
     * {@code GET  /match-scores} : get all the matchScores.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of matchScores in body.
     */
    @GetMapping("")
    public List<MatchScoreDTO> getAllMatchScores() {
        log.debug("REST request to get all MatchScores");
        return matchScoreService.findAll();
    }

    /**
     * {@code GET  /match-scores/:id} : get the "id" matchScore.
     *
     * @param id the id of the matchScoreDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the matchScoreDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<MatchScoreDTO> getMatchScore(@PathVariable("id") String id) {
        log.debug("REST request to get MatchScore : {}", id);
        Optional<MatchScoreDTO> matchScoreDTO = matchScoreService.findOne(id);
        return ResponseUtil.wrapOrNotFound(matchScoreDTO);
    }

    /**
     * {@code DELETE  /match-scores/:id} : delete the "id" matchScore.
     *
     * @param id the id of the matchScoreDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMatchScore(@PathVariable("id") String id) {
        log.debug("REST request to delete MatchScore : {}", id);
        matchScoreService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
