package com.phoenixdarts.toss.service;

import com.phoenixdarts.toss.service.dto.FileInfoDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.phoenixdarts.toss.domain.FileInfo}.
 */
public interface FileInfoService {
    /**
     * Save a fileInfo.
     *
     * @param fileInfoDTO the entity to save.
     * @return the persisted entity.
     */
    FileInfoDTO save(FileInfoDTO fileInfoDTO);

    /**
     * Updates a fileInfo.
     *
     * @param fileInfoDTO the entity to update.
     * @return the persisted entity.
     */
    FileInfoDTO update(FileInfoDTO fileInfoDTO);

    /**
     * Partially updates a fileInfo.
     *
     * @param fileInfoDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FileInfoDTO> partialUpdate(FileInfoDTO fileInfoDTO);

    /**
     * Get all the fileInfos.
     *
     * @return the list of entities.
     */
    List<FileInfoDTO> findAll();

    /**
     * Get the "id" fileInfo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FileInfoDTO> findOne(String id);

    /**
     * Delete the "id" fileInfo.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
