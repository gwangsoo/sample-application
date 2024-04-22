package com.phoenixdarts.toss.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.phoenixdarts.toss.domain.enumeration.EntryFeeSubType;
import com.phoenixdarts.toss.domain.enumeration.EntryFeeType;
import com.phoenixdarts.toss.domain.enumeration.PaymentMethodType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 참가비
 */
@Entity
@Table(name = "tb_entry_fee")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EntryFee implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Size(max = 16)
    @Id
    @GeneratedValue
    @Column(name = "id", length = 16, nullable = false, unique = true)
    private String id;

    /**
     * 참가비 구분
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "entry_fee_type", nullable = false)
    private EntryFeeType entryFeeType;

    /**
     * 참가비 상세 구분
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "entry_fee_sub_type")
    private EntryFeeSubType entryFeeSubType;

    /**
     * 참가비 납부 방법
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method_type")
    private PaymentMethodType paymentMethodType;

    /**
     * 일별/토너먼트
     */
    @NotNull
    @Max(value = 1)
    @Column(name = "schedule_number", nullable = false)
    private Integer scheduleNumber;

    /**
     * 유료 시 참가비
     */
    @Column(name = "fee")
    private Integer fee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "entryFees" }, allowSetters = true)
    private Currency currency;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "entryFee")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "divisions", "entryFee", "competition" }, allowSetters = true)
    private Set<Tournament> tournaments = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "entryFee")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "tournaments", "machineAreas", "competitionImage", "reward", "country", "operator", "entryFee" },
        allowSetters = true
    )
    private Set<Competition> competitions = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public EntryFee id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public EntryFeeType getEntryFeeType() {
        return this.entryFeeType;
    }

    public EntryFee entryFeeType(EntryFeeType entryFeeType) {
        this.setEntryFeeType(entryFeeType);
        return this;
    }

    public void setEntryFeeType(EntryFeeType entryFeeType) {
        this.entryFeeType = entryFeeType;
    }

    public EntryFeeSubType getEntryFeeSubType() {
        return this.entryFeeSubType;
    }

    public EntryFee entryFeeSubType(EntryFeeSubType entryFeeSubType) {
        this.setEntryFeeSubType(entryFeeSubType);
        return this;
    }

    public void setEntryFeeSubType(EntryFeeSubType entryFeeSubType) {
        this.entryFeeSubType = entryFeeSubType;
    }

    public PaymentMethodType getPaymentMethodType() {
        return this.paymentMethodType;
    }

    public EntryFee paymentMethodType(PaymentMethodType paymentMethodType) {
        this.setPaymentMethodType(paymentMethodType);
        return this;
    }

    public void setPaymentMethodType(PaymentMethodType paymentMethodType) {
        this.paymentMethodType = paymentMethodType;
    }

    public Integer getScheduleNumber() {
        return this.scheduleNumber;
    }

    public EntryFee scheduleNumber(Integer scheduleNumber) {
        this.setScheduleNumber(scheduleNumber);
        return this;
    }

    public void setScheduleNumber(Integer scheduleNumber) {
        this.scheduleNumber = scheduleNumber;
    }

    public Integer getFee() {
        return this.fee;
    }

    public EntryFee fee(Integer fee) {
        this.setFee(fee);
        return this;
    }

    public void setFee(Integer fee) {
        this.fee = fee;
    }

    public Currency getCurrency() {
        return this.currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public EntryFee currency(Currency currency) {
        this.setCurrency(currency);
        return this;
    }

    public Set<Tournament> getTournaments() {
        return this.tournaments;
    }

    public void setTournaments(Set<Tournament> tournaments) {
        if (this.tournaments != null) {
            this.tournaments.forEach(i -> i.setEntryFee(null));
        }
        if (tournaments != null) {
            tournaments.forEach(i -> i.setEntryFee(this));
        }
        this.tournaments = tournaments;
    }

    public EntryFee tournaments(Set<Tournament> tournaments) {
        this.setTournaments(tournaments);
        return this;
    }

    public EntryFee addTournament(Tournament tournament) {
        this.tournaments.add(tournament);
        tournament.setEntryFee(this);
        return this;
    }

    public EntryFee removeTournament(Tournament tournament) {
        this.tournaments.remove(tournament);
        tournament.setEntryFee(null);
        return this;
    }

    public Set<Competition> getCompetitions() {
        return this.competitions;
    }

    public void setCompetitions(Set<Competition> competitions) {
        if (this.competitions != null) {
            this.competitions.forEach(i -> i.setEntryFee(null));
        }
        if (competitions != null) {
            competitions.forEach(i -> i.setEntryFee(this));
        }
        this.competitions = competitions;
    }

    public EntryFee competitions(Set<Competition> competitions) {
        this.setCompetitions(competitions);
        return this;
    }

    public EntryFee addCompetition(Competition competition) {
        this.competitions.add(competition);
        competition.setEntryFee(this);
        return this;
    }

    public EntryFee removeCompetition(Competition competition) {
        this.competitions.remove(competition);
        competition.setEntryFee(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EntryFee)) {
            return false;
        }
        return getId() != null && getId().equals(((EntryFee) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EntryFee{" +
            "id=" + getId() +
            ", entryFeeType='" + getEntryFeeType() + "'" +
            ", entryFeeSubType='" + getEntryFeeSubType() + "'" +
            ", paymentMethodType='" + getPaymentMethodType() + "'" +
            ", scheduleNumber=" + getScheduleNumber() +
            ", fee=" + getFee() +
            "}";
    }
}
