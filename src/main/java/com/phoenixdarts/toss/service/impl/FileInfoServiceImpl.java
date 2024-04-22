package com.phoenixdarts.toss.service.impl;

import com.phoenixdarts.toss.domain.FileInfo;
import com.phoenixdarts.toss.repository.FileInfoRepository;
import com.phoenixdarts.toss.service.FileInfoService;
import com.phoenixdarts.toss.service.dto.FileInfoDTO;
import com.phoenixdarts.toss.service.mapper.FileInfoMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.phoenixdarts.toss.domain.FileInfo}.
 */
@Service
@Transactional
public class FileInfoServiceImpl implements FileInfoService {

    private final Logger log = LoggerFactory.getLogger(FileInfoServiceImpl.class);

    private final FileInfoRepository fileInfoRepository;

    private final FileInfoMapper fileInfoMapper;

    public FileInfoServiceImpl(FileInfoRepository fileInfoRepository, FileInfoMapper fileInfoMapper) {
        this.fileInfoRepository = fileInfoRepository;
        this.fileInfoMapper = fileInfoMapper;
    }

    @Override
    public FileInfoDTO save(FileInfoDTO fileInfoDTO) {
        log.debug("Request to save FileInfo : {}", fileInfoDTO);
        FileInfo fileInfo = fileInfoMapper.toEntity(fileInfoDTO);
        fileInfo = fileInfoRepository.save(fileInfo);
        return fileInfoMapper.toDto(fileInfo);
    }

    @Override
    public FileInfoDTO update(FileInfoDTO fileInfoDTO) {
        log.debug("Request to update FileInfo : {}", fileInfoDTO);
        FileInfo fileInfo = fileInfoMapper.toEntity(fileInfoDTO);
        fileInfo = fileInfoRepository.save(fileInfo);
        return fileInfoMapper.toDto(fileInfo);
    }

    @Override
    public Optional<FileInfoDTO> partialUpdate(FileInfoDTO fileInfoDTO) {
        log.debug("Request to partially update FileInfo : {}", fileInfoDTO);

        return fileInfoRepository
            .findById(fileInfoDTO.getId())
            .map(existingFileInfo -> {
                fileInfoMapper.partialUpdate(existingFileInfo, fileInfoDTO);

                return existingFileInfo;
            })
            .map(fileInfoRepository::save)
            .map(fileInfoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FileInfoDTO> findAll() {
        log.debug("Request to get all FileInfos");
        return fileInfoRepository.findAll().stream().map(fileInfoMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FileInfoDTO> findOne(String id) {
        log.debug("Request to get FileInfo : {}", id);
        return fileInfoRepository.findById(id).map(fileInfoMapper::toDto);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete FileInfo : {}", id);
        fileInfoRepository.deleteById(id);
    }
}
