package com.phoenixdarts.toss.backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.phoenixdarts.toss.backend.domain.enumeration.FirstThrowType;
import com.phoenixdarts.toss.backend.domain.enumeration.LegPlayMode;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 매치포맷 LEG
 */
@Entity
@Table(name = "tb_matchformat_leg")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MatchFormatLeg implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Size(max = 16)
    @Id
    @GeneratedValue
    @Column(name = "id", length = 16, nullable = false, unique = true)
    private String id;

    /**
     * LEG 순서
     */
    @Column(name = "seq")
    private Integer seq;

    /**
     * 선공설정
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "first_throw_type", nullable = false)
    private FirstThrowType firstThrowType;

    /**
     * 플레이모드
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "play_mode")
    private LegPlayMode playMode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "matchFormat" }, allowSetters = true)
    private MatchFormatOption option;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "matchFormatLegs", "matchFormat" }, allowSetters = true)
    private MatchFormatSet matchFormatSet;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public MatchFormatLeg id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getSeq() {
        return this.seq;
    }

    public MatchFormatLeg seq(Integer seq) {
        this.setSeq(seq);
        return this;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public FirstThrowType getFirstThrowType() {
        return this.firstThrowType;
    }

    public MatchFormatLeg firstThrowType(FirstThrowType firstThrowType) {
        this.setFirstThrowType(firstThrowType);
        return this;
    }

    public void setFirstThrowType(FirstThrowType firstThrowType) {
        this.firstThrowType = firstThrowType;
    }

    public LegPlayMode getPlayMode() {
        return this.playMode;
    }

    public MatchFormatLeg playMode(LegPlayMode playMode) {
        this.setPlayMode(playMode);
        return this;
    }

    public void setPlayMode(LegPlayMode playMode) {
        this.playMode = playMode;
    }

    public MatchFormatOption getOption() {
        return this.option;
    }

    public void setOption(MatchFormatOption matchFormatOption) {
        this.option = matchFormatOption;
    }

    public MatchFormatLeg option(MatchFormatOption matchFormatOption) {
        this.setOption(matchFormatOption);
        return this;
    }

    public MatchFormatSet getMatchFormatSet() {
        return this.matchFormatSet;
    }

    public void setMatchFormatSet(MatchFormatSet matchFormatSet) {
        this.matchFormatSet = matchFormatSet;
    }

    public MatchFormatLeg matchFormatSet(MatchFormatSet matchFormatSet) {
        this.setMatchFormatSet(matchFormatSet);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MatchFormatLeg)) {
            return false;
        }
        return getId() != null && getId().equals(((MatchFormatLeg) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MatchFormatLeg{" +
            "id=" + getId() +
            ", seq=" + getSeq() +
            ", firstThrowType='" + getFirstThrowType() + "'" +
            ", playMode='" + getPlayMode() + "'" +
            "}";
    }
}
