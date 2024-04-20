package com.example.bfi.web.rest;

import static com.example.bfi.domain.LocationAsserts.*;
import static com.example.bfi.web.rest.TestUtil.createUpdateProxyForBean;
import static com.example.bfi.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.bfi.IntegrationTest;
import com.example.bfi.domain.Location;
import com.example.bfi.repository.LocationRepository;
import com.example.bfi.service.dto.LocationDTO;
import com.example.bfi.service.mapper.LocationMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
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

/**
 * Integration tests for the {@link LocationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LocationResourceIT {

    private static final String DEFAULT_COUNTRY_CODE = "AA";
    private static final String UPDATED_COUNTRY_CODE = "BB";

    private static final String DEFAULT_PARTY_ID = "AAAAAAAAAA";
    private static final String UPDATED_PARTY_ID = "BBBBBBBBBB";

    private static final Boolean DEFAULT_PUBLISH = false;
    private static final Boolean UPDATED_PUBLISH = true;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_POSTAL_CODE = "AAAAAAAAAA";
    private static final String UPDATED_POSTAL_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_STATE = "AAAAAAAAAA";
    private static final String UPDATED_STATE = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY = "AAA";
    private static final String UPDATED_COUNTRY = "BBB";

    private static final String DEFAULT_TIME_ZONE = "AAAAAAAAAA";
    private static final String UPDATED_TIME_ZONE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_CHARGING_WHEN_CLOSED = false;
    private static final Boolean UPDATED_CHARGING_WHEN_CLOSED = true;

    private static final ZonedDateTime DEFAULT_LAST_UPDATED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LAST_UPDATED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/locations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private LocationMapper locationMapper;

    @Autowired
    private MockMvc restLocationMockMvc;

    private Location location;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Location createEntity() {
        Location location = new Location()
            .countryCode(DEFAULT_COUNTRY_CODE)
            .partyId(DEFAULT_PARTY_ID)
            .publish(DEFAULT_PUBLISH)
            .name(DEFAULT_NAME)
            .address(DEFAULT_ADDRESS)
            .city(DEFAULT_CITY)
            .postalCode(DEFAULT_POSTAL_CODE)
            .state(DEFAULT_STATE)
            .country(DEFAULT_COUNTRY)
            .timeZone(DEFAULT_TIME_ZONE)
            .chargingWhenClosed(DEFAULT_CHARGING_WHEN_CLOSED)
            .lastUpdated(DEFAULT_LAST_UPDATED);
        return location;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Location createUpdatedEntity() {
        Location location = new Location()
            .countryCode(UPDATED_COUNTRY_CODE)
            .partyId(UPDATED_PARTY_ID)
            .publish(UPDATED_PUBLISH)
            .name(UPDATED_NAME)
            .address(UPDATED_ADDRESS)
            .city(UPDATED_CITY)
            .postalCode(UPDATED_POSTAL_CODE)
            .state(UPDATED_STATE)
            .country(UPDATED_COUNTRY)
            .timeZone(UPDATED_TIME_ZONE)
            .chargingWhenClosed(UPDATED_CHARGING_WHEN_CLOSED)
            .lastUpdated(UPDATED_LAST_UPDATED);
        return location;
    }

    @BeforeEach
    public void initTest() {
        locationRepository.deleteAll();
        location = createEntity();
    }

    @Test
    void createLocation() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Location
        LocationDTO locationDTO = locationMapper.toDto(location);
        var returnedLocationDTO = om.readValue(
            restLocationMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(locationDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            LocationDTO.class
        );

        // Validate the Location in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedLocation = locationMapper.toEntity(returnedLocationDTO);
        assertLocationUpdatableFieldsEquals(returnedLocation, getPersistedLocation(returnedLocation));
    }

    @Test
    void createLocationWithExistingId() throws Exception {
        // Create the Location with an existing ID
        location.setId("existing_id");
        LocationDTO locationDTO = locationMapper.toDto(location);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLocationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(locationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Location in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void getAllLocations() throws Exception {
        // Initialize the database
        locationRepository.save(location);

        // Get all the locationList
        restLocationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(location.getId())))
            .andExpect(jsonPath("$.[*].countryCode").value(hasItem(DEFAULT_COUNTRY_CODE)))
            .andExpect(jsonPath("$.[*].partyId").value(hasItem(DEFAULT_PARTY_ID)))
            .andExpect(jsonPath("$.[*].publish").value(hasItem(DEFAULT_PUBLISH.booleanValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].postalCode").value(hasItem(DEFAULT_POSTAL_CODE)))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE)))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY)))
            .andExpect(jsonPath("$.[*].timeZone").value(hasItem(DEFAULT_TIME_ZONE)))
            .andExpect(jsonPath("$.[*].chargingWhenClosed").value(hasItem(DEFAULT_CHARGING_WHEN_CLOSED.booleanValue())))
            .andExpect(jsonPath("$.[*].lastUpdated").value(hasItem(sameInstant(DEFAULT_LAST_UPDATED))));
    }

    @Test
    void getLocation() throws Exception {
        // Initialize the database
        locationRepository.save(location);

        // Get the location
        restLocationMockMvc
            .perform(get(ENTITY_API_URL_ID, location.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(location.getId()))
            .andExpect(jsonPath("$.countryCode").value(DEFAULT_COUNTRY_CODE))
            .andExpect(jsonPath("$.partyId").value(DEFAULT_PARTY_ID))
            .andExpect(jsonPath("$.publish").value(DEFAULT_PUBLISH.booleanValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY))
            .andExpect(jsonPath("$.postalCode").value(DEFAULT_POSTAL_CODE))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY))
            .andExpect(jsonPath("$.timeZone").value(DEFAULT_TIME_ZONE))
            .andExpect(jsonPath("$.chargingWhenClosed").value(DEFAULT_CHARGING_WHEN_CLOSED.booleanValue()))
            .andExpect(jsonPath("$.lastUpdated").value(sameInstant(DEFAULT_LAST_UPDATED)));
    }

    @Test
    void getNonExistingLocation() throws Exception {
        // Get the location
        restLocationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingLocation() throws Exception {
        // Initialize the database
        locationRepository.save(location);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the location
        Location updatedLocation = locationRepository.findById(location.getId()).orElseThrow();
        updatedLocation
            .countryCode(UPDATED_COUNTRY_CODE)
            .partyId(UPDATED_PARTY_ID)
            .publish(UPDATED_PUBLISH)
            .name(UPDATED_NAME)
            .address(UPDATED_ADDRESS)
            .city(UPDATED_CITY)
            .postalCode(UPDATED_POSTAL_CODE)
            .state(UPDATED_STATE)
            .country(UPDATED_COUNTRY)
            .timeZone(UPDATED_TIME_ZONE)
            .chargingWhenClosed(UPDATED_CHARGING_WHEN_CLOSED)
            .lastUpdated(UPDATED_LAST_UPDATED);
        LocationDTO locationDTO = locationMapper.toDto(updatedLocation);

        restLocationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, locationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(locationDTO))
            )
            .andExpect(status().isOk());

        // Validate the Location in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedLocationToMatchAllProperties(updatedLocation);
    }

    @Test
    void putNonExistingLocation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        location.setId(UUID.randomUUID().toString());

        // Create the Location
        LocationDTO locationDTO = locationMapper.toDto(location);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLocationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, locationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(locationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Location in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchLocation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        location.setId(UUID.randomUUID().toString());

        // Create the Location
        LocationDTO locationDTO = locationMapper.toDto(location);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLocationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(locationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Location in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamLocation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        location.setId(UUID.randomUUID().toString());

        // Create the Location
        LocationDTO locationDTO = locationMapper.toDto(location);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLocationMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(locationDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Location in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateLocationWithPatch() throws Exception {
        // Initialize the database
        locationRepository.save(location);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the location using partial update
        Location partialUpdatedLocation = new Location();
        partialUpdatedLocation.setId(location.getId());

        partialUpdatedLocation
            .name(UPDATED_NAME)
            .city(UPDATED_CITY)
            .state(UPDATED_STATE)
            .chargingWhenClosed(UPDATED_CHARGING_WHEN_CLOSED)
            .lastUpdated(UPDATED_LAST_UPDATED);

        restLocationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLocation.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedLocation))
            )
            .andExpect(status().isOk());

        // Validate the Location in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertLocationUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedLocation, location), getPersistedLocation(location));
    }

    @Test
    void fullUpdateLocationWithPatch() throws Exception {
        // Initialize the database
        locationRepository.save(location);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the location using partial update
        Location partialUpdatedLocation = new Location();
        partialUpdatedLocation.setId(location.getId());

        partialUpdatedLocation
            .countryCode(UPDATED_COUNTRY_CODE)
            .partyId(UPDATED_PARTY_ID)
            .publish(UPDATED_PUBLISH)
            .name(UPDATED_NAME)
            .address(UPDATED_ADDRESS)
            .city(UPDATED_CITY)
            .postalCode(UPDATED_POSTAL_CODE)
            .state(UPDATED_STATE)
            .country(UPDATED_COUNTRY)
            .timeZone(UPDATED_TIME_ZONE)
            .chargingWhenClosed(UPDATED_CHARGING_WHEN_CLOSED)
            .lastUpdated(UPDATED_LAST_UPDATED);

        restLocationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLocation.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedLocation))
            )
            .andExpect(status().isOk());

        // Validate the Location in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertLocationUpdatableFieldsEquals(partialUpdatedLocation, getPersistedLocation(partialUpdatedLocation));
    }

    @Test
    void patchNonExistingLocation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        location.setId(UUID.randomUUID().toString());

        // Create the Location
        LocationDTO locationDTO = locationMapper.toDto(location);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLocationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, locationDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(locationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Location in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchLocation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        location.setId(UUID.randomUUID().toString());

        // Create the Location
        LocationDTO locationDTO = locationMapper.toDto(location);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLocationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(locationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Location in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamLocation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        location.setId(UUID.randomUUID().toString());

        // Create the Location
        LocationDTO locationDTO = locationMapper.toDto(location);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLocationMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(locationDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Location in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteLocation() throws Exception {
        // Initialize the database
        locationRepository.save(location);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the location
        restLocationMockMvc
            .perform(delete(ENTITY_API_URL_ID, location.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return locationRepository.count();
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

    protected Location getPersistedLocation(Location location) {
        return locationRepository.findById(location.getId()).orElseThrow();
    }

    protected void assertPersistedLocationToMatchAllProperties(Location expectedLocation) {
        assertLocationAllPropertiesEquals(expectedLocation, getPersistedLocation(expectedLocation));
    }

    protected void assertPersistedLocationToMatchUpdatableProperties(Location expectedLocation) {
        assertLocationAllUpdatablePropertiesEquals(expectedLocation, getPersistedLocation(expectedLocation));
    }
}
