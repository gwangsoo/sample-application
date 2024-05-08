package com.phoenixdarts.toss.backend.web.rest;

import com.phoenixdarts.toss.backend.repository.OperatorRepository;
import com.phoenixdarts.toss.backend.service.OperatorService;
import com.phoenixdarts.toss.backend.service.dto.OperatorDTO;
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
 * REST controller for managing {@link com.phoenixdarts.toss.backend.domain.Operator}.
 */
@RestController
@RequestMapping("/api/operators")
public class OperatorResource {

    private final Logger log = LoggerFactory.getLogger(OperatorResource.class);

    private static final String ENTITY_NAME = "operator";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OperatorService operatorService;

    private final OperatorRepository operatorRepository;

    public OperatorResource(OperatorService operatorService, OperatorRepository operatorRepository) {
        this.operatorService = operatorService;
        this.operatorRepository = operatorRepository;
    }

    /**
     * {@code POST  /operators} : Create a new operator.
     *
     * @param operatorDTO the operatorDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new operatorDTO, or with status {@code 400 (Bad Request)} if the operator has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<OperatorDTO> createOperator(@Valid @RequestBody OperatorDTO operatorDTO) throws URISyntaxException {
        log.debug("REST request to save Operator : {}", operatorDTO);
        if (operatorDTO.getId() != null) {
            throw new BadRequestAlertException("A new operator cannot already have an ID", ENTITY_NAME, "idexists");
        }
        operatorDTO = operatorService.save(operatorDTO);
        return ResponseEntity.created(new URI("/api/operators/" + operatorDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, operatorDTO.getId()))
            .body(operatorDTO);
    }

    /**
     * {@code PUT  /operators/:id} : Updates an existing operator.
     *
     * @param id the id of the operatorDTO to save.
     * @param operatorDTO the operatorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated operatorDTO,
     * or with status {@code 400 (Bad Request)} if the operatorDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the operatorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<OperatorDTO> updateOperator(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody OperatorDTO operatorDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Operator : {}, {}", id, operatorDTO);
        if (operatorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, operatorDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!operatorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        operatorDTO = operatorService.update(operatorDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, operatorDTO.getId()))
            .body(operatorDTO);
    }

    /**
     * {@code PATCH  /operators/:id} : Partial updates given fields of an existing operator, field will ignore if it is null
     *
     * @param id the id of the operatorDTO to save.
     * @param operatorDTO the operatorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated operatorDTO,
     * or with status {@code 400 (Bad Request)} if the operatorDTO is not valid,
     * or with status {@code 404 (Not Found)} if the operatorDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the operatorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<OperatorDTO> partialUpdateOperator(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody OperatorDTO operatorDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Operator partially : {}, {}", id, operatorDTO);
        if (operatorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, operatorDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!operatorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OperatorDTO> result = operatorService.partialUpdate(operatorDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, operatorDTO.getId())
        );
    }

    /**
     * {@code GET  /operators} : get all the operators.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of operators in body.
     */
    @GetMapping("")
    public List<OperatorDTO> getAllOperators() {
        log.debug("REST request to get all Operators");
        return operatorService.findAll();
    }

    /**
     * {@code GET  /operators/:id} : get the "id" operator.
     *
     * @param id the id of the operatorDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the operatorDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<OperatorDTO> getOperator(@PathVariable("id") String id) {
        log.debug("REST request to get Operator : {}", id);
        Optional<OperatorDTO> operatorDTO = operatorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(operatorDTO);
    }

    /**
     * {@code DELETE  /operators/:id} : delete the "id" operator.
     *
     * @param id the id of the operatorDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOperator(@PathVariable("id") String id) {
        log.debug("REST request to delete Operator : {}", id);
        operatorService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
