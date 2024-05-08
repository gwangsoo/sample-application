package com.phoenixdarts.toss.backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 지역정보
 */
@Entity
@Table(name = "tb_region")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Region implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 지역코드
     */
    @NotNull
    @Size(max = 6)
    @Id
    @GeneratedValue
    @Column(name = "id", length = 6, nullable = false, unique = true)
    private String id;

    @NotNull
    @Size(max = 50)
    @Column(name = "name", length = 50, nullable = false, unique = true)
    private String name;

    /**
     * 상위지역코드
     */
    @NotNull
    @Size(max = 6)
    @Column(name = "parent_area_id", length = 6, nullable = false)
    private String parentAreaId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "image", "competitions", "regions" }, allowSetters = true)
    private Country country;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "region")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "operatorRole", "region", "competitions" }, allowSetters = true)
    private Set<Operator> operators = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Region id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Region name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentAreaId() {
        return this.parentAreaId;
    }

    public Region parentAreaId(String parentAreaId) {
        this.setParentAreaId(parentAreaId);
        return this;
    }

    public void setParentAreaId(String parentAreaId) {
        this.parentAreaId = parentAreaId;
    }

    public Country getCountry() {
        return this.country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Region country(Country country) {
        this.setCountry(country);
        return this;
    }

    public Set<Operator> getOperators() {
        return this.operators;
    }

    public void setOperators(Set<Operator> operators) {
        if (this.operators != null) {
            this.operators.forEach(i -> i.setRegion(null));
        }
        if (operators != null) {
            operators.forEach(i -> i.setRegion(this));
        }
        this.operators = operators;
    }

    public Region operators(Set<Operator> operators) {
        this.setOperators(operators);
        return this;
    }

    public Region addOperator(Operator operator) {
        this.operators.add(operator);
        operator.setRegion(this);
        return this;
    }

    public Region removeOperator(Operator operator) {
        this.operators.remove(operator);
        operator.setRegion(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Region)) {
            return false;
        }
        return getId() != null && getId().equals(((Region) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Region{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", parentAreaId='" + getParentAreaId() + "'" +
            "}";
    }
}
