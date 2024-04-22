package com.phoenixdarts.toss.web.rest;

import static com.phoenixdarts.toss.domain.PaymentInfoAsserts.*;
import static com.phoenixdarts.toss.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.phoenixdarts.toss.IntegrationTest;
import com.phoenixdarts.toss.domain.PaymentInfo;
import com.phoenixdarts.toss.domain.enumeration.PaymentMethodType;
import com.phoenixdarts.toss.domain.enumeration.PaymentStatusType;
import com.phoenixdarts.toss.repository.PaymentInfoRepository;
import com.phoenixdarts.toss.service.dto.PaymentInfoDTO;
import com.phoenixdarts.toss.service.mapper.PaymentInfoMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Integration tests for the {@link PaymentInfoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PaymentInfoResourceIT {

    private static final String DEFAULT_ORDER_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_ORDER_NUMBER = "BBBBBBBBBB";

    private static final Instant DEFAULT_PAYMENT_COMPLETED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_PAYMENT_COMPLETED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final PaymentStatusType DEFAULT_STATUS = PaymentStatusType.WAITING_PAYMENT;
    private static final PaymentStatusType UPDATED_STATUS = PaymentStatusType.COMPLETE_PAYMENT;

    private static final PaymentMethodType DEFAULT_PAYMENT_METHOD_TYPE = PaymentMethodType.PG;
    private static final PaymentMethodType UPDATED_PAYMENT_METHOD_TYPE = PaymentMethodType.CASH;

    private static final Integer DEFAULT_PG_TID = 1;
    private static final Integer UPDATED_PG_TID = 2;

    private static final Integer DEFAULT_PG_STATUS = 1;
    private static final Integer UPDATED_PG_STATUS = 2;

    private static final String DEFAULT_PG_DETAIL = "AAAAAAAAAA";
    private static final String UPDATED_PG_DETAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PAYER = "AAAAAAAAAA";
    private static final String UPDATED_PAYER = "BBBBBBBBBB";

    private static final String DEFAULT_PAYER_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PAYER_PHONE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/payment-infos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private PaymentInfoRepository paymentInfoRepository;

    @Autowired
    private PaymentInfoMapper paymentInfoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPaymentInfoMockMvc;

    private PaymentInfo paymentInfo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PaymentInfo createEntity(EntityManager em) {
        PaymentInfo paymentInfo = new PaymentInfo()
            .orderNumber(DEFAULT_ORDER_NUMBER)
            .paymentCompletedDate(DEFAULT_PAYMENT_COMPLETED_DATE)
            .status(DEFAULT_STATUS)
            .paymentMethodType(DEFAULT_PAYMENT_METHOD_TYPE)
            .pgTID(DEFAULT_PG_TID)
            .pgStatus(DEFAULT_PG_STATUS)
            .pgDetail(DEFAULT_PG_DETAIL)
            .payer(DEFAULT_PAYER)
            .payerPhone(DEFAULT_PAYER_PHONE);
        return paymentInfo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PaymentInfo createUpdatedEntity(EntityManager em) {
        PaymentInfo paymentInfo = new PaymentInfo()
            .orderNumber(UPDATED_ORDER_NUMBER)
            .paymentCompletedDate(UPDATED_PAYMENT_COMPLETED_DATE)
            .status(UPDATED_STATUS)
            .paymentMethodType(UPDATED_PAYMENT_METHOD_TYPE)
            .pgTID(UPDATED_PG_TID)
            .pgStatus(UPDATED_PG_STATUS)
            .pgDetail(UPDATED_PG_DETAIL)
            .payer(UPDATED_PAYER)
            .payerPhone(UPDATED_PAYER_PHONE);
        return paymentInfo;
    }

    @BeforeEach
    public void initTest() {
        paymentInfo = createEntity(em);
    }

    @Test
    @Transactional
    void createPaymentInfo() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the PaymentInfo
        PaymentInfoDTO paymentInfoDTO = paymentInfoMapper.toDto(paymentInfo);
        var returnedPaymentInfoDTO = om.readValue(
            restPaymentInfoMockMvc
                .perform(
                    post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(paymentInfoDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            PaymentInfoDTO.class
        );

        // Validate the PaymentInfo in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedPaymentInfo = paymentInfoMapper.toEntity(returnedPaymentInfoDTO);
        assertPaymentInfoUpdatableFieldsEquals(returnedPaymentInfo, getPersistedPaymentInfo(returnedPaymentInfo));
    }

    @Test
    @Transactional
    void createPaymentInfoWithExistingId() throws Exception {
        // Create the PaymentInfo with an existing ID
        paymentInfo.setId("existing_id");
        PaymentInfoDTO paymentInfoDTO = paymentInfoMapper.toDto(paymentInfo);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaymentInfoMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(paymentInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentInfo in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkStatusIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        paymentInfo.setStatus(null);

        // Create the PaymentInfo, which fails.
        PaymentInfoDTO paymentInfoDTO = paymentInfoMapper.toDto(paymentInfo);

        restPaymentInfoMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(paymentInfoDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPaymentInfos() throws Exception {
        // Initialize the database
        paymentInfoRepository.saveAndFlush(paymentInfo);

        // Get all the paymentInfoList
        restPaymentInfoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paymentInfo.getId())))
            .andExpect(jsonPath("$.[*].orderNumber").value(hasItem(DEFAULT_ORDER_NUMBER)))
            .andExpect(jsonPath("$.[*].paymentCompletedDate").value(hasItem(DEFAULT_PAYMENT_COMPLETED_DATE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].paymentMethodType").value(hasItem(DEFAULT_PAYMENT_METHOD_TYPE.toString())))
            .andExpect(jsonPath("$.[*].pgTID").value(hasItem(DEFAULT_PG_TID)))
            .andExpect(jsonPath("$.[*].pgStatus").value(hasItem(DEFAULT_PG_STATUS)))
            .andExpect(jsonPath("$.[*].pgDetail").value(hasItem(DEFAULT_PG_DETAIL)))
            .andExpect(jsonPath("$.[*].payer").value(hasItem(DEFAULT_PAYER)))
            .andExpect(jsonPath("$.[*].payerPhone").value(hasItem(DEFAULT_PAYER_PHONE)));
    }

    @Test
    @Transactional
    void getPaymentInfo() throws Exception {
        // Initialize the database
        paymentInfoRepository.saveAndFlush(paymentInfo);

        // Get the paymentInfo
        restPaymentInfoMockMvc
            .perform(get(ENTITY_API_URL_ID, paymentInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(paymentInfo.getId()))
            .andExpect(jsonPath("$.orderNumber").value(DEFAULT_ORDER_NUMBER))
            .andExpect(jsonPath("$.paymentCompletedDate").value(DEFAULT_PAYMENT_COMPLETED_DATE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.paymentMethodType").value(DEFAULT_PAYMENT_METHOD_TYPE.toString()))
            .andExpect(jsonPath("$.pgTID").value(DEFAULT_PG_TID))
            .andExpect(jsonPath("$.pgStatus").value(DEFAULT_PG_STATUS))
            .andExpect(jsonPath("$.pgDetail").value(DEFAULT_PG_DETAIL))
            .andExpect(jsonPath("$.payer").value(DEFAULT_PAYER))
            .andExpect(jsonPath("$.payerPhone").value(DEFAULT_PAYER_PHONE));
    }

    @Test
    @Transactional
    void getNonExistingPaymentInfo() throws Exception {
        // Get the paymentInfo
        restPaymentInfoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPaymentInfo() throws Exception {
        // Initialize the database
        paymentInfoRepository.saveAndFlush(paymentInfo);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the paymentInfo
        PaymentInfo updatedPaymentInfo = paymentInfoRepository.findById(paymentInfo.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedPaymentInfo are not directly saved in db
        em.detach(updatedPaymentInfo);
        updatedPaymentInfo
            .orderNumber(UPDATED_ORDER_NUMBER)
            .paymentCompletedDate(UPDATED_PAYMENT_COMPLETED_DATE)
            .status(UPDATED_STATUS)
            .paymentMethodType(UPDATED_PAYMENT_METHOD_TYPE)
            .pgTID(UPDATED_PG_TID)
            .pgStatus(UPDATED_PG_STATUS)
            .pgDetail(UPDATED_PG_DETAIL)
            .payer(UPDATED_PAYER)
            .payerPhone(UPDATED_PAYER_PHONE);
        PaymentInfoDTO paymentInfoDTO = paymentInfoMapper.toDto(updatedPaymentInfo);

        restPaymentInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, paymentInfoDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(paymentInfoDTO))
            )
            .andExpect(status().isOk());

        // Validate the PaymentInfo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedPaymentInfoToMatchAllProperties(updatedPaymentInfo);
    }

    @Test
    @Transactional
    void putNonExistingPaymentInfo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        paymentInfo.setId(UUID.randomUUID().toString());

        // Create the PaymentInfo
        PaymentInfoDTO paymentInfoDTO = paymentInfoMapper.toDto(paymentInfo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaymentInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, paymentInfoDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(paymentInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentInfo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPaymentInfo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        paymentInfo.setId(UUID.randomUUID().toString());

        // Create the PaymentInfo
        PaymentInfoDTO paymentInfoDTO = paymentInfoMapper.toDto(paymentInfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(paymentInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentInfo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPaymentInfo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        paymentInfo.setId(UUID.randomUUID().toString());

        // Create the PaymentInfo
        PaymentInfoDTO paymentInfoDTO = paymentInfoMapper.toDto(paymentInfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentInfoMockMvc
            .perform(put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(paymentInfoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PaymentInfo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePaymentInfoWithPatch() throws Exception {
        // Initialize the database
        paymentInfoRepository.saveAndFlush(paymentInfo);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the paymentInfo using partial update
        PaymentInfo partialUpdatedPaymentInfo = new PaymentInfo();
        partialUpdatedPaymentInfo.setId(paymentInfo.getId());

        partialUpdatedPaymentInfo
            .paymentCompletedDate(UPDATED_PAYMENT_COMPLETED_DATE)
            .pgStatus(UPDATED_PG_STATUS)
            .payerPhone(UPDATED_PAYER_PHONE);

        restPaymentInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPaymentInfo.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPaymentInfo))
            )
            .andExpect(status().isOk());

        // Validate the PaymentInfo in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPaymentInfoUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedPaymentInfo, paymentInfo),
            getPersistedPaymentInfo(paymentInfo)
        );
    }

    @Test
    @Transactional
    void fullUpdatePaymentInfoWithPatch() throws Exception {
        // Initialize the database
        paymentInfoRepository.saveAndFlush(paymentInfo);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the paymentInfo using partial update
        PaymentInfo partialUpdatedPaymentInfo = new PaymentInfo();
        partialUpdatedPaymentInfo.setId(paymentInfo.getId());

        partialUpdatedPaymentInfo
            .orderNumber(UPDATED_ORDER_NUMBER)
            .paymentCompletedDate(UPDATED_PAYMENT_COMPLETED_DATE)
            .status(UPDATED_STATUS)
            .paymentMethodType(UPDATED_PAYMENT_METHOD_TYPE)
            .pgTID(UPDATED_PG_TID)
            .pgStatus(UPDATED_PG_STATUS)
            .pgDetail(UPDATED_PG_DETAIL)
            .payer(UPDATED_PAYER)
            .payerPhone(UPDATED_PAYER_PHONE);

        restPaymentInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPaymentInfo.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPaymentInfo))
            )
            .andExpect(status().isOk());

        // Validate the PaymentInfo in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPaymentInfoUpdatableFieldsEquals(partialUpdatedPaymentInfo, getPersistedPaymentInfo(partialUpdatedPaymentInfo));
    }

    @Test
    @Transactional
    void patchNonExistingPaymentInfo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        paymentInfo.setId(UUID.randomUUID().toString());

        // Create the PaymentInfo
        PaymentInfoDTO paymentInfoDTO = paymentInfoMapper.toDto(paymentInfo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaymentInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, paymentInfoDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(paymentInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentInfo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPaymentInfo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        paymentInfo.setId(UUID.randomUUID().toString());

        // Create the PaymentInfo
        PaymentInfoDTO paymentInfoDTO = paymentInfoMapper.toDto(paymentInfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(paymentInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentInfo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPaymentInfo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        paymentInfo.setId(UUID.randomUUID().toString());

        // Create the PaymentInfo
        PaymentInfoDTO paymentInfoDTO = paymentInfoMapper.toDto(paymentInfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentInfoMockMvc
            .perform(
                patch(ENTITY_API_URL).with(csrf()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(paymentInfoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PaymentInfo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePaymentInfo() throws Exception {
        // Initialize the database
        paymentInfoRepository.saveAndFlush(paymentInfo);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the paymentInfo
        restPaymentInfoMockMvc
            .perform(delete(ENTITY_API_URL_ID, paymentInfo.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return paymentInfoRepository.count();
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

    protected PaymentInfo getPersistedPaymentInfo(PaymentInfo paymentInfo) {
        return paymentInfoRepository.findById(paymentInfo.getId()).orElseThrow();
    }

    protected void assertPersistedPaymentInfoToMatchAllProperties(PaymentInfo expectedPaymentInfo) {
        assertPaymentInfoAllPropertiesEquals(expectedPaymentInfo, getPersistedPaymentInfo(expectedPaymentInfo));
    }

    protected void assertPersistedPaymentInfoToMatchUpdatableProperties(PaymentInfo expectedPaymentInfo) {
        assertPaymentInfoAllUpdatablePropertiesEquals(expectedPaymentInfo, getPersistedPaymentInfo(expectedPaymentInfo));
    }
}
