package com.phoenixdarts.toss.backend.service.impl;

import com.phoenixdarts.toss.backend.domain.PaymentInfo;
import com.phoenixdarts.toss.backend.repository.PaymentInfoRepository;
import com.phoenixdarts.toss.backend.service.PaymentInfoService;
import com.phoenixdarts.toss.backend.service.dto.PaymentInfoDTO;
import com.phoenixdarts.toss.backend.service.mapper.PaymentInfoMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.phoenixdarts.toss.backend.domain.PaymentInfo}.
 */
@Service
@Transactional
public class PaymentInfoServiceImpl implements PaymentInfoService {

    private final Logger log = LoggerFactory.getLogger(PaymentInfoServiceImpl.class);

    private final PaymentInfoRepository paymentInfoRepository;

    private final PaymentInfoMapper paymentInfoMapper;

    public PaymentInfoServiceImpl(PaymentInfoRepository paymentInfoRepository, PaymentInfoMapper paymentInfoMapper) {
        this.paymentInfoRepository = paymentInfoRepository;
        this.paymentInfoMapper = paymentInfoMapper;
    }

    @Override
    public PaymentInfoDTO save(PaymentInfoDTO paymentInfoDTO) {
        log.debug("Request to save PaymentInfo : {}", paymentInfoDTO);
        PaymentInfo paymentInfo = paymentInfoMapper.toEntity(paymentInfoDTO);
        paymentInfo = paymentInfoRepository.save(paymentInfo);
        return paymentInfoMapper.toDto(paymentInfo);
    }

    @Override
    public PaymentInfoDTO update(PaymentInfoDTO paymentInfoDTO) {
        log.debug("Request to update PaymentInfo : {}", paymentInfoDTO);
        PaymentInfo paymentInfo = paymentInfoMapper.toEntity(paymentInfoDTO);
        paymentInfo = paymentInfoRepository.save(paymentInfo);
        return paymentInfoMapper.toDto(paymentInfo);
    }

    @Override
    public Optional<PaymentInfoDTO> partialUpdate(PaymentInfoDTO paymentInfoDTO) {
        log.debug("Request to partially update PaymentInfo : {}", paymentInfoDTO);

        return paymentInfoRepository
            .findById(paymentInfoDTO.getId())
            .map(existingPaymentInfo -> {
                paymentInfoMapper.partialUpdate(existingPaymentInfo, paymentInfoDTO);

                return existingPaymentInfo;
            })
            .map(paymentInfoRepository::save)
            .map(paymentInfoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaymentInfoDTO> findAll() {
        log.debug("Request to get all PaymentInfos");
        return paymentInfoRepository.findAll().stream().map(paymentInfoMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PaymentInfoDTO> findOne(String id) {
        log.debug("Request to get PaymentInfo : {}", id);
        return paymentInfoRepository.findById(id).map(paymentInfoMapper::toDto);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete PaymentInfo : {}", id);
        paymentInfoRepository.deleteById(id);
    }
}
