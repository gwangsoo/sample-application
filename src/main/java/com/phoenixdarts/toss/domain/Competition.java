package com.phoenixdarts.toss.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.phoenixdarts.toss.domain.enumeration.CompetitionStatus;
import com.phoenixdarts.toss.domain.enumeration.EntryApplyType;
import com.phoenixdarts.toss.domain.enumeration.EntryRatingType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 대회
 */
@Entity
@Table(name = "tb_competition")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Competition implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 대회번호 국가(2)+년도(2)+일련번호(4) (KR240001)
     */
    @NotNull
    @Size(max = 16)
    @Id
    @GeneratedValue
    @Column(name = "id", length = 16, nullable = false, unique = true)
    private String id;

    /**
     * 대회명
     */
    @NotNull
    @Size(max = 256)
    @Column(name = "name", length = 256, nullable = false)
    private String name;

    /**
     * 대회기간(시작일자)
     */
    @NotNull
    @Column(name = "start_date_time", nullable = false)
    private Instant startDateTime;

    /**
     * 대회기간(종료일자)
     */
    @NotNull
    @Column(name = "end_date_time", nullable = false)
    private Instant endDateTime;

    /**
     * 참가기간(시작일자)
     */
    @NotNull
    @Column(name = "entry_start_date_time", nullable = false)
    private Instant entryStartDateTime;

    /**
     * 참가기간(종료일자)
     */
    @NotNull
    @Column(name = "entry_end_date_time", nullable = false)
    private Instant entryEndDateTime;

    /**
     * 대회상태
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private CompetitionStatus status;

    /**
     * 대회 승인 상태
     */
    @NotNull
    @Column(name = "approval", nullable = false)
    private Boolean approval;

    /**
     * 참가신청방법
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "entry_apply_type", nullable = false)
    private EntryApplyType entryApplyType;

    /**
     * 참가래이팅유형
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "entry_rating_type", nullable = false)
    private EntryRatingType entryRatingType;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "competition")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "divisions", "entryFee", "competition" }, allowSetters = true)
    private Set<Tournament> tournaments = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "competition")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "machines", "competition" }, allowSetters = true)
    private Set<MachineArea> machineAreas = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "affiliatedInfos" }, allowSetters = true)
    private FileInfo competitionImage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "rewardDetails", "competitions" }, allowSetters = true)
    private Reward reward;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "image", "competitions", "regions" }, allowSetters = true)
    private Country country;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "operatorRole", "region", "competitions" }, allowSetters = true)
    private Operator operator;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "currency", "tournaments", "competitions" }, allowSetters = true)
    private EntryFee entryFee;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Competition id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Competition name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getStartDateTime() {
        return this.startDateTime;
    }

    public Competition startDateTime(Instant startDateTime) {
        this.setStartDateTime(startDateTime);
        return this;
    }

    public void setStartDateTime(Instant startDateTime) {
        this.startDateTime = startDateTime;
    }

    public Instant getEndDateTime() {
        return this.endDateTime;
    }

    public Competition endDateTime(Instant endDateTime) {
        this.setEndDateTime(endDateTime);
        return this;
    }

    public void setEndDateTime(Instant endDateTime) {
        this.endDateTime = endDateTime;
    }

    public Instant getEntryStartDateTime() {
        return this.entryStartDateTime;
    }

    public Competition entryStartDateTime(Instant entryStartDateTime) {
        this.setEntryStartDateTime(entryStartDateTime);
        return this;
    }

    public void setEntryStartDateTime(Instant entryStartDateTime) {
        this.entryStartDateTime = entryStartDateTime;
    }

    public Instant getEntryEndDateTime() {
        return this.entryEndDateTime;
    }

    public Competition entryEndDateTime(Instant entryEndDateTime) {
        this.setEntryEndDateTime(entryEndDateTime);
        return this;
    }

    public void setEntryEndDateTime(Instant entryEndDateTime) {
        this.entryEndDateTime = entryEndDateTime;
    }

    public CompetitionStatus getStatus() {
        return this.status;
    }

    public Competition status(CompetitionStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(CompetitionStatus status) {
        this.status = status;
    }

    public Boolean getApproval() {
        return this.approval;
    }

    public Competition approval(Boolean approval) {
        this.setApproval(approval);
        return this;
    }

    public void setApproval(Boolean approval) {
        this.approval = approval;
    }

    public EntryApplyType getEntryApplyType() {
        return this.entryApplyType;
    }

    public Competition entryApplyType(EntryApplyType entryApplyType) {
        this.setEntryApplyType(entryApplyType);
        return this;
    }

    public void setEntryApplyType(EntryApplyType entryApplyType) {
        this.entryApplyType = entryApplyType;
    }

    public EntryRatingType getEntryRatingType() {
        return this.entryRatingType;
    }

    public Competition entryRatingType(EntryRatingType entryRatingType) {
        this.setEntryRatingType(entryRatingType);
        return this;
    }

    public void setEntryRatingType(EntryRatingType entryRatingType) {
        this.entryRatingType = entryRatingType;
    }

    public Set<Tournament> getTournaments() {
        return this.tournaments;
    }

    public void setTournaments(Set<Tournament> tournaments) {
        if (this.tournaments != null) {
            this.tournaments.forEach(i -> i.setCompetition(null));
        }
        if (tournaments != null) {
            tournaments.forEach(i -> i.setCompetition(this));
        }
        this.tournaments = tournaments;
    }

    public Competition tournaments(Set<Tournament> tournaments) {
        this.setTournaments(tournaments);
        return this;
    }

    public Competition addTournament(Tournament tournament) {
        this.tournaments.add(tournament);
        tournament.setCompetition(this);
        return this;
    }

    public Competition removeTournament(Tournament tournament) {
        this.tournaments.remove(tournament);
        tournament.setCompetition(null);
        return this;
    }

    public Set<MachineArea> getMachineAreas() {
        return this.machineAreas;
    }

    public void setMachineAreas(Set<MachineArea> machineAreas) {
        if (this.machineAreas != null) {
            this.machineAreas.forEach(i -> i.setCompetition(null));
        }
        if (machineAreas != null) {
            machineAreas.forEach(i -> i.setCompetition(this));
        }
        this.machineAreas = machineAreas;
    }

    public Competition machineAreas(Set<MachineArea> machineAreas) {
        this.setMachineAreas(machineAreas);
        return this;
    }

    public Competition addMachineArea(MachineArea machineArea) {
        this.machineAreas.add(machineArea);
        machineArea.setCompetition(this);
        return this;
    }

    public Competition removeMachineArea(MachineArea machineArea) {
        this.machineAreas.remove(machineArea);
        machineArea.setCompetition(null);
        return this;
    }

    public FileInfo getCompetitionImage() {
        return this.competitionImage;
    }

    public void setCompetitionImage(FileInfo fileInfo) {
        this.competitionImage = fileInfo;
    }

    public Competition competitionImage(FileInfo fileInfo) {
        this.setCompetitionImage(fileInfo);
        return this;
    }

    public Reward getReward() {
        return this.reward;
    }

    public void setReward(Reward reward) {
        this.reward = reward;
    }

    public Competition reward(Reward reward) {
        this.setReward(reward);
        return this;
    }

    public Country getCountry() {
        return this.country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Competition country(Country country) {
        this.setCountry(country);
        return this;
    }

    public Operator getOperator() {
        return this.operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    public Competition operator(Operator operator) {
        this.setOperator(operator);
        return this;
    }

    public EntryFee getEntryFee() {
        return this.entryFee;
    }

    public void setEntryFee(EntryFee entryFee) {
        this.entryFee = entryFee;
    }

    public Competition entryFee(EntryFee entryFee) {
        this.setEntryFee(entryFee);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Competition)) {
            return false;
        }
        return getId() != null && getId().equals(((Competition) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Competition{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", startDateTime='" + getStartDateTime() + "'" +
            ", endDateTime='" + getEndDateTime() + "'" +
            ", entryStartDateTime='" + getEntryStartDateTime() + "'" +
            ", entryEndDateTime='" + getEntryEndDateTime() + "'" +
            ", status='" + getStatus() + "'" +
            ", approval='" + getApproval() + "'" +
            ", entryApplyType='" + getEntryApplyType() + "'" +
            ", entryRatingType='" + getEntryRatingType() + "'" +
            "}";
    }
}
