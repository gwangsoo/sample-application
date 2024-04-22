package com.phoenixdarts.toss.service.dto;

import com.phoenixdarts.toss.domain.enumeration.AttendanceStatusType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link com.phoenixdarts.toss.domain.MatchAttendance} entity.
 */
@Schema(description = "매치 출석")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MatchAttendanceDTO implements Serializable {

    @NotNull
    @Size(max = 16)
    private String id;

    @NotNull
    private AttendanceStatusType attendanceStatusType;

    private ZonedDateTime attendanceDateTime;

    private EntryDTO entry;

    private MatchTeamDTO matchTeam;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public AttendanceStatusType getAttendanceStatusType() {
        return attendanceStatusType;
    }

    public void setAttendanceStatusType(AttendanceStatusType attendanceStatusType) {
        this.attendanceStatusType = attendanceStatusType;
    }

    public ZonedDateTime getAttendanceDateTime() {
        return attendanceDateTime;
    }

    public void setAttendanceDateTime(ZonedDateTime attendanceDateTime) {
        this.attendanceDateTime = attendanceDateTime;
    }

    public EntryDTO getEntry() {
        return entry;
    }

    public void setEntry(EntryDTO entry) {
        this.entry = entry;
    }

    public MatchTeamDTO getMatchTeam() {
        return matchTeam;
    }

    public void setMatchTeam(MatchTeamDTO matchTeam) {
        this.matchTeam = matchTeam;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MatchAttendanceDTO)) {
            return false;
        }

        MatchAttendanceDTO matchAttendanceDTO = (MatchAttendanceDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, matchAttendanceDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MatchAttendanceDTO{" +
            "id='" + getId() + "'" +
            ", attendanceStatusType='" + getAttendanceStatusType() + "'" +
            ", attendanceDateTime='" + getAttendanceDateTime() + "'" +
            ", entry=" + getEntry() +
            ", matchTeam=" + getMatchTeam() +
            "}";
    }
}
