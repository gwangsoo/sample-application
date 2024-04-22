package com.phoenixdarts.toss.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link com.phoenixdarts.toss.domain.MatchCall} entity.
 */
@Schema(description = "호출정보")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MatchCallDTO implements Serializable {

    @NotNull
    @Size(max = 16)
    private String id;

    private ZonedDateTime callDateTime;

    @Size(max = 2048)
    private String callMessage;

    private MatchTeamDTO matchTeam;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ZonedDateTime getCallDateTime() {
        return callDateTime;
    }

    public void setCallDateTime(ZonedDateTime callDateTime) {
        this.callDateTime = callDateTime;
    }

    public String getCallMessage() {
        return callMessage;
    }

    public void setCallMessage(String callMessage) {
        this.callMessage = callMessage;
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
        if (!(o instanceof MatchCallDTO)) {
            return false;
        }

        MatchCallDTO matchCallDTO = (MatchCallDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, matchCallDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MatchCallDTO{" +
            "id='" + getId() + "'" +
            ", callDateTime='" + getCallDateTime() + "'" +
            ", callMessage='" + getCallMessage() + "'" +
            ", matchTeam=" + getMatchTeam() +
            "}";
    }
}
