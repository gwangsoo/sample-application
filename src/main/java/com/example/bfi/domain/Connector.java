package com.example.bfi.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * 컨넥터
 */
@Document(collection = "connector")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Connector implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Size(max = 36)
    @Id
    private String id;

    @Field("max_voltage")
    private Integer maxVoltage;

    @Field("max_amperage")
    private Integer maxAmperage;

    @Field("max_electric_power")
    private Integer maxElectricPower;

    @Size(max = 36)
    @Field("tariff_ids")
    private String tariffIds;

    @Field("last_updated")
    private ZonedDateTime lastUpdated;

    @DBRef
    @Field("evse")
    @JsonIgnoreProperties(value = { "connectors", "location" }, allowSetters = true)
    private Evse evse;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Connector id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getMaxVoltage() {
        return this.maxVoltage;
    }

    public Connector maxVoltage(Integer maxVoltage) {
        this.setMaxVoltage(maxVoltage);
        return this;
    }

    public void setMaxVoltage(Integer maxVoltage) {
        this.maxVoltage = maxVoltage;
    }

    public Integer getMaxAmperage() {
        return this.maxAmperage;
    }

    public Connector maxAmperage(Integer maxAmperage) {
        this.setMaxAmperage(maxAmperage);
        return this;
    }

    public void setMaxAmperage(Integer maxAmperage) {
        this.maxAmperage = maxAmperage;
    }

    public Integer getMaxElectricPower() {
        return this.maxElectricPower;
    }

    public Connector maxElectricPower(Integer maxElectricPower) {
        this.setMaxElectricPower(maxElectricPower);
        return this;
    }

    public void setMaxElectricPower(Integer maxElectricPower) {
        this.maxElectricPower = maxElectricPower;
    }

    public String getTariffIds() {
        return this.tariffIds;
    }

    public Connector tariffIds(String tariffIds) {
        this.setTariffIds(tariffIds);
        return this;
    }

    public void setTariffIds(String tariffIds) {
        this.tariffIds = tariffIds;
    }

    public ZonedDateTime getLastUpdated() {
        return this.lastUpdated;
    }

    public Connector lastUpdated(ZonedDateTime lastUpdated) {
        this.setLastUpdated(lastUpdated);
        return this;
    }

    public void setLastUpdated(ZonedDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Evse getEvse() {
        return this.evse;
    }

    public void setEvse(Evse evse) {
        this.evse = evse;
    }

    public Connector evse(Evse evse) {
        this.setEvse(evse);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Connector)) {
            return false;
        }
        return getId() != null && getId().equals(((Connector) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Connector{" +
            "id=" + getId() +
            ", maxVoltage=" + getMaxVoltage() +
            ", maxAmperage=" + getMaxAmperage() +
            ", maxElectricPower=" + getMaxElectricPower() +
            ", tariffIds='" + getTariffIds() + "'" +
            ", lastUpdated='" + getLastUpdated() + "'" +
            "}";
    }
}
