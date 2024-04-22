package com.phoenixdarts.toss.service.dto;

import com.phoenixdarts.toss.domain.enumeration.MatchFormatBullOptionType;
import com.phoenixdarts.toss.domain.enumeration.MatchFormatFreezeOptionType;
import com.phoenixdarts.toss.domain.enumeration.MatchFormatInOptionType;
import com.phoenixdarts.toss.domain.enumeration.MatchFormatOutOptionType;
import com.phoenixdarts.toss.domain.enumeration.MatchFormatTeamFinishOptionType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.phoenixdarts.toss.domain.MatchFormatOption} entity.
 */
@Schema(description = "매치포맷 옵션")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MatchFormatOptionDTO implements Serializable {

    @NotNull
    @Size(max = 16)
    private String id;

    private MatchFormatInOptionType game01InOptionType;

    private MatchFormatOutOptionType game01OutOptionType;

    private MatchFormatBullOptionType game01BullOptionType;

    private Boolean game01Arrange;

    private Boolean cricketOverKill;

    private Integer cricketScore;

    private MatchFormatFreezeOptionType teamGame01FreezeView;

    private Boolean teamGame01Point;

    private Boolean teamCricketMark;

    private MatchFormatTeamFinishOptionType teamCricketFinish;

    private Boolean teamCricketPoint;

    private MatchFormatDTO matchFormat;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public MatchFormatInOptionType getGame01InOptionType() {
        return game01InOptionType;
    }

    public void setGame01InOptionType(MatchFormatInOptionType game01InOptionType) {
        this.game01InOptionType = game01InOptionType;
    }

    public MatchFormatOutOptionType getGame01OutOptionType() {
        return game01OutOptionType;
    }

    public void setGame01OutOptionType(MatchFormatOutOptionType game01OutOptionType) {
        this.game01OutOptionType = game01OutOptionType;
    }

    public MatchFormatBullOptionType getGame01BullOptionType() {
        return game01BullOptionType;
    }

    public void setGame01BullOptionType(MatchFormatBullOptionType game01BullOptionType) {
        this.game01BullOptionType = game01BullOptionType;
    }

    public Boolean getGame01Arrange() {
        return game01Arrange;
    }

    public void setGame01Arrange(Boolean game01Arrange) {
        this.game01Arrange = game01Arrange;
    }

    public Boolean getCricketOverKill() {
        return cricketOverKill;
    }

    public void setCricketOverKill(Boolean cricketOverKill) {
        this.cricketOverKill = cricketOverKill;
    }

    public Integer getCricketScore() {
        return cricketScore;
    }

    public void setCricketScore(Integer cricketScore) {
        this.cricketScore = cricketScore;
    }

    public MatchFormatFreezeOptionType getTeamGame01FreezeView() {
        return teamGame01FreezeView;
    }

    public void setTeamGame01FreezeView(MatchFormatFreezeOptionType teamGame01FreezeView) {
        this.teamGame01FreezeView = teamGame01FreezeView;
    }

    public Boolean getTeamGame01Point() {
        return teamGame01Point;
    }

    public void setTeamGame01Point(Boolean teamGame01Point) {
        this.teamGame01Point = teamGame01Point;
    }

    public Boolean getTeamCricketMark() {
        return teamCricketMark;
    }

    public void setTeamCricketMark(Boolean teamCricketMark) {
        this.teamCricketMark = teamCricketMark;
    }

    public MatchFormatTeamFinishOptionType getTeamCricketFinish() {
        return teamCricketFinish;
    }

    public void setTeamCricketFinish(MatchFormatTeamFinishOptionType teamCricketFinish) {
        this.teamCricketFinish = teamCricketFinish;
    }

    public Boolean getTeamCricketPoint() {
        return teamCricketPoint;
    }

    public void setTeamCricketPoint(Boolean teamCricketPoint) {
        this.teamCricketPoint = teamCricketPoint;
    }

    public MatchFormatDTO getMatchFormat() {
        return matchFormat;
    }

    public void setMatchFormat(MatchFormatDTO matchFormat) {
        this.matchFormat = matchFormat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MatchFormatOptionDTO)) {
            return false;
        }

        MatchFormatOptionDTO matchFormatOptionDTO = (MatchFormatOptionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, matchFormatOptionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MatchFormatOptionDTO{" +
            "id='" + getId() + "'" +
            ", game01InOptionType='" + getGame01InOptionType() + "'" +
            ", game01OutOptionType='" + getGame01OutOptionType() + "'" +
            ", game01BullOptionType='" + getGame01BullOptionType() + "'" +
            ", game01Arrange='" + getGame01Arrange() + "'" +
            ", cricketOverKill='" + getCricketOverKill() + "'" +
            ", cricketScore=" + getCricketScore() +
            ", teamGame01FreezeView='" + getTeamGame01FreezeView() + "'" +
            ", teamGame01Point='" + getTeamGame01Point() + "'" +
            ", teamCricketMark='" + getTeamCricketMark() + "'" +
            ", teamCricketFinish='" + getTeamCricketFinish() + "'" +
            ", teamCricketPoint='" + getTeamCricketPoint() + "'" +
            ", matchFormat=" + getMatchFormat() +
            "}";
    }
}
