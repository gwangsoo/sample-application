package com.phoenixdarts.toss.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * RewardItem
 */
@Entity
@Table(name = "tb_rewad_item")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RewardItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Size(max = 16)
    @Id
    @GeneratedValue
    @Column(name = "id", length = 16, nullable = false, unique = true)
    private String id;

    /**
     * 아이템명
     */
    @NotNull
    @Size(max = 50)
    @Column(name = "name", length = 50, nullable = false, unique = true)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "affiliatedInfos" }, allowSetters = true)
    private FileInfo itemImage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "rewardItems", "reward" }, allowSetters = true)
    private RewardDetail rewardDetail;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public RewardItem id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public RewardItem name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FileInfo getItemImage() {
        return this.itemImage;
    }

    public void setItemImage(FileInfo fileInfo) {
        this.itemImage = fileInfo;
    }

    public RewardItem itemImage(FileInfo fileInfo) {
        this.setItemImage(fileInfo);
        return this;
    }

    public RewardDetail getRewardDetail() {
        return this.rewardDetail;
    }

    public void setRewardDetail(RewardDetail rewardDetail) {
        this.rewardDetail = rewardDetail;
    }

    public RewardItem rewardDetail(RewardDetail rewardDetail) {
        this.setRewardDetail(rewardDetail);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RewardItem)) {
            return false;
        }
        return getId() != null && getId().equals(((RewardItem) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RewardItem{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
