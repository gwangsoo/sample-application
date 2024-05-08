package com.phoenixdarts.toss.backend.service.impl;

import com.phoenixdarts.toss.backend.domain.Game;
import com.phoenixdarts.toss.backend.repository.GameRepository;
import com.phoenixdarts.toss.backend.service.GameService;
import com.phoenixdarts.toss.backend.service.dto.GameDTO;
import com.phoenixdarts.toss.backend.service.mapper.GameMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.phoenixdarts.toss.backend.domain.Game}.
 */
@Service
@Transactional
public class GameServiceImpl implements GameService {

    private final Logger log = LoggerFactory.getLogger(GameServiceImpl.class);

    private final GameRepository gameRepository;

    private final GameMapper gameMapper;

    public GameServiceImpl(GameRepository gameRepository, GameMapper gameMapper) {
        this.gameRepository = gameRepository;
        this.gameMapper = gameMapper;
    }

    @Override
    public GameDTO save(GameDTO gameDTO) {
        log.debug("Request to save Game : {}", gameDTO);
        Game game = gameMapper.toEntity(gameDTO);
        game = gameRepository.save(game);
        return gameMapper.toDto(game);
    }

    @Override
    public GameDTO update(GameDTO gameDTO) {
        log.debug("Request to update Game : {}", gameDTO);
        Game game = gameMapper.toEntity(gameDTO);
        game = gameRepository.save(game);
        return gameMapper.toDto(game);
    }

    @Override
    public Optional<GameDTO> partialUpdate(GameDTO gameDTO) {
        log.debug("Request to partially update Game : {}", gameDTO);

        return gameRepository
            .findById(gameDTO.getId())
            .map(existingGame -> {
                gameMapper.partialUpdate(existingGame, gameDTO);

                return existingGame;
            })
            .map(gameRepository::save)
            .map(gameMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<GameDTO> findAll() {
        log.debug("Request to get all Games");
        return gameRepository.findAll().stream().map(gameMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<GameDTO> findOne(String id) {
        log.debug("Request to get Game : {}", id);
        return gameRepository.findById(id).map(gameMapper::toDto);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Game : {}", id);
        gameRepository.deleteById(id);
    }
}
