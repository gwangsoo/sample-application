package com.phoenixdarts.toss.web.rest;

import com.phoenixdarts.toss.repository.RewardItemRepository;
import com.phoenixdarts.toss.service.RewardItemService;
import com.phoenixdarts.toss.service.dto.RewardItemDTO;
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
 * REST controller for managing {@link com.phoenixdarts.toss.domain.RewardItem}.
 */
@RestController
@RequestMapping("/api/reward-items")
public class RewardItemResource {

    private final Logger log = LoggerFactory.getLogger(RewardItemResource.class);

    private static final String ENTITY_NAME = "rewardItem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RewardItemService rewardItemService;

    private final RewardItemRepository rewardItemRepository;

    public RewardItemResource(RewardItemService rewardItemService, RewardItemRepository rewardItemRepository) {
        this.rewardItemService = rewardItemService;
        this.rewardItemRepository = rewardItemRepository;
    }

    /**
     * {@code POST  /reward-items} : Create a new rewardItem.
     *
     * @param rewardItemDTO the rewardItemDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new rewardItemDTO, or with status {@code 400 (Bad Request)} if the rewardItem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<RewardItemDTO> createRewardItem(@Valid @RequestBody RewardItemDTO rewardItemDTO) throws URISyntaxException {
        log.debug("REST request to save RewardItem : {}", rewardItemDTO);
        if (rewardItemDTO.getId() != null) {
            throw new BadRequestAlertException("A new rewardItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        rewardItemDTO = rewardItemService.save(rewardItemDTO);
        return ResponseEntity.created(new URI("/api/reward-items/" + rewardItemDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, rewardItemDTO.getId()))
            .body(rewardItemDTO);
    }

    /**
     * {@code PUT  /reward-items/:id} : Updates an existing rewardItem.
     *
     * @param id the id of the rewardItemDTO to save.
     * @param rewardItemDTO the rewardItemDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rewardItemDTO,
     * or with status {@code 400 (Bad Request)} if the rewardItemDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the rewardItemDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<RewardItemDTO> updateRewardItem(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody RewardItemDTO rewardItemDTO
    ) throws URISyntaxException {
        log.debug("REST request to update RewardItem : {}, {}", id, rewardItemDTO);
        if (rewardItemDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rewardItemDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rewardItemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        rewardItemDTO = rewardItemService.update(rewardItemDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, rewardItemDTO.getId()))
            .body(rewardItemDTO);
    }

    /**
     * {@code PATCH  /reward-items/:id} : Partial updates given fields of an existing rewardItem, field will ignore if it is null
     *
     * @param id the id of the rewardItemDTO to save.
     * @param rewardItemDTO the rewardItemDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rewardItemDTO,
     * or with status {@code 400 (Bad Request)} if the rewardItemDTO is not valid,
     * or with status {@code 404 (Not Found)} if the rewardItemDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the rewardItemDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RewardItemDTO> partialUpdateRewardItem(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody RewardItemDTO rewardItemDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update RewardItem partially : {}, {}", id, rewardItemDTO);
        if (rewardItemDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rewardItemDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rewardItemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RewardItemDTO> result = rewardItemService.partialUpdate(rewardItemDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, rewardItemDTO.getId())
        );
    }

    /**
     * {@code GET  /reward-items} : get all the rewardItems.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rewardItems in body.
     */
    @GetMapping("")
    public List<RewardItemDTO> getAllRewardItems() {
        log.debug("REST request to get all RewardItems");
        return rewardItemService.findAll();
    }

    /**
     * {@code GET  /reward-items/:id} : get the "id" rewardItem.
     *
     * @param id the id of the rewardItemDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the rewardItemDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<RewardItemDTO> getRewardItem(@PathVariable("id") String id) {
        log.debug("REST request to get RewardItem : {}", id);
        Optional<RewardItemDTO> rewardItemDTO = rewardItemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(rewardItemDTO);
    }

    /**
     * {@code DELETE  /reward-items/:id} : delete the "id" rewardItem.
     *
     * @param id the id of the rewardItemDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRewardItem(@PathVariable("id") String id) {
        log.debug("REST request to delete RewardItem : {}", id);
        rewardItemService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
