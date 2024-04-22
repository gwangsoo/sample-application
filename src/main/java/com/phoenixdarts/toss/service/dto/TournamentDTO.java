package com.phoenixdarts.toss.service.dto;

import com.phoenixdarts.toss.domain.enumeration.DivisionAssignMethod;
import com.phoenixdarts.toss.domain.enumeration.DivisionRuleType;
import com.phoenixdarts.toss.domain.enumeration.EntryApprovalType;
import com.phoenixdarts.toss.domain.enumeration.EntryGenderType;
import com.phoenixdarts.toss.domain.enumeration.HandicapType;
import com.phoenixdarts.toss.domain.enumeration.SeedingRuleType;
import com.phoenixdarts.toss.domain.enumeration.TournamentPlayMode;
import com.phoenixdarts.toss.domain.enumeration.TournamentType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.phoenixdarts.toss.domain.Tournament} entity.
 */
@Schema(description = "토너먼트")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TournamentDTO implements Serializable {

    @NotNull
    @Size(max = 16)
    private String id;

    @NotNull
    @Min(value = 1)
    @Max(value = 99)
    private Integer seq;

    @NotNull
    @Size(max = 256)
    private String name;

    @NotNull
    @Min(value = 1)
    private Integer day;

    private Boolean eventTournament;

    private Boolean entryClose;

    @NotNull
    private EntryApprovalType entryApprovalType;

    @NotNull
    private TournamentType tournamentType;

    @NotNull
    private SeedingRuleType seedingRule;

    @NotNull
    private TournamentPlayMode tournamentPlayMode;

    private Boolean thirdPlaceDecision;

    @NotNull
    private DivisionRuleType divisionRule;

    @NotNull
    private Boolean entryLimit;

    @NotNull
    @Min(value = 0)
    private Integer numOfEntry;

    @NotNull
    private DivisionAssignMethod divisionAssignMethod;

    @NotNull
    private EntryGenderType entryGenderType;

    @NotNull
    private HandicapType handicap;

    private EntryFeeDTO entryFee;

    private CompetitionDTO competition;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Boolean getEventTournament() {
        return eventTournament;
    }

    public void setEventTournament(Boolean eventTournament) {
        this.eventTournament = eventTournament;
    }

    public Boolean getEntryClose() {
        return entryClose;
    }

    public void setEntryClose(Boolean entryClose) {
        this.entryClose = entryClose;
    }

    public EntryApprovalType getEntryApprovalType() {
        return entryApprovalType;
    }

    public void setEntryApprovalType(EntryApprovalType entryApprovalType) {
        this.entryApprovalType = entryApprovalType;
    }

    public TournamentType getTournamentType() {
        return tournamentType;
    }

    public void setTournamentType(TournamentType tournamentType) {
        this.tournamentType = tournamentType;
    }

    public SeedingRuleType getSeedingRule() {
        return seedingRule;
    }

    public void setSeedingRule(SeedingRuleType seedingRule) {
        this.seedingRule = seedingRule;
    }

    public TournamentPlayMode getTournamentPlayMode() {
        return tournamentPlayMode;
    }

    public void setTournamentPlayMode(TournamentPlayMode tournamentPlayMode) {
        this.tournamentPlayMode = tournamentPlayMode;
    }

    public Boolean getThirdPlaceDecision() {
        return thirdPlaceDecision;
    }

    public void setThirdPlaceDecision(Boolean thirdPlaceDecision) {
        this.thirdPlaceDecision = thirdPlaceDecision;
    }

    public DivisionRuleType getDivisionRule() {
        return divisionRule;
    }

    public void setDivisionRule(DivisionRuleType divisionRule) {
        this.divisionRule = divisionRule;
    }

    public Boolean getEntryLimit() {
        return entryLimit;
    }

    public void setEntryLimit(Boolean entryLimit) {
        this.entryLimit = entryLimit;
    }

    public Integer getNumOfEntry() {
        return numOfEntry;
    }

    public void setNumOfEntry(Integer numOfEntry) {
        this.numOfEntry = numOfEntry;
    }

    public DivisionAssignMethod getDivisionAssignMethod() {
        return divisionAssignMethod;
    }

    public void setDivisionAssignMethod(DivisionAssignMethod divisionAssignMethod) {
        this.divisionAssignMethod = divisionAssignMethod;
    }

    public EntryGenderType getEntryGenderType() {
        return entryGenderType;
    }

    public void setEntryGenderType(EntryGenderType entryGenderType) {
        this.entryGenderType = entryGenderType;
    }

    public HandicapType getHandicap() {
        return handicap;
    }

    public void setHandicap(HandicapType handicap) {
        this.handicap = handicap;
    }

    public EntryFeeDTO getEntryFee() {
        return entryFee;
    }

    public void setEntryFee(EntryFeeDTO entryFee) {
        this.entryFee = entryFee;
    }

    public CompetitionDTO getCompetition() {
        return competition;
    }

    public void setCompetition(CompetitionDTO competition) {
        this.competition = competition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TournamentDTO)) {
            return false;
        }

        TournamentDTO tournamentDTO = (TournamentDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, tournamentDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TournamentDTO{" +
            "id='" + getId() + "'" +
            ", seq=" + getSeq() +
            ", name='" + getName() + "'" +
            ", day=" + getDay() +
            ", eventTournament='" + getEventTournament() + "'" +
            ", entryClose='" + getEntryClose() + "'" +
            ", entryApprovalType='" + getEntryApprovalType() + "'" +
            ", tournamentType='" + getTournamentType() + "'" +
            ", seedingRule='" + getSeedingRule() + "'" +
            ", tournamentPlayMode='" + getTournamentPlayMode() + "'" +
            ", thirdPlaceDecision='" + getThirdPlaceDecision() + "'" +
            ", divisionRule='" + getDivisionRule() + "'" +
            ", entryLimit='" + getEntryLimit() + "'" +
            ", numOfEntry=" + getNumOfEntry() +
            ", divisionAssignMethod='" + getDivisionAssignMethod() + "'" +
            ", entryGenderType='" + getEntryGenderType() + "'" +
            ", handicap='" + getHandicap() + "'" +
            ", entryFee=" + getEntryFee() +
            ", competition=" + getCompetition() +
            "}";
    }
}
