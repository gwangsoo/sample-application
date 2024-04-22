package com.phoenixdarts.toss.service.dto;

import com.phoenixdarts.toss.domain.enumeration.AffiliatedType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.phoenixdarts.toss.domain.AffiliatedInfo} entity.
 */
@Schema(description = "소속정보 (Shop,동호회)")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AffiliatedInfoDTO implements Serializable {

    @NotNull
    @Size(max = 16)
    private String id;

    @NotNull
    private AffiliatedType affiliatedType;

    @Size(max = 20)
    private String seq;

    @NotNull
    @Size(max = 256)
    private String name;

    @Size(max = 500)
    private String address;

    @Size(max = 30)
    private String telNo;

    @Size(max = 256)
    private String homepageUrl;

    private FileInfoDTO fileInfo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public AffiliatedType getAffiliatedType() {
        return affiliatedType;
    }

    public void setAffiliatedType(AffiliatedType affiliatedType) {
        this.affiliatedType = affiliatedType;
    }

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelNo() {
        return telNo;
    }

    public void setTelNo(String telNo) {
        this.telNo = telNo;
    }

    public String getHomepageUrl() {
        return homepageUrl;
    }

    public void setHomepageUrl(String homepageUrl) {
        this.homepageUrl = homepageUrl;
    }

    public FileInfoDTO getFileInfo() {
        return fileInfo;
    }

    public void setFileInfo(FileInfoDTO fileInfo) {
        this.fileInfo = fileInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AffiliatedInfoDTO)) {
            return false;
        }

        AffiliatedInfoDTO affiliatedInfoDTO = (AffiliatedInfoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, affiliatedInfoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AffiliatedInfoDTO{" +
            "id='" + getId() + "'" +
            ", affiliatedType='" + getAffiliatedType() + "'" +
            ", seq='" + getSeq() + "'" +
            ", name='" + getName() + "'" +
            ", address='" + getAddress() + "'" +
            ", telNo='" + getTelNo() + "'" +
            ", homepageUrl='" + getHomepageUrl() + "'" +
            ", fileInfo=" + getFileInfo() +
            "}";
    }
}
