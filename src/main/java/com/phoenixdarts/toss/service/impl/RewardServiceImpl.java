package com.phoenixdarts.toss.service.impl;

import com.phoenixdarts.toss.domain.Reward;
import com.phoenixdarts.toss.repository.RewardRepository;
import com.phoenixdarts.toss.service.RewardService;
import com.phoenixdarts.toss.service.dto.RewardDTO;
import com.phoenixdarts.toss.service.mapper.RewardMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.phoenixdarts.toss.domain.Reward}.
 */
@Service
@Transactional
public class RewardServiceImpl implements RewardService {

    private final Logger log = LoggerFactory.getLogger(RewardServiceImpl.class);

    private final RewardRepository rewardRepository;

    private final RewardMapper rewardMapper;

    public RewardServiceImpl(RewardRepository rewardRepository, RewardMapper rewardMapper) {
        this.rewardRepository = rewardRepository;
        this.rewardMapper = rewardMapper;
    }

    @Override
    public RewardDTO save(RewardDTO rewardDTO) {
        log.debug("Request to save Reward : {}", rewardDTO);
        Reward reward = rewardMapper.toEntity(rewardDTO);
        reward = rewardRepository.save(reward);
        return rewardMapper.toDto(reward);
    }

    @Override
    public RewardDTO update(RewardDTO rewardDTO) {
        log.debug("Request to update Reward : {}", rewardDTO);
        Reward reward = rewardMapper.toEntity(rewardDTO);
        reward = rewardRepository.save(reward);
        return rewardMapper.toDto(reward);
    }

    @Override
    public Optional<RewardDTO> partialUpdate(RewardDTO rewardDTO) {
        log.debug("Request to partially update Reward : {}", rewardDTO);

        return rewardRepository
            .findById(rewardDTO.getId())
            .map(existingReward -> {
                rewardMapper.partialUpdate(existingReward, rewardDTO);

                return existingReward;
            })
            .map(rewardRepository::save)
            .map(rewardMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RewardDTO> findAll() {
        log.debug("Request to get all Rewards");
        return rewardRepository.findAll().stream().map(rewardMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RewardDTO> findOne(String id) {
        log.debug("Request to get Reward : {}", id);
        return rewardRepository.findById(id).map(rewardMapper::toDto);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Reward : {}", id);
        rewardRepository.deleteById(id);
    }
}
