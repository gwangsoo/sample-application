package com.phoenixdarts.toss.service.impl;

import com.phoenixdarts.toss.domain.RewardDetail;
import com.phoenixdarts.toss.repository.RewardDetailRepository;
import com.phoenixdarts.toss.service.RewardDetailService;
import com.phoenixdarts.toss.service.dto.RewardDetailDTO;
import com.phoenixdarts.toss.service.mapper.RewardDetailMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.phoenixdarts.toss.domain.RewardDetail}.
 */
@Service
@Transactional
public class RewardDetailServiceImpl implements RewardDetailService {

    private final Logger log = LoggerFactory.getLogger(RewardDetailServiceImpl.class);

    private final RewardDetailRepository rewardDetailRepository;

    private final RewardDetailMapper rewardDetailMapper;

    public RewardDetailServiceImpl(RewardDetailRepository rewardDetailRepository, RewardDetailMapper rewardDetailMapper) {
        this.rewardDetailRepository = rewardDetailRepository;
        this.rewardDetailMapper = rewardDetailMapper;
    }

    @Override
    public RewardDetailDTO save(RewardDetailDTO rewardDetailDTO) {
        log.debug("Request to save RewardDetail : {}", rewardDetailDTO);
        RewardDetail rewardDetail = rewardDetailMapper.toEntity(rewardDetailDTO);
        rewardDetail = rewardDetailRepository.save(rewardDetail);
        return rewardDetailMapper.toDto(rewardDetail);
    }

    @Override
    public RewardDetailDTO update(RewardDetailDTO rewardDetailDTO) {
        log.debug("Request to update RewardDetail : {}", rewardDetailDTO);
        RewardDetail rewardDetail = rewardDetailMapper.toEntity(rewardDetailDTO);
        rewardDetail = rewardDetailRepository.save(rewardDetail);
        return rewardDetailMapper.toDto(rewardDetail);
    }

    @Override
    public Optional<RewardDetailDTO> partialUpdate(RewardDetailDTO rewardDetailDTO) {
        log.debug("Request to partially update RewardDetail : {}", rewardDetailDTO);

        return rewardDetailRepository
            .findById(rewardDetailDTO.getId())
            .map(existingRewardDetail -> {
                rewardDetailMapper.partialUpdate(existingRewardDetail, rewardDetailDTO);

                return existingRewardDetail;
            })
            .map(rewardDetailRepository::save)
            .map(rewardDetailMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RewardDetailDTO> findAll() {
        log.debug("Request to get all RewardDetails");
        return rewardDetailRepository.findAll().stream().map(rewardDetailMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RewardDetailDTO> findOne(String id) {
        log.debug("Request to get RewardDetail : {}", id);
        return rewardDetailRepository.findById(id).map(rewardDetailMapper::toDto);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete RewardDetail : {}", id);
        rewardDetailRepository.deleteById(id);
    }
}
