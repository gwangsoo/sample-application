package com.phoenixdarts.toss.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 호출정보
 */
@Entity
@Table(name = "tb_match_call")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MatchCall implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Size(max = 16)
    @Id
    @GeneratedValue
    @Column(name = "id", length = 16, nullable = false, unique = true)
    private String id;

    /**
     * 호출시간
     */
    @Column(name = "call_date_time")
    private ZonedDateTime callDateTime;

    /**
     * 호출내용
     */
    @Size(max = 2048)
    @Column(name = "call_message", length = 2048)
    private String callMessage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "matchAttendances", "matchCalls", "team" }, allowSetters = true)
    private MatchTeam matchTeam;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public MatchCall id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ZonedDateTime getCallDateTime() {
        return this.callDateTime;
    }

    public MatchCall callDateTime(ZonedDateTime callDateTime) {
        this.setCallDateTime(callDateTime);
        return this;
    }

    public void setCallDateTime(ZonedDateTime callDateTime) {
        this.callDateTime = callDateTime;
    }

    public String getCallMessage() {
        return this.callMessage;
    }

    public MatchCall callMessage(String callMessage) {
        this.setCallMessage(callMessage);
        return this;
    }

    public void setCallMessage(String callMessage) {
        this.callMessage = callMessage;
    }

    public MatchTeam getMatchTeam() {
        return this.matchTeam;
    }

    public void setMatchTeam(MatchTeam matchTeam) {
        this.matchTeam = matchTeam;
    }

    public MatchCall matchTeam(MatchTeam matchTeam) {
        this.setMatchTeam(matchTeam);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MatchCall)) {
            return false;
        }
        return getId() != null && getId().equals(((MatchCall) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MatchCall{" +
            "id=" + getId() +
            ", callDateTime='" + getCallDateTime() + "'" +
            ", callMessage='" + getCallMessage() + "'" +
            "}";
    }
}
