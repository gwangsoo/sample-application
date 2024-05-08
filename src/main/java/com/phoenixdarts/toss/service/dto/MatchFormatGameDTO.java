package com.phoenixdarts.toss.backend.service.dto;

import com.phoenixdarts.toss.backend.domain.enumeration.GameCategoryType;
import com.phoenixdarts.toss.backend.domain.enumeration.GameType;
import com.phoenixdarts.toss.backend.domain.enumeration.MachineCreditType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.phoenixdarts.toss.backend.domain.MatchFormatGame} entity.
 */
@Schema(description = "매치포맷 게임")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MatchFormatGameDTO implements Serializable {

    @NotNull
    @Size(max = 16)
    private String id;

    @NotNull
    private GameCategoryType gameCategoryType;

    @NotNull
    private GameType gameType;

    @NotNull
    private Integer roundNum;

    @NotNull
    private MachineCreditType machineCreditType;

    private Boolean includingChoiceGame;

    private GameDTO game;

    private MatchFormatDTO matchFormat;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public GameCategoryType getGameCategoryType() {
        return gameCategoryType;
    }

    public void setGameCategoryType(GameCategoryType gameCategoryType) {
        this.gameCategoryType = gameCategoryType;
    }

    public GameType getGameType() {
        return gameType;
    }

    public void setGameType(GameType gameType) {
        this.gameType = gameType;
    }

    public Integer getRoundNum() {
        return roundNum;
    }

    public void setRoundNum(Integer roundNum) {
        this.roundNum = roundNum;
    }

    public MachineCreditType getMachineCreditType() {
        return machineCreditType;
    }

    public void setMachineCreditType(MachineCreditType machineCreditType) {
        this.machineCreditType = machineCreditType;
    }

    public Boolean getIncludingChoiceGame() {
        return includingChoiceGame;
    }

    public void setIncludingChoiceGame(Boolean includingChoiceGame) {
        this.includingChoiceGame = includingChoiceGame;
    }

    public GameDTO getGame() {
        return game;
    }

    public void setGame(GameDTO game) {
        this.game = game;
    }

    public MatchFormatDTO getMatchFormat() {
        return matchFormat;
    }

    public void setMatchFormat(MatchFormatDTO matchFormat) {
        this.matchFormat = matchFormat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MatchFormatGameDTO)) {
            return false;
        }

        MatchFormatGameDTO matchFormatGameDTO = (MatchFormatGameDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, matchFormatGameDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MatchFormatGameDTO{" +
            "id='" + getId() + "'" +
            ", gameCategoryType='" + getGameCategoryType() + "'" +
            ", gameType='" + getGameType() + "'" +
            ", roundNum=" + getRoundNum() +
            ", machineCreditType='" + getMachineCreditType() + "'" +
            ", includingChoiceGame='" + getIncludingChoiceGame() + "'" +
            ", game=" + getGame() +
            ", matchFormat=" + getMatchFormat() +
            "}";
    }
}
