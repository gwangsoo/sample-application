package com.phoenixdarts.toss.backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.phoenixdarts.toss.backend.domain.enumeration.FirstThrowType;
import com.phoenixdarts.toss.backend.domain.enumeration.MatchFormatType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 매치포맷
 */
@Entity
@Table(name = "tb_matchformat")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MatchFormat implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Size(max = 16)
    @Id
    @GeneratedValue
    @Column(name = "id", length = 16, nullable = false, unique = true)
    private String id;

    /**
     * 매치포맷명
     */
    @NotNull
    @Size(max = 256)
    @Column(name = "name", length = 256, nullable = false)
    private String name;

    /**
     * 매치포맷 설명
     */
    @NotNull
    @Size(max = 256)
    @Column(name = "description", length = 256, nullable = false)
    private String description;

    /**
     * 매치포맷유형
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "match_format_type", nullable = false)
    private MatchFormatType matchFormatType;

    /**
     * Set 선공 설정
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "first_set")
    private FirstThrowType firstSet;

    @Enumerated(EnumType.STRING)
    @Column(name = "middle_set")
    private FirstThrowType middleSet;

    @Enumerated(EnumType.STRING)
    @Column(name = "last_set")
    private FirstThrowType lastSet;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "matchFormat")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "game", "matchFormat" }, allowSetters = true)
    private Set<MatchFormatGame> matchFormatGames = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "matchFormat")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "matchFormat" }, allowSetters = true)
    private Set<MatchFormatOption> matchFormatOptions = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "matchFormat")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "matchFormatLegs", "matchFormat" }, allowSetters = true)
    private Set<MatchFormatSet> matchFormatSets = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "matchFormats", "eventPoints", "teams", "tournament", "matches" }, allowSetters = true)
    private Division division;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public MatchFormat id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public MatchFormat name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public MatchFormat description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MatchFormatType getMatchFormatType() {
        return this.matchFormatType;
    }

    public MatchFormat matchFormatType(MatchFormatType matchFormatType) {
        this.setMatchFormatType(matchFormatType);
        return this;
    }

    public void setMatchFormatType(MatchFormatType matchFormatType) {
        this.matchFormatType = matchFormatType;
    }

    public FirstThrowType getFirstSet() {
        return this.firstSet;
    }

    public MatchFormat firstSet(FirstThrowType firstSet) {
        this.setFirstSet(firstSet);
        return this;
    }

    public void setFirstSet(FirstThrowType firstSet) {
        this.firstSet = firstSet;
    }

    public FirstThrowType getMiddleSet() {
        return this.middleSet;
    }

    public MatchFormat middleSet(FirstThrowType middleSet) {
        this.setMiddleSet(middleSet);
        return this;
    }

    public void setMiddleSet(FirstThrowType middleSet) {
        this.middleSet = middleSet;
    }

    public FirstThrowType getLastSet() {
        return this.lastSet;
    }

    public MatchFormat lastSet(FirstThrowType lastSet) {
        this.setLastSet(lastSet);
        return this;
    }

    public void setLastSet(FirstThrowType lastSet) {
        this.lastSet = lastSet;
    }

    public Set<MatchFormatGame> getMatchFormatGames() {
        return this.matchFormatGames;
    }

    public void setMatchFormatGames(Set<MatchFormatGame> matchFormatGames) {
        if (this.matchFormatGames != null) {
            this.matchFormatGames.forEach(i -> i.setMatchFormat(null));
        }
        if (matchFormatGames != null) {
            matchFormatGames.forEach(i -> i.setMatchFormat(this));
        }
        this.matchFormatGames = matchFormatGames;
    }

    public MatchFormat matchFormatGames(Set<MatchFormatGame> matchFormatGames) {
        this.setMatchFormatGames(matchFormatGames);
        return this;
    }

    public MatchFormat addMatchFormatGame(MatchFormatGame matchFormatGame) {
        this.matchFormatGames.add(matchFormatGame);
        matchFormatGame.setMatchFormat(this);
        return this;
    }

    public MatchFormat removeMatchFormatGame(MatchFormatGame matchFormatGame) {
        this.matchFormatGames.remove(matchFormatGame);
        matchFormatGame.setMatchFormat(null);
        return this;
    }

    public Set<MatchFormatOption> getMatchFormatOptions() {
        return this.matchFormatOptions;
    }

    public void setMatchFormatOptions(Set<MatchFormatOption> matchFormatOptions) {
        if (this.matchFormatOptions != null) {
            this.matchFormatOptions.forEach(i -> i.setMatchFormat(null));
        }
        if (matchFormatOptions != null) {
            matchFormatOptions.forEach(i -> i.setMatchFormat(this));
        }
        this.matchFormatOptions = matchFormatOptions;
    }

    public MatchFormat matchFormatOptions(Set<MatchFormatOption> matchFormatOptions) {
        this.setMatchFormatOptions(matchFormatOptions);
        return this;
    }

    public MatchFormat addMatchFormatOption(MatchFormatOption matchFormatOption) {
        this.matchFormatOptions.add(matchFormatOption);
        matchFormatOption.setMatchFormat(this);
        return this;
    }

    public MatchFormat removeMatchFormatOption(MatchFormatOption matchFormatOption) {
        this.matchFormatOptions.remove(matchFormatOption);
        matchFormatOption.setMatchFormat(null);
        return this;
    }

    public Set<MatchFormatSet> getMatchFormatSets() {
        return this.matchFormatSets;
    }

    public void setMatchFormatSets(Set<MatchFormatSet> matchFormatSets) {
        if (this.matchFormatSets != null) {
            this.matchFormatSets.forEach(i -> i.setMatchFormat(null));
        }
        if (matchFormatSets != null) {
            matchFormatSets.forEach(i -> i.setMatchFormat(this));
        }
        this.matchFormatSets = matchFormatSets;
    }

    public MatchFormat matchFormatSets(Set<MatchFormatSet> matchFormatSets) {
        this.setMatchFormatSets(matchFormatSets);
        return this;
    }

    public MatchFormat addMatchFormatSet(MatchFormatSet matchFormatSet) {
        this.matchFormatSets.add(matchFormatSet);
        matchFormatSet.setMatchFormat(this);
        return this;
    }

    public MatchFormat removeMatchFormatSet(MatchFormatSet matchFormatSet) {
        this.matchFormatSets.remove(matchFormatSet);
        matchFormatSet.setMatchFormat(null);
        return this;
    }

    public Division getDivision() {
        return this.division;
    }

    public void setDivision(Division division) {
        this.division = division;
    }

    public MatchFormat division(Division division) {
        this.setDivision(division);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MatchFormat)) {
            return false;
        }
        return getId() != null && getId().equals(((MatchFormat) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MatchFormat{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", matchFormatType='" + getMatchFormatType() + "'" +
            ", firstSet='" + getFirstSet() + "'" +
            ", middleSet='" + getMiddleSet() + "'" +
            ", lastSet='" + getLastSet() + "'" +
            "}";
    }
}
