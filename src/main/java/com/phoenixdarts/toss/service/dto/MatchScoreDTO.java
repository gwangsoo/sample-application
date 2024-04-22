package com.phoenixdarts.toss.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.phoenixdarts.toss.domain.MatchScore} entity.
 */
@Schema(description = "매치점수")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MatchScoreDTO implements Serializable {

    @NotNull
    @Size(max = 16)
    private String id;

    private Integer setNo;

    private Integer lgeNo;

    private String legGameName;

    private Float homeLegScore;

    private Float awayLegScore;

    private MatchDTO match;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getSetNo() {
        return setNo;
    }

    public void setSetNo(Integer setNo) {
        this.setNo = setNo;
    }

    public Integer getLgeNo() {
        return lgeNo;
    }

    public void setLgeNo(Integer lgeNo) {
        this.lgeNo = lgeNo;
    }

    public String getLegGameName() {
        return legGameName;
    }

    public void setLegGameName(String legGameName) {
        this.legGameName = legGameName;
    }

    public Float getHomeLegScore() {
        return homeLegScore;
    }

    public void setHomeLegScore(Float homeLegScore) {
        this.homeLegScore = homeLegScore;
    }

    public Float getAwayLegScore() {
        return awayLegScore;
    }

    public void setAwayLegScore(Float awayLegScore) {
        this.awayLegScore = awayLegScore;
    }

    public MatchDTO getMatch() {
        return match;
    }

    public void setMatch(MatchDTO match) {
        this.match = match;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MatchScoreDTO)) {
            return false;
        }

        MatchScoreDTO matchScoreDTO = (MatchScoreDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, matchScoreDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MatchScoreDTO{" +
            "id='" + getId() + "'" +
            ", setNo=" + getSetNo() +
            ", lgeNo=" + getLgeNo() +
            ", legGameName='" + getLegGameName() + "'" +
            ", homeLegScore=" + getHomeLegScore() +
            ", awayLegScore=" + getAwayLegScore() +
            ", match=" + getMatch() +
            "}";
    }
}
