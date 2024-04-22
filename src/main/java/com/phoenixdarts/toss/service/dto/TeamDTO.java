package com.phoenixdarts.toss.service.dto;

import com.phoenixdarts.toss.domain.enumeration.EntryApprovalStatusType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link com.phoenixdarts.toss.domain.Team} entity.
 */
@Schema(description = "Team")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TeamDTO implements Serializable {

    @NotNull
    @Size(max = 16)
    private String id;

    @Size(max = 8)
    private String teamNo;

    @NotNull
    private EntryApprovalStatusType approvalStatus;

    private ZonedDateTime entryDate;

    @Size(max = 256)
    private String memo;

    @Min(value = 1)
    @Max(value = 999)
    private Integer groupNo;

    @Min(value = 1)
    @Max(value = 9)
    private Integer groupSeq;

    @Min(value = 1)
    @Max(value = 999)
    private Integer seedNo;

    private EntryDTO captain;

    private DivisionDTO tteam;

    private AffiliatedInfoDTO affiliatedInfo;

    private PaymentInfoDTO paymentInfo;

    private DivisionDTO division;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTeamNo() {
        return teamNo;
    }

    public void setTeamNo(String teamNo) {
        this.teamNo = teamNo;
    }

    public EntryApprovalStatusType getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(EntryApprovalStatusType approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public ZonedDateTime getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(ZonedDateTime entryDate) {
        this.entryDate = entryDate;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Integer getGroupNo() {
        return groupNo;
    }

    public void setGroupNo(Integer groupNo) {
        this.groupNo = groupNo;
    }

    public Integer getGroupSeq() {
        return groupSeq;
    }

    public void setGroupSeq(Integer groupSeq) {
        this.groupSeq = groupSeq;
    }

    public Integer getSeedNo() {
        return seedNo;
    }

    public void setSeedNo(Integer seedNo) {
        this.seedNo = seedNo;
    }

    public EntryDTO getCaptain() {
        return captain;
    }

    public void setCaptain(EntryDTO captain) {
        this.captain = captain;
    }

    public DivisionDTO getTteam() {
        return tteam;
    }

    public void setTteam(DivisionDTO tteam) {
        this.tteam = tteam;
    }

    public AffiliatedInfoDTO getAffiliatedInfo() {
        return affiliatedInfo;
    }

    public void setAffiliatedInfo(AffiliatedInfoDTO affiliatedInfo) {
        this.affiliatedInfo = affiliatedInfo;
    }

    public PaymentInfoDTO getPaymentInfo() {
        return paymentInfo;
    }

    public void setPaymentInfo(PaymentInfoDTO paymentInfo) {
        this.paymentInfo = paymentInfo;
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
        if (!(o instanceof TeamDTO)) {
            return false;
        }

        TeamDTO teamDTO = (TeamDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, teamDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TeamDTO{" +
            "id='" + getId() + "'" +
            ", teamNo='" + getTeamNo() + "'" +
            ", approvalStatus='" + getApprovalStatus() + "'" +
            ", entryDate='" + getEntryDate() + "'" +
            ", memo='" + getMemo() + "'" +
            ", groupNo=" + getGroupNo() +
            ", groupSeq=" + getGroupSeq() +
            ", seedNo=" + getSeedNo() +
            ", captain=" + getCaptain() +
            ", tteam=" + getTteam() +
            ", affiliatedInfo=" + getAffiliatedInfo() +
            ", paymentInfo=" + getPaymentInfo() +
            ", division=" + getDivision() +
            "}";
    }
}
