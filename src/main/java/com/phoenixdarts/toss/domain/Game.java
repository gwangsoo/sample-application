package com.phoenixdarts.toss.backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.phoenixdarts.toss.backend.domain.enumeration.GameCategoryType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 게임
 */
@Entity
@Table(name = "tb_game")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Game implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Size(max = 16)
    @Id
    @GeneratedValue
    @Column(name = "id", length = 16, nullable = false, unique = true)
    private String id;

    /**
     * 게임카테고리
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "game_category_type", nullable = false)
    private GameCategoryType gameCategoryType;

    /**
     * 게임명
     */
    @NotNull
    @Size(max = 256)
    @Column(name = "name", length = 256, nullable = false)
    private String name;

    /**
     * 게임설명
     */
    @NotNull
    @Size(max = 256)
    @Column(name = "description", length = 256, nullable = false)
    private String description;

    /**
     * 라운드수 기본값
     */
    @Column(name = "round_num_default")
    private Integer roundNumDefault;

    /**
     * 라운드수 범위(최소)
     */
    @Column(name = "round_num_min")
    private Integer roundNumMin;

    /**
     * 라운드수 범위(최대)
     */
    @Column(name = "round_num_max")
    private Integer roundNumMax;

    /**
     * 라운드수 무제한
     */
    @Column(name = "round_num_unlimit")
    private Integer roundNumUnlimit;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "game")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "game", "matchFormat" }, allowSetters = true)
    private Set<MatchFormatGame> matchFormatGames = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Game id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public GameCategoryType getGameCategoryType() {
        return this.gameCategoryType;
    }

    public Game gameCategoryType(GameCategoryType gameCategoryType) {
        this.setGameCategoryType(gameCategoryType);
        return this;
    }

    public void setGameCategoryType(GameCategoryType gameCategoryType) {
        this.gameCategoryType = gameCategoryType;
    }

    public String getName() {
        return this.name;
    }

    public Game name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public Game description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getRoundNumDefault() {
        return this.roundNumDefault;
    }

    public Game roundNumDefault(Integer roundNumDefault) {
        this.setRoundNumDefault(roundNumDefault);
        return this;
    }

    public void setRoundNumDefault(Integer roundNumDefault) {
        this.roundNumDefault = roundNumDefault;
    }

    public Integer getRoundNumMin() {
        return this.roundNumMin;
    }

    public Game roundNumMin(Integer roundNumMin) {
        this.setRoundNumMin(roundNumMin);
        return this;
    }

    public void setRoundNumMin(Integer roundNumMin) {
        this.roundNumMin = roundNumMin;
    }

    public Integer getRoundNumMax() {
        return this.roundNumMax;
    }

    public Game roundNumMax(Integer roundNumMax) {
        this.setRoundNumMax(roundNumMax);
        return this;
    }

    public void setRoundNumMax(Integer roundNumMax) {
        this.roundNumMax = roundNumMax;
    }

    public Integer getRoundNumUnlimit() {
        return this.roundNumUnlimit;
    }

    public Game roundNumUnlimit(Integer roundNumUnlimit) {
        this.setRoundNumUnlimit(roundNumUnlimit);
        return this;
    }

    public void setRoundNumUnlimit(Integer roundNumUnlimit) {
        this.roundNumUnlimit = roundNumUnlimit;
    }

    public Set<MatchFormatGame> getMatchFormatGames() {
        return this.matchFormatGames;
    }

    public void setMatchFormatGames(Set<MatchFormatGame> matchFormatGames) {
        if (this.matchFormatGames != null) {
            this.matchFormatGames.forEach(i -> i.setGame(null));
        }
        if (matchFormatGames != null) {
            matchFormatGames.forEach(i -> i.setGame(this));
        }
        this.matchFormatGames = matchFormatGames;
    }

    public Game matchFormatGames(Set<MatchFormatGame> matchFormatGames) {
        this.setMatchFormatGames(matchFormatGames);
        return this;
    }

    public Game addMatchFormatGame(MatchFormatGame matchFormatGame) {
        this.matchFormatGames.add(matchFormatGame);
        matchFormatGame.setGame(this);
        return this;
    }

    public Game removeMatchFormatGame(MatchFormatGame matchFormatGame) {
        this.matchFormatGames.remove(matchFormatGame);
        matchFormatGame.setGame(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Game)) {
            return false;
        }
        return getId() != null && getId().equals(((Game) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Game{" +
            "id=" + getId() +
            ", gameCategoryType='" + getGameCategoryType() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", roundNumDefault=" + getRoundNumDefault() +
            ", roundNumMin=" + getRoundNumMin() +
            ", roundNumMax=" + getRoundNumMax() +
            ", roundNumUnlimit=" + getRoundNumUnlimit() +
            "}";
    }
}
