package com.phoenixdarts.toss.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.phoenixdarts.toss.domain.enumeration.PaymentMethodType;
import com.phoenixdarts.toss.domain.enumeration.PaymentStatusType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 결제 정보
 */
@Entity
@Table(name = "tb_payment_info")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PaymentInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Size(max = 16)
    @Id
    @GeneratedValue
    @Column(name = "id", length = 16, nullable = false, unique = true)
    private String id;

    /**
     * 참가자 결제 상태
     */
    @Size(max = 50)
    @Column(name = "order_number", length = 50)
    private String orderNumber;

    /**
     * 결제 일자
     */
    @Column(name = "payment_completed_date")
    private Instant paymentCompletedDate;

    /**
     * 결제 수단
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private PaymentStatusType status;

    /**
     * 결제 수단
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method_type")
    private PaymentMethodType paymentMethodType;

    /**
     * PG 결제 TID
     */
    @Column(name = "pg_tid")
    private Integer pgTID;

    /**
     * PG 결제 상태
     */
    @Min(value = 1)
    @Column(name = "pg_status")
    private Integer pgStatus;

    /**
     * PG 결제 상세내용
     */
    @Size(max = 255)
    @Column(name = "pg_detail", length = 255)
    private String pgDetail;

    /**
     * 결제자 정보
     */
    @Size(max = 50)
    @Column(name = "payer", length = 50)
    private String payer;

    @Size(max = 20)
    @Column(name = "payer_phone", length = 20)
    private String payerPhone;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "paymentInfo")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "entries", "captain", "tteam", "affiliatedInfo", "paymentInfo", "division", "matchTeams" },
        allowSetters = true
    )
    private Set<Team> teams = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public PaymentInfo id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return this.orderNumber;
    }

    public PaymentInfo orderNumber(String orderNumber) {
        this.setOrderNumber(orderNumber);
        return this;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Instant getPaymentCompletedDate() {
        return this.paymentCompletedDate;
    }

    public PaymentInfo paymentCompletedDate(Instant paymentCompletedDate) {
        this.setPaymentCompletedDate(paymentCompletedDate);
        return this;
    }

    public void setPaymentCompletedDate(Instant paymentCompletedDate) {
        this.paymentCompletedDate = paymentCompletedDate;
    }

    public PaymentStatusType getStatus() {
        return this.status;
    }

    public PaymentInfo status(PaymentStatusType status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(PaymentStatusType status) {
        this.status = status;
    }

    public PaymentMethodType getPaymentMethodType() {
        return this.paymentMethodType;
    }

    public PaymentInfo paymentMethodType(PaymentMethodType paymentMethodType) {
        this.setPaymentMethodType(paymentMethodType);
        return this;
    }

    public void setPaymentMethodType(PaymentMethodType paymentMethodType) {
        this.paymentMethodType = paymentMethodType;
    }

    public Integer getPgTID() {
        return this.pgTID;
    }

    public PaymentInfo pgTID(Integer pgTID) {
        this.setPgTID(pgTID);
        return this;
    }

    public void setPgTID(Integer pgTID) {
        this.pgTID = pgTID;
    }

    public Integer getPgStatus() {
        return this.pgStatus;
    }

    public PaymentInfo pgStatus(Integer pgStatus) {
        this.setPgStatus(pgStatus);
        return this;
    }

    public void setPgStatus(Integer pgStatus) {
        this.pgStatus = pgStatus;
    }

    public String getPgDetail() {
        return this.pgDetail;
    }

    public PaymentInfo pgDetail(String pgDetail) {
        this.setPgDetail(pgDetail);
        return this;
    }

    public void setPgDetail(String pgDetail) {
        this.pgDetail = pgDetail;
    }

    public String getPayer() {
        return this.payer;
    }

    public PaymentInfo payer(String payer) {
        this.setPayer(payer);
        return this;
    }

    public void setPayer(String payer) {
        this.payer = payer;
    }

    public String getPayerPhone() {
        return this.payerPhone;
    }

    public PaymentInfo payerPhone(String payerPhone) {
        this.setPayerPhone(payerPhone);
        return this;
    }

    public void setPayerPhone(String payerPhone) {
        this.payerPhone = payerPhone;
    }

    public Set<Team> getTeams() {
        return this.teams;
    }

    public void setTeams(Set<Team> teams) {
        if (this.teams != null) {
            this.teams.forEach(i -> i.setPaymentInfo(null));
        }
        if (teams != null) {
            teams.forEach(i -> i.setPaymentInfo(this));
        }
        this.teams = teams;
    }

    public PaymentInfo teams(Set<Team> teams) {
        this.setTeams(teams);
        return this;
    }

    public PaymentInfo addTeam(Team team) {
        this.teams.add(team);
        team.setPaymentInfo(this);
        return this;
    }

    public PaymentInfo removeTeam(Team team) {
        this.teams.remove(team);
        team.setPaymentInfo(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PaymentInfo)) {
            return false;
        }
        return getId() != null && getId().equals(((PaymentInfo) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PaymentInfo{" +
            "id=" + getId() +
            ", orderNumber='" + getOrderNumber() + "'" +
            ", paymentCompletedDate='" + getPaymentCompletedDate() + "'" +
            ", status='" + getStatus() + "'" +
            ", paymentMethodType='" + getPaymentMethodType() + "'" +
            ", pgTID=" + getPgTID() +
            ", pgStatus=" + getPgStatus() +
            ", pgDetail='" + getPgDetail() + "'" +
            ", payer='" + getPayer() + "'" +
            ", payerPhone='" + getPayerPhone() + "'" +
            "}";
    }
}
