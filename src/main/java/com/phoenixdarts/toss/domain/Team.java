package com.phoenixdarts.toss.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.phoenixdarts.toss.domain.enumeration.EntryApprovalStatusType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Team
 */
@Entity
@Table(name = "tb_team")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Team implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Size(max = 16)
    @Id
    @GeneratedValue
    @Column(name = "id", length = 16, nullable = false, unique = true)
    private String id;

    /**
     * 참가팀번호
     */
    @Size(max = 8)
    @Column(name = "team_no", length = 8)
    private String teamNo;

    /**
     * 참가자 참여 여부 상태
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "approval_status", nullable = false)
    private EntryApprovalStatusType approvalStatus;

    /**
     * Entry date
     */
    @Column(name = "entry_date")
    private ZonedDateTime entryDate;

    /**
     * 메모
     */
    @Size(max = 256)
    @Column(name = "memo", length = 256)
    private String memo;

    /**
     * 그룹번호(RoundRobin속성) RounRobin의 경우 unique(DivisionId,GroupNo,GroupSeq)
     */
    @Min(value = 1)
    @Max(value = 999)
    @Column(name = "group_no")
    private Integer groupNo;

    /**
     * 그룹순서(RoundRobin속성)
     */
    @Min(value = 1)
    @Max(value = 9)
    @Column(name = "group_seq")
    private Integer groupSeq;

    /**
     * 시드번호 (토너먼트별 시드값 정책)
     */
    @Min(value = 1)
    @Max(value = 999)
    @Column(name = "seed_no")
    private Integer seedNo;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "team")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "team", "matchAttendances" }, allowSetters = true)
    private Set<Entry> entries = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "team", "matchAttendances" }, allowSetters = true)
    private Entry captain;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "matchFormats", "eventPoints", "teams", "tournament", "matches" }, allowSetters = true)
    private Division tteam;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "fileInfo", "teams" }, allowSetters = true)
    private AffiliatedInfo affiliatedInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "teams" }, allowSetters = true)
    private PaymentInfo paymentInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "matchFormats", "eventPoints", "teams", "tournament", "matches" }, allowSetters = true)
    private Division division;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "team")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "matchAttendances", "matchCalls", "team" }, allowSetters = true)
    private Set<MatchTeam> matchTeams = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Team id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTeamNo() {
        return this.teamNo;
    }

    public Team teamNo(String teamNo) {
        this.setTeamNo(teamNo);
        return this;
    }

    public void setTeamNo(String teamNo) {
        this.teamNo = teamNo;
    }

    public EntryApprovalStatusType getApprovalStatus() {
        return this.approvalStatus;
    }

    public Team approvalStatus(EntryApprovalStatusType approvalStatus) {
        this.setApprovalStatus(approvalStatus);
        return this;
    }

    public void setApprovalStatus(EntryApprovalStatusType approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public ZonedDateTime getEntryDate() {
        return this.entryDate;
    }

    public Team entryDate(ZonedDateTime entryDate) {
        this.setEntryDate(entryDate);
        return this;
    }

    public void setEntryDate(ZonedDateTime entryDate) {
        this.entryDate = entryDate;
    }

    public String getMemo() {
        return this.memo;
    }

    public Team memo(String memo) {
        this.setMemo(memo);
        return this;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Integer getGroupNo() {
        return this.groupNo;
    }

    public Team groupNo(Integer groupNo) {
        this.setGroupNo(groupNo);
        return this;
    }

    public void setGroupNo(Integer groupNo) {
        this.groupNo = groupNo;
    }

    public Integer getGroupSeq() {
        return this.groupSeq;
    }

    public Team groupSeq(Integer groupSeq) {
        this.setGroupSeq(groupSeq);
        return this;
    }

    public void setGroupSeq(Integer groupSeq) {
        this.groupSeq = groupSeq;
    }

    public Integer getSeedNo() {
        return this.seedNo;
    }

    public Team seedNo(Integer seedNo) {
        this.setSeedNo(seedNo);
        return this;
    }

    public void setSeedNo(Integer seedNo) {
        this.seedNo = seedNo;
    }

    public Set<Entry> getEntries() {
        return this.entries;
    }

    public void setEntries(Set<Entry> entries) {
        if (this.entries != null) {
            this.entries.forEach(i -> i.setTeam(null));
        }
        if (entries != null) {
            entries.forEach(i -> i.setTeam(this));
        }
        this.entries = entries;
    }

    public Team entries(Set<Entry> entries) {
        this.setEntries(entries);
        return this;
    }

    public Team addEntry(Entry entry) {
        this.entries.add(entry);
        entry.setTeam(this);
        return this;
    }

    public Team removeEntry(Entry entry) {
        this.entries.remove(entry);
        entry.setTeam(null);
        return this;
    }

    public Entry getCaptain() {
        return this.captain;
    }

    public void setCaptain(Entry entry) {
        this.captain = entry;
    }

    public Team captain(Entry entry) {
        this.setCaptain(entry);
        return this;
    }

    public Division getTteam() {
        return this.tteam;
    }

    public void setTteam(Division division) {
        this.tteam = division;
    }

    public Team tteam(Division division) {
        this.setTteam(division);
        return this;
    }

    public AffiliatedInfo getAffiliatedInfo() {
        return this.affiliatedInfo;
    }

    public void setAffiliatedInfo(AffiliatedInfo affiliatedInfo) {
        this.affiliatedInfo = affiliatedInfo;
    }

    public Team affiliatedInfo(AffiliatedInfo affiliatedInfo) {
        this.setAffiliatedInfo(affiliatedInfo);
        return this;
    }

    public PaymentInfo getPaymentInfo() {
        return this.paymentInfo;
    }

    public void setPaymentInfo(PaymentInfo paymentInfo) {
        this.paymentInfo = paymentInfo;
    }

    public Team paymentInfo(PaymentInfo paymentInfo) {
        this.setPaymentInfo(paymentInfo);
        return this;
    }

    public Division getDivision() {
        return this.division;
    }

    public void setDivision(Division division) {
        this.division = division;
    }

    public Team division(Division division) {
        this.setDivision(division);
        return this;
    }

    public Set<MatchTeam> getMatchTeams() {
        return this.matchTeams;
    }

    public void setMatchTeams(Set<MatchTeam> matchTeams) {
        if (this.matchTeams != null) {
            this.matchTeams.forEach(i -> i.setTeam(null));
        }
        if (matchTeams != null) {
            matchTeams.forEach(i -> i.setTeam(this));
        }
        this.matchTeams = matchTeams;
    }

    public Team matchTeams(Set<MatchTeam> matchTeams) {
        this.setMatchTeams(matchTeams);
        return this;
    }

    public Team addMatchTeam(MatchTeam matchTeam) {
        this.matchTeams.add(matchTeam);
        matchTeam.setTeam(this);
        return this;
    }

    public Team removeMatchTeam(MatchTeam matchTeam) {
        this.matchTeams.remove(matchTeam);
        matchTeam.setTeam(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Team)) {
            return false;
        }
        return getId() != null && getId().equals(((Team) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Team{" +
            "id=" + getId() +
            ", teamNo='" + getTeamNo() + "'" +
            ", approvalStatus='" + getApprovalStatus() + "'" +
            ", entryDate='" + getEntryDate() + "'" +
            ", memo='" + getMemo() + "'" +
            ", groupNo=" + getGroupNo() +
            ", groupSeq=" + getGroupSeq() +
            ", seedNo=" + getSeedNo() +
            "}";
    }
}
