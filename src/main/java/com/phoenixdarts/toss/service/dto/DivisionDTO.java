package com.phoenixdarts.toss.backend.service.dto;

import com.phoenixdarts.toss.backend.domain.enumeration.EventRangeType;
import com.phoenixdarts.toss.backend.domain.enumeration.NextRoundDecisionType;
import com.phoenixdarts.toss.backend.domain.enumeration.RoundRobinGroupType;
import com.phoenixdarts.toss.backend.domain.enumeration.RoundRobinRankingDecisionType;
import com.phoenixdarts.toss.backend.domain.enumeration.ThirdDecisionRankingRule;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.phoenixdarts.toss.backend.domain.Division} entity.
 */
@Schema(description = "디비전")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DivisionDTO implements Serializable {

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

    @DecimalMin(value = "0")
    private Float ratingRuleTeamMin;

    @DecimalMin(value = "0")
    private Float ratingRuleTeamMax;

    private Boolean ratingRuleIndividualLimit;

    @DecimalMin(value = "0")
    private Float ratingRuleIndividualMin;

    @DecimalMin(value = "0")
    private Float ratingRuleIndividualMax;

    @NotNull
    private Boolean entryLimit;

    private RoundRobinRankingDecisionType roundRobinRankingDecisionType;

    private RoundRobinGroupType roundRobinGroupType;

    private NextRoundDecisionType nextRoundDecisionType;

    private Boolean roundRoginThirdDecision;

    private ThirdDecisionRankingRule thirdDecisionRankingRule;

    private Boolean useAllRoundSameFormat;

    private EventRangeType eventRangeType;

    private Integer eliminationTeamCount;

    private TournamentDTO tournament;

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

    public Float getRatingRuleTeamMin() {
        return ratingRuleTeamMin;
    }

    public void setRatingRuleTeamMin(Float ratingRuleTeamMin) {
        this.ratingRuleTeamMin = ratingRuleTeamMin;
    }

    public Float getRatingRuleTeamMax() {
        return ratingRuleTeamMax;
    }

    public void setRatingRuleTeamMax(Float ratingRuleTeamMax) {
        this.ratingRuleTeamMax = ratingRuleTeamMax;
    }

    public Boolean getRatingRuleIndividualLimit() {
        return ratingRuleIndividualLimit;
    }

    public void setRatingRuleIndividualLimit(Boolean ratingRuleIndividualLimit) {
        this.ratingRuleIndividualLimit = ratingRuleIndividualLimit;
    }

    public Float getRatingRuleIndividualMin() {
        return ratingRuleIndividualMin;
    }

    public void setRatingRuleIndividualMin(Float ratingRuleIndividualMin) {
        this.ratingRuleIndividualMin = ratingRuleIndividualMin;
    }

    public Float getRatingRuleIndividualMax() {
        return ratingRuleIndividualMax;
    }

    public void setRatingRuleIndividualMax(Float ratingRuleIndividualMax) {
        this.ratingRuleIndividualMax = ratingRuleIndividualMax;
    }

    public Boolean getEntryLimit() {
        return entryLimit;
    }

    public void setEntryLimit(Boolean entryLimit) {
        this.entryLimit = entryLimit;
    }

    public RoundRobinRankingDecisionType getRoundRobinRankingDecisionType() {
        return roundRobinRankingDecisionType;
    }

    public void setRoundRobinRankingDecisionType(RoundRobinRankingDecisionType roundRobinRankingDecisionType) {
        this.roundRobinRankingDecisionType = roundRobinRankingDecisionType;
    }

    public RoundRobinGroupType getRoundRobinGroupType() {
        return roundRobinGroupType;
    }

    public void setRoundRobinGroupType(RoundRobinGroupType roundRobinGroupType) {
        this.roundRobinGroupType = roundRobinGroupType;
    }

    public NextRoundDecisionType getNextRoundDecisionType() {
        return nextRoundDecisionType;
    }

    public void setNextRoundDecisionType(NextRoundDecisionType nextRoundDecisionType) {
        this.nextRoundDecisionType = nextRoundDecisionType;
    }

    public Boolean getRoundRoginThirdDecision() {
        return roundRoginThirdDecision;
    }

    public void setRoundRoginThirdDecision(Boolean roundRoginThirdDecision) {
        this.roundRoginThirdDecision = roundRoginThirdDecision;
    }

    public ThirdDecisionRankingRule getThirdDecisionRankingRule() {
        return thirdDecisionRankingRule;
    }

    public void setThirdDecisionRankingRule(ThirdDecisionRankingRule thirdDecisionRankingRule) {
        this.thirdDecisionRankingRule = thirdDecisionRankingRule;
    }

    public Boolean getUseAllRoundSameFormat() {
        return useAllRoundSameFormat;
    }

    public void setUseAllRoundSameFormat(Boolean useAllRoundSameFormat) {
        this.useAllRoundSameFormat = useAllRoundSameFormat;
    }

    public EventRangeType getEventRangeType() {
        return eventRangeType;
    }

    public void setEventRangeType(EventRangeType eventRangeType) {
        this.eventRangeType = eventRangeType;
    }

    public Integer getEliminationTeamCount() {
        return eliminationTeamCount;
    }

    public void setEliminationTeamCount(Integer eliminationTeamCount) {
        this.eliminationTeamCount = eliminationTeamCount;
    }

    public TournamentDTO getTournament() {
        return tournament;
    }

    public void setTournament(TournamentDTO tournament) {
        this.tournament = tournament;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DivisionDTO)) {
            return false;
        }

        DivisionDTO divisionDTO = (DivisionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, divisionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DivisionDTO{" +
            "id='" + getId() + "'" +
            ", seq=" + getSeq() +
            ", name='" + getName() + "'" +
            ", ratingRuleTeamMin=" + getRatingRuleTeamMin() +
            ", ratingRuleTeamMax=" + getRatingRuleTeamMax() +
            ", ratingRuleIndividualLimit='" + getRatingRuleIndividualLimit() + "'" +
            ", ratingRuleIndividualMin=" + getRatingRuleIndividualMin() +
            ", ratingRuleIndividualMax=" + getRatingRuleIndividualMax() +
            ", entryLimit='" + getEntryLimit() + "'" +
            ", roundRobinRankingDecisionType='" + getRoundRobinRankingDecisionType() + "'" +
            ", roundRobinGroupType='" + getRoundRobinGroupType() + "'" +
            ", nextRoundDecisionType='" + getNextRoundDecisionType() + "'" +
            ", roundRoginThirdDecision='" + getRoundRoginThirdDecision() + "'" +
            ", thirdDecisionRankingRule='" + getThirdDecisionRankingRule() + "'" +
            ", useAllRoundSameFormat='" + getUseAllRoundSameFormat() + "'" +
            ", eventRangeType='" + getEventRangeType() + "'" +
            ", eliminationTeamCount=" + getEliminationTeamCount() +
            ", tournament=" + getTournament() +
            "}";
    }
}
