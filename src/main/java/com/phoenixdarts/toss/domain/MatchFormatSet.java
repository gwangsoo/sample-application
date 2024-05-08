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
 * 매치포맷 SET
 */
@Entity
@Table(name = "tb_matchformat_Set")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MatchFormatSet implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Size(max = 16)
    @Id
    @GeneratedValue
    @Column(name = "id", length = 16, nullable = false, unique = true)
    private String id;

    /**
     * 승리 시에 획득하는 Point
     */
    @NotNull
    @Min(value = 1)
    @Column(name = "point", nullable = false)
    private Integer point;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "matchFormatSet")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "option", "matchFormatSet" }, allowSetters = true)
    private Set<MatchFormatLeg> matchFormatLegs = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "matchFormatGames", "matchFormatOptions", "matchFormatSets", "division" }, allowSetters = true)
    private MatchFormat matchFormat;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public MatchFormatSet id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getPoint() {
        return this.point;
    }

    public MatchFormatSet point(Integer point) {
        this.setPoint(point);
        return this;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public Set<MatchFormatLeg> getMatchFormatLegs() {
        return this.matchFormatLegs;
    }

    public void setMatchFormatLegs(Set<MatchFormatLeg> matchFormatLegs) {
        if (this.matchFormatLegs != null) {
            this.matchFormatLegs.forEach(i -> i.setMatchFormatSet(null));
        }
        if (matchFormatLegs != null) {
            matchFormatLegs.forEach(i -> i.setMatchFormatSet(this));
        }
        this.matchFormatLegs = matchFormatLegs;
    }

    public MatchFormatSet matchFormatLegs(Set<MatchFormatLeg> matchFormatLegs) {
        this.setMatchFormatLegs(matchFormatLegs);
        return this;
    }

    public MatchFormatSet addMatchFormatLeg(MatchFormatLeg matchFormatLeg) {
        this.matchFormatLegs.add(matchFormatLeg);
        matchFormatLeg.setMatchFormatSet(this);
        return this;
    }

    public MatchFormatSet removeMatchFormatLeg(MatchFormatLeg matchFormatLeg) {
        this.matchFormatLegs.remove(matchFormatLeg);
        matchFormatLeg.setMatchFormatSet(null);
        return this;
    }

    public MatchFormat getMatchFormat() {
        return this.matchFormat;
    }

    public void setMatchFormat(MatchFormat matchFormat) {
        this.matchFormat = matchFormat;
    }

    public MatchFormatSet matchFormat(MatchFormat matchFormat) {
        this.setMatchFormat(matchFormat);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MatchFormatSet)) {
            return false;
        }
        return getId() != null && getId().equals(((MatchFormatSet) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MatchFormatSet{" +
            "id=" + getId() +
            ", point=" + getPoint() +
            "}";
    }
}
