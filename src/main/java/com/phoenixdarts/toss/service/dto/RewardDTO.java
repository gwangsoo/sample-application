package com.phoenixdarts.toss.backend.service.dto;

import com.phoenixdarts.toss.backend.domain.enumeration.MachineKindType;
import com.phoenixdarts.toss.backend.domain.enumeration.RewardMethodSubType;
import com.phoenixdarts.toss.backend.domain.enumeration.RewardMethodType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.phoenixdarts.toss.backend.domain.Reward} entity.
 */
@Schema(description = "리워드")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RewardDTO implements Serializable {

    @NotNull
    @Size(max = 16)
    private String id;

    @NotNull
    private RewardMethodType rewardMethodType;

    @NotNull
    private RewardMethodSubType rewardMethodSubType;

    @NotNull
    private Integer rewardCategoryNum;

    private MachineKindType machineKindType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public RewardMethodType getRewardMethodType() {
        return rewardMethodType;
    }

    public void setRewardMethodType(RewardMethodType rewardMethodType) {
        this.rewardMethodType = rewardMethodType;
    }

    public RewardMethodSubType getRewardMethodSubType() {
        return rewardMethodSubType;
    }

    public void setRewardMethodSubType(RewardMethodSubType rewardMethodSubType) {
        this.rewardMethodSubType = rewardMethodSubType;
    }

    public Integer getRewardCategoryNum() {
        return rewardCategoryNum;
    }

    public void setRewardCategoryNum(Integer rewardCategoryNum) {
        this.rewardCategoryNum = rewardCategoryNum;
    }

    public MachineKindType getMachineKindType() {
        return machineKindType;
    }

    public void setMachineKindType(MachineKindType machineKindType) {
        this.machineKindType = machineKindType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RewardDTO)) {
            return false;
        }

        RewardDTO rewardDTO = (RewardDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, rewardDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RewardDTO{" +
            "id='" + getId() + "'" +
            ", rewardMethodType='" + getRewardMethodType() + "'" +
            ", rewardMethodSubType='" + getRewardMethodSubType() + "'" +
            ", rewardCategoryNum=" + getRewardCategoryNum() +
            ", machineKindType='" + getMachineKindType() + "'" +
            "}";
    }
}
