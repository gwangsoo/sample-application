package com.phoenixdarts.toss.backend.service.dto;

import com.phoenixdarts.toss.backend.domain.enumeration.PlayerCallModeType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.phoenixdarts.toss.backend.domain.MatchTeam} entity.
 */
@Schema(description = "매치상세정보")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MatchTeamDTO implements Serializable {

    @NotNull
    @Size(max = 16)
    private String id;

    private Boolean isWinner;

    private Float avgPpd;

    private Float avgMpr;

    private Integer winSet;

    private Integer winLeg;

    @NotNull
    private PlayerCallModeType playerCallModeType;

    private TeamDTO team;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getIsWinner() {
        return isWinner;
    }

    public void setIsWinner(Boolean isWinner) {
        this.isWinner = isWinner;
    }

    public Float getAvgPpd() {
        return avgPpd;
    }

    public void setAvgPpd(Float avgPpd) {
        this.avgPpd = avgPpd;
    }

    public Float getAvgMpr() {
        return avgMpr;
    }

    public void setAvgMpr(Float avgMpr) {
        this.avgMpr = avgMpr;
    }

    public Integer getWinSet() {
        return winSet;
    }

    public void setWinSet(Integer winSet) {
        this.winSet = winSet;
    }

    public Integer getWinLeg() {
        return winLeg;
    }

    public void setWinLeg(Integer winLeg) {
        this.winLeg = winLeg;
    }

    public PlayerCallModeType getPlayerCallModeType() {
        return playerCallModeType;
    }

    public void setPlayerCallModeType(PlayerCallModeType playerCallModeType) {
        this.playerCallModeType = playerCallModeType;
    }

    public TeamDTO getTeam() {
        return team;
    }

    public void setTeam(TeamDTO team) {
        this.team = team;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MatchTeamDTO)) {
            return false;
        }

        MatchTeamDTO matchTeamDTO = (MatchTeamDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, matchTeamDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MatchTeamDTO{" +
            "id='" + getId() + "'" +
            ", isWinner='" + getIsWinner() + "'" +
            ", avgPpd=" + getAvgPpd() +
            ", avgMpr=" + getAvgMpr() +
            ", winSet=" + getWinSet() +
            ", winLeg=" + getWinLeg() +
            ", playerCallModeType='" + getPlayerCallModeType() + "'" +
            ", team=" + getTeam() +
            "}";
    }
}
