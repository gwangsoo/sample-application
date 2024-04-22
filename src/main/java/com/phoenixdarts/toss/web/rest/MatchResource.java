package com.phoenixdarts.toss.web.rest;

import com.phoenixdarts.toss.repository.MatchRepository;
import com.phoenixdarts.toss.service.MatchService;
import com.phoenixdarts.toss.service.dto.MatchDTO;
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
 * REST controller for managing {@link com.phoenixdarts.toss.domain.Match}.
 */
@RestController
@RequestMapping("/api/matches")
public class MatchResource {

    private final Logger log = LoggerFactory.getLogger(MatchResource.class);

    private static final String ENTITY_NAME = "match";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MatchService matchService;

    private final MatchRepository matchRepository;

    public MatchResource(MatchService matchService, MatchRepository matchRepository) {
        this.matchService = matchService;
        this.matchRepository = matchRepository;
    }

    /**
     * {@code POST  /matches} : Create a new match.
     *
     * @param matchDTO the matchDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new matchDTO, or with status {@code 400 (Bad Request)} if the match has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<MatchDTO> createMatch(@Valid @RequestBody MatchDTO matchDTO) throws URISyntaxException {
        log.debug("REST request to save Match : {}", matchDTO);
        if (matchDTO.getId() != null) {
            throw new BadRequestAlertException("A new match cannot already have an ID", ENTITY_NAME, "idexists");
        }
        matchDTO = matchService.save(matchDTO);
        return ResponseEntity.created(new URI("/api/matches/" + matchDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, matchDTO.getId()))
            .body(matchDTO);
    }

    /**
     * {@code PUT  /matches/:id} : Updates an existing match.
     *
     * @param id the id of the matchDTO to save.
     * @param matchDTO the matchDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated matchDTO,
     * or with status {@code 400 (Bad Request)} if the matchDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the matchDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<MatchDTO> updateMatch(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody MatchDTO matchDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Match : {}, {}", id, matchDTO);
        if (matchDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, matchDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!matchRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        matchDTO = matchService.update(matchDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, matchDTO.getId()))
            .body(matchDTO);
    }

    /**
     * {@code PATCH  /matches/:id} : Partial updates given fields of an existing match, field will ignore if it is null
     *
     * @param id the id of the matchDTO to save.
     * @param matchDTO the matchDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated matchDTO,
     * or with status {@code 400 (Bad Request)} if the matchDTO is not valid,
     * or with status {@code 404 (Not Found)} if the matchDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the matchDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MatchDTO> partialUpdateMatch(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody MatchDTO matchDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Match partially : {}, {}", id, matchDTO);
        if (matchDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, matchDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!matchRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MatchDTO> result = matchService.partialUpdate(matchDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, matchDTO.getId())
        );
    }

    /**
     * {@code GET  /matches} : get all the matches.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of matches in body.
     */
    @GetMapping("")
    public List<MatchDTO> getAllMatches() {
        log.debug("REST request to get all Matches");
        return matchService.findAll();
    }

    /**
     * {@code GET  /matches/:id} : get the "id" match.
     *
     * @param id the id of the matchDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the matchDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<MatchDTO> getMatch(@PathVariable("id") String id) {
        log.debug("REST request to get Match : {}", id);
        Optional<MatchDTO> matchDTO = matchService.findOne(id);
        return ResponseUtil.wrapOrNotFound(matchDTO);
    }

    /**
     * {@code DELETE  /matches/:id} : delete the "id" match.
     *
     * @param id the id of the matchDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMatch(@PathVariable("id") String id) {
        log.debug("REST request to delete Match : {}", id);
        matchService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
