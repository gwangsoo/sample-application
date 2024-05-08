package com.phoenixdarts.toss.backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.phoenixdarts.toss.backend.domain.enumeration.GameCategoryType;
import com.phoenixdarts.toss.backend.domain.enumeration.GameType;
import com.phoenixdarts.toss.backend.domain.enumeration.MachineCreditType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 매치포맷 게임
 */
@Entity
@Table(name = "tb_matchformat_game")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MatchFormatGame implements Serializable {

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
     * 게임
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "game_type", nullable = false)
    private GameType gameType;

    /**
     * 라운드수
     */
    @NotNull
    @Column(name = "round_num", nullable = false)
    private Integer roundNum;

    /**
     * Credit(별도 게임비용)
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "machine_credit_type", nullable = false)
    private MachineCreditType machineCreditType;

    /**
     * Choice 게임 포함 여부
     */
    @Column(name = "including_choice_game")
    private Boolean includingChoiceGame;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "matchFormatGames" }, allowSetters = true)
    private Game game;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "matchFormatGames", "matchFormatOptions", "matchFormatSets", "division" }, allowSetters = true)
    private MatchFormat matchFormat;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public MatchFormatGame id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public GameCategoryType getGameCategoryType() {
        return this.gameCategoryType;
    }

    public MatchFormatGame gameCategoryType(GameCategoryType gameCategoryType) {
        this.setGameCategoryType(gameCategoryType);
        return this;
    }

    public void setGameCategoryType(GameCategoryType gameCategoryType) {
        this.gameCategoryType = gameCategoryType;
    }

    public GameType getGameType() {
        return this.gameType;
    }

    public MatchFormatGame gameType(GameType gameType) {
        this.setGameType(gameType);
        return this;
    }

    public void setGameType(GameType gameType) {
        this.gameType = gameType;
    }

    public Integer getRoundNum() {
        return this.roundNum;
    }

    public MatchFormatGame roundNum(Integer roundNum) {
        this.setRoundNum(roundNum);
        return this;
    }

    public void setRoundNum(Integer roundNum) {
        this.roundNum = roundNum;
    }

    public MachineCreditType getMachineCreditType() {
        return this.machineCreditType;
    }

    public MatchFormatGame machineCreditType(MachineCreditType machineCreditType) {
        this.setMachineCreditType(machineCreditType);
        return this;
    }

    public void setMachineCreditType(MachineCreditType machineCreditType) {
        this.machineCreditType = machineCreditType;
    }

    public Boolean getIncludingChoiceGame() {
        return this.includingChoiceGame;
    }

    public MatchFormatGame includingChoiceGame(Boolean includingChoiceGame) {
        this.setIncludingChoiceGame(includingChoiceGame);
        return this;
    }

    public void setIncludingChoiceGame(Boolean includingChoiceGame) {
        this.includingChoiceGame = includingChoiceGame;
    }

    public Game getGame() {
        return this.game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public MatchFormatGame game(Game game) {
        this.setGame(game);
        return this;
    }

    public MatchFormat getMatchFormat() {
        return this.matchFormat;
    }

    public void setMatchFormat(MatchFormat matchFormat) {
        this.matchFormat = matchFormat;
    }

    public MatchFormatGame matchFormat(MatchFormat matchFormat) {
        this.setMatchFormat(matchFormat);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MatchFormatGame)) {
            return false;
        }
        return getId() != null && getId().equals(((MatchFormatGame) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MatchFormatGame{" +
            "id=" + getId() +
            ", gameCategoryType='" + getGameCategoryType() + "'" +
            ", gameType='" + getGameType() + "'" +
            ", roundNum=" + getRoundNum() +
            ", machineCreditType='" + getMachineCreditType() + "'" +
            ", includingChoiceGame='" + getIncludingChoiceGame() + "'" +
            "}";
    }
}
