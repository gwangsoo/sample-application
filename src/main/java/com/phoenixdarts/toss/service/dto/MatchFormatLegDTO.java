package com.phoenixdarts.toss.service.dto;

import com.phoenixdarts.toss.domain.enumeration.FirstThrowType;
import com.phoenixdarts.toss.domain.enumeration.LegPlayMode;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.phoenixdarts.toss.domain.MatchFormatLeg} entity.
 */
@Schema(description = "매치포맷 LEG")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MatchFormatLegDTO implements Serializable {

    @NotNull
    @Size(max = 16)
    private String id;

    private Integer seq;

    @NotNull
    private FirstThrowType firstThrowType;

    private LegPlayMode playMode;

    private MatchFormatOptionDTO option;

    private MatchFormatSetDTO matchFormatSet;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public FirstThrowType getFirstThrowType() {
        return firstThrowType;
    }

    public void setFirstThrowType(FirstThrowType firstThrowType) {
        this.firstThrowType = firstThrowType;
    }

    public LegPlayMode getPlayMode() {
        return playMode;
    }

    public void setPlayMode(LegPlayMode playMode) {
        this.playMode = playMode;
    }

    public MatchFormatOptionDTO getOption() {
        return option;
    }

    public void setOption(MatchFormatOptionDTO option) {
        this.option = option;
    }

    public MatchFormatSetDTO getMatchFormatSet() {
        return matchFormatSet;
    }

    public void setMatchFormatSet(MatchFormatSetDTO matchFormatSet) {
        this.matchFormatSet = matchFormatSet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MatchFormatLegDTO)) {
            return false;
        }

        MatchFormatLegDTO matchFormatLegDTO = (MatchFormatLegDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, matchFormatLegDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MatchFormatLegDTO{" +
            "id='" + getId() + "'" +
            ", seq=" + getSeq() +
            ", firstThrowType='" + getFirstThrowType() + "'" +
            ", playMode='" + getPlayMode() + "'" +
            ", option=" + getOption() +
            ", matchFormatSet=" + getMatchFormatSet() +
            "}";
    }
}
