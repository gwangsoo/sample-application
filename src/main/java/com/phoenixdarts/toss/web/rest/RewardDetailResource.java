package com.phoenixdarts.toss.web.rest;

import com.phoenixdarts.toss.repository.RewardDetailRepository;
import com.phoenixdarts.toss.service.RewardDetailService;
import com.phoenixdarts.toss.service.dto.RewardDetailDTO;
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
 * REST controller for managing {@link com.phoenixdarts.toss.domain.RewardDetail}.
 */
@RestController
@RequestMapping("/api/reward-details")
public class RewardDetailResource {

    private final Logger log = LoggerFactory.getLogger(RewardDetailResource.class);

    private static final String ENTITY_NAME = "rewardDetail";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RewardDetailService rewardDetailService;

    private final RewardDetailRepository rewardDetailRepository;

    public RewardDetailResource(RewardDetailService rewardDetailService, RewardDetailRepository rewardDetailRepository) {
        this.rewardDetailService = rewardDetailService;
        this.rewardDetailRepository = rewardDetailRepository;
    }

    /**
     * {@code POST  /reward-details} : Create a new rewardDetail.
     *
     * @param rewardDetailDTO the rewardDetailDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new rewardDetailDTO, or with status {@code 400 (Bad Request)} if the rewardDetail has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<RewardDetailDTO> createRewardDetail(@Valid @RequestBody RewardDetailDTO rewardDetailDTO)
        throws URISyntaxException {
        log.debug("REST request to save RewardDetail : {}", rewardDetailDTO);
        if (rewardDetailDTO.getId() != null) {
            throw new BadRequestAlertException("A new rewardDetail cannot already have an ID", ENTITY_NAME, "idexists");
        }
        rewardDetailDTO = rewardDetailService.save(rewardDetailDTO);
        return ResponseEntity.created(new URI("/api/reward-details/" + rewardDetailDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, rewardDetailDTO.getId()))
            .body(rewardDetailDTO);
    }

    /**
     * {@code PUT  /reward-details/:id} : Updates an existing rewardDetail.
     *
     * @param id the id of the rewardDetailDTO to save.
     * @param rewardDetailDTO the rewardDetailDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rewardDetailDTO,
     * or with status {@code 400 (Bad Request)} if the rewardDetailDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the rewardDetailDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<RewardDetailDTO> updateRewardDetail(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody RewardDetailDTO rewardDetailDTO
    ) throws URISyntaxException {
        log.debug("REST request to update RewardDetail : {}, {}", id, rewardDetailDTO);
        if (rewardDetailDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rewardDetailDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rewardDetailRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        rewardDetailDTO = rewardDetailService.update(rewardDetailDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, rewardDetailDTO.getId()))
            .body(rewardDetailDTO);
    }

    /**
     * {@code PATCH  /reward-details/:id} : Partial updates given fields of an existing rewardDetail, field will ignore if it is null
     *
     * @param id the id of the rewardDetailDTO to save.
     * @param rewardDetailDTO the rewardDetailDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rewardDetailDTO,
     * or with status {@code 400 (Bad Request)} if the rewardDetailDTO is not valid,
     * or with status {@code 404 (Not Found)} if the rewardDetailDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the rewardDetailDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RewardDetailDTO> partialUpdateRewardDetail(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody RewardDetailDTO rewardDetailDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update RewardDetail partially : {}, {}", id, rewardDetailDTO);
        if (rewardDetailDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rewardDetailDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rewardDetailRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RewardDetailDTO> result = rewardDetailService.partialUpdate(rewardDetailDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, rewardDetailDTO.getId())
        );
    }

    /**
     * {@code GET  /reward-details} : get all the rewardDetails.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rewardDetails in body.
     */
    @GetMapping("")
    public List<RewardDetailDTO> getAllRewardDetails() {
        log.debug("REST request to get all RewardDetails");
        return rewardDetailService.findAll();
    }

    /**
     * {@code GET  /reward-details/:id} : get the "id" rewardDetail.
     *
     * @param id the id of the rewardDetailDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the rewardDetailDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<RewardDetailDTO> getRewardDetail(@PathVariable("id") String id) {
        log.debug("REST request to get RewardDetail : {}", id);
        Optional<RewardDetailDTO> rewardDetailDTO = rewardDetailService.findOne(id);
        return ResponseUtil.wrapOrNotFound(rewardDetailDTO);
    }

    /**
     * {@code DELETE  /reward-details/:id} : delete the "id" rewardDetail.
     *
     * @param id the id of the rewardDetailDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRewardDetail(@PathVariable("id") String id) {
        log.debug("REST request to delete RewardDetail : {}", id);
        rewardDetailService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
