package com.phoenixdarts.toss.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@Table(name = "tb_reward_detail")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RewardDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Size(max = 16)
    @Id
    @GeneratedValue
    @Column(name = "id", length = 16, nullable = false, unique = true)
    private String id;

    /**
     * 지급방법이 DAY대회명 + Day + Entry Item
     */
    @Size(max = 256)
    @Column(name = "name", length = 256)
    private String name;

    /**
     * 토너먼트ID
     */
    @Column(name = "tournament_id")
    private String tournamentId;

    /**
     * 디비전ID
     */
    @Column(name = "division_id")
    private String divisionId;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "rewardDetail")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "itemImage", "rewardDetail" }, allowSetters = true)
    private Set<RewardItem> rewardItems = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "rewardDetails", "competitions" }, allowSetters = true)
    private Reward reward;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public RewardDetail id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public RewardDetail name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTournamentId() {
        return this.tournamentId;
    }

    public RewardDetail tournamentId(String tournamentId) {
        this.setTournamentId(tournamentId);
        return this;
    }

    public void setTournamentId(String tournamentId) {
        this.tournamentId = tournamentId;
    }

    public String getDivisionId() {
        return this.divisionId;
    }

    public RewardDetail divisionId(String divisionId) {
        this.setDivisionId(divisionId);
        return this;
    }

    public void setDivisionId(String divisionId) {
        this.divisionId = divisionId;
    }

    public Set<RewardItem> getRewardItems() {
        return this.rewardItems;
    }

    public void setRewardItems(Set<RewardItem> rewardItems) {
        if (this.rewardItems != null) {
            this.rewardItems.forEach(i -> i.setRewardDetail(null));
        }
        if (rewardItems != null) {
            rewardItems.forEach(i -> i.setRewardDetail(this));
        }
        this.rewardItems = rewardItems;
    }

    public RewardDetail rewardItems(Set<RewardItem> rewardItems) {
        this.setRewardItems(rewardItems);
        return this;
    }

    public RewardDetail addRewardItem(RewardItem rewardItem) {
        this.rewardItems.add(rewardItem);
        rewardItem.setRewardDetail(this);
        return this;
    }

    public RewardDetail removeRewardItem(RewardItem rewardItem) {
        this.rewardItems.remove(rewardItem);
        rewardItem.setRewardDetail(null);
        return this;
    }

    public Reward getReward() {
        return this.reward;
    }

    public void setReward(Reward reward) {
        this.reward = reward;
    }

    public RewardDetail reward(Reward reward) {
        this.setReward(reward);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RewardDetail)) {
            return false;
        }
        return getId() != null && getId().equals(((RewardDetail) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RewardDetail{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", tournamentId='" + getTournamentId() + "'" +
            ", divisionId='" + getDivisionId() + "'" +
            "}";
    }
}
