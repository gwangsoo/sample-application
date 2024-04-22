package com.phoenixdarts.toss.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.phoenixdarts.toss.domain.MachineArea} entity.
 */
@Schema(description = "머신 배치")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MachineAreaDTO implements Serializable {

    @NotNull
    @Size(max = 16)
    private String id;

    @NotNull
    @Size(max = 256)
    private String mame;

    @NotNull
    private Integer seq;

    private Integer numOfMachine;

    private CompetitionDTO competition;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMame() {
        return mame;
    }

    public void setMame(String mame) {
        this.mame = mame;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public Integer getNumOfMachine() {
        return numOfMachine;
    }

    public void setNumOfMachine(Integer numOfMachine) {
        this.numOfMachine = numOfMachine;
    }

    public CompetitionDTO getCompetition() {
        return competition;
    }

    public void setCompetition(CompetitionDTO competition) {
        this.competition = competition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MachineAreaDTO)) {
            return false;
        }

        MachineAreaDTO machineAreaDTO = (MachineAreaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, machineAreaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MachineAreaDTO{" +
            "id='" + getId() + "'" +
            ", mame='" + getMame() + "'" +
            ", seq=" + getSeq() +
            ", numOfMachine=" + getNumOfMachine() +
            ", competition=" + getCompetition() +
            "}";
    }
}
