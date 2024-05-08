package com.phoenixdarts.toss.backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.phoenixdarts.toss.backend.domain.enumeration.MatchFormatBullOptionType;
import com.phoenixdarts.toss.backend.domain.enumeration.MatchFormatFreezeOptionType;
import com.phoenixdarts.toss.backend.domain.enumeration.MatchFormatInOptionType;
import com.phoenixdarts.toss.backend.domain.enumeration.MatchFormatOutOptionType;
import com.phoenixdarts.toss.backend.domain.enumeration.MatchFormatTeamFinishOptionType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 매치포맷 옵션
 */
@Entity
@Table(name = "tb_matchformat_option")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MatchFormatOption implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Size(max = 16)
    @Id
    @GeneratedValue
    @Column(name = "id", length = 16, nullable = false, unique = true)
    private String id;

    /**
     * 01게임 In Option
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "game_01_in_option_type")
    private MatchFormatInOptionType game01InOptionType;

    /**
     * 01게임 Out Option
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "game_01_out_option_type")
    private MatchFormatOutOptionType game01OutOptionType;

    /**
     * 01게임 Bull Option
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "game_01_bull_option_type")
    private MatchFormatBullOptionType game01BullOptionType;

    /**
     * 01게임 arrange
     */
    @Column(name = "game_01_arrange")
    private Boolean game01Arrange;

    /**
     * Cricket Over Kill
     */
    @Column(name = "cricket_over_kill")
    private Boolean cricketOverKill;

    /**
     * Cricket Score
     */
    @Column(name = "cricket_score")
    private Integer cricketScore;

    /**
     * Team 01게임 Freeze Option
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "team_game_01_freeze_view")
    private MatchFormatFreezeOptionType teamGame01FreezeView;

    /**
     * Team 01게임 Point
     */
    @Column(name = "team_game_01_point")
    private Boolean teamGame01Point;

    /**
     * Team Cricket Mark
     */
    @Column(name = "team_cricket_mark")
    private Boolean teamCricketMark;

    /**
     * Team Cricket Finish
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "team_cricket_finish")
    private MatchFormatTeamFinishOptionType teamCricketFinish;

    /**
     * Team Cricket Point
     */
    @Column(name = "team_cricket_point")
    private Boolean teamCricketPoint;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "matchFormatGames", "matchFormatOptions", "matchFormatSets", "division" }, allowSetters = true)
    private MatchFormat matchFormat;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public MatchFormatOption id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public MatchFormatInOptionType getGame01InOptionType() {
        return this.game01InOptionType;
    }

    public MatchFormatOption game01InOptionType(MatchFormatInOptionType game01InOptionType) {
        this.setGame01InOptionType(game01InOptionType);
        return this;
    }

    public void setGame01InOptionType(MatchFormatInOptionType game01InOptionType) {
        this.game01InOptionType = game01InOptionType;
    }

    public MatchFormatOutOptionType getGame01OutOptionType() {
        return this.game01OutOptionType;
    }

    public MatchFormatOption game01OutOptionType(MatchFormatOutOptionType game01OutOptionType) {
        this.setGame01OutOptionType(game01OutOptionType);
        return this;
    }

    public void setGame01OutOptionType(MatchFormatOutOptionType game01OutOptionType) {
        this.game01OutOptionType = game01OutOptionType;
    }

    public MatchFormatBullOptionType getGame01BullOptionType() {
        return this.game01BullOptionType;
    }

    public MatchFormatOption game01BullOptionType(MatchFormatBullOptionType game01BullOptionType) {
        this.setGame01BullOptionType(game01BullOptionType);
        return this;
    }

    public void setGame01BullOptionType(MatchFormatBullOptionType game01BullOptionType) {
        this.game01BullOptionType = game01BullOptionType;
    }

    public Boolean getGame01Arrange() {
        return this.game01Arrange;
    }

    public MatchFormatOption game01Arrange(Boolean game01Arrange) {
        this.setGame01Arrange(game01Arrange);
        return this;
    }

    public void setGame01Arrange(Boolean game01Arrange) {
        this.game01Arrange = game01Arrange;
    }

    public Boolean getCricketOverKill() {
        return this.cricketOverKill;
    }

    public MatchFormatOption cricketOverKill(Boolean cricketOverKill) {
        this.setCricketOverKill(cricketOverKill);
        return this;
    }

    public void setCricketOverKill(Boolean cricketOverKill) {
        this.cricketOverKill = cricketOverKill;
    }

    public Integer getCricketScore() {
        return this.cricketScore;
    }

    public MatchFormatOption cricketScore(Integer cricketScore) {
        this.setCricketScore(cricketScore);
        return this;
    }

    public void setCricketScore(Integer cricketScore) {
        this.cricketScore = cricketScore;
    }

    public MatchFormatFreezeOptionType getTeamGame01FreezeView() {
        return this.teamGame01FreezeView;
    }

    public MatchFormatOption teamGame01FreezeView(MatchFormatFreezeOptionType teamGame01FreezeView) {
        this.setTeamGame01FreezeView(teamGame01FreezeView);
        return this;
    }

    public void setTeamGame01FreezeView(MatchFormatFreezeOptionType teamGame01FreezeView) {
        this.teamGame01FreezeView = teamGame01FreezeView;
    }

    public Boolean getTeamGame01Point() {
        return this.teamGame01Point;
    }

    public MatchFormatOption teamGame01Point(Boolean teamGame01Point) {
        this.setTeamGame01Point(teamGame01Point);
        return this;
    }

    public void setTeamGame01Point(Boolean teamGame01Point) {
        this.teamGame01Point = teamGame01Point;
    }

    public Boolean getTeamCricketMark() {
        return this.teamCricketMark;
    }

    public MatchFormatOption teamCricketMark(Boolean teamCricketMark) {
        this.setTeamCricketMark(teamCricketMark);
        return this;
    }

    public void setTeamCricketMark(Boolean teamCricketMark) {
        this.teamCricketMark = teamCricketMark;
    }

    public MatchFormatTeamFinishOptionType getTeamCricketFinish() {
        return this.teamCricketFinish;
    }

    public MatchFormatOption teamCricketFinish(MatchFormatTeamFinishOptionType teamCricketFinish) {
        this.setTeamCricketFinish(teamCricketFinish);
        return this;
    }

    public void setTeamCricketFinish(MatchFormatTeamFinishOptionType teamCricketFinish) {
        this.teamCricketFinish = teamCricketFinish;
    }

    public Boolean getTeamCricketPoint() {
        return this.teamCricketPoint;
    }

    public MatchFormatOption teamCricketPoint(Boolean teamCricketPoint) {
        this.setTeamCricketPoint(teamCricketPoint);
        return this;
    }

    public void setTeamCricketPoint(Boolean teamCricketPoint) {
        this.teamCricketPoint = teamCricketPoint;
    }

    public MatchFormat getMatchFormat() {
        return this.matchFormat;
    }

    public void setMatchFormat(MatchFormat matchFormat) {
        this.matchFormat = matchFormat;
    }

    public MatchFormatOption matchFormat(MatchFormat matchFormat) {
        this.setMatchFormat(matchFormat);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MatchFormatOption)) {
            return false;
        }
        return getId() != null && getId().equals(((MatchFormatOption) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MatchFormatOption{" +
            "id=" + getId() +
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
            "}";
    }
}
