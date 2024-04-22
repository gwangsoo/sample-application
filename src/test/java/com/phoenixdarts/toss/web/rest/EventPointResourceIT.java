package com.phoenixdarts.toss.web.rest;

import static com.phoenixdarts.toss.domain.EventPointAsserts.*;
import static com.phoenixdarts.toss.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.phoenixdarts.toss.IntegrationTest;
import com.phoenixdarts.toss.domain.EventPoint;
import com.phoenixdarts.toss.repository.EventPointRepository;
import com.phoenixdarts.toss.service.dto.EventPointDTO;
import com.phoenixdarts.toss.service.mapper.EventPointMapper;
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
 * Integration tests for the {@link EventPointResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EventPointResourceIT {

    private static final Integer DEFAULT_SEQ = 1;
    private static final Integer UPDATED_SEQ = 2;

    private static final Integer DEFAULT_RATING = 1;
    private static final Integer UPDATED_RATING = 2;

    private static final Double DEFAULT_RATING_MIN = 1D;
    private static final Double UPDATED_RATING_MIN = 2D;

    private static final Double DEFAULT_RATING_MAX = 1D;
    private static final Double UPDATED_RATING_MAX = 2D;

    private static final String ENTITY_API_URL = "/api/event-points";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private EventPointRepository eventPointRepository;

    @Autowired
    private EventPointMapper eventPointMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEventPointMockMvc;

    private EventPoint eventPoint;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EventPoint createEntity(EntityManager em) {
        EventPoint eventPoint = new EventPoint()
            .seq(DEFAULT_SEQ)
            .rating(DEFAULT_RATING)
            .ratingMin(DEFAULT_RATING_MIN)
            .ratingMax(DEFAULT_RATING_MAX);
        return eventPoint;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EventPoint createUpdatedEntity(EntityManager em) {
        EventPoint eventPoint = new EventPoint()
            .seq(UPDATED_SEQ)
            .rating(UPDATED_RATING)
            .ratingMin(UPDATED_RATING_MIN)
            .ratingMax(UPDATED_RATING_MAX);
        return eventPoint;
    }

    @BeforeEach
    public void initTest() {
        eventPoint = createEntity(em);
    }

    @Test
    @Transactional
    void createEventPoint() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the EventPoint
        EventPointDTO eventPointDTO = eventPointMapper.toDto(eventPoint);
        var returnedEventPointDTO = om.readValue(
            restEventPointMockMvc
                .perform(
                    post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(eventPointDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            EventPointDTO.class
        );

        // Validate the EventPoint in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedEventPoint = eventPointMapper.toEntity(returnedEventPointDTO);
        assertEventPointUpdatableFieldsEquals(returnedEventPoint, getPersistedEventPoint(returnedEventPoint));
    }

    @Test
    @Transactional
    void createEventPointWithExistingId() throws Exception {
        // Create the EventPoint with an existing ID
        eventPoint.setId("existing_id");
        EventPointDTO eventPointDTO = eventPointMapper.toDto(eventPoint);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEventPointMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(eventPointDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EventPoint in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkSeqIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        eventPoint.setSeq(null);

        // Create the EventPoint, which fails.
        EventPointDTO eventPointDTO = eventPointMapper.toDto(eventPoint);

        restEventPointMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(eventPointDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkRatingIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        eventPoint.setRating(null);

        // Create the EventPoint, which fails.
        EventPointDTO eventPointDTO = eventPointMapper.toDto(eventPoint);

        restEventPointMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(eventPointDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllEventPoints() throws Exception {
        // Initialize the database
        eventPointRepository.saveAndFlush(eventPoint);

        // Get all the eventPointList
        restEventPointMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(eventPoint.getId())))
            .andExpect(jsonPath("$.[*].seq").value(hasItem(DEFAULT_SEQ)))
            .andExpect(jsonPath("$.[*].rating").value(hasItem(DEFAULT_RATING)))
            .andExpect(jsonPath("$.[*].ratingMin").value(hasItem(DEFAULT_RATING_MIN.doubleValue())))
            .andExpect(jsonPath("$.[*].ratingMax").value(hasItem(DEFAULT_RATING_MAX.doubleValue())));
    }

    @Test
    @Transactional
    void getEventPoint() throws Exception {
        // Initialize the database
        eventPointRepository.saveAndFlush(eventPoint);

        // Get the eventPoint
        restEventPointMockMvc
            .perform(get(ENTITY_API_URL_ID, eventPoint.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(eventPoint.getId()))
            .andExpect(jsonPath("$.seq").value(DEFAULT_SEQ))
            .andExpect(jsonPath("$.rating").value(DEFAULT_RATING))
            .andExpect(jsonPath("$.ratingMin").value(DEFAULT_RATING_MIN.doubleValue()))
            .andExpect(jsonPath("$.ratingMax").value(DEFAULT_RATING_MAX.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingEventPoint() throws Exception {
        // Get the eventPoint
        restEventPointMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEventPoint() throws Exception {
        // Initialize the database
        eventPointRepository.saveAndFlush(eventPoint);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the eventPoint
        EventPoint updatedEventPoint = eventPointRepository.findById(eventPoint.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedEventPoint are not directly saved in db
        em.detach(updatedEventPoint);
        updatedEventPoint.seq(UPDATED_SEQ).rating(UPDATED_RATING).ratingMin(UPDATED_RATING_MIN).ratingMax(UPDATED_RATING_MAX);
        EventPointDTO eventPointDTO = eventPointMapper.toDto(updatedEventPoint);

        restEventPointMockMvc
            .perform(
                put(ENTITY_API_URL_ID, eventPointDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(eventPointDTO))
            )
            .andExpect(status().isOk());

        // Validate the EventPoint in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedEventPointToMatchAllProperties(updatedEventPoint);
    }

    @Test
    @Transactional
    void putNonExistingEventPoint() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        eventPoint.setId(UUID.randomUUID().toString());

        // Create the EventPoint
        EventPointDTO eventPointDTO = eventPointMapper.toDto(eventPoint);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEventPointMockMvc
            .perform(
                put(ENTITY_API_URL_ID, eventPointDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(eventPointDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EventPoint in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEventPoint() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        eventPoint.setId(UUID.randomUUID().toString());

        // Create the EventPoint
        EventPointDTO eventPointDTO = eventPointMapper.toDto(eventPoint);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEventPointMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(eventPointDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EventPoint in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEventPoint() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        eventPoint.setId(UUID.randomUUID().toString());

        // Create the EventPoint
        EventPointDTO eventPointDTO = eventPointMapper.toDto(eventPoint);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEventPointMockMvc
            .perform(put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(eventPointDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the EventPoint in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEventPointWithPatch() throws Exception {
        // Initialize the database
        eventPointRepository.saveAndFlush(eventPoint);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the eventPoint using partial update
        EventPoint partialUpdatedEventPoint = new EventPoint();
        partialUpdatedEventPoint.setId(eventPoint.getId());

        partialUpdatedEventPoint.seq(UPDATED_SEQ).rating(UPDATED_RATING);

        restEventPointMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEventPoint.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEventPoint))
            )
            .andExpect(status().isOk());

        // Validate the EventPoint in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEventPointUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedEventPoint, eventPoint),
            getPersistedEventPoint(eventPoint)
        );
    }

    @Test
    @Transactional
    void fullUpdateEventPointWithPatch() throws Exception {
        // Initialize the database
        eventPointRepository.saveAndFlush(eventPoint);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the eventPoint using partial update
        EventPoint partialUpdatedEventPoint = new EventPoint();
        partialUpdatedEventPoint.setId(eventPoint.getId());

        partialUpdatedEventPoint.seq(UPDATED_SEQ).rating(UPDATED_RATING).ratingMin(UPDATED_RATING_MIN).ratingMax(UPDATED_RATING_MAX);

        restEventPointMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEventPoint.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEventPoint))
            )
            .andExpect(status().isOk());

        // Validate the EventPoint in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEventPointUpdatableFieldsEquals(partialUpdatedEventPoint, getPersistedEventPoint(partialUpdatedEventPoint));
    }

    @Test
    @Transactional
    void patchNonExistingEventPoint() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        eventPoint.setId(UUID.randomUUID().toString());

        // Create the EventPoint
        EventPointDTO eventPointDTO = eventPointMapper.toDto(eventPoint);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEventPointMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, eventPointDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(eventPointDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EventPoint in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEventPoint() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        eventPoint.setId(UUID.randomUUID().toString());

        // Create the EventPoint
        EventPointDTO eventPointDTO = eventPointMapper.toDto(eventPoint);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEventPointMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(eventPointDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EventPoint in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEventPoint() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        eventPoint.setId(UUID.randomUUID().toString());

        // Create the EventPoint
        EventPointDTO eventPointDTO = eventPointMapper.toDto(eventPoint);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEventPointMockMvc
            .perform(
                patch(ENTITY_API_URL).with(csrf()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(eventPointDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EventPoint in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEventPoint() throws Exception {
        // Initialize the database
        eventPointRepository.saveAndFlush(eventPoint);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the eventPoint
        restEventPointMockMvc
            .perform(delete(ENTITY_API_URL_ID, eventPoint.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return eventPointRepository.count();
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

    protected EventPoint getPersistedEventPoint(EventPoint eventPoint) {
        return eventPointRepository.findById(eventPoint.getId()).orElseThrow();
    }

    protected void assertPersistedEventPointToMatchAllProperties(EventPoint expectedEventPoint) {
        assertEventPointAllPropertiesEquals(expectedEventPoint, getPersistedEventPoint(expectedEventPoint));
    }

    protected void assertPersistedEventPointToMatchUpdatableProperties(EventPoint expectedEventPoint) {
        assertEventPointAllUpdatablePropertiesEquals(expectedEventPoint, getPersistedEventPoint(expectedEventPoint));
    }
}
