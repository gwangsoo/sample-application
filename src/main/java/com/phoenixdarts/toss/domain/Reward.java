package com.phoenixdarts.toss.backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.phoenixdarts.toss.backend.domain.enumeration.MachineKindType;
import com.phoenixdarts.toss.backend.domain.enumeration.RewardMethodSubType;
import com.phoenixdarts.toss.backend.domain.enumeration.RewardMethodType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 리워드
 */
@Entity
@Table(name = "tb_reward")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Reward implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Size(max = 16)
    @Id
    @GeneratedValue
    @Column(name = "id", length = 16, nullable = false, unique = true)
    private String id;

    /**
     * 지급방법1 (리워드설정)
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "reward_method_type", nullable = false)
    private RewardMethodType rewardMethodType;

    /**
     * 지급방법2 (리워드설정)
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "reward_method_sub_type", nullable = false)
    private RewardMethodSubType rewardMethodSubType;

    /**
     * 아이템카테고리 수
     */
    @NotNull
    @Column(name = "reward_category_num", nullable = false)
    private Integer rewardCategoryNum;

    /**
     * 머신종류
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "machine_kind_type")
    private MachineKindType machineKindType;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "reward")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "rewardItems", "reward" }, allowSetters = true)
    private Set<RewardDetail> rewardDetails = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "reward")
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

    public Reward id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public RewardMethodType getRewardMethodType() {
        return this.rewardMethodType;
    }

    public Reward rewardMethodType(RewardMethodType rewardMethodType) {
        this.setRewardMethodType(rewardMethodType);
        return this;
    }

    public void setRewardMethodType(RewardMethodType rewardMethodType) {
        this.rewardMethodType = rewardMethodType;
    }

    public RewardMethodSubType getRewardMethodSubType() {
        return this.rewardMethodSubType;
    }

    public Reward rewardMethodSubType(RewardMethodSubType rewardMethodSubType) {
        this.setRewardMethodSubType(rewardMethodSubType);
        return this;
    }

    public void setRewardMethodSubType(RewardMethodSubType rewardMethodSubType) {
        this.rewardMethodSubType = rewardMethodSubType;
    }

    public Integer getRewardCategoryNum() {
        return this.rewardCategoryNum;
    }

    public Reward rewardCategoryNum(Integer rewardCategoryNum) {
        this.setRewardCategoryNum(rewardCategoryNum);
        return this;
    }

    public void setRewardCategoryNum(Integer rewardCategoryNum) {
        this.rewardCategoryNum = rewardCategoryNum;
    }

    public MachineKindType getMachineKindType() {
        return this.machineKindType;
    }

    public Reward machineKindType(MachineKindType machineKindType) {
        this.setMachineKindType(machineKindType);
        return this;
    }

    public void setMachineKindType(MachineKindType machineKindType) {
        this.machineKindType = machineKindType;
    }

    public Set<RewardDetail> getRewardDetails() {
        return this.rewardDetails;
    }

    public void setRewardDetails(Set<RewardDetail> rewardDetails) {
        if (this.rewardDetails != null) {
            this.rewardDetails.forEach(i -> i.setReward(null));
        }
        if (rewardDetails != null) {
            rewardDetails.forEach(i -> i.setReward(this));
        }
        this.rewardDetails = rewardDetails;
    }

    public Reward rewardDetails(Set<RewardDetail> rewardDetails) {
        this.setRewardDetails(rewardDetails);
        return this;
    }

    public Reward addRewardDetail(RewardDetail rewardDetail) {
        this.rewardDetails.add(rewardDetail);
        rewardDetail.setReward(this);
        return this;
    }

    public Reward removeRewardDetail(RewardDetail rewardDetail) {
        this.rewardDetails.remove(rewardDetail);
        rewardDetail.setReward(null);
        return this;
    }

    public Set<Competition> getCompetitions() {
        return this.competitions;
    }

    public void setCompetitions(Set<Competition> competitions) {
        if (this.competitions != null) {
            this.competitions.forEach(i -> i.setReward(null));
        }
        if (competitions != null) {
            competitions.forEach(i -> i.setReward(this));
        }
        this.competitions = competitions;
    }

    public Reward competitions(Set<Competition> competitions) {
        this.setCompetitions(competitions);
        return this;
    }

    public Reward addCompetition(Competition competition) {
        this.competitions.add(competition);
        competition.setReward(this);
        return this;
    }

    public Reward removeCompetition(Competition competition) {
        this.competitions.remove(competition);
        competition.setReward(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Reward)) {
            return false;
        }
        return getId() != null && getId().equals(((Reward) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Reward{" +
            "id=" + getId() +
            ", rewardMethodType='" + getRewardMethodType() + "'" +
            ", rewardMethodSubType='" + getRewardMethodSubType() + "'" +
            ", rewardCategoryNum=" + getRewardCategoryNum() +
            ", machineKindType='" + getMachineKindType() + "'" +
            "}";
    }
}
