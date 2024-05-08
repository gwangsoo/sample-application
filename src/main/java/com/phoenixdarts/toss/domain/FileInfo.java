package com.phoenixdarts.toss.backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 파일정보
 */
@Entity
@Table(name = "tb_fileinfo")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FileInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Size(max = 16)
    @Id
    @GeneratedValue
    @Column(name = "id", length = 16, nullable = false, unique = true)
    private String id;

    /**
     * 원래파일명
     */
    @NotNull
    @Size(max = 256)
    @Column(name = "original_name", length = 256, nullable = false)
    private String originalName;

    /**
     * MIME
     */
    @NotNull
    @Size(max = 128)
    @Column(name = "mime_type", length = 128, nullable = false)
    private String mimeType;

    /**
     * 파일사이즈
     */
    @Column(name = "file_size")
    private Long fileSize;

    /**
     * 파일저장경로
     */
    @NotNull
    @Size(max = 256)
    @Column(name = "saved_path", length = 256, nullable = false)
    private String savedPath;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "fileInfo")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "fileInfo", "teams" }, allowSetters = true)
    private Set<AffiliatedInfo> affiliatedInfos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public FileInfo id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOriginalName() {
        return this.originalName;
    }

    public FileInfo originalName(String originalName) {
        this.setOriginalName(originalName);
        return this;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getMimeType() {
        return this.mimeType;
    }

    public FileInfo mimeType(String mimeType) {
        this.setMimeType(mimeType);
        return this;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public Long getFileSize() {
        return this.fileSize;
    }

    public FileInfo fileSize(Long fileSize) {
        this.setFileSize(fileSize);
        return this;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public String getSavedPath() {
        return this.savedPath;
    }

    public FileInfo savedPath(String savedPath) {
        this.setSavedPath(savedPath);
        return this;
    }

    public void setSavedPath(String savedPath) {
        this.savedPath = savedPath;
    }

    public Set<AffiliatedInfo> getAffiliatedInfos() {
        return this.affiliatedInfos;
    }

    public void setAffiliatedInfos(Set<AffiliatedInfo> affiliatedInfos) {
        if (this.affiliatedInfos != null) {
            this.affiliatedInfos.forEach(i -> i.setFileInfo(null));
        }
        if (affiliatedInfos != null) {
            affiliatedInfos.forEach(i -> i.setFileInfo(this));
        }
        this.affiliatedInfos = affiliatedInfos;
    }

    public FileInfo affiliatedInfos(Set<AffiliatedInfo> affiliatedInfos) {
        this.setAffiliatedInfos(affiliatedInfos);
        return this;
    }

    public FileInfo addAffiliatedInfo(AffiliatedInfo affiliatedInfo) {
        this.affiliatedInfos.add(affiliatedInfo);
        affiliatedInfo.setFileInfo(this);
        return this;
    }

    public FileInfo removeAffiliatedInfo(AffiliatedInfo affiliatedInfo) {
        this.affiliatedInfos.remove(affiliatedInfo);
        affiliatedInfo.setFileInfo(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FileInfo)) {
            return false;
        }
        return getId() != null && getId().equals(((FileInfo) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FileInfo{" +
            "id=" + getId() +
            ", originalName='" + getOriginalName() + "'" +
            ", mimeType='" + getMimeType() + "'" +
            ", fileSize=" + getFileSize() +
            ", savedPath='" + getSavedPath() + "'" +
            "}";
    }
}
