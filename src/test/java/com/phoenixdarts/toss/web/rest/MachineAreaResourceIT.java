package com.phoenixdarts.toss.web.rest;

import static com.phoenixdarts.toss.domain.MachineAreaAsserts.*;
import static com.phoenixdarts.toss.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.phoenixdarts.toss.IntegrationTest;
import com.phoenixdarts.toss.domain.MachineArea;
import com.phoenixdarts.toss.repository.MachineAreaRepository;
import com.phoenixdarts.toss.service.dto.MachineAreaDTO;
import com.phoenixdarts.toss.service.mapper.MachineAreaMapper;
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
 * Integration tests for the {@link MachineAreaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MachineAreaResourceIT {

    private static final String DEFAULT_MAME = "AAAAAAAAAA";
    private static final String UPDATED_MAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_SEQ = 1;
    private static final Integer UPDATED_SEQ = 2;

    private static final Integer DEFAULT_NUM_OF_MACHINE = 1;
    private static final Integer UPDATED_NUM_OF_MACHINE = 2;

    private static final String ENTITY_API_URL = "/api/machine-areas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private MachineAreaRepository machineAreaRepository;

    @Autowired
    private MachineAreaMapper machineAreaMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMachineAreaMockMvc;

    private MachineArea machineArea;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MachineArea createEntity(EntityManager em) {
        MachineArea machineArea = new MachineArea().mame(DEFAULT_MAME).seq(DEFAULT_SEQ).numOfMachine(DEFAULT_NUM_OF_MACHINE);
        return machineArea;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MachineArea createUpdatedEntity(EntityManager em) {
        MachineArea machineArea = new MachineArea().mame(UPDATED_MAME).seq(UPDATED_SEQ).numOfMachine(UPDATED_NUM_OF_MACHINE);
        return machineArea;
    }

    @BeforeEach
    public void initTest() {
        machineArea = createEntity(em);
    }

    @Test
    @Transactional
    void createMachineArea() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the MachineArea
        MachineAreaDTO machineAreaDTO = machineAreaMapper.toDto(machineArea);
        var returnedMachineAreaDTO = om.readValue(
            restMachineAreaMockMvc
                .perform(
                    post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(machineAreaDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            MachineAreaDTO.class
        );

        // Validate the MachineArea in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedMachineArea = machineAreaMapper.toEntity(returnedMachineAreaDTO);
        assertMachineAreaUpdatableFieldsEquals(returnedMachineArea, getPersistedMachineArea(returnedMachineArea));
    }

    @Test
    @Transactional
    void createMachineAreaWithExistingId() throws Exception {
        // Create the MachineArea with an existing ID
        machineArea.setId("existing_id");
        MachineAreaDTO machineAreaDTO = machineAreaMapper.toDto(machineArea);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMachineAreaMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(machineAreaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MachineArea in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkMameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        machineArea.setMame(null);

        // Create the MachineArea, which fails.
        MachineAreaDTO machineAreaDTO = machineAreaMapper.toDto(machineArea);

        restMachineAreaMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(machineAreaDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSeqIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        machineArea.setSeq(null);

        // Create the MachineArea, which fails.
        MachineAreaDTO machineAreaDTO = machineAreaMapper.toDto(machineArea);

        restMachineAreaMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(machineAreaDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllMachineAreas() throws Exception {
        // Initialize the database
        machineAreaRepository.saveAndFlush(machineArea);

        // Get all the machineAreaList
        restMachineAreaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(machineArea.getId())))
            .andExpect(jsonPath("$.[*].mame").value(hasItem(DEFAULT_MAME)))
            .andExpect(jsonPath("$.[*].seq").value(hasItem(DEFAULT_SEQ)))
            .andExpect(jsonPath("$.[*].numOfMachine").value(hasItem(DEFAULT_NUM_OF_MACHINE)));
    }

    @Test
    @Transactional
    void getMachineArea() throws Exception {
        // Initialize the database
        machineAreaRepository.saveAndFlush(machineArea);

        // Get the machineArea
        restMachineAreaMockMvc
            .perform(get(ENTITY_API_URL_ID, machineArea.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(machineArea.getId()))
            .andExpect(jsonPath("$.mame").value(DEFAULT_MAME))
            .andExpect(jsonPath("$.seq").value(DEFAULT_SEQ))
            .andExpect(jsonPath("$.numOfMachine").value(DEFAULT_NUM_OF_MACHINE));
    }

    @Test
    @Transactional
    void getNonExistingMachineArea() throws Exception {
        // Get the machineArea
        restMachineAreaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMachineArea() throws Exception {
        // Initialize the database
        machineAreaRepository.saveAndFlush(machineArea);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the machineArea
        MachineArea updatedMachineArea = machineAreaRepository.findById(machineArea.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedMachineArea are not directly saved in db
        em.detach(updatedMachineArea);
        updatedMachineArea.mame(UPDATED_MAME).seq(UPDATED_SEQ).numOfMachine(UPDATED_NUM_OF_MACHINE);
        MachineAreaDTO machineAreaDTO = machineAreaMapper.toDto(updatedMachineArea);

        restMachineAreaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, machineAreaDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(machineAreaDTO))
            )
            .andExpect(status().isOk());

        // Validate the MachineArea in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedMachineAreaToMatchAllProperties(updatedMachineArea);
    }

    @Test
    @Transactional
    void putNonExistingMachineArea() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        machineArea.setId(UUID.randomUUID().toString());

        // Create the MachineArea
        MachineAreaDTO machineAreaDTO = machineAreaMapper.toDto(machineArea);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMachineAreaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, machineAreaDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(machineAreaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MachineArea in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMachineArea() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        machineArea.setId(UUID.randomUUID().toString());

        // Create the MachineArea
        MachineAreaDTO machineAreaDTO = machineAreaMapper.toDto(machineArea);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMachineAreaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(machineAreaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MachineArea in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMachineArea() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        machineArea.setId(UUID.randomUUID().toString());

        // Create the MachineArea
        MachineAreaDTO machineAreaDTO = machineAreaMapper.toDto(machineArea);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMachineAreaMockMvc
            .perform(put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(machineAreaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the MachineArea in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMachineAreaWithPatch() throws Exception {
        // Initialize the database
        machineAreaRepository.saveAndFlush(machineArea);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the machineArea using partial update
        MachineArea partialUpdatedMachineArea = new MachineArea();
        partialUpdatedMachineArea.setId(machineArea.getId());

        restMachineAreaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMachineArea.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMachineArea))
            )
            .andExpect(status().isOk());

        // Validate the MachineArea in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMachineAreaUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedMachineArea, machineArea),
            getPersistedMachineArea(machineArea)
        );
    }

    @Test
    @Transactional
    void fullUpdateMachineAreaWithPatch() throws Exception {
        // Initialize the database
        machineAreaRepository.saveAndFlush(machineArea);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the machineArea using partial update
        MachineArea partialUpdatedMachineArea = new MachineArea();
        partialUpdatedMachineArea.setId(machineArea.getId());

        partialUpdatedMachineArea.mame(UPDATED_MAME).seq(UPDATED_SEQ).numOfMachine(UPDATED_NUM_OF_MACHINE);

        restMachineAreaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMachineArea.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMachineArea))
            )
            .andExpect(status().isOk());

        // Validate the MachineArea in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMachineAreaUpdatableFieldsEquals(partialUpdatedMachineArea, getPersistedMachineArea(partialUpdatedMachineArea));
    }

    @Test
    @Transactional
    void patchNonExistingMachineArea() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        machineArea.setId(UUID.randomUUID().toString());

        // Create the MachineArea
        MachineAreaDTO machineAreaDTO = machineAreaMapper.toDto(machineArea);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMachineAreaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, machineAreaDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(machineAreaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MachineArea in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMachineArea() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        machineArea.setId(UUID.randomUUID().toString());

        // Create the MachineArea
        MachineAreaDTO machineAreaDTO = machineAreaMapper.toDto(machineArea);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMachineAreaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(machineAreaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MachineArea in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMachineArea() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        machineArea.setId(UUID.randomUUID().toString());

        // Create the MachineArea
        MachineAreaDTO machineAreaDTO = machineAreaMapper.toDto(machineArea);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMachineAreaMockMvc
            .perform(
                patch(ENTITY_API_URL).with(csrf()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(machineAreaDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MachineArea in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMachineArea() throws Exception {
        // Initialize the database
        machineAreaRepository.saveAndFlush(machineArea);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the machineArea
        restMachineAreaMockMvc
            .perform(delete(ENTITY_API_URL_ID, machineArea.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return machineAreaRepository.count();
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

    protected MachineArea getPersistedMachineArea(MachineArea machineArea) {
        return machineAreaRepository.findById(machineArea.getId()).orElseThrow();
    }

    protected void assertPersistedMachineAreaToMatchAllProperties(MachineArea expectedMachineArea) {
        assertMachineAreaAllPropertiesEquals(expectedMachineArea, getPersistedMachineArea(expectedMachineArea));
    }

    protected void assertPersistedMachineAreaToMatchUpdatableProperties(MachineArea expectedMachineArea) {
        assertMachineAreaAllUpdatablePropertiesEquals(expectedMachineArea, getPersistedMachineArea(expectedMachineArea));
    }
}
