package com.phoenixdarts.toss.backend.web.rest;

import com.phoenixdarts.toss.backend.repository.AffiliatedInfoRepository;
import com.phoenixdarts.toss.backend.service.AffiliatedInfoService;
import com.phoenixdarts.toss.backend.service.dto.AffiliatedInfoDTO;
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
 * REST controller for managing {@link com.phoenixdarts.toss.backend.domain.AffiliatedInfo}.
 */
@RestController
@RequestMapping("/api/affiliated-infos")
public class AffiliatedInfoResource {

    private final Logger log = LoggerFactory.getLogger(AffiliatedInfoResource.class);

    private static final String ENTITY_NAME = "affiliatedInfo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AffiliatedInfoService affiliatedInfoService;

    private final AffiliatedInfoRepository affiliatedInfoRepository;

    public AffiliatedInfoResource(AffiliatedInfoService affiliatedInfoService, AffiliatedInfoRepository affiliatedInfoRepository) {
        this.affiliatedInfoService = affiliatedInfoService;
        this.affiliatedInfoRepository = affiliatedInfoRepository;
    }

    /**
     * {@code POST  /affiliated-infos} : Create a new affiliatedInfo.
     *
     * @param affiliatedInfoDTO the affiliatedInfoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new affiliatedInfoDTO, or with status {@code 400 (Bad Request)} if the affiliatedInfo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<AffiliatedInfoDTO> createAffiliatedInfo(@Valid @RequestBody AffiliatedInfoDTO affiliatedInfoDTO)
        throws URISyntaxException {
        log.debug("REST request to save AffiliatedInfo : {}", affiliatedInfoDTO);
        if (affiliatedInfoDTO.getId() != null) {
            throw new BadRequestAlertException("A new affiliatedInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        affiliatedInfoDTO = affiliatedInfoService.save(affiliatedInfoDTO);
        return ResponseEntity.created(new URI("/api/affiliated-infos/" + affiliatedInfoDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, affiliatedInfoDTO.getId()))
            .body(affiliatedInfoDTO);
    }

    /**
     * {@code PUT  /affiliated-infos/:id} : Updates an existing affiliatedInfo.
     *
     * @param id the id of the affiliatedInfoDTO to save.
     * @param affiliatedInfoDTO the affiliatedInfoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated affiliatedInfoDTO,
     * or with status {@code 400 (Bad Request)} if the affiliatedInfoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the affiliatedInfoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<AffiliatedInfoDTO> updateAffiliatedInfo(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody AffiliatedInfoDTO affiliatedInfoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AffiliatedInfo : {}, {}", id, affiliatedInfoDTO);
        if (affiliatedInfoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, affiliatedInfoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!affiliatedInfoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        affiliatedInfoDTO = affiliatedInfoService.update(affiliatedInfoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, affiliatedInfoDTO.getId()))
            .body(affiliatedInfoDTO);
    }

    /**
     * {@code PATCH  /affiliated-infos/:id} : Partial updates given fields of an existing affiliatedInfo, field will ignore if it is null
     *
     * @param id the id of the affiliatedInfoDTO to save.
     * @param affiliatedInfoDTO the affiliatedInfoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated affiliatedInfoDTO,
     * or with status {@code 400 (Bad Request)} if the affiliatedInfoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the affiliatedInfoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the affiliatedInfoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AffiliatedInfoDTO> partialUpdateAffiliatedInfo(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody AffiliatedInfoDTO affiliatedInfoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AffiliatedInfo partially : {}, {}", id, affiliatedInfoDTO);
        if (affiliatedInfoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, affiliatedInfoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!affiliatedInfoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AffiliatedInfoDTO> result = affiliatedInfoService.partialUpdate(affiliatedInfoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, affiliatedInfoDTO.getId())
        );
    }

    /**
     * {@code GET  /affiliated-infos} : get all the affiliatedInfos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of affiliatedInfos in body.
     */
    @GetMapping("")
    public List<AffiliatedInfoDTO> getAllAffiliatedInfos() {
        log.debug("REST request to get all AffiliatedInfos");
        return affiliatedInfoService.findAll();
    }

    /**
     * {@code GET  /affiliated-infos/:id} : get the "id" affiliatedInfo.
     *
     * @param id the id of the affiliatedInfoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the affiliatedInfoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AffiliatedInfoDTO> getAffiliatedInfo(@PathVariable("id") String id) {
        log.debug("REST request to get AffiliatedInfo : {}", id);
        Optional<AffiliatedInfoDTO> affiliatedInfoDTO = affiliatedInfoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(affiliatedInfoDTO);
    }

    /**
     * {@code DELETE  /affiliated-infos/:id} : delete the "id" affiliatedInfo.
     *
     * @param id the id of the affiliatedInfoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAffiliatedInfo(@PathVariable("id") String id) {
        log.debug("REST request to delete AffiliatedInfo : {}", id);
        affiliatedInfoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
