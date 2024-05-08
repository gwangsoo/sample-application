package com.phoenixdarts.toss.backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.phoenixdarts.toss.backend.domain.enumeration.EventRangeType;
import com.phoenixdarts.toss.backend.domain.enumeration.NextRoundDecisionType;
import com.phoenixdarts.toss.backend.domain.enumeration.RoundRobinGroupType;
import com.phoenixdarts.toss.backend.domain.enumeration.RoundRobinRankingDecisionType;
import com.phoenixdarts.toss.backend.domain.enumeration.ThirdDecisionRankingRule;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 디비전
 */
@Entity
@Table(name = "tb_division")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Division implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Size(max = 16)
    @Id
    @GeneratedValue
    @Column(name = "id", length = 16, nullable = false, unique = true)
    private String id;

    /**
     * 디비전순서 (노출순서와 매치ID에 사용)
     */
    @NotNull
    @Min(value = 1)
    @Max(value = 99)
    @Column(name = "seq", nullable = false)
    private Integer seq;

    /**
     * 디비전명
     */
    @NotNull
    @Size(max = 256)
    @Column(name = "name", length = 256, nullable = false)
    private String name;

    /**
     * 팀합산 Rating (최소)
     */
    @DecimalMin(value = "0")
    @Column(name = "rating_rule_team_min")
    private Float ratingRuleTeamMin;

    /**
     * 팀합산 Rating (최대)
     */
    @DecimalMin(value = "0")
    @Column(name = "rating_rule_team_max")
    private Float ratingRuleTeamMax;

    /**
     * 개인 레이팅 제한 여부 false unlimited, true limit
     */
    @Column(name = "rating_rule_individual_limit")
    private Boolean ratingRuleIndividualLimit;

    /**
     * 개인 Rating (최소)
     */
    @DecimalMin(value = "0")
    @Column(name = "rating_rule_individual_min")
    private Float ratingRuleIndividualMin;

    /**
     * 개인 Rating (최대)
     */
    @DecimalMin(value = "0")
    @Column(name = "rating_rule_individual_max")
    private Float ratingRuleIndividualMax;

    /**
     * 참가자 인원수 설정 false unlimited, true limit
     */
    @NotNull
    @Column(name = "entry_limit", nullable = false)
    private Boolean entryLimit;

    /**
     * 라운드로빈 순위 결정 방식
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "round_robin_ranking_decision_type")
    private RoundRobinRankingDecisionType roundRobinRankingDecisionType;

    /**
     * 라운드로빈 그룹 종류
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "round_robin_group_type")
    private RoundRobinGroupType roundRobinGroupType;

    /**
     * 다음(결승) 라운드 진출 방식
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "next_round_decision_type")
    private NextRoundDecisionType nextRoundDecisionType;

    /**
     * 라운드로빈 3위 결승 진출 여부
     */
    @Column(name = "round_rogin_third_decision")
    private Boolean roundRoginThirdDecision;

    /**
     * 라운드로빈 3위 결승 진출 랭킹 기준
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "third_decision_ranking_rule")
    private ThirdDecisionRankingRule thirdDecisionRankingRule;

    /**
     * 모든 Round 동일 Format 사용
     */
    @Column(name = "use_all_round_same_format")
    private Boolean useAllRoundSameFormat;

    /**
     * Event Range 구분 기준
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "event_range_type")
    private EventRangeType eventRangeType;

    /**
     * elimination team 수
     */
    @Column(name = "elimination_team_count")
    private Integer eliminationTeamCount;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "division")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "matchFormatGames", "matchFormatOptions", "matchFormatSets", "division" }, allowSetters = true)
    private Set<MatchFormat> matchFormats = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "division")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "division" }, allowSetters = true)
    private Set<EventPoint> eventPoints = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "division")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "entries", "captain", "tteam", "affiliatedInfo", "paymentInfo", "division", "matchTeams" },
        allowSetters = true
    )
    private Set<Team> teams = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "divisions", "entryFee", "competition" }, allowSetters = true)
    private Tournament tournament;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "division")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "matchScores", "home", "away", "tmatch", "division", "machines" }, allowSetters = true)
    private Set<Match> matches = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Division id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getSeq() {
        return this.seq;
    }

    public Division seq(Integer seq) {
        this.setSeq(seq);
        return this;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public String getName() {
        return this.name;
    }

    public Division name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getRatingRuleTeamMin() {
        return this.ratingRuleTeamMin;
    }

    public Division ratingRuleTeamMin(Float ratingRuleTeamMin) {
        this.setRatingRuleTeamMin(ratingRuleTeamMin);
        return this;
    }

    public void setRatingRuleTeamMin(Float ratingRuleTeamMin) {
        this.ratingRuleTeamMin = ratingRuleTeamMin;
    }

    public Float getRatingRuleTeamMax() {
        return this.ratingRuleTeamMax;
    }

    public Division ratingRuleTeamMax(Float ratingRuleTeamMax) {
        this.setRatingRuleTeamMax(ratingRuleTeamMax);
        return this;
    }

    public void setRatingRuleTeamMax(Float ratingRuleTeamMax) {
        this.ratingRuleTeamMax = ratingRuleTeamMax;
    }

    public Boolean getRatingRuleIndividualLimit() {
        return this.ratingRuleIndividualLimit;
    }

    public Division ratingRuleIndividualLimit(Boolean ratingRuleIndividualLimit) {
        this.setRatingRuleIndividualLimit(ratingRuleIndividualLimit);
        return this;
    }

    public void setRatingRuleIndividualLimit(Boolean ratingRuleIndividualLimit) {
        this.ratingRuleIndividualLimit = ratingRuleIndividualLimit;
    }

    public Float getRatingRuleIndividualMin() {
        return this.ratingRuleIndividualMin;
    }

    public Division ratingRuleIndividualMin(Float ratingRuleIndividualMin) {
        this.setRatingRuleIndividualMin(ratingRuleIndividualMin);
        return this;
    }

    public void setRatingRuleIndividualMin(Float ratingRuleIndividualMin) {
        this.ratingRuleIndividualMin = ratingRuleIndividualMin;
    }

    public Float getRatingRuleIndividualMax() {
        return this.ratingRuleIndividualMax;
    }

    public Division ratingRuleIndividualMax(Float ratingRuleIndividualMax) {
        this.setRatingRuleIndividualMax(ratingRuleIndividualMax);
        return this;
    }

    public void setRatingRuleIndividualMax(Float ratingRuleIndividualMax) {
        this.ratingRuleIndividualMax = ratingRuleIndividualMax;
    }

    public Boolean getEntryLimit() {
        return this.entryLimit;
    }

    public Division entryLimit(Boolean entryLimit) {
        this.setEntryLimit(entryLimit);
        return this;
    }

    public void setEntryLimit(Boolean entryLimit) {
        this.entryLimit = entryLimit;
    }

    public RoundRobinRankingDecisionType getRoundRobinRankingDecisionType() {
        return this.roundRobinRankingDecisionType;
    }

    public Division roundRobinRankingDecisionType(RoundRobinRankingDecisionType roundRobinRankingDecisionType) {
        this.setRoundRobinRankingDecisionType(roundRobinRankingDecisionType);
        return this;
    }

    public void setRoundRobinRankingDecisionType(RoundRobinRankingDecisionType roundRobinRankingDecisionType) {
        this.roundRobinRankingDecisionType = roundRobinRankingDecisionType;
    }

    public RoundRobinGroupType getRoundRobinGroupType() {
        return this.roundRobinGroupType;
    }

    public Division roundRobinGroupType(RoundRobinGroupType roundRobinGroupType) {
        this.setRoundRobinGroupType(roundRobinGroupType);
        return this;
    }

    public void setRoundRobinGroupType(RoundRobinGroupType roundRobinGroupType) {
        this.roundRobinGroupType = roundRobinGroupType;
    }

    public NextRoundDecisionType getNextRoundDecisionType() {
        return this.nextRoundDecisionType;
    }

    public Division nextRoundDecisionType(NextRoundDecisionType nextRoundDecisionType) {
        this.setNextRoundDecisionType(nextRoundDecisionType);
        return this;
    }

    public void setNextRoundDecisionType(NextRoundDecisionType nextRoundDecisionType) {
        this.nextRoundDecisionType = nextRoundDecisionType;
    }

    public Boolean getRoundRoginThirdDecision() {
        return this.roundRoginThirdDecision;
    }

    public Division roundRoginThirdDecision(Boolean roundRoginThirdDecision) {
        this.setRoundRoginThirdDecision(roundRoginThirdDecision);
        return this;
    }

    public void setRoundRoginThirdDecision(Boolean roundRoginThirdDecision) {
        this.roundRoginThirdDecision = roundRoginThirdDecision;
    }

    public ThirdDecisionRankingRule getThirdDecisionRankingRule() {
        return this.thirdDecisionRankingRule;
    }

    public Division thirdDecisionRankingRule(ThirdDecisionRankingRule thirdDecisionRankingRule) {
        this.setThirdDecisionRankingRule(thirdDecisionRankingRule);
        return this;
    }

    public void setThirdDecisionRankingRule(ThirdDecisionRankingRule thirdDecisionRankingRule) {
        this.thirdDecisionRankingRule = thirdDecisionRankingRule;
    }

    public Boolean getUseAllRoundSameFormat() {
        return this.useAllRoundSameFormat;
    }

    public Division useAllRoundSameFormat(Boolean useAllRoundSameFormat) {
        this.setUseAllRoundSameFormat(useAllRoundSameFormat);
        return this;
    }

    public void setUseAllRoundSameFormat(Boolean useAllRoundSameFormat) {
        this.useAllRoundSameFormat = useAllRoundSameFormat;
    }

    public EventRangeType getEventRangeType() {
        return this.eventRangeType;
    }

    public Division eventRangeType(EventRangeType eventRangeType) {
        this.setEventRangeType(eventRangeType);
        return this;
    }

    public void setEventRangeType(EventRangeType eventRangeType) {
        this.eventRangeType = eventRangeType;
    }

    public Integer getEliminationTeamCount() {
        return this.eliminationTeamCount;
    }

    public Division eliminationTeamCount(Integer eliminationTeamCount) {
        this.setEliminationTeamCount(eliminationTeamCount);
        return this;
    }

    public void setEliminationTeamCount(Integer eliminationTeamCount) {
        this.eliminationTeamCount = eliminationTeamCount;
    }

    public Set<MatchFormat> getMatchFormats() {
        return this.matchFormats;
    }

    public void setMatchFormats(Set<MatchFormat> matchFormats) {
        if (this.matchFormats != null) {
            this.matchFormats.forEach(i -> i.setDivision(null));
        }
        if (matchFormats != null) {
            matchFormats.forEach(i -> i.setDivision(this));
        }
        this.matchFormats = matchFormats;
    }

    public Division matchFormats(Set<MatchFormat> matchFormats) {
        this.setMatchFormats(matchFormats);
        return this;
    }

    public Division addMatchFormat(MatchFormat matchFormat) {
        this.matchFormats.add(matchFormat);
        matchFormat.setDivision(this);
        return this;
    }

    public Division removeMatchFormat(MatchFormat matchFormat) {
        this.matchFormats.remove(matchFormat);
        matchFormat.setDivision(null);
        return this;
    }

    public Set<EventPoint> getEventPoints() {
        return this.eventPoints;
    }

    public void setEventPoints(Set<EventPoint> eventPoints) {
        if (this.eventPoints != null) {
            this.eventPoints.forEach(i -> i.setDivision(null));
        }
        if (eventPoints != null) {
            eventPoints.forEach(i -> i.setDivision(this));
        }
        this.eventPoints = eventPoints;
    }

    public Division eventPoints(Set<EventPoint> eventPoints) {
        this.setEventPoints(eventPoints);
        return this;
    }

    public Division addEventPoint(EventPoint eventPoint) {
        this.eventPoints.add(eventPoint);
        eventPoint.setDivision(this);
        return this;
    }

    public Division removeEventPoint(EventPoint eventPoint) {
        this.eventPoints.remove(eventPoint);
        eventPoint.setDivision(null);
        return this;
    }

    public Set<Team> getTeams() {
        return this.teams;
    }

    public void setTeams(Set<Team> teams) {
        if (this.teams != null) {
            this.teams.forEach(i -> i.setDivision(null));
        }
        if (teams != null) {
            teams.forEach(i -> i.setDivision(this));
        }
        this.teams = teams;
    }

    public Division teams(Set<Team> teams) {
        this.setTeams(teams);
        return this;
    }

    public Division addTeam(Team team) {
        this.teams.add(team);
        team.setDivision(this);
        return this;
    }

    public Division removeTeam(Team team) {
        this.teams.remove(team);
        team.setDivision(null);
        return this;
    }

    public Tournament getTournament() {
        return this.tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    public Division tournament(Tournament tournament) {
        this.setTournament(tournament);
        return this;
    }

    public Set<Match> getMatches() {
        return this.matches;
    }

    public void setMatches(Set<Match> matches) {
        if (this.matches != null) {
            this.matches.forEach(i -> i.setDivision(null));
        }
        if (matches != null) {
            matches.forEach(i -> i.setDivision(this));
        }
        this.matches = matches;
    }

    public Division matches(Set<Match> matches) {
        this.setMatches(matches);
        return this;
    }

    public Division addMatch(Match match) {
        this.matches.add(match);
        match.setDivision(this);
        return this;
    }

    public Division removeMatch(Match match) {
        this.matches.remove(match);
        match.setDivision(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Division)) {
            return false;
        }
        return getId() != null && getId().equals(((Division) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Division{" +
            "id=" + getId() +
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
            "}";
    }
}
