package com.phoenixdarts.toss.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.phoenixdarts.toss.domain.enumeration.AffiliatedType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 소속정보 (Shop,동호회)
 */
@Entity
@Table(name = "tb_affiliated_info")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AffiliatedInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Size(max = 16)
    @Id
    @GeneratedValue
    @Column(name = "id", length = 16, nullable = false, unique = true)
    private String id;

    /**
     * 소속구분
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "affiliated_type", nullable = false)
    private AffiliatedType affiliatedType;

    /**
     * seq (shop, club)
     */
    @Size(max = 20)
    @Column(name = "seq", length = 20)
    private String seq;

    /**
     * 명칭
     */
    @NotNull
    @Size(max = 256)
    @Column(name = "name", length = 256, nullable = false)
    private String name;

    /**
     * 주소
     */
    @Size(max = 500)
    @Column(name = "address", length = 500)
    private String address;

    /**
     * 전화번호
     */
    @Size(max = 30)
    @Column(name = "tel_no", length = 30)
    private String telNo;

    /**
     * 홈페이지
     */
    @Size(max = 256)
    @Column(name = "homepage_url", length = 256)
    private String homepageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "affiliatedInfos" }, allowSetters = true)
    private FileInfo fileInfo;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "affiliatedInfo")
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

    public AffiliatedInfo id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public AffiliatedType getAffiliatedType() {
        return this.affiliatedType;
    }

    public AffiliatedInfo affiliatedType(AffiliatedType affiliatedType) {
        this.setAffiliatedType(affiliatedType);
        return this;
    }

    public void setAffiliatedType(AffiliatedType affiliatedType) {
        this.affiliatedType = affiliatedType;
    }

    public String getSeq() {
        return this.seq;
    }

    public AffiliatedInfo seq(String seq) {
        this.setSeq(seq);
        return this;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

    public String getName() {
        return this.name;
    }

    public AffiliatedInfo name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return this.address;
    }

    public AffiliatedInfo address(String address) {
        this.setAddress(address);
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelNo() {
        return this.telNo;
    }

    public AffiliatedInfo telNo(String telNo) {
        this.setTelNo(telNo);
        return this;
    }

    public void setTelNo(String telNo) {
        this.telNo = telNo;
    }

    public String getHomepageUrl() {
        return this.homepageUrl;
    }

    public AffiliatedInfo homepageUrl(String homepageUrl) {
        this.setHomepageUrl(homepageUrl);
        return this;
    }

    public void setHomepageUrl(String homepageUrl) {
        this.homepageUrl = homepageUrl;
    }

    public FileInfo getFileInfo() {
        return this.fileInfo;
    }

    public void setFileInfo(FileInfo fileInfo) {
        this.fileInfo = fileInfo;
    }

    public AffiliatedInfo fileInfo(FileInfo fileInfo) {
        this.setFileInfo(fileInfo);
        return this;
    }

    public Set<Team> getTeams() {
        return this.teams;
    }

    public void setTeams(Set<Team> teams) {
        if (this.teams != null) {
            this.teams.forEach(i -> i.setAffiliatedInfo(null));
        }
        if (teams != null) {
            teams.forEach(i -> i.setAffiliatedInfo(this));
        }
        this.teams = teams;
    }

    public AffiliatedInfo teams(Set<Team> teams) {
        this.setTeams(teams);
        return this;
    }

    public AffiliatedInfo addTeam(Team team) {
        this.teams.add(team);
        team.setAffiliatedInfo(this);
        return this;
    }

    public AffiliatedInfo removeTeam(Team team) {
        this.teams.remove(team);
        team.setAffiliatedInfo(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AffiliatedInfo)) {
            return false;
        }
        return getId() != null && getId().equals(((AffiliatedInfo) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AffiliatedInfo{" +
            "id=" + getId() +
            ", affiliatedType='" + getAffiliatedType() + "'" +
            ", seq='" + getSeq() + "'" +
            ", name='" + getName() + "'" +
            ", address='" + getAddress() + "'" +
            ", telNo='" + getTelNo() + "'" +
            ", homepageUrl='" + getHomepageUrl() + "'" +
            "}";
    }
}
