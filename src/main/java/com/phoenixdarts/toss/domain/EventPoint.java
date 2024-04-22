package com.phoenixdarts.toss.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 이벤트 포인트
 */
@Entity
@Table(name = "tb_eventpoint")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EventPoint implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Size(max = 16)
    @Id
    @GeneratedValue
    @Column(name = "id", length = 16, nullable = false, unique = true)
    private String id;

    /**
     * 순서
     */
    @NotNull
    @Column(name = "seq", nullable = false)
    private Integer seq;

    /**
     * Rating
     */
    @NotNull
    @Column(name = "rating", nullable = false)
    private Integer rating;

    /**
     * Rating 범위(최소)
     */
    @Column(name = "rating_min")
    private Double ratingMin;

    /**
     * Rating 범위(최대)
     */
    @Column(name = "rating_max")
    private Double ratingMax;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "matchFormats", "eventPoints", "teams", "tournament", "matches" }, allowSetters = true)
    private Division division;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public EventPoint id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getSeq() {
        return this.seq;
    }

    public EventPoint seq(Integer seq) {
        this.setSeq(seq);
        return this;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public Integer getRating() {
        return this.rating;
    }

    public EventPoint rating(Integer rating) {
        this.setRating(rating);
        return this;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Double getRatingMin() {
        return this.ratingMin;
    }

    public EventPoint ratingMin(Double ratingMin) {
        this.setRatingMin(ratingMin);
        return this;
    }

    public void setRatingMin(Double ratingMin) {
        this.ratingMin = ratingMin;
    }

    public Double getRatingMax() {
        return this.ratingMax;
    }

    public EventPoint ratingMax(Double ratingMax) {
        this.setRatingMax(ratingMax);
        return this;
    }

    public void setRatingMax(Double ratingMax) {
        this.ratingMax = ratingMax;
    }

    public Division getDivision() {
        return this.division;
    }

    public void setDivision(Division division) {
        this.division = division;
    }

    public EventPoint division(Division division) {
        this.setDivision(division);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EventPoint)) {
            return false;
        }
        return getId() != null && getId().equals(((EventPoint) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EventPoint{" +
            "id=" + getId() +
            ", seq=" + getSeq() +
            ", rating=" + getRating() +
            ", ratingMin=" + getRatingMin() +
            ", ratingMax=" + getRatingMax() +
            "}";
    }
}
