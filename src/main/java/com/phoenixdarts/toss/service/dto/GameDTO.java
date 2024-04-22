package com.phoenixdarts.toss.service.dto;

import com.phoenixdarts.toss.domain.enumeration.GameCategoryType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.phoenixdarts.toss.domain.Game} entity.
 */
@Schema(description = "게임")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class GameDTO implements Serializable {

    @NotNull
    @Size(max = 16)
    private String id;

    @NotNull
    private GameCategoryType gameCategoryType;

    @NotNull
    @Size(max = 256)
    private String name;

    @NotNull
    @Size(max = 256)
    private String description;

    private Integer roundNumDefault;

    private Integer roundNumMin;

    private Integer roundNumMax;

    private Integer roundNumUnlimit;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getRoundNumDefault() {
        return roundNumDefault;
    }

    public void setRoundNumDefault(Integer roundNumDefault) {
        this.roundNumDefault = roundNumDefault;
    }

    public Integer getRoundNumMin() {
        return roundNumMin;
    }

    public void setRoundNumMin(Integer roundNumMin) {
        this.roundNumMin = roundNumMin;
    }

    public Integer getRoundNumMax() {
        return roundNumMax;
    }

    public void setRoundNumMax(Integer roundNumMax) {
        this.roundNumMax = roundNumMax;
    }

    public Integer getRoundNumUnlimit() {
        return roundNumUnlimit;
    }

    public void setRoundNumUnlimit(Integer roundNumUnlimit) {
        this.roundNumUnlimit = roundNumUnlimit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GameDTO)) {
            return false;
        }

        GameDTO gameDTO = (GameDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, gameDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GameDTO{" +
            "id='" + getId() + "'" +
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
