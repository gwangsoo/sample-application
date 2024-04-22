package com.phoenixdarts.toss.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.phoenixdarts.toss.domain.enumeration.DivisionAssignMethod;
import com.phoenixdarts.toss.domain.enumeration.DivisionRuleType;
import com.phoenixdarts.toss.domain.enumeration.EntryApprovalType;
import com.phoenixdarts.toss.domain.enumeration.EntryGenderType;
import com.phoenixdarts.toss.domain.enumeration.HandicapType;
import com.phoenixdarts.toss.domain.enumeration.SeedingRuleType;
import com.phoenixdarts.toss.domain.enumeration.TournamentPlayMode;
import com.phoenixdarts.toss.domain.enumeration.TournamentType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 토너먼트
 */
@Entity
@Table(name = "tb_tournament")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Tournament implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Size(max = 16)
    @Id
    @GeneratedValue
    @Column(name = "id", length = 16, nullable = false, unique = true)
    private String id;

    /**
     * 토너먼트순서 (노출순서와 매치ID에 사용)
     */
    @NotNull
    @Min(value = 1)
    @Max(value = 99)
    @Column(name = "seq", nullable = false)
    private Integer seq;

    /**
     * 토너먼트명
     */
    @NotNull
    @Size(max = 256)
    @Column(name = "name", length = 256, nullable = false)
    private String name;

    /**
     * 대회진행일(차수)
     */
    @NotNull
    @Min(value = 1)
    @Column(name = "day", nullable = false)
    private Integer day;

    /**
     * 패자 부활 Tournament 여부
     */
    @Column(name = "event_tournament")
    private Boolean eventTournament;

    /**
     * 참가마감 버튼
     */
    @Column(name = "entry_close")
    private Boolean entryClose;

    /**
     * 참가승인방법
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "entry_approval_type", nullable = false)
    private EntryApprovalType entryApprovalType;

    /**
     * 토너먼트유형
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tournament_type", nullable = false)
    private TournamentType tournamentType;

    /**
     * Seeding Rule
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "seeding_rule", nullable = false)
    private SeedingRuleType seedingRule;

    /**
     * 토너먼트 Play Mode
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tournament_play_mode", nullable = false)
    private TournamentPlayMode tournamentPlayMode;

    /**
     * 3위 결정 여부
     */
    @Column(name = "third_place_decision")
    private Boolean thirdPlaceDecision;

    /**
     * Division 구분 기준
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "division_rule", nullable = false)
    private DivisionRuleType divisionRule;

    /**
     * 엔트리 인원수 설정 false unlimited, true limit
     */
    @NotNull
    @Column(name = "entry_limit", nullable = false)
    private Boolean entryLimit;

    /**
     * 엔트리인원수
     */
    @NotNull
    @Min(value = 0)
    @Column(name = "num_of_entry", nullable = false)
    private Integer numOfEntry;

    /**
     * 디비전배정방법
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "division_assign_method", nullable = false)
    private DivisionAssignMethod divisionAssignMethod;

    /**
     * 참가자 성별
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "entry_gender_type", nullable = false)
    private EntryGenderType entryGenderType;

    /**
     * Handicap
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "handicap", nullable = false)
    private HandicapType handicap;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tournament")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "matchFormats", "eventPoints", "teams", "tournament", "matches" }, allowSetters = true)
    private Set<Division> divisions = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "currency", "tournaments", "competitions" }, allowSetters = true)
    private EntryFee entryFee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = { "tournaments", "machineAreas", "competitionImage", "reward", "country", "operator", "entryFee" },
        allowSetters = true
    )
    private Competition competition;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Tournament id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getSeq() {
        return this.seq;
    }

    public Tournament seq(Integer seq) {
        this.setSeq(seq);
        return this;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public String getName() {
        return this.name;
    }

    public Tournament name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDay() {
        return this.day;
    }

    public Tournament day(Integer day) {
        this.setDay(day);
        return this;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Boolean getEventTournament() {
        return this.eventTournament;
    }

    public Tournament eventTournament(Boolean eventTournament) {
        this.setEventTournament(eventTournament);
        return this;
    }

    public void setEventTournament(Boolean eventTournament) {
        this.eventTournament = eventTournament;
    }

    public Boolean getEntryClose() {
        return this.entryClose;
    }

    public Tournament entryClose(Boolean entryClose) {
        this.setEntryClose(entryClose);
        return this;
    }

    public void setEntryClose(Boolean entryClose) {
        this.entryClose = entryClose;
    }

    public EntryApprovalType getEntryApprovalType() {
        return this.entryApprovalType;
    }

    public Tournament entryApprovalType(EntryApprovalType entryApprovalType) {
        this.setEntryApprovalType(entryApprovalType);
        return this;
    }

    public void setEntryApprovalType(EntryApprovalType entryApprovalType) {
        this.entryApprovalType = entryApprovalType;
    }

    public TournamentType getTournamentType() {
        return this.tournamentType;
    }

    public Tournament tournamentType(TournamentType tournamentType) {
        this.setTournamentType(tournamentType);
        return this;
    }

    public void setTournamentType(TournamentType tournamentType) {
        this.tournamentType = tournamentType;
    }

    public SeedingRuleType getSeedingRule() {
        return this.seedingRule;
    }

    public Tournament seedingRule(SeedingRuleType seedingRule) {
        this.setSeedingRule(seedingRule);
        return this;
    }

    public void setSeedingRule(SeedingRuleType seedingRule) {
        this.seedingRule = seedingRule;
    }

    public TournamentPlayMode getTournamentPlayMode() {
        return this.tournamentPlayMode;
    }

    public Tournament tournamentPlayMode(TournamentPlayMode tournamentPlayMode) {
        this.setTournamentPlayMode(tournamentPlayMode);
        return this;
    }

    public void setTournamentPlayMode(TournamentPlayMode tournamentPlayMode) {
        this.tournamentPlayMode = tournamentPlayMode;
    }

    public Boolean getThirdPlaceDecision() {
        return this.thirdPlaceDecision;
    }

    public Tournament thirdPlaceDecision(Boolean thirdPlaceDecision) {
        this.setThirdPlaceDecision(thirdPlaceDecision);
        return this;
    }

    public void setThirdPlaceDecision(Boolean thirdPlaceDecision) {
        this.thirdPlaceDecision = thirdPlaceDecision;
    }

    public DivisionRuleType getDivisionRule() {
        return this.divisionRule;
    }

    public Tournament divisionRule(DivisionRuleType divisionRule) {
        this.setDivisionRule(divisionRule);
        return this;
    }

    public void setDivisionRule(DivisionRuleType divisionRule) {
        this.divisionRule = divisionRule;
    }

    public Boolean getEntryLimit() {
        return this.entryLimit;
    }

    public Tournament entryLimit(Boolean entryLimit) {
        this.setEntryLimit(entryLimit);
        return this;
    }

    public void setEntryLimit(Boolean entryLimit) {
        this.entryLimit = entryLimit;
    }

    public Integer getNumOfEntry() {
        return this.numOfEntry;
    }

    public Tournament numOfEntry(Integer numOfEntry) {
        this.setNumOfEntry(numOfEntry);
        return this;
    }

    public void setNumOfEntry(Integer numOfEntry) {
        this.numOfEntry = numOfEntry;
    }

    public DivisionAssignMethod getDivisionAssignMethod() {
        return this.divisionAssignMethod;
    }

    public Tournament divisionAssignMethod(DivisionAssignMethod divisionAssignMethod) {
        this.setDivisionAssignMethod(divisionAssignMethod);
        return this;
    }

    public void setDivisionAssignMethod(DivisionAssignMethod divisionAssignMethod) {
        this.divisionAssignMethod = divisionAssignMethod;
    }

    public EntryGenderType getEntryGenderType() {
        return this.entryGenderType;
    }

    public Tournament entryGenderType(EntryGenderType entryGenderType) {
        this.setEntryGenderType(entryGenderType);
        return this;
    }

    public void setEntryGenderType(EntryGenderType entryGenderType) {
        this.entryGenderType = entryGenderType;
    }

    public HandicapType getHandicap() {
        return this.handicap;
    }

    public Tournament handicap(HandicapType handicap) {
        this.setHandicap(handicap);
        return this;
    }

    public void setHandicap(HandicapType handicap) {
        this.handicap = handicap;
    }

    public Set<Division> getDivisions() {
        return this.divisions;
    }

    public void setDivisions(Set<Division> divisions) {
        if (this.divisions != null) {
            this.divisions.forEach(i -> i.setTournament(null));
        }
        if (divisions != null) {
            divisions.forEach(i -> i.setTournament(this));
        }
        this.divisions = divisions;
    }

    public Tournament divisions(Set<Division> divisions) {
        this.setDivisions(divisions);
        return this;
    }

    public Tournament addDivision(Division division) {
        this.divisions.add(division);
        division.setTournament(this);
        return this;
    }

    public Tournament removeDivision(Division division) {
        this.divisions.remove(division);
        division.setTournament(null);
        return this;
    }

    public EntryFee getEntryFee() {
        return this.entryFee;
    }

    public void setEntryFee(EntryFee entryFee) {
        this.entryFee = entryFee;
    }

    public Tournament entryFee(EntryFee entryFee) {
        this.setEntryFee(entryFee);
        return this;
    }

    public Competition getCompetition() {
        return this.competition;
    }

    public void setCompetition(Competition competition) {
        this.competition = competition;
    }

    public Tournament competition(Competition competition) {
        this.setCompetition(competition);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Tournament)) {
            return false;
        }
        return getId() != null && getId().equals(((Tournament) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Tournament{" +
            "id=" + getId() +
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
            "}";
    }
}
