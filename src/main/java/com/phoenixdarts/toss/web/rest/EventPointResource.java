package com.phoenixdarts.toss.backend.web.rest;

import com.phoenixdarts.toss.backend.repository.EventPointRepository;
import com.phoenixdarts.toss.backend.service.EventPointService;
import com.phoenixdarts.toss.backend.service.dto.EventPointDTO;
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
 * REST controller for managing {@link com.phoenixdarts.toss.backend.domain.EventPoint}.
 */
@RestController
@RequestMapping("/api/event-points")
public class EventPointResource {

    private final Logger log = LoggerFactory.getLogger(EventPointResource.class);

    private static final String ENTITY_NAME = "eventPoint";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EventPointService eventPointService;

    private final EventPointRepository eventPointRepository;

    public EventPointResource(EventPointService eventPointService, EventPointRepository eventPointRepository) {
        this.eventPointService = eventPointService;
        this.eventPointRepository = eventPointRepository;
    }

    /**
     * {@code POST  /event-points} : Create a new eventPoint.
     *
     * @param eventPointDTO the eventPointDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new eventPointDTO, or with status {@code 400 (Bad Request)} if the eventPoint has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<EventPointDTO> createEventPoint(@Valid @RequestBody EventPointDTO eventPointDTO) throws URISyntaxException {
        log.debug("REST request to save EventPoint : {}", eventPointDTO);
        if (eventPointDTO.getId() != null) {
            throw new BadRequestAlertException("A new eventPoint cannot already have an ID", ENTITY_NAME, "idexists");
        }
        eventPointDTO = eventPointService.save(eventPointDTO);
        return ResponseEntity.created(new URI("/api/event-points/" + eventPointDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, eventPointDTO.getId()))
            .body(eventPointDTO);
    }

    /**
     * {@code PUT  /event-points/:id} : Updates an existing eventPoint.
     *
     * @param id the id of the eventPointDTO to save.
     * @param eventPointDTO the eventPointDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated eventPointDTO,
     * or with status {@code 400 (Bad Request)} if the eventPointDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the eventPointDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<EventPointDTO> updateEventPoint(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody EventPointDTO eventPointDTO
    ) throws URISyntaxException {
        log.debug("REST request to update EventPoint : {}, {}", id, eventPointDTO);
        if (eventPointDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, eventPointDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!eventPointRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        eventPointDTO = eventPointService.update(eventPointDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, eventPointDTO.getId()))
            .body(eventPointDTO);
    }

    /**
     * {@code PATCH  /event-points/:id} : Partial updates given fields of an existing eventPoint, field will ignore if it is null
     *
     * @param id the id of the eventPointDTO to save.
     * @param eventPointDTO the eventPointDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated eventPointDTO,
     * or with status {@code 400 (Bad Request)} if the eventPointDTO is not valid,
     * or with status {@code 404 (Not Found)} if the eventPointDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the eventPointDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EventPointDTO> partialUpdateEventPoint(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody EventPointDTO eventPointDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update EventPoint partially : {}, {}", id, eventPointDTO);
        if (eventPointDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, eventPointDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!eventPointRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EventPointDTO> result = eventPointService.partialUpdate(eventPointDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, eventPointDTO.getId())
        );
    }

    /**
     * {@code GET  /event-points} : get all the eventPoints.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of eventPoints in body.
     */
    @GetMapping("")
    public List<EventPointDTO> getAllEventPoints() {
        log.debug("REST request to get all EventPoints");
        return eventPointService.findAll();
    }

    /**
     * {@code GET  /event-points/:id} : get the "id" eventPoint.
     *
     * @param id the id of the eventPointDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the eventPointDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<EventPointDTO> getEventPoint(@PathVariable("id") String id) {
        log.debug("REST request to get EventPoint : {}", id);
        Optional<EventPointDTO> eventPointDTO = eventPointService.findOne(id);
        return ResponseUtil.wrapOrNotFound(eventPointDTO);
    }

    /**
     * {@code DELETE  /event-points/:id} : delete the "id" eventPoint.
     *
     * @param id the id of the eventPointDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEventPoint(@PathVariable("id") String id) {
        log.debug("REST request to delete EventPoint : {}", id);
        eventPointService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
