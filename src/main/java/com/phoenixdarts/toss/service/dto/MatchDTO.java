package com.phoenixdarts.toss.service.dto;

import com.phoenixdarts.toss.domain.enumeration.MatchStatus;
import com.phoenixdarts.toss.domain.enumeration.MatchType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.phoenixdarts.toss.domain.Match} entity.
 */
@Schema(description = "매치리스트")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MatchDTO implements Serializable {

    @NotNull
    @Size(max = 16)
    private String id;

    @NotNull
    @Size(max = 9)
    private String matchNo;

    @NotNull
    private MatchType matchType;

    @Min(value = 1)
    @Max(value = 999)
    private Integer groupNo;

    @Min(value = 1)
    @Max(value = 999)
    private Integer groupMatchSeq;

    private Integer roundNum;

    @NotNull
    private MatchStatus matchStatus;

    @Size(max = 9)
    private String nextMatchNo;

    private MatchTeamDTO home;

    private MatchTeamDTO away;

    private MachineDTO tmatch;

    private DivisionDTO division;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMatchNo() {
        return matchNo;
    }

    public void setMatchNo(String matchNo) {
        this.matchNo = matchNo;
    }

    public MatchType getMatchType() {
        return matchType;
    }

    public void setMatchType(MatchType matchType) {
        this.matchType = matchType;
    }

    public Integer getGroupNo() {
        return groupNo;
    }

    public void setGroupNo(Integer groupNo) {
        this.groupNo = groupNo;
    }

    public Integer getGroupMatchSeq() {
        return groupMatchSeq;
    }

    public void setGroupMatchSeq(Integer groupMatchSeq) {
        this.groupMatchSeq = groupMatchSeq;
    }

    public Integer getRoundNum() {
        return roundNum;
    }

    public void setRoundNum(Integer roundNum) {
        this.roundNum = roundNum;
    }

    public MatchStatus getMatchStatus() {
        return matchStatus;
    }

    public void setMatchStatus(MatchStatus matchStatus) {
        this.matchStatus = matchStatus;
    }

    public String getNextMatchNo() {
        return nextMatchNo;
    }

    public void setNextMatchNo(String nextMatchNo) {
        this.nextMatchNo = nextMatchNo;
    }

    public MatchTeamDTO getHome() {
        return home;
    }

    public void setHome(MatchTeamDTO home) {
        this.home = home;
    }

    public MatchTeamDTO getAway() {
        return away;
    }

    public void setAway(MatchTeamDTO away) {
        this.away = away;
    }

    public MachineDTO getTmatch() {
        return tmatch;
    }

    public void setTmatch(MachineDTO tmatch) {
        this.tmatch = tmatch;
    }

    public DivisionDTO getDivision() {
        return division;
    }

    public void setDivision(DivisionDTO division) {
        this.division = division;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MatchDTO)) {
            return false;
        }

        MatchDTO matchDTO = (MatchDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, matchDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MatchDTO{" +
            "id='" + getId() + "'" +
            ", matchNo='" + getMatchNo() + "'" +
            ", matchType='" + getMatchType() + "'" +
            ", groupNo=" + getGroupNo() +
            ", groupMatchSeq=" + getGroupMatchSeq() +
            ", roundNum=" + getRoundNum() +
            ", matchStatus='" + getMatchStatus() + "'" +
            ", nextMatchNo='" + getNextMatchNo() + "'" +
            ", home=" + getHome() +
            ", away=" + getAway() +
            ", tmatch=" + getTmatch() +
            ", division=" + getDivision() +
            "}";
    }
}
