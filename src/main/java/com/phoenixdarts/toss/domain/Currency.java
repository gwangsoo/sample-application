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
 * 통화
 */
@Entity
@Table(name = "tb_currency")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Currency implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Size(max = 3)
    @Id
    @GeneratedValue
    @Column(name = "id", length = 3, nullable = false, unique = true)
    private String id;

    @NotNull
    @Size(max = 128)
    @Column(name = "name", length = 128, nullable = false)
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "currency")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "currency", "tournaments", "competitions" }, allowSetters = true)
    private Set<EntryFee> entryFees = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Currency id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Currency name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<EntryFee> getEntryFees() {
        return this.entryFees;
    }

    public void setEntryFees(Set<EntryFee> entryFees) {
        if (this.entryFees != null) {
            this.entryFees.forEach(i -> i.setCurrency(null));
        }
        if (entryFees != null) {
            entryFees.forEach(i -> i.setCurrency(this));
        }
        this.entryFees = entryFees;
    }

    public Currency entryFees(Set<EntryFee> entryFees) {
        this.setEntryFees(entryFees);
        return this;
    }

    public Currency addEntryFee(EntryFee entryFee) {
        this.entryFees.add(entryFee);
        entryFee.setCurrency(this);
        return this;
    }

    public Currency removeEntryFee(EntryFee entryFee) {
        this.entryFees.remove(entryFee);
        entryFee.setCurrency(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Currency)) {
            return false;
        }
        return getId() != null && getId().equals(((Currency) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Currency{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
