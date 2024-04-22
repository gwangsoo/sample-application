package com.phoenixdarts.toss.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.phoenixdarts.toss.domain.enumeration.MatchStatus;
import com.phoenixdarts.toss.domain.enumeration.MatchType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 매치리스트
 */
@Entity
@Table(name = "tb_match")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Match implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Size(max = 16)
    @Id
    @GeneratedValue
    @Column(name = "id", length = 16, nullable = false, unique = true)
    private String id;

    /**
     * 매치번호 unique(divisionId, matchNo)
     */
    @NotNull
    @Size(max = 9)
    @Column(name = "match_no", length = 9, nullable = false)
    private String matchNo;

    /**
     * 매치구분
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "match_type", nullable = false)
    private MatchType matchType;

    /**
     * 그룹번호(RoundRobin)
     */
    @Min(value = 1)
    @Max(value = 999)
    @Column(name = "group_no")
    private Integer groupNo;

    /**
     * 그룹시합순서(RoundRobin)
     */
    @Min(value = 1)
    @Max(value = 999)
    @Column(name = "group_match_seq")
    private Integer groupMatchSeq;

    /**
     * Elimination 라운드번호
     */
    @Column(name = "round_num")
    private Integer roundNum;

    /**
     * 매치상태
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "match_status", nullable = false)
    private MatchStatus matchStatus;

    /**
     * 다음매치번호
     */
    @Size(max = 9)
    @Column(name = "next_match_no", length = 9)
    private String nextMatchNo;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "match")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "match" }, allowSetters = true)
    private Set<MatchScore> matchScores = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "matchAttendances", "matchCalls", "team" }, allowSetters = true)
    private MatchTeam home;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "matchAttendances", "matchCalls", "team" }, allowSetters = true)
    private MatchTeam away;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "match", "machineArea" }, allowSetters = true)
    private Machine tmatch;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "matchFormats", "eventPoints", "teams", "tournament", "matches" }, allowSetters = true)
    private Division division;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "match")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "match", "machineArea" }, allowSetters = true)
    private Set<Machine> machines = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Match id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMatchNo() {
        return this.matchNo;
    }

    public Match matchNo(String matchNo) {
        this.setMatchNo(matchNo);
        return this;
    }

    public void setMatchNo(String matchNo) {
        this.matchNo = matchNo;
    }

    public MatchType getMatchType() {
        return this.matchType;
    }

    public Match matchType(MatchType matchType) {
        this.setMatchType(matchType);
        return this;
    }

    public void setMatchType(MatchType matchType) {
        this.matchType = matchType;
    }

    public Integer getGroupNo() {
        return this.groupNo;
    }

    public Match groupNo(Integer groupNo) {
        this.setGroupNo(groupNo);
        return this;
    }

    public void setGroupNo(Integer groupNo) {
        this.groupNo = groupNo;
    }

    public Integer getGroupMatchSeq() {
        return this.groupMatchSeq;
    }

    public Match groupMatchSeq(Integer groupMatchSeq) {
        this.setGroupMatchSeq(groupMatchSeq);
        return this;
    }

    public void setGroupMatchSeq(Integer groupMatchSeq) {
        this.groupMatchSeq = groupMatchSeq;
    }

    public Integer getRoundNum() {
        return this.roundNum;
    }

    public Match roundNum(Integer roundNum) {
        this.setRoundNum(roundNum);
        return this;
    }

    public void setRoundNum(Integer roundNum) {
        this.roundNum = roundNum;
    }

    public MatchStatus getMatchStatus() {
        return this.matchStatus;
    }

    public Match matchStatus(MatchStatus matchStatus) {
        this.setMatchStatus(matchStatus);
        return this;
    }

    public void setMatchStatus(MatchStatus matchStatus) {
        this.matchStatus = matchStatus;
    }

    public String getNextMatchNo() {
        return this.nextMatchNo;
    }

    public Match nextMatchNo(String nextMatchNo) {
        this.setNextMatchNo(nextMatchNo);
        return this;
    }

    public void setNextMatchNo(String nextMatchNo) {
        this.nextMatchNo = nextMatchNo;
    }

    public Set<MatchScore> getMatchScores() {
        return this.matchScores;
    }

    public void setMatchScores(Set<MatchScore> matchScores) {
        if (this.matchScores != null) {
            this.matchScores.forEach(i -> i.setMatch(null));
        }
        if (matchScores != null) {
            matchScores.forEach(i -> i.setMatch(this));
        }
        this.matchScores = matchScores;
    }

    public Match matchScores(Set<MatchScore> matchScores) {
        this.setMatchScores(matchScores);
        return this;
    }

    public Match addMatchScore(MatchScore matchScore) {
        this.matchScores.add(matchScore);
        matchScore.setMatch(this);
        return this;
    }

    public Match removeMatchScore(MatchScore matchScore) {
        this.matchScores.remove(matchScore);
        matchScore.setMatch(null);
        return this;
    }

    public MatchTeam getHome() {
        return this.home;
    }

    public void setHome(MatchTeam matchTeam) {
        this.home = matchTeam;
    }

    public Match home(MatchTeam matchTeam) {
        this.setHome(matchTeam);
        return this;
    }

    public MatchTeam getAway() {
        return this.away;
    }

    public void setAway(MatchTeam matchTeam) {
        this.away = matchTeam;
    }

    public Match away(MatchTeam matchTeam) {
        this.setAway(matchTeam);
        return this;
    }

    public Machine getTmatch() {
        return this.tmatch;
    }

    public void setTmatch(Machine machine) {
        this.tmatch = machine;
    }

    public Match tmatch(Machine machine) {
        this.setTmatch(machine);
        return this;
    }

    public Division getDivision() {
        return this.division;
    }

    public void setDivision(Division division) {
        this.division = division;
    }

    public Match division(Division division) {
        this.setDivision(division);
        return this;
    }

    public Set<Machine> getMachines() {
        return this.machines;
    }

    public void setMachines(Set<Machine> machines) {
        if (this.machines != null) {
            this.machines.forEach(i -> i.setMatch(null));
        }
        if (machines != null) {
            machines.forEach(i -> i.setMatch(this));
        }
        this.machines = machines;
    }

    public Match machines(Set<Machine> machines) {
        this.setMachines(machines);
        return this;
    }

    public Match addMachine(Machine machine) {
        this.machines.add(machine);
        machine.setMatch(this);
        return this;
    }

    public Match removeMachine(Machine machine) {
        this.machines.remove(machine);
        machine.setMatch(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Match)) {
            return false;
        }
        return getId() != null && getId().equals(((Match) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Match{" +
            "id=" + getId() +
            ", matchNo='" + getMatchNo() + "'" +
            ", matchType='" + getMatchType() + "'" +
            ", groupNo=" + getGroupNo() +
            ", groupMatchSeq=" + getGroupMatchSeq() +
            ", roundNum=" + getRoundNum() +
            ", matchStatus='" + getMatchStatus() + "'" +
            ", nextMatchNo='" + getNextMatchNo() + "'" +
            "}";
    }
}
