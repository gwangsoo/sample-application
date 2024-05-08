package com.phoenixdarts.toss.backend.web.rest;

import static com.phoenixdarts.toss.backend.domain.FileInfoAsserts.*;
import static com.phoenixdarts.toss.backend.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.phoenixdarts.toss.IntegrationTest;
import com.phoenixdarts.toss.backend.domain.FileInfo;
import com.phoenixdarts.toss.backend.repository.FileInfoRepository;
import com.phoenixdarts.toss.backend.service.dto.FileInfoDTO;
import com.phoenixdarts.toss.backend.service.mapper.FileInfoMapper;
import jakarta.persistence.EntityManager;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link FileInfoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FileInfoResourceIT {

    private static final String DEFAULT_ORIGINAL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ORIGINAL_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MIME_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_MIME_TYPE = "BBBBBBBBBB";

    private static final Long DEFAULT_FILE_SIZE = 1L;
    private static final Long UPDATED_FILE_SIZE = 2L;

    private static final String DEFAULT_SAVED_PATH = "AAAAAAAAAA";
    private static final String UPDATED_SAVED_PATH = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/file-infos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private FileInfoRepository fileInfoRepository;

    @Autowired
    private FileInfoMapper fileInfoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFileInfoMockMvc;

    private FileInfo fileInfo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FileInfo createEntity(EntityManager em) {
        FileInfo fileInfo = new FileInfo()
            .originalName(DEFAULT_ORIGINAL_NAME)
            .mimeType(DEFAULT_MIME_TYPE)
            .fileSize(DEFAULT_FILE_SIZE)
            .savedPath(DEFAULT_SAVED_PATH);
        return fileInfo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FileInfo createUpdatedEntity(EntityManager em) {
        FileInfo fileInfo = new FileInfo()
            .originalName(UPDATED_ORIGINAL_NAME)
            .mimeType(UPDATED_MIME_TYPE)
            .fileSize(UPDATED_FILE_SIZE)
            .savedPath(UPDATED_SAVED_PATH);
        return fileInfo;
    }

    @BeforeEach
    public void initTest() {
        fileInfo = createEntity(em);
    }

    @Test
    @Transactional
    void createFileInfo() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the FileInfo
        FileInfoDTO fileInfoDTO = fileInfoMapper.toDto(fileInfo);
        var returnedFileInfoDTO = om.readValue(
            restFileInfoMockMvc
                .perform(
                    post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(fileInfoDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            FileInfoDTO.class
        );

        // Validate the FileInfo in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedFileInfo = fileInfoMapper.toEntity(returnedFileInfoDTO);
        assertFileInfoUpdatableFieldsEquals(returnedFileInfo, getPersistedFileInfo(returnedFileInfo));
    }

    @Test
    @Transactional
    void createFileInfoWithExistingId() throws Exception {
        // Create the FileInfo with an existing ID
        fileInfo.setId("existing_id");
        FileInfoDTO fileInfoDTO = fileInfoMapper.toDto(fileInfo);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFileInfoMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(fileInfoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FileInfo in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkOriginalNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        fileInfo.setOriginalName(null);

        // Create the FileInfo, which fails.
        FileInfoDTO fileInfoDTO = fileInfoMapper.toDto(fileInfo);

        restFileInfoMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(fileInfoDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMimeTypeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        fileInfo.setMimeType(null);

        // Create the FileInfo, which fails.
        FileInfoDTO fileInfoDTO = fileInfoMapper.toDto(fileInfo);

        restFileInfoMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(fileInfoDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSavedPathIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        fileInfo.setSavedPath(null);

        // Create the FileInfo, which fails.
        FileInfoDTO fileInfoDTO = fileInfoMapper.toDto(fileInfo);

        restFileInfoMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(fileInfoDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllFileInfos() throws Exception {
        // Initialize the database
        fileInfoRepository.saveAndFlush(fileInfo);

        // Get all the fileInfoList
        restFileInfoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fileInfo.getId())))
            .andExpect(jsonPath("$.[*].originalName").value(hasItem(DEFAULT_ORIGINAL_NAME)))
            .andExpect(jsonPath("$.[*].mimeType").value(hasItem(DEFAULT_MIME_TYPE)))
            .andExpect(jsonPath("$.[*].fileSize").value(hasItem(DEFAULT_FILE_SIZE.intValue())))
            .andExpect(jsonPath("$.[*].savedPath").value(hasItem(DEFAULT_SAVED_PATH)));
    }

    @Test
    @Transactional
    void getFileInfo() throws Exception {
        // Initialize the database
        fileInfoRepository.saveAndFlush(fileInfo);

        // Get the fileInfo
        restFileInfoMockMvc
            .perform(get(ENTITY_API_URL_ID, fileInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fileInfo.getId()))
            .andExpect(jsonPath("$.originalName").value(DEFAULT_ORIGINAL_NAME))
            .andExpect(jsonPath("$.mimeType").value(DEFAULT_MIME_TYPE))
            .andExpect(jsonPath("$.fileSize").value(DEFAULT_FILE_SIZE.intValue()))
            .andExpect(jsonPath("$.savedPath").value(DEFAULT_SAVED_PATH));
    }

    @Test
    @Transactional
    void getNonExistingFileInfo() throws Exception {
        // Get the fileInfo
        restFileInfoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFileInfo() throws Exception {
        // Initialize the database
        fileInfoRepository.saveAndFlush(fileInfo);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the fileInfo
        FileInfo updatedFileInfo = fileInfoRepository.findById(fileInfo.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedFileInfo are not directly saved in db
        em.detach(updatedFileInfo);
        updatedFileInfo
            .originalName(UPDATED_ORIGINAL_NAME)
            .mimeType(UPDATED_MIME_TYPE)
            .fileSize(UPDATED_FILE_SIZE)
            .savedPath(UPDATED_SAVED_PATH);
        FileInfoDTO fileInfoDTO = fileInfoMapper.toDto(updatedFileInfo);

        restFileInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fileInfoDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(fileInfoDTO))
            )
            .andExpect(status().isOk());

        // Validate the FileInfo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedFileInfoToMatchAllProperties(updatedFileInfo);
    }

    @Test
    @Transactional
    void putNonExistingFileInfo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fileInfo.setId(UUID.randomUUID().toString());

        // Create the FileInfo
        FileInfoDTO fileInfoDTO = fileInfoMapper.toDto(fileInfo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFileInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fileInfoDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(fileInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FileInfo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFileInfo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fileInfo.setId(UUID.randomUUID().toString());

        // Create the FileInfo
        FileInfoDTO fileInfoDTO = fileInfoMapper.toDto(fileInfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFileInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(fileInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FileInfo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFileInfo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fileInfo.setId(UUID.randomUUID().toString());

        // Create the FileInfo
        FileInfoDTO fileInfoDTO = fileInfoMapper.toDto(fileInfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFileInfoMockMvc
            .perform(put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(fileInfoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FileInfo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFileInfoWithPatch() throws Exception {
        // Initialize the database
        fileInfoRepository.saveAndFlush(fileInfo);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the fileInfo using partial update
        FileInfo partialUpdatedFileInfo = new FileInfo();
        partialUpdatedFileInfo.setId(fileInfo.getId());

        partialUpdatedFileInfo.originalName(UPDATED_ORIGINAL_NAME).fileSize(UPDATED_FILE_SIZE).savedPath(UPDATED_SAVED_PATH);

        restFileInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFileInfo.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFileInfo))
            )
            .andExpect(status().isOk());

        // Validate the FileInfo in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFileInfoUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedFileInfo, fileInfo), getPersistedFileInfo(fileInfo));
    }

    @Test
    @Transactional
    void fullUpdateFileInfoWithPatch() throws Exception {
        // Initialize the database
        fileInfoRepository.saveAndFlush(fileInfo);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the fileInfo using partial update
        FileInfo partialUpdatedFileInfo = new FileInfo();
        partialUpdatedFileInfo.setId(fileInfo.getId());

        partialUpdatedFileInfo
            .originalName(UPDATED_ORIGINAL_NAME)
            .mimeType(UPDATED_MIME_TYPE)
            .fileSize(UPDATED_FILE_SIZE)
            .savedPath(UPDATED_SAVED_PATH);

        restFileInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFileInfo.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFileInfo))
            )
            .andExpect(status().isOk());

        // Validate the FileInfo in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFileInfoUpdatableFieldsEquals(partialUpdatedFileInfo, getPersistedFileInfo(partialUpdatedFileInfo));
    }

    @Test
    @Transactional
    void patchNonExistingFileInfo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fileInfo.setId(UUID.randomUUID().toString());

        // Create the FileInfo
        FileInfoDTO fileInfoDTO = fileInfoMapper.toDto(fileInfo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFileInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, fileInfoDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(fileInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FileInfo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFileInfo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fileInfo.setId(UUID.randomUUID().toString());

        // Create the FileInfo
        FileInfoDTO fileInfoDTO = fileInfoMapper.toDto(fileInfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFileInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(fileInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FileInfo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFileInfo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fileInfo.setId(UUID.randomUUID().toString());

        // Create the FileInfo
        FileInfoDTO fileInfoDTO = fileInfoMapper.toDto(fileInfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFileInfoMockMvc
            .perform(
                patch(ENTITY_API_URL).with(csrf()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(fileInfoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FileInfo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFileInfo() throws Exception {
        // Initialize the database
        fileInfoRepository.saveAndFlush(fileInfo);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the fileInfo
        restFileInfoMockMvc
            .perform(delete(ENTITY_API_URL_ID, fileInfo.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return fileInfoRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected FileInfo getPersistedFileInfo(FileInfo fileInfo) {
        return fileInfoRepository.findById(fileInfo.getId()).orElseThrow();
    }

    protected void assertPersistedFileInfoToMatchAllProperties(FileInfo expectedFileInfo) {
        assertFileInfoAllPropertiesEquals(expectedFileInfo, getPersistedFileInfo(expectedFileInfo));
    }

    protected void assertPersistedFileInfoToMatchUpdatableProperties(FileInfo expectedFileInfo) {
        assertFileInfoAllUpdatablePropertiesEquals(expectedFileInfo, getPersistedFileInfo(expectedFileInfo));
    }
}
