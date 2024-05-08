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
 * 관리자 Role (National dealer..)
 */
@Entity
@Table(name = "tb_operator_role")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OperatorRole implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Size(max = 16)
    @Id
    @GeneratedValue
    @Column(name = "id", length = 16, nullable = false, unique = true)
    private String id;

    /**
     * 관리자 Role 명
     */
    @NotNull
    @Size(max = 50)
    @Column(name = "name", length = 50, nullable = false)
    private String name;

    /**
     * 출력 순서
     */
    @NotNull
    @Column(name = "display_order", nullable = false)
    private Integer displayOrder;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "operatorRole")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "operatorRole" }, allowSetters = true)
    private Set<Role> roles = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "operatorRole")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "operatorRole", "region", "competitions" }, allowSetters = true)
    private Set<Operator> operators = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public OperatorRole id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public OperatorRole name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDisplayOrder() {
        return this.displayOrder;
    }

    public OperatorRole displayOrder(Integer displayOrder) {
        this.setDisplayOrder(displayOrder);
        return this;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public Set<Role> getRoles() {
        return this.roles;
    }

    public void setRoles(Set<Role> roles) {
        if (this.roles != null) {
            this.roles.forEach(i -> i.setOperatorRole(null));
        }
        if (roles != null) {
            roles.forEach(i -> i.setOperatorRole(this));
        }
        this.roles = roles;
    }

    public OperatorRole roles(Set<Role> roles) {
        this.setRoles(roles);
        return this;
    }

    public OperatorRole addRole(Role role) {
        this.roles.add(role);
        role.setOperatorRole(this);
        return this;
    }

    public OperatorRole removeRole(Role role) {
        this.roles.remove(role);
        role.setOperatorRole(null);
        return this;
    }

    public Set<Operator> getOperators() {
        return this.operators;
    }

    public void setOperators(Set<Operator> operators) {
        if (this.operators != null) {
            this.operators.forEach(i -> i.setOperatorRole(null));
        }
        if (operators != null) {
            operators.forEach(i -> i.setOperatorRole(this));
        }
        this.operators = operators;
    }

    public OperatorRole operators(Set<Operator> operators) {
        this.setOperators(operators);
        return this;
    }

    public OperatorRole addOperator(Operator operator) {
        this.operators.add(operator);
        operator.setOperatorRole(this);
        return this;
    }

    public OperatorRole removeOperator(Operator operator) {
        this.operators.remove(operator);
        operator.setOperatorRole(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OperatorRole)) {
            return false;
        }
        return getId() != null && getId().equals(((OperatorRole) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OperatorRole{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", displayOrder=" + getDisplayOrder() +
            "}";
    }
}
