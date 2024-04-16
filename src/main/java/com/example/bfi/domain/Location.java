package com.example.bfi.domain;

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
 * 충전소
 */
@Document(collection = "location")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Location implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Size(max = 36)
    @Id
    private String id;

    @Size(max = 2)
    @Field("country_code")
    private String countryCode;

    @Size(max = 36)
    @Field("party_id")
    private String partyId;

    @Field("publish")
    private Boolean publish;

    @Size(max = 255)
    @Field("name")
    private String name;

    @Size(max = 45)
    @Field("address")
    private String address;

    @Size(max = 45)
    @Field("city")
    private String city;

    @Size(max = 10)
    @Field("postal_code")
    private String postalCode;

    @Size(max = 20)
    @Field("state")
    private String state;

    @Size(max = 3)
    @Field("country")
    private String country;

    @Size(max = 255)
    @Field("time_zone")
    private String timeZone;

    @Field("charging_when_closed")
    private Boolean chargingWhenClosed;

    @Field("last_updated")
    private ZonedDateTime lastUpdated;

    @DBRef
    @Field("evse")
    @JsonIgnoreProperties(value = { "connectors", "location" }, allowSetters = true)
    private Set<Evse> evses = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Location id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCountryCode() {
        return this.countryCode;
    }

    public Location countryCode(String countryCode) {
        this.setCountryCode(countryCode);
        return this;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getPartyId() {
        return this.partyId;
    }

    public Location partyId(String partyId) {
        this.setPartyId(partyId);
        return this;
    }

    public void setPartyId(String partyId) {
        this.partyId = partyId;
    }

    public Boolean getPublish() {
        return this.publish;
    }

    public Location publish(Boolean publish) {
        this.setPublish(publish);
        return this;
    }

    public void setPublish(Boolean publish) {
        this.publish = publish;
    }

    public String getName() {
        return this.name;
    }

    public Location name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return this.address;
    }

    public Location address(String address) {
        this.setAddress(address);
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return this.city;
    }

    public Location city(String city) {
        this.setCity(city);
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return this.postalCode;
    }

    public Location postalCode(String postalCode) {
        this.setPostalCode(postalCode);
        return this;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getState() {
        return this.state;
    }

    public Location state(String state) {
        this.setState(state);
        return this;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return this.country;
    }

    public Location country(String country) {
        this.setCountry(country);
        return this;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getTimeZone() {
        return this.timeZone;
    }

    public Location timeZone(String timeZone) {
        this.setTimeZone(timeZone);
        return this;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public Boolean getChargingWhenClosed() {
        return this.chargingWhenClosed;
    }

    public Location chargingWhenClosed(Boolean chargingWhenClosed) {
        this.setChargingWhenClosed(chargingWhenClosed);
        return this;
    }

    public void setChargingWhenClosed(Boolean chargingWhenClosed) {
        this.chargingWhenClosed = chargingWhenClosed;
    }

    public ZonedDateTime getLastUpdated() {
        return this.lastUpdated;
    }

    public Location lastUpdated(ZonedDateTime lastUpdated) {
        this.setLastUpdated(lastUpdated);
        return this;
    }

    public void setLastUpdated(ZonedDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Set<Evse> getEvses() {
        return this.evses;
    }

    public void setEvses(Set<Evse> evses) {
        if (this.evses != null) {
            this.evses.forEach(i -> i.setLocation(null));
        }
        if (evses != null) {
            evses.forEach(i -> i.setLocation(this));
        }
        this.evses = evses;
    }

    public Location evses(Set<Evse> evses) {
        this.setEvses(evses);
        return this;
    }

    public Location addEvse(Evse evse) {
        this.evses.add(evse);
        evse.setLocation(this);
        return this;
    }

    public Location removeEvse(Evse evse) {
        this.evses.remove(evse);
        evse.setLocation(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Location)) {
            return false;
        }
        return getId() != null && getId().equals(((Location) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Location{" +
            "id=" + getId() +
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
