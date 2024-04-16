package com.example.bfi.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link com.example.bfi.domain.Location} entity.
 */
@Schema(description = "충전소")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LocationDTO implements Serializable {

    @NotNull
    @Size(max = 36)
    private String id;

    @Size(max = 2)
    private String countryCode;

    @Size(max = 36)
    private String partyId;

    private Boolean publish;

    @Size(max = 255)
    private String name;

    @Size(max = 45)
    private String address;

    @Size(max = 45)
    private String city;

    @Size(max = 10)
    private String postalCode;

    @Size(max = 20)
    private String state;

    @Size(max = 3)
    private String country;

    @Size(max = 255)
    private String timeZone;

    private Boolean chargingWhenClosed;

    private ZonedDateTime lastUpdated;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getPartyId() {
        return partyId;
    }

    public void setPartyId(String partyId) {
        this.partyId = partyId;
    }

    public Boolean getPublish() {
        return publish;
    }

    public void setPublish(Boolean publish) {
        this.publish = publish;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public Boolean getChargingWhenClosed() {
        return chargingWhenClosed;
    }

    public void setChargingWhenClosed(Boolean chargingWhenClosed) {
        this.chargingWhenClosed = chargingWhenClosed;
    }

    public ZonedDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(ZonedDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LocationDTO)) {
            return false;
        }

        LocationDTO locationDTO = (LocationDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, locationDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LocationDTO{" +
            "id='" + getId() + "'" +
            ", countryCode='" + getCountryCode() + "'" +
            ", partyId='" + getPartyId() + "'" +
            ", publish='" + getPublish() + "'" +
            ", name='" + getName() + "'" +
            ", address='" + getAddress() + "'" +
            ", city='" + getCity() + "'" +
            ", postalCode='" + getPostalCode() + "'" +
            ", state='" + getState() + "'" +
            ", country='" + getCountry() + "'" +
            ", timeZone='" + getTimeZone() + "'" +
            ", chargingWhenClosed='" + getChargingWhenClosed() + "'" +
            ", lastUpdated='" + getLastUpdated() + "'" +
            "}";
    }
}
