package com.phoenixdarts.toss.backend.repository;

import com.phoenixdarts.toss.backend.domain.FileInfo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the FileInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FileInfoRepository extends JpaRepository<FileInfo, String> {}
