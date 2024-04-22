package com.phoenixdarts.toss.service.dto;

import com.phoenixdarts.toss.domain.enumeration.FirstThrowType;
import com.phoenixdarts.toss.domain.enumeration.MatchFormatType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.phoenixdarts.toss.domain.MatchFormat} entity.
 */
@Schema(description = "매치포맷")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MatchFormatDTO implements Serializable {

    @NotNull
    @Size(max = 16)
    private String id;

    @NotNull
    @Size(max = 256)
    private String name;

    @NotNull
    @Size(max = 256)
    private String description;

    @NotNull
    private MatchFormatType matchFormatType;

    private FirstThrowType firstSet;

    private FirstThrowType middleSet;

    private FirstThrowType lastSet;

    private DivisionDTO division;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public MatchFormatType getMatchFormatType() {
        return matchFormatType;
    }

    public void setMatchFormatType(MatchFormatType matchFormatType) {
        this.matchFormatType = matchFormatType;
    }

    public FirstThrowType getFirstSet() {
        return firstSet;
    }

    public void setFirstSet(FirstThrowType firstSet) {
        this.firstSet = firstSet;
    }

    public FirstThrowType getMiddleSet() {
        return middleSet;
    }

    public void setMiddleSet(FirstThrowType middleSet) {
        this.middleSet = middleSet;
    }

    public FirstThrowType getLastSet() {
        return lastSet;
    }

    public void setLastSet(FirstThrowType lastSet) {
        this.lastSet = lastSet;
    }

    public DivisionDTO getDivision() {
        return division;
    }

    public void setDivision(DivisionDTO division) {
        this.division = division;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MatchFormatDTO)) {
            return false;
        }

        MatchFormatDTO matchFormatDTO = (MatchFormatDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, matchFormatDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MatchFormatDTO{" +
            "id='" + getId() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", matchFormatType='" + getMatchFormatType() + "'" +
            ", firstSet='" + getFirstSet() + "'" +
            ", middleSet='" + getMiddleSet() + "'" +
            ", lastSet='" + getLastSet() + "'" +
            ", division=" + getDivision() +
            "}";
    }
}
