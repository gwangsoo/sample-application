package com.phoenixdarts.toss.backend.web.rest;

import com.phoenixdarts.toss.backend.repository.MatchTeamRepository;
import com.phoenixdarts.toss.backend.service.MatchTeamService;
import com.phoenixdarts.toss.backend.service.dto.MatchTeamDTO;
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
 * REST controller for managing {@link com.phoenixdarts.toss.backend.domain.MatchTeam}.
 */
@RestController
@RequestMapping("/api/match-teams")
public class MatchTeamResource {

    private final Logger log = LoggerFactory.getLogger(MatchTeamResource.class);

    private static final String ENTITY_NAME = "matchTeam";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MatchTeamService matchTeamService;

    private final MatchTeamRepository matchTeamRepository;

    public MatchTeamResource(MatchTeamService matchTeamService, MatchTeamRepository matchTeamRepository) {
        this.matchTeamService = matchTeamService;
        this.matchTeamRepository = matchTeamRepository;
    }

    /**
     * {@code POST  /match-teams} : Create a new matchTeam.
     *
     * @param matchTeamDTO the matchTeamDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new matchTeamDTO, or with status {@code 400 (Bad Request)} if the matchTeam has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<MatchTeamDTO> createMatchTeam(@Valid @RequestBody MatchTeamDTO matchTeamDTO) throws URISyntaxException {
        log.debug("REST request to save MatchTeam : {}", matchTeamDTO);
        if (matchTeamDTO.getId() != null) {
            throw new BadRequestAlertException("A new matchTeam cannot already have an ID", ENTITY_NAME, "idexists");
        }
        matchTeamDTO = matchTeamService.save(matchTeamDTO);
        return ResponseEntity.created(new URI("/api/match-teams/" + matchTeamDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, matchTeamDTO.getId()))
            .body(matchTeamDTO);
    }

    /**
     * {@code PUT  /match-teams/:id} : Updates an existing matchTeam.
     *
     * @param id the id of the matchTeamDTO to save.
     * @param matchTeamDTO the matchTeamDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated matchTeamDTO,
     * or with status {@code 400 (Bad Request)} if the matchTeamDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the matchTeamDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<MatchTeamDTO> updateMatchTeam(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody MatchTeamDTO matchTeamDTO
    ) throws URISyntaxException {
        log.debug("REST request to update MatchTeam : {}, {}", id, matchTeamDTO);
        if (matchTeamDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, matchTeamDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!matchTeamRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        matchTeamDTO = matchTeamService.update(matchTeamDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, matchTeamDTO.getId()))
            .body(matchTeamDTO);
    }

    /**
     * {@code PATCH  /match-teams/:id} : Partial updates given fields of an existing matchTeam, field will ignore if it is null
     *
     * @param id the id of the matchTeamDTO to save.
     * @param matchTeamDTO the matchTeamDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated matchTeamDTO,
     * or with status {@code 400 (Bad Request)} if the matchTeamDTO is not valid,
     * or with status {@code 404 (Not Found)} if the matchTeamDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the matchTeamDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MatchTeamDTO> partialUpdateMatchTeam(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody MatchTeamDTO matchTeamDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update MatchTeam partially : {}, {}", id, matchTeamDTO);
        if (matchTeamDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, matchTeamDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!matchTeamRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MatchTeamDTO> result = matchTeamService.partialUpdate(matchTeamDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, matchTeamDTO.getId())
        );
    }

    /**
     * {@code GET  /match-teams} : get all the matchTeams.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of matchTeams in body.
     */
    @GetMapping("")
    public List<MatchTeamDTO> getAllMatchTeams() {
        log.debug("REST request to get all MatchTeams");
        return matchTeamService.findAll();
    }

    /**
     * {@code GET  /match-teams/:id} : get the "id" matchTeam.
     *
     * @param id the id of the matchTeamDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the matchTeamDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<MatchTeamDTO> getMatchTeam(@PathVariable("id") String id) {
        log.debug("REST request to get MatchTeam : {}", id);
        Optional<MatchTeamDTO> matchTeamDTO = matchTeamService.findOne(id);
        return ResponseUtil.wrapOrNotFound(matchTeamDTO);
    }

    /**
     * {@code DELETE  /match-teams/:id} : delete the "id" matchTeam.
     *
     * @param id the id of the matchTeamDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMatchTeam(@PathVariable("id") String id) {
        log.debug("REST request to delete MatchTeam : {}", id);
        matchTeamService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
