package com.example.bfi.domain;

import com.example.bfi.domain.enumeration.EvseStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * 충전기
 */
@Document(collection = "evse")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Evse implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Size(max = 36)
    @Field("uid")
    private String uid;

    @Size(max = 48)
    @Field("evse_id")
    private String evseId;

    @Field("status")
    private EvseStatus status;

    @Size(max = 256)
    @Field("directions")
    private String directions;

    @Field("last_updated")
    private ZonedDateTime lastUpdated;

    @DBRef
    @Field("connector")
    @JsonIgnoreProperties(value = { "evse" }, allowSetters = true)
    private Set<Connector> connectors = new HashSet<>();

    @DBRef
    @Field("location")
    @JsonIgnoreProperties(value = { "evses" }, allowSetters = true)
    private Location location;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Evse id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return this.uid;
    }

    public Evse uid(String uid) {
        this.setUid(uid);
        return this;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEvseId() {
        return this.evseId;
    }

    public Evse evseId(String evseId) {
        this.setEvseId(evseId);
        return this;
    }

    public void setEvseId(String evseId) {
        this.evseId = evseId;
    }

    public EvseStatus getStatus() {
        return this.status;
    }

    public Evse status(EvseStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(EvseStatus status) {
        this.status = status;
    }

    public String getDirections() {
        return this.directions;
    }

    public Evse directions(String directions) {
        this.setDirections(directions);
        return this;
    }

    public void setDirections(String directions) {
        this.directions = directions;
    }

    public ZonedDateTime getLastUpdated() {
        return this.lastUpdated;
    }

    public Evse lastUpdated(ZonedDateTime lastUpdated) {
        this.setLastUpdated(lastUpdated);
        return this;
    }

    public void setLastUpdated(ZonedDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Set<Connector> getConnectors() {
        return this.connectors;
    }

    public void setConnectors(Set<Connector> connectors) {
        if (this.connectors != null) {
            this.connectors.forEach(i -> i.setEvse(null));
        }
        if (connectors != null) {
            connectors.forEach(i -> i.setEvse(this));
        }
        this.connectors = connectors;
    }

    public Evse connectors(Set<Connector> connectors) {
        this.setConnectors(connectors);
        return this;
    }

    public Evse addConnector(Connector connector) {
        this.connectors.add(connector);
        connector.setEvse(this);
        return this;
    }

    public Evse removeConnector(Connector connector) {
        this.connectors.remove(connector);
        connector.setEvse(null);
        return this;
    }

    public Location getLocation() {
        return this.location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Evse location(Location location) {
        this.setLocation(location);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Evse)) {
            return false;
        }
        return getId() != null && getId().equals(((Evse) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Evse{" +
            "id=" + getId() +
            ", uid='" + getUid() + "'" +
            ", evseId='" + getEvseId() + "'" +
            ", status='" + getStatus() + "'" +
            ", directions='" + getDirections() + "'" +
            ", lastUpdated='" + getLastUpdated() + "'" +
            "}";
    }
}
