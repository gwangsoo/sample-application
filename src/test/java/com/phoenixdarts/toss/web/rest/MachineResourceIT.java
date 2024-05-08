package com.phoenixdarts.toss.backend.web.rest;

import static com.phoenixdarts.toss.backend.domain.MachineAsserts.*;
import static com.phoenixdarts.toss.backend.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.phoenixdarts.toss.IntegrationTest;
import com.phoenixdarts.toss.backend.domain.Machine;
import com.phoenixdarts.toss.backend.domain.enumeration.MachineStatusType;
import com.phoenixdarts.toss.backend.repository.MachineRepository;
import com.phoenixdarts.toss.backend.service.dto.MachineDTO;
import com.phoenixdarts.toss.backend.service.mapper.MachineMapper;
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
 * Integration tests for the {@link MachineResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MachineResourceIT {

    private static final Integer DEFAULT_MACHINE_NO = 1;
    private static final Integer UPDATED_MACHINE_NO = 2;

    private static final MachineStatusType DEFAULT_MACHINE_STATUS_TYPE = MachineStatusType.GOOD;
    private static final MachineStatusType UPDATED_MACHINE_STATUS_TYPE = MachineStatusType.BREAKDOWN;

    private static final String ENTITY_API_URL = "/api/machines";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private MachineRepository machineRepository;

    @Autowired
    private MachineMapper machineMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMachineMockMvc;

    private Machine machine;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Machine createEntity(EntityManager em) {
        Machine machine = new Machine().machineNo(DEFAULT_MACHINE_NO).machineStatusType(DEFAULT_MACHINE_STATUS_TYPE);
        return machine;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Machine createUpdatedEntity(EntityManager em) {
        Machine machine = new Machine().machineNo(UPDATED_MACHINE_NO).machineStatusType(UPDATED_MACHINE_STATUS_TYPE);
        return machine;
    }

    @BeforeEach
    public void initTest() {
        machine = createEntity(em);
    }

    @Test
    @Transactional
    void createMachine() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Machine
        MachineDTO machineDTO = machineMapper.toDto(machine);
        var returnedMachineDTO = om.readValue(
            restMachineMockMvc
                .perform(
                    post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(machineDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            MachineDTO.class
        );

        // Validate the Machine in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedMachine = machineMapper.toEntity(returnedMachineDTO);
        assertMachineUpdatableFieldsEquals(returnedMachine, getPersistedMachine(returnedMachine));
    }

    @Test
    @Transactional
    void createMachineWithExistingId() throws Exception {
        // Create the Machine with an existing ID
        machine.setId("existing_id");
        MachineDTO machineDTO = machineMapper.toDto(machine);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMachineMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(machineDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Machine in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkMachineNoIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        machine.setMachineNo(null);

        // Create the Machine, which fails.
        MachineDTO machineDTO = machineMapper.toDto(machine);

        restMachineMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(machineDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMachineStatusTypeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        machine.setMachineStatusType(null);

        // Create the Machine, which fails.
        MachineDTO machineDTO = machineMapper.toDto(machine);

        restMachineMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(machineDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllMachines() throws Exception {
        // Initialize the database
        machineRepository.saveAndFlush(machine);

        // Get all the machineList
        restMachineMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(machine.getId())))
            .andExpect(jsonPath("$.[*].machineNo").value(hasItem(DEFAULT_MACHINE_NO)))
            .andExpect(jsonPath("$.[*].machineStatusType").value(hasItem(DEFAULT_MACHINE_STATUS_TYPE.toString())));
    }

    @Test
    @Transactional
    void getMachine() throws Exception {
        // Initialize the database
        machineRepository.saveAndFlush(machine);

        // Get the machine
        restMachineMockMvc
            .perform(get(ENTITY_API_URL_ID, machine.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(machine.getId()))
            .andExpect(jsonPath("$.machineNo").value(DEFAULT_MACHINE_NO))
            .andExpect(jsonPath("$.machineStatusType").value(DEFAULT_MACHINE_STATUS_TYPE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingMachine() throws Exception {
        // Get the machine
        restMachineMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMachine() throws Exception {
        // Initialize the database
        machineRepository.saveAndFlush(machine);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the machine
        Machine updatedMachine = machineRepository.findById(machine.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedMachine are not directly saved in db
        em.detach(updatedMachine);
        updatedMachine.machineNo(UPDATED_MACHINE_NO).machineStatusType(UPDATED_MACHINE_STATUS_TYPE);
        MachineDTO machineDTO = machineMapper.toDto(updatedMachine);

        restMachineMockMvc
            .perform(
                put(ENTITY_API_URL_ID, machineDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(machineDTO))
            )
            .andExpect(status().isOk());

        // Validate the Machine in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedMachineToMatchAllProperties(updatedMachine);
    }

    @Test
    @Transactional
    void putNonExistingMachine() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        machine.setId(UUID.randomUUID().toString());

        // Create the Machine
        MachineDTO machineDTO = machineMapper.toDto(machine);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMachineMockMvc
            .perform(
                put(ENTITY_API_URL_ID, machineDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(machineDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Machine in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMachine() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        machine.setId(UUID.randomUUID().toString());

        // Create the Machine
        MachineDTO machineDTO = machineMapper.toDto(machine);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMachineMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(machineDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Machine in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMachine() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        machine.setId(UUID.randomUUID().toString());

        // Create the Machine
        MachineDTO machineDTO = machineMapper.toDto(machine);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMachineMockMvc
            .perform(put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(machineDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Machine in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMachineWithPatch() throws Exception {
        // Initialize the database
        machineRepository.saveAndFlush(machine);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the machine using partial update
        Machine partialUpdatedMachine = new Machine();
        partialUpdatedMachine.setId(machine.getId());

        partialUpdatedMachine.machineStatusType(UPDATED_MACHINE_STATUS_TYPE);

        restMachineMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMachine.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMachine))
            )
            .andExpect(status().isOk());

        // Validate the Machine in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMachineUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedMachine, machine), getPersistedMachine(machine));
    }

    @Test
    @Transactional
    void fullUpdateMachineWithPatch() throws Exception {
        // Initialize the database
        machineRepository.saveAndFlush(machine);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the machine using partial update
        Machine partialUpdatedMachine = new Machine();
        partialUpdatedMachine.setId(machine.getId());

        partialUpdatedMachine.machineNo(UPDATED_MACHINE_NO).machineStatusType(UPDATED_MACHINE_STATUS_TYPE);

        restMachineMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMachine.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMachine))
            )
            .andExpect(status().isOk());

        // Validate the Machine in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMachineUpdatableFieldsEquals(partialUpdatedMachine, getPersistedMachine(partialUpdatedMachine));
    }

    @Test
    @Transactional
    void patchNonExistingMachine() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        machine.setId(UUID.randomUUID().toString());

        // Create the Machine
        MachineDTO machineDTO = machineMapper.toDto(machine);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMachineMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, machineDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(machineDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Machine in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMachine() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        machine.setId(UUID.randomUUID().toString());

        // Create the Machine
        MachineDTO machineDTO = machineMapper.toDto(machine);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMachineMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(machineDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Machine in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMachine() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        machine.setId(UUID.randomUUID().toString());

        // Create the Machine
        MachineDTO machineDTO = machineMapper.toDto(machine);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMachineMockMvc
            .perform(
                patch(ENTITY_API_URL).with(csrf()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(machineDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Machine in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMachine() throws Exception {
        // Initialize the database
        machineRepository.saveAndFlush(machine);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the machine
        restMachineMockMvc
            .perform(delete(ENTITY_API_URL_ID, machine.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return machineRepository.count();
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

    protected Machine getPersistedMachine(Machine machine) {
        return machineRepository.findById(machine.getId()).orElseThrow();
    }

    protected void assertPersistedMachineToMatchAllProperties(Machine expectedMachine) {
        assertMachineAllPropertiesEquals(expectedMachine, getPersistedMachine(expectedMachine));
    }

    protected void assertPersistedMachineToMatchUpdatableProperties(Machine expectedMachine) {
        assertMachineAllUpdatablePropertiesEquals(expectedMachine, getPersistedMachine(expectedMachine));
    }
}
