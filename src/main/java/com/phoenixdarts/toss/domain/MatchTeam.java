package com.phoenixdarts.toss.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.phoenixdarts.toss.domain.enumeration.PlayerCallModeType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 매치상세정보
 */
@Entity
@Table(name = "tb_match_detail")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MatchTeam implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Size(max = 16)
    @Id
    @GeneratedValue
    @Column(name = "id", length = 16, nullable = false, unique = true)
    private String id;

    /**
     * 승패여부
     */
    @Column(name = "is_winner")
    private Boolean isWinner;

    /**
     * AVG. PPD
     */
    @Column(name = "avg_ppd")
    private Float avgPpd;

    /**
     * AVG. MPR
     */
    @Column(name = "avg_mpr")
    private Float avgMpr;

    /**
     * winSet
     */
    @Column(name = "win_set")
    private Integer winSet;

    /**
     * winLeg
     */
    @Column(name = "win_leg")
    private Integer winLeg;

    /**
     * 선수 호출 구분
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "player_call_mode_type", nullable = false)
    private PlayerCallModeType playerCallModeType;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "matchTeam")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "entry", "matchTeam" }, allowSetters = true)
    private Set<MatchAttendance> matchAttendances = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "matchTeam")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "matchTeam" }, allowSetters = true)
    private Set<MatchCall> matchCalls = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = { "entries", "captain", "tteam", "affiliatedInfo", "paymentInfo", "division", "matchTeams" },
        allowSetters = true
    )
    private Team team;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public MatchTeam id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getIsWinner() {
        return this.isWinner;
    }

    public MatchTeam isWinner(Boolean isWinner) {
        this.setIsWinner(isWinner);
        return this;
    }

    public void setIsWinner(Boolean isWinner) {
        this.isWinner = isWinner;
    }

    public Float getAvgPpd() {
        return this.avgPpd;
    }

    public MatchTeam avgPpd(Float avgPpd) {
        this.setAvgPpd(avgPpd);
        return this;
    }

    public void setAvgPpd(Float avgPpd) {
        this.avgPpd = avgPpd;
    }

    public Float getAvgMpr() {
        return this.avgMpr;
    }

    public MatchTeam avgMpr(Float avgMpr) {
        this.setAvgMpr(avgMpr);
        return this;
    }

    public void setAvgMpr(Float avgMpr) {
        this.avgMpr = avgMpr;
    }

    public Integer getWinSet() {
        return this.winSet;
    }

    public MatchTeam winSet(Integer winSet) {
        this.setWinSet(winSet);
        return this;
    }

    public void setWinSet(Integer winSet) {
        this.winSet = winSet;
    }

    public Integer getWinLeg() {
        return this.winLeg;
    }

    public MatchTeam winLeg(Integer winLeg) {
        this.setWinLeg(winLeg);
        return this;
    }

    public void setWinLeg(Integer winLeg) {
        this.winLeg = winLeg;
    }

    public PlayerCallModeType getPlayerCallModeType() {
        return this.playerCallModeType;
    }

    public MatchTeam playerCallModeType(PlayerCallModeType playerCallModeType) {
        this.setPlayerCallModeType(playerCallModeType);
        return this;
    }

    public void setPlayerCallModeType(PlayerCallModeType playerCallModeType) {
        this.playerCallModeType = playerCallModeType;
    }

    public Set<MatchAttendance> getMatchAttendances() {
        return this.matchAttendances;
    }

    public void setMatchAttendances(Set<MatchAttendance> matchAttendances) {
        if (this.matchAttendances != null) {
            this.matchAttendances.forEach(i -> i.setMatchTeam(null));
        }
        if (matchAttendances != null) {
            matchAttendances.forEach(i -> i.setMatchTeam(this));
        }
        this.matchAttendances = matchAttendances;
    }

    public MatchTeam matchAttendances(Set<MatchAttendance> matchAttendances) {
        this.setMatchAttendances(matchAttendances);
        return this;
    }

    public MatchTeam addMatchAttendance(MatchAttendance matchAttendance) {
        this.matchAttendances.add(matchAttendance);
        matchAttendance.setMatchTeam(this);
        return this;
    }

    public MatchTeam removeMatchAttendance(MatchAttendance matchAttendance) {
        this.matchAttendances.remove(matchAttendance);
        matchAttendance.setMatchTeam(null);
        return this;
    }

    public Set<MatchCall> getMatchCalls() {
        return this.matchCalls;
    }

    public void setMatchCalls(Set<MatchCall> matchCalls) {
        if (this.matchCalls != null) {
            this.matchCalls.forEach(i -> i.setMatchTeam(null));
        }
        if (matchCalls != null) {
            matchCalls.forEach(i -> i.setMatchTeam(this));
        }
        this.matchCalls = matchCalls;
    }

    public MatchTeam matchCalls(Set<MatchCall> matchCalls) {
        this.setMatchCalls(matchCalls);
        return this;
    }

    public MatchTeam addMatchCall(MatchCall matchCall) {
        this.matchCalls.add(matchCall);
        matchCall.setMatchTeam(this);
        return this;
    }

    public MatchTeam removeMatchCall(MatchCall matchCall) {
        this.matchCalls.remove(matchCall);
        matchCall.setMatchTeam(null);
        return this;
    }

    public Team getTeam() {
        return this.team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public MatchTeam team(Team team) {
        this.setTeam(team);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MatchTeam)) {
            return false;
        }
        return getId() != null && getId().equals(((MatchTeam) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MatchTeam{" +
            "id=" + getId() +
            ", isWinner='" + getIsWinner() + "'" +
            ", avgPpd=" + getAvgPpd() +
            ", avgMpr=" + getAvgMpr() +
            ", winSet=" + getWinSet() +
            ", winLeg=" + getWinLeg() +
            ", playerCallModeType='" + getPlayerCallModeType() + "'" +
            "}";
    }
}
