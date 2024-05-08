package com.phoenixdarts.toss.backend.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.phoenixdarts.toss.backend.domain.FileInfo} entity.
 */
@Schema(description = "파일정보")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FileInfoDTO implements Serializable {

    @NotNull
    @Size(max = 16)
    private String id;

    @NotNull
    @Size(max = 256)
    private String originalName;

    @NotNull
    @Size(max = 128)
    private String mimeType;

    private Long fileSize;

    @NotNull
    @Size(max = 256)
    private String savedPath;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public String getSavedPath() {
        return savedPath;
    }

    public void setSavedPath(String savedPath) {
        this.savedPath = savedPath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FileInfoDTO)) {
            return false;
        }

        FileInfoDTO fileInfoDTO = (FileInfoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, fileInfoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FileInfoDTO{" +
            "id='" + getId() + "'" +
            ", originalName='" + getOriginalName() + "'" +
            ", mimeType='" + getMimeType() + "'" +
            ", fileSize=" + getFileSize() +
            ", savedPath='" + getSavedPath() + "'" +
            "}";
    }
}
