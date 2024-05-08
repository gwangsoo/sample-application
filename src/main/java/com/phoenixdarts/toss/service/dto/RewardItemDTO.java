package com.phoenixdarts.toss.backend.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.phoenixdarts.toss.backend.domain.RewardItem} entity.
 */
@Schema(description = "RewardItem")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RewardItemDTO implements Serializable {

    @NotNull
    @Size(max = 16)
    private String id;

    @NotNull
    @Size(max = 50)
    private String name;

    private FileInfoDTO itemImage;

    private RewardDetailDTO rewardDetail;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FileInfoDTO getItemImage() {
        return itemImage;
    }

    public void setItemImage(FileInfoDTO itemImage) {
        this.itemImage = itemImage;
    }

    public RewardDetailDTO getRewardDetail() {
        return rewardDetail;
    }

    public void setRewardDetail(RewardDetailDTO rewardDetail) {
        this.rewardDetail = rewardDetail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RewardItemDTO)) {
            return false;
        }

        RewardItemDTO rewardItemDTO = (RewardItemDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, rewardItemDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RewardItemDTO{" +
            "id='" + getId() + "'" +
            ", name='" + getName() + "'" +
            ", itemImage=" + getItemImage() +
            ", rewardDetail=" + getRewardDetail() +
            "}";
    }
}
