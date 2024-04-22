package com.phoenixdarts.toss.web.rest;

import static com.phoenixdarts.toss.domain.MatchAttendanceAsserts.*;
import static com.phoenixdarts.toss.web.rest.TestUtil.createUpdateProxyForBean;
import static com.phoenixdarts.toss.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.phoenixdarts.toss.IntegrationTest;
import com.phoenixdarts.toss.domain.MatchAttendance;
import com.phoenixdarts.toss.domain.enumeration.AttendanceStatusType;
import com.phoenixdarts.toss.repository.MatchAttendanceRepository;
import com.phoenixdarts.toss.service.dto.MatchAttendanceDTO;
import com.phoenixdarts.toss.service.mapper.MatchAttendanceMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
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
 * Integration tests for the {@link MatchAttendanceResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MatchAttendanceResourceIT {

    private static final AttendanceStatusType DEFAULT_ATTENDANCE_STATUS_TYPE = AttendanceStatusType.NON_ATTENDANCE;
    private static final AttendanceStatusType UPDATED_ATTENDANCE_STATUS_TYPE = AttendanceStatusType.GIVE_UP;

    private static final ZonedDateTime DEFAULT_ATTENDANCE_DATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_ATTENDANCE_DATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/match-attendances";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private MatchAttendanceRepository matchAttendanceRepository;

    @Autowired
    private MatchAttendanceMapper matchAttendanceMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMatchAttendanceMockMvc;

    private MatchAttendance matchAttendance;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MatchAttendance createEntity(EntityManager em) {
        MatchAttendance matchAttendance = new MatchAttendance()
            .attendanceStatusType(DEFAULT_ATTENDANCE_STATUS_TYPE)
            .attendanceDateTime(DEFAULT_ATTENDANCE_DATE_TIME);
        return matchAttendance;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MatchAttendance createUpdatedEntity(EntityManager em) {
        MatchAttendance matchAttendance = new MatchAttendance()
            .attendanceStatusType(UPDATED_ATTENDANCE_STATUS_TYPE)
            .attendanceDateTime(UPDATED_ATTENDANCE_DATE_TIME);
        return matchAttendance;
    }

    @BeforeEach
    public void initTest() {
        matchAttendance = createEntity(em);
    }

    @Test
    @Transactional
    void createMatchAttendance() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the MatchAttendance
        MatchAttendanceDTO matchAttendanceDTO = matchAttendanceMapper.toDto(matchAttendance);
        var returnedMatchAttendanceDTO = om.readValue(
            restMatchAttendanceMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(matchAttendanceDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            MatchAttendanceDTO.class
        );

        // Validate the MatchAttendance in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedMatchAttendance = matchAttendanceMapper.toEntity(returnedMatchAttendanceDTO);
        assertMatchAttendanceUpdatableFieldsEquals(returnedMatchAttendance, getPersistedMatchAttendance(returnedMatchAttendance));
    }

    @Test
    @Transactional
    void createMatchAttendanceWithExistingId() throws Exception {
        // Create the MatchAttendance with an existing ID
        matchAttendance.setId("existing_id");
        MatchAttendanceDTO matchAttendanceDTO = matchAttendanceMapper.toDto(matchAttendance);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMatchAttendanceMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(matchAttendanceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MatchAttendance in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkAttendanceStatusTypeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        matchAttendance.setAttendanceStatusType(null);

        // Create the MatchAttendance, which fails.
        MatchAttendanceDTO matchAttendanceDTO = matchAttendanceMapper.toDto(matchAttendance);

        restMatchAttendanceMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(matchAttendanceDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllMatchAttendances() throws Exception {
        // Initialize the database
        matchAttendanceRepository.saveAndFlush(matchAttendance);

        // Get all the matchAttendanceList
        restMatchAttendanceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(matchAttendance.getId())))
            .andExpect(jsonPath("$.[*].attendanceStatusType").value(hasItem(DEFAULT_ATTENDANCE_STATUS_TYPE.toString())))
            .andExpect(jsonPath("$.[*].attendanceDateTime").value(hasItem(sameInstant(DEFAULT_ATTENDANCE_DATE_TIME))));
    }

    @Test
    @Transactional
    void getMatchAttendance() throws Exception {
        // Initialize the database
        matchAttendanceRepository.saveAndFlush(matchAttendance);

        // Get the matchAttendance
        restMatchAttendanceMockMvc
            .perform(get(ENTITY_API_URL_ID, matchAttendance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(matchAttendance.getId()))
            .andExpect(jsonPath("$.attendanceStatusType").value(DEFAULT_ATTENDANCE_STATUS_TYPE.toString()))
            .andExpect(jsonPath("$.attendanceDateTime").value(sameInstant(DEFAULT_ATTENDANCE_DATE_TIME)));
    }

    @Test
    @Transactional
    void getNonExistingMatchAttendance() throws Exception {
        // Get the matchAttendance
        restMatchAttendanceMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMatchAttendance() throws Exception {
        // Initialize the database
        matchAttendanceRepository.saveAndFlush(matchAttendance);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the matchAttendance
        MatchAttendance updatedMatchAttendance = matchAttendanceRepository.findById(matchAttendance.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedMatchAttendance are not directly saved in db
        em.detach(updatedMatchAttendance);
        updatedMatchAttendance.attendanceStatusType(UPDATED_ATTENDANCE_STATUS_TYPE).attendanceDateTime(UPDATED_ATTENDANCE_DATE_TIME);
        MatchAttendanceDTO matchAttendanceDTO = matchAttendanceMapper.toDto(updatedMatchAttendance);

        restMatchAttendanceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, matchAttendanceDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(matchAttendanceDTO))
            )
            .andExpect(status().isOk());

        // Validate the MatchAttendance in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedMatchAttendanceToMatchAllProperties(updatedMatchAttendance);
    }

    @Test
    @Transactional
    void putNonExistingMatchAttendance() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        matchAttendance.setId(UUID.randomUUID().toString());

        // Create the MatchAttendance
        MatchAttendanceDTO matchAttendanceDTO = matchAttendanceMapper.toDto(matchAttendance);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMatchAttendanceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, matchAttendanceDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(matchAttendanceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MatchAttendance in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMatchAttendance() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        matchAttendance.setId(UUID.randomUUID().toString());

        // Create the MatchAttendance
        MatchAttendanceDTO matchAttendanceDTO = matchAttendanceMapper.toDto(matchAttendance);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMatchAttendanceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(matchAttendanceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MatchAttendance in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMatchAttendance() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        matchAttendance.setId(UUID.randomUUID().toString());

        // Create the MatchAttendance
        MatchAttendanceDTO matchAttendanceDTO = matchAttendanceMapper.toDto(matchAttendance);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMatchAttendanceMockMvc
            .perform(
                put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(matchAttendanceDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MatchAttendance in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMatchAttendanceWithPatch() throws Exception {
        // Initialize the database
        matchAttendanceRepository.saveAndFlush(matchAttendance);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the matchAttendance using partial update
        MatchAttendance partialUpdatedMatchAttendance = new MatchAttendance();
        partialUpdatedMatchAttendance.setId(matchAttendance.getId());

        restMatchAttendanceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMatchAttendance.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMatchAttendance))
            )
            .andExpect(status().isOk());

        // Validate the MatchAttendance in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMatchAttendanceUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedMatchAttendance, matchAttendance),
            getPersistedMatchAttendance(matchAttendance)
        );
    }

    @Test
    @Transactional
    void fullUpdateMatchAttendanceWithPatch() throws Exception {
        // Initialize the database
        matchAttendanceRepository.saveAndFlush(matchAttendance);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the matchAttendance using partial update
        MatchAttendance partialUpdatedMatchAttendance = new MatchAttendance();
        partialUpdatedMatchAttendance.setId(matchAttendance.getId());

        partialUpdatedMatchAttendance.attendanceStatusType(UPDATED_ATTENDANCE_STATUS_TYPE).attendanceDateTime(UPDATED_ATTENDANCE_DATE_TIME);

        restMatchAttendanceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMatchAttendance.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMatchAttendance))
            )
            .andExpect(status().isOk());

        // Validate the MatchAttendance in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMatchAttendanceUpdatableFieldsEquals(
            partialUpdatedMatchAttendance,
            getPersistedMatchAttendance(partialUpdatedMatchAttendance)
        );
    }

    @Test
    @Transactional
    void patchNonExistingMatchAttendance() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        matchAttendance.setId(UUID.randomUUID().toString());

        // Create the MatchAttendance
        MatchAttendanceDTO matchAttendanceDTO = matchAttendanceMapper.toDto(matchAttendance);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMatchAttendanceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, matchAttendanceDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(matchAttendanceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MatchAttendance in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMatchAttendance() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        matchAttendance.setId(UUID.randomUUID().toString());

        // Create the MatchAttendance
        MatchAttendanceDTO matchAttendanceDTO = matchAttendanceMapper.toDto(matchAttendance);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMatchAttendanceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(matchAttendanceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MatchAttendance in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMatchAttendance() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        matchAttendance.setId(UUID.randomUUID().toString());

        // Create the MatchAttendance
        MatchAttendanceDTO matchAttendanceDTO = matchAttendanceMapper.toDto(matchAttendance);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMatchAttendanceMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(matchAttendanceDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MatchAttendance in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMatchAttendance() throws Exception {
        // Initialize the database
        matchAttendanceRepository.saveAndFlush(matchAttendance);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the matchAttendance
        restMatchAttendanceMockMvc
            .perform(delete(ENTITY_API_URL_ID, matchAttendance.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return matchAttendanceRepository.count();
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

    protected MatchAttendance getPersistedMatchAttendance(MatchAttendance matchAttendance) {
        return matchAttendanceRepository.findById(matchAttendance.getId()).orElseThrow();
    }

    protected void assertPersistedMatchAttendanceToMatchAllProperties(MatchAttendance expectedMatchAttendance) {
        assertMatchAttendanceAllPropertiesEquals(expectedMatchAttendance, getPersistedMatchAttendance(expectedMatchAttendance));
    }

    protected void assertPersistedMatchAttendanceToMatchUpdatableProperties(MatchAttendance expectedMatchAttendance) {
        assertMatchAttendanceAllUpdatablePropertiesEquals(expectedMatchAttendance, getPersistedMatchAttendance(expectedMatchAttendance));
    }
}
