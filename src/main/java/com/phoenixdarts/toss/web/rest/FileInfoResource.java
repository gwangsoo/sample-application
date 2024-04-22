package com.phoenixdarts.toss.web.rest;

import com.phoenixdarts.toss.repository.FileInfoRepository;
import com.phoenixdarts.toss.service.FileInfoService;
import com.phoenixdarts.toss.service.dto.FileInfoDTO;
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
 * REST controller for managing {@link com.phoenixdarts.toss.domain.FileInfo}.
 */
@RestController
@RequestMapping("/api/file-infos")
public class FileInfoResource {

    private final Logger log = LoggerFactory.getLogger(FileInfoResource.class);

    private static final String ENTITY_NAME = "fileInfo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FileInfoService fileInfoService;

    private final FileInfoRepository fileInfoRepository;

    public FileInfoResource(FileInfoService fileInfoService, FileInfoRepository fileInfoRepository) {
        this.fileInfoService = fileInfoService;
        this.fileInfoRepository = fileInfoRepository;
    }

    /**
     * {@code POST  /file-infos} : Create a new fileInfo.
     *
     * @param fileInfoDTO the fileInfoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fileInfoDTO, or with status {@code 400 (Bad Request)} if the fileInfo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<FileInfoDTO> createFileInfo(@Valid @RequestBody FileInfoDTO fileInfoDTO) throws URISyntaxException {
        log.debug("REST request to save FileInfo : {}", fileInfoDTO);
        if (fileInfoDTO.getId() != null) {
            throw new BadRequestAlertException("A new fileInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        fileInfoDTO = fileInfoService.save(fileInfoDTO);
        return ResponseEntity.created(new URI("/api/file-infos/" + fileInfoDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, fileInfoDTO.getId()))
            .body(fileInfoDTO);
    }

    /**
     * {@code PUT  /file-infos/:id} : Updates an existing fileInfo.
     *
     * @param id the id of the fileInfoDTO to save.
     * @param fileInfoDTO the fileInfoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fileInfoDTO,
     * or with status {@code 400 (Bad Request)} if the fileInfoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fileInfoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<FileInfoDTO> updateFileInfo(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody FileInfoDTO fileInfoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update FileInfo : {}, {}", id, fileInfoDTO);
        if (fileInfoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fileInfoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fileInfoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        fileInfoDTO = fileInfoService.update(fileInfoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fileInfoDTO.getId()))
            .body(fileInfoDTO);
    }

    /**
     * {@code PATCH  /file-infos/:id} : Partial updates given fields of an existing fileInfo, field will ignore if it is null
     *
     * @param id the id of the fileInfoDTO to save.
     * @param fileInfoDTO the fileInfoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fileInfoDTO,
     * or with status {@code 400 (Bad Request)} if the fileInfoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the fileInfoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the fileInfoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FileInfoDTO> partialUpdateFileInfo(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody FileInfoDTO fileInfoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update FileInfo partially : {}, {}", id, fileInfoDTO);
        if (fileInfoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fileInfoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fileInfoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FileInfoDTO> result = fileInfoService.partialUpdate(fileInfoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fileInfoDTO.getId())
        );
    }

    /**
     * {@code GET  /file-infos} : get all the fileInfos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fileInfos in body.
     */
    @GetMapping("")
    public List<FileInfoDTO> getAllFileInfos() {
        log.debug("REST request to get all FileInfos");
        return fileInfoService.findAll();
    }

    /**
     * {@code GET  /file-infos/:id} : get the "id" fileInfo.
     *
     * @param id the id of the fileInfoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fileInfoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<FileInfoDTO> getFileInfo(@PathVariable("id") String id) {
        log.debug("REST request to get FileInfo : {}", id);
        Optional<FileInfoDTO> fileInfoDTO = fileInfoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fileInfoDTO);
    }

    /**
     * {@code DELETE  /file-infos/:id} : delete the "id" fileInfo.
     *
     * @param id the id of the fileInfoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFileInfo(@PathVariable("id") String id) {
        log.debug("REST request to delete FileInfo : {}", id);
        fileInfoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
