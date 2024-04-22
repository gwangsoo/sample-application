package com.phoenixdarts.toss.web.rest;

import com.phoenixdarts.toss.repository.MatchFormatSetRepository;
import com.phoenixdarts.toss.service.MatchFormatSetService;
import com.phoenixdarts.toss.service.dto.MatchFormatSetDTO;
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
 * REST controller for managing {@link com.phoenixdarts.toss.domain.MatchFormatSet}.
 */
@RestController
@RequestMapping("/api/match-format-sets")
public class MatchFormatSetResource {

    private final Logger log = LoggerFactory.getLogger(MatchFormatSetResource.class);

    private static final String ENTITY_NAME = "matchFormatSet";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MatchFormatSetService matchFormatSetService;

    private final MatchFormatSetRepository matchFormatSetRepository;

    public MatchFormatSetResource(MatchFormatSetService matchFormatSetService, MatchFormatSetRepository matchFormatSetRepository) {
        this.matchFormatSetService = matchFormatSetService;
        this.matchFormatSetRepository = matchFormatSetRepository;
    }

    /**
     * {@code POST  /match-format-sets} : Create a new matchFormatSet.
     *
     * @param matchFormatSetDTO the matchFormatSetDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new matchFormatSetDTO, or with status {@code 400 (Bad Request)} if the matchFormatSet has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<MatchFormatSetDTO> createMatchFormatSet(@Valid @RequestBody MatchFormatSetDTO matchFormatSetDTO)
        throws URISyntaxException {
        log.debug("REST request to save MatchFormatSet : {}", matchFormatSetDTO);
        if (matchFormatSetDTO.getId() != null) {
            throw new BadRequestAlertException("A new matchFormatSet cannot already have an ID", ENTITY_NAME, "idexists");
        }
        matchFormatSetDTO = matchFormatSetService.save(matchFormatSetDTO);
        return ResponseEntity.created(new URI("/api/match-format-sets/" + matchFormatSetDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, matchFormatSetDTO.getId()))
            .body(matchFormatSetDTO);
    }

    /**
     * {@code PUT  /match-format-sets/:id} : Updates an existing matchFormatSet.
     *
     * @param id the id of the matchFormatSetDTO to save.
     * @param matchFormatSetDTO the matchFormatSetDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated matchFormatSetDTO,
     * or with status {@code 400 (Bad Request)} if the matchFormatSetDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the matchFormatSetDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<MatchFormatSetDTO> updateMatchFormatSet(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody MatchFormatSetDTO matchFormatSetDTO
    ) throws URISyntaxException {
        log.debug("REST request to update MatchFormatSet : {}, {}", id, matchFormatSetDTO);
        if (matchFormatSetDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, matchFormatSetDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!matchFormatSetRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        matchFormatSetDTO = matchFormatSetService.update(matchFormatSetDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, matchFormatSetDTO.getId()))
            .body(matchFormatSetDTO);
    }

    /**
     * {@code PATCH  /match-format-sets/:id} : Partial updates given fields of an existing matchFormatSet, field will ignore if it is null
     *
     * @param id the id of the matchFormatSetDTO to save.
     * @param matchFormatSetDTO the matchFormatSetDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated matchFormatSetDTO,
     * or with status {@code 400 (Bad Request)} if the matchFormatSetDTO is not valid,
     * or with status {@code 404 (Not Found)} if the matchFormatSetDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the matchFormatSetDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MatchFormatSetDTO> partialUpdateMatchFormatSet(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody MatchFormatSetDTO matchFormatSetDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update MatchFormatSet partially : {}, {}", id, matchFormatSetDTO);
        if (matchFormatSetDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, matchFormatSetDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!matchFormatSetRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MatchFormatSetDTO> result = matchFormatSetService.partialUpdate(matchFormatSetDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, matchFormatSetDTO.getId())
        );
    }

    /**
     * {@code GET  /match-format-sets} : get all the matchFormatSets.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of matchFormatSets in body.
     */
    @GetMapping("")
    public List<MatchFormatSetDTO> getAllMatchFormatSets() {
        log.debug("REST request to get all MatchFormatSets");
        return matchFormatSetService.findAll();
    }

    /**
     * {@code GET  /match-format-sets/:id} : get the "id" matchFormatSet.
     *
     * @param id the id of the matchFormatSetDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the matchFormatSetDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<MatchFormatSetDTO> getMatchFormatSet(@PathVariable("id") String id) {
        log.debug("REST request to get MatchFormatSet : {}", id);
        Optional<MatchFormatSetDTO> matchFormatSetDTO = matchFormatSetService.findOne(id);
        return ResponseUtil.wrapOrNotFound(matchFormatSetDTO);
    }

    /**
     * {@code DELETE  /match-format-sets/:id} : delete the "id" matchFormatSet.
     *
     * @param id the id of the matchFormatSetDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMatchFormatSet(@PathVariable("id") String id) {
        log.debug("REST request to delete MatchFormatSet : {}", id);
        matchFormatSetService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
