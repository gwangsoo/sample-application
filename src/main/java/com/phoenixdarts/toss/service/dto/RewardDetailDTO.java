package com.phoenixdarts.toss.backend.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.phoenixdarts.toss.backend.domain.RewardDetail} entity.
 */
@Schema(description = "리워드")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RewardDetailDTO implements Serializable {

    @NotNull
    @Size(max = 16)
    private String id;

    @Size(max = 256)
    private String name;

    private String tournamentId;

    private String divisionId;

    private RewardDTO reward;

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

    public String getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(String tournamentId) {
        this.tournamentId = tournamentId;
    }

    public String getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(String divisionId) {
        this.divisionId = divisionId;
    }

    public RewardDTO getReward() {
        return reward;
    }

    public void setReward(RewardDTO reward) {
        this.reward = reward;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RewardDetailDTO)) {
            return false;
        }

        RewardDetailDTO rewardDetailDTO = (RewardDetailDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, rewardDetailDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RewardDetailDTO{" +
            "id='" + getId() + "'" +
            ", name='" + getName() + "'" +
            ", tournamentId='" + getTournamentId() + "'" +
            ", divisionId='" + getDivisionId() + "'" +
            ", reward=" + getReward() +
            "}";
    }
}
