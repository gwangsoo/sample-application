package com.example.bfi.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link com.example.bfi.domain.Connector} entity.
 */
@Schema(description = "컨넥터")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ConnectorDTO implements Serializable {

    @NotNull
    @Size(max = 36)
    private String id;

    private Integer maxVoltage;

    private Integer maxAmperage;

    private Integer maxElectricPower;

    @Size(max = 36)
    private String tariffIds;

    private ZonedDateTime lastUpdated;

    private EvseDTO evse;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getMaxVoltage() {
        return maxVoltage;
    }

    public void setMaxVoltage(Integer maxVoltage) {
        this.maxVoltage = maxVoltage;
    }

    public Integer getMaxAmperage() {
        return maxAmperage;
    }

    public void setMaxAmperage(Integer maxAmperage) {
        this.maxAmperage = maxAmperage;
    }

    public Integer getMaxElectricPower() {
        return maxElectricPower;
    }

    public void setMaxElectricPower(Integer maxElectricPower) {
        this.maxElectricPower = maxElectricPower;
    }

    public String getTariffIds() {
        return tariffIds;
    }

    public void setTariffIds(String tariffIds) {
        this.tariffIds = tariffIds;
    }

    public ZonedDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(ZonedDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public EvseDTO getEvse() {
        return evse;
    }

    public void setEvse(EvseDTO evse) {
        this.evse = evse;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ConnectorDTO)) {
            return false;
        }

        ConnectorDTO connectorDTO = (ConnectorDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, connectorDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ConnectorDTO{" +
            "id='" + getId() + "'" +
            ", maxVoltage=" + getMaxVoltage() +
            ", maxAmperage=" + getMaxAmperage() +
            ", maxElectricPower=" + getMaxElectricPower() +
            ", tariffIds='" + getTariffIds() + "'" +
            ", lastUpdated='" + getLastUpdated() + "'" +
            ", evse=" + getEvse() +
            "}";
    }
}
