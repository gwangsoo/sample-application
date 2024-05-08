package com.phoenixdarts.toss.backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.phoenixdarts.toss.backend.domain.enumeration.AttendanceStatusType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 매치 출석
 */
@Entity
@Table(name = "tb_match_attendance")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MatchAttendance implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Size(max = 16)
    @Id
    @GeneratedValue
    @Column(name = "id", length = 16, nullable = false, unique = true)
    private String id;

    /**
     * 출석 상태
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "attendance_status_type", nullable = false)
    private AttendanceStatusType attendanceStatusType;

    /**
     * 출석시간
     */
    @Column(name = "attendance_date_time")
    private ZonedDateTime attendanceDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "team", "matchAttendances" }, allowSetters = true)
    private Entry entry;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "matchAttendances", "matchCalls", "team" }, allowSetters = true)
    private MatchTeam matchTeam;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public MatchAttendance id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public AttendanceStatusType getAttendanceStatusType() {
        return this.attendanceStatusType;
    }

    public MatchAttendance attendanceStatusType(AttendanceStatusType attendanceStatusType) {
        this.setAttendanceStatusType(attendanceStatusType);
        return this;
    }

    public void setAttendanceStatusType(AttendanceStatusType attendanceStatusType) {
        this.attendanceStatusType = attendanceStatusType;
    }

    public ZonedDateTime getAttendanceDateTime() {
        return this.attendanceDateTime;
    }

    public MatchAttendance attendanceDateTime(ZonedDateTime attendanceDateTime) {
        this.setAttendanceDateTime(attendanceDateTime);
        return this;
    }

    public void setAttendanceDateTime(ZonedDateTime attendanceDateTime) {
        this.attendanceDateTime = attendanceDateTime;
    }

    public Entry getEntry() {
        return this.entry;
    }

    public void setEntry(Entry entry) {
        this.entry = entry;
    }

    public MatchAttendance entry(Entry entry) {
        this.setEntry(entry);
        return this;
    }

    public MatchTeam getMatchTeam() {
        return this.matchTeam;
    }

    public void setMatchTeam(MatchTeam matchTeam) {
        this.matchTeam = matchTeam;
    }

    public MatchAttendance matchTeam(MatchTeam matchTeam) {
        this.setMatchTeam(matchTeam);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MatchAttendance)) {
            return false;
        }
        return getId() != null && getId().equals(((MatchAttendance) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MatchAttendance{" +
            "id=" + getId() +
            ", attendanceStatusType='" + getAttendanceStatusType() + "'" +
            ", attendanceDateTime='" + getAttendanceDateTime() + "'" +
            "}";
    }
}
