package com.phoenixdarts.toss.backend.web.rest;

import com.phoenixdarts.toss.backend.repository.MatchFormatGameRepository;
import com.phoenixdarts.toss.backend.service.MatchFormatGameService;
import com.phoenixdarts.toss.backend.service.dto.MatchFormatGameDTO;
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
 * REST controller for managing {@link com.phoenixdarts.toss.backend.domain.MatchFormatGame}.
 */
@RestController
@RequestMapping("/api/match-format-games")
public class MatchFormatGameResource {

    private final Logger log = LoggerFactory.getLogger(MatchFormatGameResource.class);

    private static final String ENTITY_NAME = "matchFormatGame";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MatchFormatGameService matchFormatGameService;

    private final MatchFormatGameRepository matchFormatGameRepository;

    public MatchFormatGameResource(MatchFormatGameService matchFormatGameService, MatchFormatGameRepository matchFormatGameRepository) {
        this.matchFormatGameService = matchFormatGameService;
        this.matchFormatGameRepository = matchFormatGameRepository;
    }

    /**
     * {@code POST  /match-format-games} : Create a new matchFormatGame.
     *
     * @param matchFormatGameDTO the matchFormatGameDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new matchFormatGameDTO, or with status {@code 400 (Bad Request)} if the matchFormatGame has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<MatchFormatGameDTO> createMatchFormatGame(@Valid @RequestBody MatchFormatGameDTO matchFormatGameDTO)
        throws URISyntaxException {
        log.debug("REST request to save MatchFormatGame : {}", matchFormatGameDTO);
        if (matchFormatGameDTO.getId() != null) {
            throw new BadRequestAlertException("A new matchFormatGame cannot already have an ID", ENTITY_NAME, "idexists");
        }
        matchFormatGameDTO = matchFormatGameService.save(matchFormatGameDTO);
        return ResponseEntity.created(new URI("/api/match-format-games/" + matchFormatGameDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, matchFormatGameDTO.getId()))
            .body(matchFormatGameDTO);
    }

    /**
     * {@code PUT  /match-format-games/:id} : Updates an existing matchFormatGame.
     *
     * @param id the id of the matchFormatGameDTO to save.
     * @param matchFormatGameDTO the matchFormatGameDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated matchFormatGameDTO,
     * or with status {@code 400 (Bad Request)} if the matchFormatGameDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the matchFormatGameDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<MatchFormatGameDTO> updateMatchFormatGame(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody MatchFormatGameDTO matchFormatGameDTO
    ) throws URISyntaxException {
        log.debug("REST request to update MatchFormatGame : {}, {}", id, matchFormatGameDTO);
        if (matchFormatGameDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, matchFormatGameDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!matchFormatGameRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        matchFormatGameDTO = matchFormatGameService.update(matchFormatGameDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, matchFormatGameDTO.getId()))
            .body(matchFormatGameDTO);
    }

    /**
     * {@code PATCH  /match-format-games/:id} : Partial updates given fields of an existing matchFormatGame, field will ignore if it is null
     *
     * @param id the id of the matchFormatGameDTO to save.
     * @param matchFormatGameDTO the matchFormatGameDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated matchFormatGameDTO,
     * or with status {@code 400 (Bad Request)} if the matchFormatGameDTO is not valid,
     * or with status {@code 404 (Not Found)} if the matchFormatGameDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the matchFormatGameDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MatchFormatGameDTO> partialUpdateMatchFormatGame(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody MatchFormatGameDTO matchFormatGameDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update MatchFormatGame partially : {}, {}", id, matchFormatGameDTO);
        if (matchFormatGameDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, matchFormatGameDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!matchFormatGameRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MatchFormatGameDTO> result = matchFormatGameService.partialUpdate(matchFormatGameDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, matchFormatGameDTO.getId())
        );
    }

    /**
     * {@code GET  /match-format-games} : get all the matchFormatGames.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of matchFormatGames in body.
     */
    @GetMapping("")
    public List<MatchFormatGameDTO> getAllMatchFormatGames() {
        log.debug("REST request to get all MatchFormatGames");
        return matchFormatGameService.findAll();
    }

    /**
     * {@code GET  /match-format-games/:id} : get the "id" matchFormatGame.
     *
     * @param id the id of the matchFormatGameDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the matchFormatGameDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<MatchFormatGameDTO> getMatchFormatGame(@PathVariable("id") String id) {
        log.debug("REST request to get MatchFormatGame : {}", id);
        Optional<MatchFormatGameDTO> matchFormatGameDTO = matchFormatGameService.findOne(id);
        return ResponseUtil.wrapOrNotFound(matchFormatGameDTO);
    }

    /**
     * {@code DELETE  /match-format-games/:id} : delete the "id" matchFormatGame.
     *
     * @param id the id of the matchFormatGameDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMatchFormatGame(@PathVariable("id") String id) {
        log.debug("REST request to delete MatchFormatGame : {}", id);
        matchFormatGameService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
