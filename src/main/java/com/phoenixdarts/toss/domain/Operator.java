package com.phoenixdarts.toss.backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 관리자
 */
@Entity
@Table(name = "tb_operator")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Operator implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Size(max = 16)
    @Id
    @GeneratedValue
    @Column(name = "id", length = 16, nullable = false, unique = true)
    private String id;

    /**
     * 계정
     */
    @NotNull
    @Size(max = 50)
    @Column(name = "user_id", length = 50, nullable = false, unique = true)
    private String userId;

    /**
     * 이름
     */
    @NotNull
    @Size(max = 50)
    @Column(name = "user_name", length = 50, nullable = false, unique = true)
    private String userName;

    /**
     * 모바일
     */
    @Size(max = 20)
    @Column(name = "phone", length = 20)
    private String phone;

    /**
     * 이메일
     */
    @Size(max = 128)
    @Column(name = "email", length = 128)
    private String email;

    /**
     * 주소
     */
    @Size(max = 256)
    @Column(name = "address", length = 256)
    private String address;

    /**
     * 승인상태
     */
    @NotNull
    @Column(name = "approval_status", nullable = false)
    private Boolean approvalStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "roles", "operators" }, allowSetters = true)
    private OperatorRole operatorRole;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "country", "operators" }, allowSetters = true)
    private Region region;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "operator")
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

    public Operator id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return this.userId;
    }

    public Operator userId(String userId) {
        this.setUserId(userId);
        return this;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return this.userName;
    }

    public Operator userName(String userName) {
        this.setUserName(userName);
        return this;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return this.phone;
    }

    public Operator phone(String phone) {
        this.setPhone(phone);
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return this.email;
    }

    public Operator email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return this.address;
    }

    public Operator address(String address) {
        this.setAddress(address);
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Boolean getApprovalStatus() {
        return this.approvalStatus;
    }

    public Operator approvalStatus(Boolean approvalStatus) {
        this.setApprovalStatus(approvalStatus);
        return this;
    }

    public void setApprovalStatus(Boolean approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public OperatorRole getOperatorRole() {
        return this.operatorRole;
    }

    public void setOperatorRole(OperatorRole operatorRole) {
        this.operatorRole = operatorRole;
    }

    public Operator operatorRole(OperatorRole operatorRole) {
        this.setOperatorRole(operatorRole);
        return this;
    }

    public Region getRegion() {
        return this.region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public Operator region(Region region) {
        this.setRegion(region);
        return this;
    }

    public Set<Competition> getCompetitions() {
        return this.competitions;
    }

    public void setCompetitions(Set<Competition> competitions) {
        if (this.competitions != null) {
            this.competitions.forEach(i -> i.setOperator(null));
        }
        if (competitions != null) {
            competitions.forEach(i -> i.setOperator(this));
        }
        this.competitions = competitions;
    }

    public Operator competitions(Set<Competition> competitions) {
        this.setCompetitions(competitions);
        return this;
    }

    public Operator addCompetition(Competition competition) {
        this.competitions.add(competition);
        competition.setOperator(this);
        return this;
    }

    public Operator removeCompetition(Competition competition) {
        this.competitions.remove(competition);
        competition.setOperator(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Operator)) {
            return false;
        }
        return getId() != null && getId().equals(((Operator) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Operator{" +
            "id=" + getId() +
            ", userId='" + getUserId() + "'" +
            ", userName='" + getUserName() + "'" +
            ", phone='" + getPhone() + "'" +
            ", email='" + getEmail() + "'" +
            ", address='" + getAddress() + "'" +
            ", approvalStatus='" + getApprovalStatus() + "'" +
            "}";
    }
}
