package com.phoenixdarts.toss.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.phoenixdarts.toss.domain.MatchFormatSet} entity.
 */
@Schema(description = "매치포맷 SET")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MatchFormatSetDTO implements Serializable {

    @NotNull
    @Size(max = 16)
    private String id;

    @NotNull
    @Min(value = 1)
    private Integer point;

    private MatchFormatDTO matchFormat;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
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
        if (!(o instanceof MatchFormatSetDTO)) {
            return false;
        }

        MatchFormatSetDTO matchFormatSetDTO = (MatchFormatSetDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, matchFormatSetDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MatchFormatSetDTO{" +
            "id='" + getId() + "'" +
            ", point=" + getPoint() +
            ", matchFormat=" + getMatchFormat() +
            "}";
    }
}
