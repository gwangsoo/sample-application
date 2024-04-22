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
 * 국가
 */
@Entity
@Table(name = "tb_country")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Country implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 국가코드
     */
    @NotNull
    @Size(max = 2)
    @Id
    @GeneratedValue
    @Column(name = "id", length = 2, nullable = false, unique = true)
    private String id;

    @NotNull
    @Size(max = 50)
    @Column(name = "name", length = 50, nullable = false, unique = true)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "affiliatedInfos" }, allowSetters = true)
    private FileInfo image;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "country")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "tournaments", "machineAreas", "competitionImage", "reward", "country", "operator", "entryFee" },
        allowSetters = true
    )
    private Set<Competition> competitions = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "country")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "country", "operators" }, allowSetters = true)
    private Set<Region> regions = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Country id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Country name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FileInfo getImage() {
        return this.image;
    }

    public void setImage(FileInfo fileInfo) {
        this.image = fileInfo;
    }

    public Country image(FileInfo fileInfo) {
        this.setImage(fileInfo);
        return this;
    }

    public Set<Competition> getCompetitions() {
        return this.competitions;
    }

    public void setCompetitions(Set<Competition> competitions) {
        if (this.competitions != null) {
            this.competitions.forEach(i -> i.setCountry(null));
        }
        if (competitions != null) {
            competitions.forEach(i -> i.setCountry(this));
        }
        this.competitions = competitions;
    }

    public Country competitions(Set<Competition> competitions) {
        this.setCompetitions(competitions);
        return this;
    }

    public Country addCompetition(Competition competition) {
        this.competitions.add(competition);
        competition.setCountry(this);
        return this;
    }

    public Country removeCompetition(Competition competition) {
        this.competitions.remove(competition);
        competition.setCountry(null);
        return this;
    }

    public Set<Region> getRegions() {
        return this.regions;
    }

    public void setRegions(Set<Region> regions) {
        if (this.regions != null) {
            this.regions.forEach(i -> i.setCountry(null));
        }
        if (regions != null) {
            regions.forEach(i -> i.setCountry(this));
        }
        this.regions = regions;
    }

    public Country regions(Set<Region> regions) {
        this.setRegions(regions);
        return this;
    }

    public Country addRegion(Region region) {
        this.regions.add(region);
        region.setCountry(this);
        return this;
    }

    public Country removeRegion(Region region) {
        this.regions.remove(region);
        region.setCountry(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Country)) {
            return false;
        }
        return getId() != null && getId().equals(((Country) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Country{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
