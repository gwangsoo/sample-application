package com.phoenixdarts.toss.backend.web.rest;

import static com.phoenixdarts.toss.backend.domain.EntryAsserts.*;
import static com.phoenixdarts.toss.backend.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.phoenixdarts.toss.IntegrationTest;
import com.phoenixdarts.toss.backend.domain.Entry;
import com.phoenixdarts.toss.backend.domain.enumeration.AttendanceStatusType;
import com.phoenixdarts.toss.backend.domain.enumeration.GenderType;
import com.phoenixdarts.toss.backend.repository.EntryRepository;
import com.phoenixdarts.toss.backend.service.dto.EntryDTO;
import com.phoenixdarts.toss.backend.service.mapper.EntryMapper;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link EntryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EntryResourceIT {

    private static final String DEFAULT_ENTRY_NO = "AAAA";
    private static final String UPDATED_ENTRY_NO = "BBBB";

    private static final String DEFAULT_PHOENIX_NO = "AAAAAAAAAA";
    private static final String UPDATED_PHOENIX_NO = "BBBBBBBBBB";

    private static final String DEFAULT_CARD_NO = "AAAAAAAAAA";
    private static final String UPDATED_CARD_NO = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ENGLISH_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ENGLISH_NAME = "BBBBBBBBBB";

    private static final Float DEFAULT_RATING = 0F;
    private static final Float UPDATED_RATING = 1F;

    private static final String DEFAULT_MOBILE_NO = "AAAAAAAAAA";
    private static final String UPDATED_MOBILE_NO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_BIRTH_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BIRTH_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final GenderType DEFAULT_GENDER_TYPE = GenderType.MALE;
    private static final GenderType UPDATED_GENDER_TYPE = GenderType.FEMAIL;

    private static final AttendanceStatusType DEFAULT_ATTENDANCE_STATUS_TYPE = AttendanceStatusType.NON_ATTENDANCE;
    private static final AttendanceStatusType UPDATED_ATTENDANCE_STATUS_TYPE = AttendanceStatusType.GIVE_UP;

    private static final String ENTITY_API_URL = "/api/entries";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private EntryRepository entryRepository;

    @Autowired
    private EntryMapper entryMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEntryMockMvc;

    private Entry entry;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Entry createEntity(EntityManager em) {
        Entry entry = new Entry()
            .entryNo(DEFAULT_ENTRY_NO)
            .phoenixNo(DEFAULT_PHOENIX_NO)
            .cardNo(DEFAULT_CARD_NO)
            .name(DEFAULT_NAME)
            .englishName(DEFAULT_ENGLISH_NAME)
            .rating(DEFAULT_RATING)
            .mobileNo(DEFAULT_MOBILE_NO)
            .birthDate(DEFAULT_BIRTH_DATE)
            .email(DEFAULT_EMAIL)
            .genderType(DEFAULT_GENDER_TYPE)
            .attendanceStatusType(DEFAULT_ATTENDANCE_STATUS_TYPE);
        return entry;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Entry createUpdatedEntity(EntityManager em) {
        Entry entry = new Entry()
            .entryNo(UPDATED_ENTRY_NO)
            .phoenixNo(UPDATED_PHOENIX_NO)
            .cardNo(UPDATED_CARD_NO)
            .name(UPDATED_NAME)
            .englishName(UPDATED_ENGLISH_NAME)
            .rating(UPDATED_RATING)
            .mobileNo(UPDATED_MOBILE_NO)
            .birthDate(UPDATED_BIRTH_DATE)
            .email(UPDATED_EMAIL)
            .genderType(UPDATED_GENDER_TYPE)
            .attendanceStatusType(UPDATED_ATTENDANCE_STATUS_TYPE);
        return entry;
    }

    @BeforeEach
    public void initTest() {
        entry = createEntity(em);
    }

    @Test
    @Transactional
    void createEntry() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Entry
        EntryDTO entryDTO = entryMapper.toDto(entry);
        var returnedEntryDTO = om.readValue(
            restEntryMockMvc
                .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(entryDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            EntryDTO.class
        );

        // Validate the Entry in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedEntry = entryMapper.toEntity(returnedEntryDTO);
        assertEntryUpdatableFieldsEquals(returnedEntry, getPersistedEntry(returnedEntry));
    }

    @Test
    @Transactional
    void createEntryWithExistingId() throws Exception {
        // Create the Entry with an existing ID
        entry.setId("existing_id");
        EntryDTO entryDTO = entryMapper.toDto(entry);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEntryMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(entryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Entry in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkEntryNoIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        entry.setEntryNo(null);

        // Create the Entry, which fails.
        EntryDTO entryDTO = entryMapper.toDto(entry);

        restEntryMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(entryDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCardNoIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        entry.setCardNo(null);

        // Create the Entry, which fails.
        EntryDTO entryDTO = entryMapper.toDto(entry);

        restEntryMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(entryDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        entry.setName(null);

        // Create the Entry, which fails.
        EntryDTO entryDTO = entryMapper.toDto(entry);

        restEntryMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(entryDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEnglishNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        entry.setEnglishName(null);

        // Create the Entry, which fails.
        EntryDTO entryDTO = entryMapper.toDto(entry);

        restEntryMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(entryDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkRatingIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        entry.setRating(null);

        // Create the Entry, which fails.
        EntryDTO entryDTO = entryMapper.toDto(entry);

        restEntryMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(entryDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkGenderTypeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        entry.setGenderType(null);

        // Create the Entry, which fails.
        EntryDTO entryDTO = entryMapper.toDto(entry);

        restEntryMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(entryDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllEntries() throws Exception {
        // Initialize the database
        entryRepository.saveAndFlush(entry);

        // Get all the entryList
        restEntryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(entry.getId())))
            .andExpect(jsonPath("$.[*].entryNo").value(hasItem(DEFAULT_ENTRY_NO)))
            .andExpect(jsonPath("$.[*].phoenixNo").value(hasItem(DEFAULT_PHOENIX_NO)))
            .andExpect(jsonPath("$.[*].cardNo").value(hasItem(DEFAULT_CARD_NO)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].englishName").value(hasItem(DEFAULT_ENGLISH_NAME)))
            .andExpect(jsonPath("$.[*].rating").value(hasItem(DEFAULT_RATING.doubleValue())))
            .andExpect(jsonPath("$.[*].mobileNo").value(hasItem(DEFAULT_MOBILE_NO)))
            .andExpect(jsonPath("$.[*].birthDate").value(hasItem(DEFAULT_BIRTH_DATE.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].genderType").value(hasItem(DEFAULT_GENDER_TYPE.toString())))
            .andExpect(jsonPath("$.[*].attendanceStatusType").value(hasItem(DEFAULT_ATTENDANCE_STATUS_TYPE.toString())));
    }

    @Test
    @Transactional
    void getEntry() throws Exception {
        // Initialize the database
        entryRepository.saveAndFlush(entry);

        // Get the entry
        restEntryMockMvc
            .perform(get(ENTITY_API_URL_ID, entry.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(entry.getId()))
            .andExpect(jsonPath("$.entryNo").value(DEFAULT_ENTRY_NO))
            .andExpect(jsonPath("$.phoenixNo").value(DEFAULT_PHOENIX_NO))
            .andExpect(jsonPath("$.cardNo").value(DEFAULT_CARD_NO))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.englishName").value(DEFAULT_ENGLISH_NAME))
            .andExpect(jsonPath("$.rating").value(DEFAULT_RATING.doubleValue()))
            .andExpect(jsonPath("$.mobileNo").value(DEFAULT_MOBILE_NO))
            .andExpect(jsonPath("$.birthDate").value(DEFAULT_BIRTH_DATE.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.genderType").value(DEFAULT_GENDER_TYPE.toString()))
            .andExpect(jsonPath("$.attendanceStatusType").value(DEFAULT_ATTENDANCE_STATUS_TYPE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingEntry() throws Exception {
        // Get the entry
        restEntryMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEntry() throws Exception {
        // Initialize the database
        entryRepository.saveAndFlush(entry);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the entry
        Entry updatedEntry = entryRepository.findById(entry.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedEntry are not directly saved in db
        em.detach(updatedEntry);
        updatedEntry
            .entryNo(UPDATED_ENTRY_NO)
            .phoenixNo(UPDATED_PHOENIX_NO)
            .cardNo(UPDATED_CARD_NO)
            .name(UPDATED_NAME)
            .englishName(UPDATED_ENGLISH_NAME)
            .rating(UPDATED_RATING)
            .mobileNo(UPDATED_MOBILE_NO)
            .birthDate(UPDATED_BIRTH_DATE)
            .email(UPDATED_EMAIL)
            .genderType(UPDATED_GENDER_TYPE)
            .attendanceStatusType(UPDATED_ATTENDANCE_STATUS_TYPE);
        EntryDTO entryDTO = entryMapper.toDto(updatedEntry);

        restEntryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, entryDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(entryDTO))
            )
            .andExpect(status().isOk());

        // Validate the Entry in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedEntryToMatchAllProperties(updatedEntry);
    }

    @Test
    @Transactional
    void putNonExistingEntry() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        entry.setId(UUID.randomUUID().toString());

        // Create the Entry
        EntryDTO entryDTO = entryMapper.toDto(entry);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEntryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, entryDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(entryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Entry in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEntry() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        entry.setId(UUID.randomUUID().toString());

        // Create the Entry
        EntryDTO entryDTO = entryMapper.toDto(entry);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEntryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(entryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Entry in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEntry() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        entry.setId(UUID.randomUUID().toString());

        // Create the Entry
        EntryDTO entryDTO = entryMapper.toDto(entry);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEntryMockMvc
            .perform(put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(entryDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Entry in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEntryWithPatch() throws Exception {
        // Initialize the database
        entryRepository.saveAndFlush(entry);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the entry using partial update
        Entry partialUpdatedEntry = new Entry();
        partialUpdatedEntry.setId(entry.getId());

        partialUpdatedEntry
            .phoenixNo(UPDATED_PHOENIX_NO)
            .name(UPDATED_NAME)
            .englishName(UPDATED_ENGLISH_NAME)
            .mobileNo(UPDATED_MOBILE_NO)
            .birthDate(UPDATED_BIRTH_DATE)
            .email(UPDATED_EMAIL)
            .attendanceStatusType(UPDATED_ATTENDANCE_STATUS_TYPE);

        restEntryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEntry.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEntry))
            )
            .andExpect(status().isOk());

        // Validate the Entry in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEntryUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedEntry, entry), getPersistedEntry(entry));
    }

    @Test
    @Transactional
    void fullUpdateEntryWithPatch() throws Exception {
        // Initialize the database
        entryRepository.saveAndFlush(entry);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the entry using partial update
        Entry partialUpdatedEntry = new Entry();
        partialUpdatedEntry.setId(entry.getId());

        partialUpdatedEntry
            .entryNo(UPDATED_ENTRY_NO)
            .phoenixNo(UPDATED_PHOENIX_NO)
            .cardNo(UPDATED_CARD_NO)
            .name(UPDATED_NAME)
            .englishName(UPDATED_ENGLISH_NAME)
            .rating(UPDATED_RATING)
            .mobileNo(UPDATED_MOBILE_NO)
            .birthDate(UPDATED_BIRTH_DATE)
            .email(UPDATED_EMAIL)
            .genderType(UPDATED_GENDER_TYPE)
            .attendanceStatusType(UPDATED_ATTENDANCE_STATUS_TYPE);

        restEntryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEntry.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEntry))
            )
            .andExpect(status().isOk());

        // Validate the Entry in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEntryUpdatableFieldsEquals(partialUpdatedEntry, getPersistedEntry(partialUpdatedEntry));
    }

    @Test
    @Transactional
    void patchNonExistingEntry() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        entry.setId(UUID.randomUUID().toString());

        // Create the Entry
        EntryDTO entryDTO = entryMapper.toDto(entry);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEntryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, entryDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(entryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Entry in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEntry() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        entry.setId(UUID.randomUUID().toString());

        // Create the Entry
        EntryDTO entryDTO = entryMapper.toDto(entry);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEntryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(entryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Entry in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEntry() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        entry.setId(UUID.randomUUID().toString());

        // Create the Entry
        EntryDTO entryDTO = entryMapper.toDto(entry);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEntryMockMvc
            .perform(patch(ENTITY_API_URL).with(csrf()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(entryDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Entry in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEntry() throws Exception {
        // Initialize the database
        entryRepository.saveAndFlush(entry);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the entry
        restEntryMockMvc
            .perform(delete(ENTITY_API_URL_ID, entry.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return entryRepository.count();
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

    protected Entry getPersistedEntry(Entry entry) {
        return entryRepository.findById(entry.getId()).orElseThrow();
    }

    protected void assertPersistedEntryToMatchAllProperties(Entry expectedEntry) {
        assertEntryAllPropertiesEquals(expectedEntry, getPersistedEntry(expectedEntry));
    }

    protected void assertPersistedEntryToMatchUpdatableProperties(Entry expectedEntry) {
        assertEntryAllUpdatablePropertiesEquals(expectedEntry, getPersistedEntry(expectedEntry));
    }
}
