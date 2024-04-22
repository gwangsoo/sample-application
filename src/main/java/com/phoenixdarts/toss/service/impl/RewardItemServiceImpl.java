package com.phoenixdarts.toss.service.impl;

import com.phoenixdarts.toss.domain.RewardItem;
import com.phoenixdarts.toss.repository.RewardItemRepository;
import com.phoenixdarts.toss.service.RewardItemService;
import com.phoenixdarts.toss.service.dto.RewardItemDTO;
import com.phoenixdarts.toss.service.mapper.RewardItemMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.phoenixdarts.toss.domain.RewardItem}.
 */
@Service
@Transactional
public class RewardItemServiceImpl implements RewardItemService {

    private final Logger log = LoggerFactory.getLogger(RewardItemServiceImpl.class);

    private final RewardItemRepository rewardItemRepository;

    private final RewardItemMapper rewardItemMapper;

    public RewardItemServiceImpl(RewardItemRepository rewardItemRepository, RewardItemMapper rewardItemMapper) {
        this.rewardItemRepository = rewardItemRepository;
        this.rewardItemMapper = rewardItemMapper;
    }

    @Override
    public RewardItemDTO save(RewardItemDTO rewardItemDTO) {
        log.debug("Request to save RewardItem : {}", rewardItemDTO);
        RewardItem rewardItem = rewardItemMapper.toEntity(rewardItemDTO);
        rewardItem = rewardItemRepository.save(rewardItem);
        return rewardItemMapper.toDto(rewardItem);
    }

    @Override
    public RewardItemDTO update(RewardItemDTO rewardItemDTO) {
        log.debug("Request to update RewardItem : {}", rewardItemDTO);
        RewardItem rewardItem = rewardItemMapper.toEntity(rewardItemDTO);
        rewardItem = rewardItemRepository.save(rewardItem);
        return rewardItemMapper.toDto(rewardItem);
    }

    @Override
    public Optional<RewardItemDTO> partialUpdate(RewardItemDTO rewardItemDTO) {
        log.debug("Request to partially update RewardItem : {}", rewardItemDTO);

        return rewardItemRepository
            .findById(rewardItemDTO.getId())
            .map(existingRewardItem -> {
                rewardItemMapper.partialUpdate(existingRewardItem, rewardItemDTO);

                return existingRewardItem;
            })
            .map(rewardItemRepository::save)
            .map(rewardItemMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RewardItemDTO> findAll() {
        log.debug("Request to get all RewardItems");
        return rewardItemRepository.findAll().stream().map(rewardItemMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RewardItemDTO> findOne(String id) {
        log.debug("Request to get RewardItem : {}", id);
        return rewardItemRepository.findById(id).map(rewardItemMapper::toDto);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete RewardItem : {}", id);
        rewardItemRepository.deleteById(id);
    }
}
