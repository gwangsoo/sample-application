package com.phoenixdarts.toss.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 매치점수
 */
@Entity
@Table(name = "tb_match_score")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MatchScore implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Size(max = 16)
    @Id
    @GeneratedValue
    @Column(name = "id", length = 16, nullable = false, unique = true)
    private String id;

    /**
     * 세트순서
     */
    @Column(name = "set_no")
    private Integer setNo;

    /**
     * 레그순서
     */
    @Column(name = "lge_no")
    private Integer lgeNo;

    /**
     * 레그게임명
     */
    @Column(name = "leg_game_name")
    private String legGameName;

    /**
     * 레그 홈팀 점수
     */
    @Column(name = "home_leg_score")
    private Float homeLegScore;

    /**
     * 레그 어웨이팀 점수
     */
    @Column(name = "away_leg_score")
    private Float awayLegScore;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "matchScores", "home", "away", "tmatch", "division", "machines" }, allowSetters = true)
    private Match match;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public MatchScore id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getSetNo() {
        return this.setNo;
    }

    public MatchScore setNo(Integer setNo) {
        this.setSetNo(setNo);
        return this;
    }

    public void setSetNo(Integer setNo) {
        this.setNo = setNo;
    }

    public Integer getLgeNo() {
        return this.lgeNo;
    }

    public MatchScore lgeNo(Integer lgeNo) {
        this.setLgeNo(lgeNo);
        return this;
    }

    public void setLgeNo(Integer lgeNo) {
        this.lgeNo = lgeNo;
    }

    public String getLegGameName() {
        return this.legGameName;
    }

    public MatchScore legGameName(String legGameName) {
        this.setLegGameName(legGameName);
        return this;
    }

    public void setLegGameName(String legGameName) {
        this.legGameName = legGameName;
    }

    public Float getHomeLegScore() {
        return this.homeLegScore;
    }

    public MatchScore homeLegScore(Float homeLegScore) {
        this.setHomeLegScore(homeLegScore);
        return this;
    }

    public void setHomeLegScore(Float homeLegScore) {
        this.homeLegScore = homeLegScore;
    }

    public Float getAwayLegScore() {
        return this.awayLegScore;
    }

    public MatchScore awayLegScore(Float awayLegScore) {
        this.setAwayLegScore(awayLegScore);
        return this;
    }

    public void setAwayLegScore(Float awayLegScore) {
        this.awayLegScore = awayLegScore;
    }

    public Match getMatch() {
        return this.match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public MatchScore match(Match match) {
        this.setMatch(match);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MatchScore)) {
            return false;
        }
        return getId() != null && getId().equals(((MatchScore) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MatchScore{" +
            "id=" + getId() +
            ", setNo=" + getSetNo() +
            ", lgeNo=" + getLgeNo() +
            ", legGameName='" + getLegGameName() + "'" +
            ", homeLegScore=" + getHomeLegScore() +
            ", awayLegScore=" + getAwayLegScore() +
            "}";
    }
}
