package com.phoenixdarts.toss.backend.web.rest;

import com.phoenixdarts.toss.backend.repository.MatchFormatOptionRepository;
import com.phoenixdarts.toss.backend.service.MatchFormatOptionService;
import com.phoenixdarts.toss.backend.service.dto.MatchFormatOptionDTO;
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
 * REST controller for managing {@link com.phoenixdarts.toss.backend.domain.MatchFormatOption}.
 */
@RestController
@RequestMapping("/api/match-format-options")
public class MatchFormatOptionResource {

    private final Logger log = LoggerFactory.getLogger(MatchFormatOptionResource.class);

    private static final String ENTITY_NAME = "matchFormatOption";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MatchFormatOptionService matchFormatOptionService;

    private final MatchFormatOptionRepository matchFormatOptionRepository;

    public MatchFormatOptionResource(
        MatchFormatOptionService matchFormatOptionService,
        MatchFormatOptionRepository matchFormatOptionRepository
    ) {
        this.matchFormatOptionService = matchFormatOptionService;
        this.matchFormatOptionRepository = matchFormatOptionRepository;
    }

    /**
     * {@code POST  /match-format-options} : Create a new matchFormatOption.
     *
     * @param matchFormatOptionDTO the matchFormatOptionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new matchFormatOptionDTO, or with status {@code 400 (Bad Request)} if the matchFormatOption has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<MatchFormatOptionDTO> createMatchFormatOption(@Valid @RequestBody MatchFormatOptionDTO matchFormatOptionDTO)
        throws URISyntaxException {
        log.debug("REST request to save MatchFormatOption : {}", matchFormatOptionDTO);
        if (matchFormatOptionDTO.getId() != null) {
            throw new BadRequestAlertException("A new matchFormatOption cannot already have an ID", ENTITY_NAME, "idexists");
        }
        matchFormatOptionDTO = matchFormatOptionService.save(matchFormatOptionDTO);
        return ResponseEntity.created(new URI("/api/match-format-options/" + matchFormatOptionDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, matchFormatOptionDTO.getId()))
            .body(matchFormatOptionDTO);
    }

    /**
     * {@code PUT  /match-format-options/:id} : Updates an existing matchFormatOption.
     *
     * @param id the id of the matchFormatOptionDTO to save.
     * @param matchFormatOptionDTO the matchFormatOptionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated matchFormatOptionDTO,
     * or with status {@code 400 (Bad Request)} if the matchFormatOptionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the matchFormatOptionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<MatchFormatOptionDTO> updateMatchFormatOption(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody MatchFormatOptionDTO matchFormatOptionDTO
    ) throws URISyntaxException {
        log.debug("REST request to update MatchFormatOption : {}, {}", id, matchFormatOptionDTO);
        if (matchFormatOptionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, matchFormatOptionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!matchFormatOptionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        matchFormatOptionDTO = matchFormatOptionService.update(matchFormatOptionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, matchFormatOptionDTO.getId()))
            .body(matchFormatOptionDTO);
    }

    /**
     * {@code PATCH  /match-format-options/:id} : Partial updates given fields of an existing matchFormatOption, field will ignore if it is null
     *
     * @param id the id of the matchFormatOptionDTO to save.
     * @param matchFormatOptionDTO the matchFormatOptionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated matchFormatOptionDTO,
     * or with status {@code 400 (Bad Request)} if the matchFormatOptionDTO is not valid,
     * or with status {@code 404 (Not Found)} if the matchFormatOptionDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the matchFormatOptionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MatchFormatOptionDTO> partialUpdateMatchFormatOption(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody MatchFormatOptionDTO matchFormatOptionDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update MatchFormatOption partially : {}, {}", id, matchFormatOptionDTO);
        if (matchFormatOptionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, matchFormatOptionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!matchFormatOptionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MatchFormatOptionDTO> result = matchFormatOptionService.partialUpdate(matchFormatOptionDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, matchFormatOptionDTO.getId())
        );
    }

    /**
     * {@code GET  /match-format-options} : get all the matchFormatOptions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of matchFormatOptions in body.
     */
    @GetMapping("")
    public List<MatchFormatOptionDTO> getAllMatchFormatOptions() {
        log.debug("REST request to get all MatchFormatOptions");
        return matchFormatOptionService.findAll();
    }

    /**
     * {@code GET  /match-format-options/:id} : get the "id" matchFormatOption.
     *
     * @param id the id of the matchFormatOptionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the matchFormatOptionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<MatchFormatOptionDTO> getMatchFormatOption(@PathVariable("id") String id) {
        log.debug("REST request to get MatchFormatOption : {}", id);
        Optional<MatchFormatOptionDTO> matchFormatOptionDTO = matchFormatOptionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(matchFormatOptionDTO);
    }

    /**
     * {@code DELETE  /match-format-options/:id} : delete the "id" matchFormatOption.
     *
     * @param id the id of the matchFormatOptionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMatchFormatOption(@PathVariable("id") String id) {
        log.debug("REST request to delete MatchFormatOption : {}", id);
        matchFormatOptionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
