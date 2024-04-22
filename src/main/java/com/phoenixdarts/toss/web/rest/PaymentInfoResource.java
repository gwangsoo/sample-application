package com.phoenixdarts.toss.web.rest;

import com.phoenixdarts.toss.repository.PaymentInfoRepository;
import com.phoenixdarts.toss.service.PaymentInfoService;
import com.phoenixdarts.toss.service.dto.PaymentInfoDTO;
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
 * REST controller for managing {@link com.phoenixdarts.toss.domain.PaymentInfo}.
 */
@RestController
@RequestMapping("/api/payment-infos")
public class PaymentInfoResource {

    private final Logger log = LoggerFactory.getLogger(PaymentInfoResource.class);

    private static final String ENTITY_NAME = "paymentInfo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PaymentInfoService paymentInfoService;

    private final PaymentInfoRepository paymentInfoRepository;

    public PaymentInfoResource(PaymentInfoService paymentInfoService, PaymentInfoRepository paymentInfoRepository) {
        this.paymentInfoService = paymentInfoService;
        this.paymentInfoRepository = paymentInfoRepository;
    }

    /**
     * {@code POST  /payment-infos} : Create a new paymentInfo.
     *
     * @param paymentInfoDTO the paymentInfoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new paymentInfoDTO, or with status {@code 400 (Bad Request)} if the paymentInfo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<PaymentInfoDTO> createPaymentInfo(@Valid @RequestBody PaymentInfoDTO paymentInfoDTO) throws URISyntaxException {
        log.debug("REST request to save PaymentInfo : {}", paymentInfoDTO);
        if (paymentInfoDTO.getId() != null) {
            throw new BadRequestAlertException("A new paymentInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        paymentInfoDTO = paymentInfoService.save(paymentInfoDTO);
        return ResponseEntity.created(new URI("/api/payment-infos/" + paymentInfoDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, paymentInfoDTO.getId()))
            .body(paymentInfoDTO);
    }

    /**
     * {@code PUT  /payment-infos/:id} : Updates an existing paymentInfo.
     *
     * @param id the id of the paymentInfoDTO to save.
     * @param paymentInfoDTO the paymentInfoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paymentInfoDTO,
     * or with status {@code 400 (Bad Request)} if the paymentInfoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the paymentInfoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PaymentInfoDTO> updatePaymentInfo(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody PaymentInfoDTO paymentInfoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update PaymentInfo : {}, {}", id, paymentInfoDTO);
        if (paymentInfoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, paymentInfoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!paymentInfoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        paymentInfoDTO = paymentInfoService.update(paymentInfoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, paymentInfoDTO.getId()))
            .body(paymentInfoDTO);
    }

    /**
     * {@code PATCH  /payment-infos/:id} : Partial updates given fields of an existing paymentInfo, field will ignore if it is null
     *
     * @param id the id of the paymentInfoDTO to save.
     * @param paymentInfoDTO the paymentInfoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paymentInfoDTO,
     * or with status {@code 400 (Bad Request)} if the paymentInfoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the paymentInfoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the paymentInfoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PaymentInfoDTO> partialUpdatePaymentInfo(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody PaymentInfoDTO paymentInfoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update PaymentInfo partially : {}, {}", id, paymentInfoDTO);
        if (paymentInfoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, paymentInfoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!paymentInfoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PaymentInfoDTO> result = paymentInfoService.partialUpdate(paymentInfoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, paymentInfoDTO.getId())
        );
    }

    /**
     * {@code GET  /payment-infos} : get all the paymentInfos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of paymentInfos in body.
     */
    @GetMapping("")
    public List<PaymentInfoDTO> getAllPaymentInfos() {
        log.debug("REST request to get all PaymentInfos");
        return paymentInfoService.findAll();
    }

    /**
     * {@code GET  /payment-infos/:id} : get the "id" paymentInfo.
     *
     * @param id the id of the paymentInfoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the paymentInfoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PaymentInfoDTO> getPaymentInfo(@PathVariable("id") String id) {
        log.debug("REST request to get PaymentInfo : {}", id);
        Optional<PaymentInfoDTO> paymentInfoDTO = paymentInfoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(paymentInfoDTO);
    }

    /**
     * {@code DELETE  /payment-infos/:id} : delete the "id" paymentInfo.
     *
     * @param id the id of the paymentInfoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePaymentInfo(@PathVariable("id") String id) {
        log.debug("REST request to delete PaymentInfo : {}", id);
        paymentInfoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
