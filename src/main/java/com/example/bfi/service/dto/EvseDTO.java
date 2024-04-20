package com.example.bfi.service.dto;

import com.example.bfi.domain.enumeration.EvseStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link com.example.bfi.domain.Evse} entity.
 */
@Schema(description = "충전기")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EvseDTO implements Serializable {

    private String id;

    @NotNull
    @Size(max = 36)
    private String uid;

    @Size(max = 48)
    private String evseId;

    private EvseStatus status;

    @Size(max = 256)
    private String directions;

    private ZonedDateTime lastUpdated;

    private LocationDTO location;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEvseId() {
        return evseId;
    }

    public void setEvseId(String evseId) {
        this.evseId = evseId;
    }

    public EvseStatus getStatus() {
        return status;
    }

    public void setStatus(EvseStatus status) {
        this.status = status;
    }

    public String getDirections() {
        return directions;
    }

    public void setDirections(String directions) {
        this.directions = directions;
    }

    public ZonedDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(ZonedDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public LocationDTO getLocation() {
        return location;
    }

    public void setLocation(LocationDTO location) {
        this.location = location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EvseDTO)) {
            return false;
        }

        EvseDTO evseDTO = (EvseDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, evseDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EvseDTO{" +
            "id='" + getId() + "'" +
            ", uid='" + getUid() + "'" +
            ", evseId='" + getEvseId() + "'" +
            ", status='" + getStatus() + "'" +
            ", directions='" + getDirections() + "'" +
            ", lastUpdated='" + getLastUpdated() + "'" +
            ", location=" + getLocation() +
            "}";
    }
}
