package com.phoenixdarts.toss.service.dto;

import com.phoenixdarts.toss.domain.enumeration.MachineStatusType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.phoenixdarts.toss.domain.Machine} entity.
 */
@Schema(description = "머신")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MachineDTO implements Serializable {

    @NotNull
    @Size(max = 16)
    private String id;

    @NotNull
    private Integer machineNo;

    @NotNull
    private MachineStatusType machineStatusType;

    private MatchDTO match;

    private MachineAreaDTO machineArea;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getMachineNo() {
        return machineNo;
    }

    public void setMachineNo(Integer machineNo) {
        this.machineNo = machineNo;
    }

    public MachineStatusType getMachineStatusType() {
        return machineStatusType;
    }

    public void setMachineStatusType(MachineStatusType machineStatusType) {
        this.machineStatusType = machineStatusType;
    }

    public MatchDTO getMatch() {
        return match;
    }

    public void setMatch(MatchDTO match) {
        this.match = match;
    }

    public MachineAreaDTO getMachineArea() {
        return machineArea;
    }

    public void setMachineArea(MachineAreaDTO machineArea) {
        this.machineArea = machineArea;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MachineDTO)) {
            return false;
        }

        MachineDTO machineDTO = (MachineDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, machineDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MachineDTO{" +
            "id='" + getId() + "'" +
            ", machineNo=" + getMachineNo() +
            ", machineStatusType='" + getMachineStatusType() + "'" +
            ", match=" + getMatch() +
            ", machineArea=" + getMachineArea() +
            "}";
    }
}
