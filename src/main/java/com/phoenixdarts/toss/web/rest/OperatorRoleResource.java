package com.phoenixdarts.toss.web.rest;

import com.phoenixdarts.toss.repository.OperatorRoleRepository;
import com.phoenixdarts.toss.service.OperatorRoleService;
import com.phoenixdarts.toss.service.dto.OperatorRoleDTO;
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
 * REST controller for managing {@link com.phoenixdarts.toss.domain.OperatorRole}.
 */
@RestController
@RequestMapping("/api/operator-roles")
public class OperatorRoleResource {

    private final Logger log = LoggerFactory.getLogger(OperatorRoleResource.class);

    private static final String ENTITY_NAME = "operatorRole";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OperatorRoleService operatorRoleService;

    private final OperatorRoleRepository operatorRoleRepository;

    public OperatorRoleResource(OperatorRoleService operatorRoleService, OperatorRoleRepository operatorRoleRepository) {
        this.operatorRoleService = operatorRoleService;
        this.operatorRoleRepository = operatorRoleRepository;
    }

    /**
     * {@code POST  /operator-roles} : Create a new operatorRole.
     *
     * @param operatorRoleDTO the operatorRoleDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new operatorRoleDTO, or with status {@code 400 (Bad Request)} if the operatorRole has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<OperatorRoleDTO> createOperatorRole(@Valid @RequestBody OperatorRoleDTO operatorRoleDTO)
        throws URISyntaxException {
        log.debug("REST request to save OperatorRole : {}", operatorRoleDTO);
        if (operatorRoleDTO.getId() != null) {
            throw new BadRequestAlertException("A new operatorRole cannot already have an ID", ENTITY_NAME, "idexists");
        }
        operatorRoleDTO = operatorRoleService.save(operatorRoleDTO);
        return ResponseEntity.created(new URI("/api/operator-roles/" + operatorRoleDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, operatorRoleDTO.getId()))
            .body(operatorRoleDTO);
    }

    /**
     * {@code PUT  /operator-roles/:id} : Updates an existing operatorRole.
     *
     * @param id the id of the operatorRoleDTO to save.
     * @param operatorRoleDTO the operatorRoleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated operatorRoleDTO,
     * or with status {@code 400 (Bad Request)} if the operatorRoleDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the operatorRoleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<OperatorRoleDTO> updateOperatorRole(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody OperatorRoleDTO operatorRoleDTO
    ) throws URISyntaxException {
        log.debug("REST request to update OperatorRole : {}, {}", id, operatorRoleDTO);
        if (operatorRoleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, operatorRoleDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!operatorRoleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        operatorRoleDTO = operatorRoleService.update(operatorRoleDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, operatorRoleDTO.getId()))
            .body(operatorRoleDTO);
    }

    /**
     * {@code PATCH  /operator-roles/:id} : Partial updates given fields of an existing operatorRole, field will ignore if it is null
     *
     * @param id the id of the operatorRoleDTO to save.
     * @param operatorRoleDTO the operatorRoleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated operatorRoleDTO,
     * or with status {@code 400 (Bad Request)} if the operatorRoleDTO is not valid,
     * or with status {@code 404 (Not Found)} if the operatorRoleDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the operatorRoleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<OperatorRoleDTO> partialUpdateOperatorRole(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody OperatorRoleDTO operatorRoleDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update OperatorRole partially : {}, {}", id, operatorRoleDTO);
        if (operatorRoleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, operatorRoleDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!operatorRoleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OperatorRoleDTO> result = operatorRoleService.partialUpdate(operatorRoleDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, operatorRoleDTO.getId())
        );
    }

    /**
     * {@code GET  /operator-roles} : get all the operatorRoles.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of operatorRoles in body.
     */
    @GetMapping("")
    public List<OperatorRoleDTO> getAllOperatorRoles() {
        log.debug("REST request to get all OperatorRoles");
        return operatorRoleService.findAll();
    }

    /**
     * {@code GET  /operator-roles/:id} : get the "id" operatorRole.
     *
     * @param id the id of the operatorRoleDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the operatorRoleDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<OperatorRoleDTO> getOperatorRole(@PathVariable("id") String id) {
        log.debug("REST request to get OperatorRole : {}", id);
        Optional<OperatorRoleDTO> operatorRoleDTO = operatorRoleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(operatorRoleDTO);
    }

    /**
     * {@code DELETE  /operator-roles/:id} : delete the "id" operatorRole.
     *
     * @param id the id of the operatorRoleDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOperatorRole(@PathVariable("id") String id) {
        log.debug("REST request to delete OperatorRole : {}", id);
        operatorRoleService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
