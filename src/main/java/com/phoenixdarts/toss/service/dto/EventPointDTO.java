package com.phoenixdarts.toss.backend.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.phoenixdarts.toss.backend.domain.EventPoint} entity.
 */
@Schema(description = "이벤트 포인트")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EventPointDTO implements Serializable {

    @NotNull
    @Size(max = 16)
    private String id;

    @NotNull
    private Integer seq;

    @NotNull
    private Integer rating;

    private Double ratingMin;

    private Double ratingMax;

    private DivisionDTO division;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Double getRatingMin() {
        return ratingMin;
    }

    public void setRatingMin(Double ratingMin) {
        this.ratingMin = ratingMin;
    }

    public Double getRatingMax() {
        return ratingMax;
    }

    public void setRatingMax(Double ratingMax) {
        this.ratingMax = ratingMax;
    }

    public DivisionDTO getDivision() {
        return division;
    }

    public void setDivision(DivisionDTO division) {
        this.division = division;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EventPointDTO)) {
            return false;
        }

        EventPointDTO eventPointDTO = (EventPointDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, eventPointDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EventPointDTO{" +
            "id='" + getId() + "'" +
            ", seq=" + getSeq() +
            ", rating=" + getRating() +
            ", ratingMin=" + getRatingMin() +
            ", ratingMax=" + getRatingMax() +
            ", division=" + getDivision() +
            "}";
    }
}
