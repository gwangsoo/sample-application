package com.phoenixdarts.toss.service.dto;

import com.phoenixdarts.toss.domain.enumeration.CompetitionStatus;
import com.phoenixdarts.toss.domain.enumeration.EntryApplyType;
import com.phoenixdarts.toss.domain.enumeration.EntryRatingType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.phoenixdarts.toss.domain.Competition} entity.
 */
@Schema(description = "대회")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CompetitionDTO implements Serializable {

    @NotNull
    @Size(max = 16)
    private String id;

    @NotNull
    @Size(max = 256)
    private String name;

    @NotNull
    private Instant startDateTime;

    @NotNull
    private Instant endDateTime;

    @NotNull
    private Instant entryStartDateTime;

    @NotNull
    private Instant entryEndDateTime;

    @NotNull
    private CompetitionStatus status;

    @NotNull
    private Boolean approval;

    @NotNull
    private EntryApplyType entryApplyType;

    @NotNull
    private EntryRatingType entryRatingType;

    private FileInfoDTO competitionImage;

    private RewardDTO reward;

    private CountryDTO country;

    private OperatorDTO operator;

    private EntryFeeDTO entryFee;

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

    public Instant getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(Instant startDateTime) {
        this.startDateTime = startDateTime;
    }

    public Instant getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(Instant endDateTime) {
        this.endDateTime = endDateTime;
    }

    public Instant getEntryStartDateTime() {
        return entryStartDateTime;
    }

    public void setEntryStartDateTime(Instant entryStartDateTime) {
        this.entryStartDateTime = entryStartDateTime;
    }

    public Instant getEntryEndDateTime() {
        return entryEndDateTime;
    }

    public void setEntryEndDateTime(Instant entryEndDateTime) {
        this.entryEndDateTime = entryEndDateTime;
    }

    public CompetitionStatus getStatus() {
        return status;
    }

    public void setStatus(CompetitionStatus status) {
        this.status = status;
    }

    public Boolean getApproval() {
        return approval;
    }

    public void setApproval(Boolean approval) {
        this.approval = approval;
    }

    public EntryApplyType getEntryApplyType() {
        return entryApplyType;
    }

    public void setEntryApplyType(EntryApplyType entryApplyType) {
        this.entryApplyType = entryApplyType;
    }

    public EntryRatingType getEntryRatingType() {
        return entryRatingType;
    }

    public void setEntryRatingType(EntryRatingType entryRatingType) {
        this.entryRatingType = entryRatingType;
    }

    public FileInfoDTO getCompetitionImage() {
        return competitionImage;
    }

    public void setCompetitionImage(FileInfoDTO competitionImage) {
        this.competitionImage = competitionImage;
    }

    public RewardDTO getReward() {
        return reward;
    }

    public void setReward(RewardDTO reward) {
        this.reward = reward;
    }

    public CountryDTO getCountry() {
        return country;
    }

    public void setCountry(CountryDTO country) {
        this.country = country;
    }

    public OperatorDTO getOperator() {
        return operator;
    }

    public void setOperator(OperatorDTO operator) {
        this.operator = operator;
    }

    public EntryFeeDTO getEntryFee() {
        return entryFee;
    }

    public void setEntryFee(EntryFeeDTO entryFee) {
        this.entryFee = entryFee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CompetitionDTO)) {
            return false;
        }

        CompetitionDTO competitionDTO = (CompetitionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, competitionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CompetitionDTO{" +
            "id='" + getId() + "'" +
            ", name='" + getName() + "'" +
            ", startDateTime='" + getStartDateTime() + "'" +
            ", endDateTime='" + getEndDateTime() + "'" +
            ", entryStartDateTime='" + getEntryStartDateTime() + "'" +
            ", entryEndDateTime='" + getEntryEndDateTime() + "'" +
            ", status='" + getStatus() + "'" +
            ", approval='" + getApproval() + "'" +
            ", entryApplyType='" + getEntryApplyType() + "'" +
            ", entryRatingType='" + getEntryRatingType() + "'" +
            ", competitionImage=" + getCompetitionImage() +
            ", reward=" + getReward() +
            ", country=" + getCountry() +
            ", operator=" + getOperator() +
            ", entryFee=" + getEntryFee() +
            "}";
    }
}
