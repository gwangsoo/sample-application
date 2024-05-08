package com.phoenixdarts.toss.backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.phoenixdarts.toss.backend.domain.enumeration.AuthLevelType;
import com.phoenixdarts.toss.backend.domain.enumeration.AuthScopeType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 권한
 */
@Entity
@Table(name = "tb_role")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Size(max = 16)
    @Id
    @GeneratedValue
    @Column(name = "id", length = 16, nullable = false, unique = true)
    private String id;

    /**
     * Role (전체국가...) 명
     */
    @NotNull
    @Size(max = 50)
    @Column(name = "name", length = 50, nullable = false)
    private String name;

    /**
     * 관리범위 (GLOBAL...)
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "auth_scope_type", nullable = false)
    private AuthScopeType authScopeType;

    /**
     * 권한등급 (View...)
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "auth_level_type", nullable = false)
    private AuthLevelType authLevelType;

    /**
     * 출력 순서
     */
    @NotNull
    @Column(name = "display_order", nullable = false)
    private Integer displayOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "roles", "operators" }, allowSetters = true)
    private OperatorRole operatorRole;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Role id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Role name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AuthScopeType getAuthScopeType() {
        return this.authScopeType;
    }

    public Role authScopeType(AuthScopeType authScopeType) {
        this.setAuthScopeType(authScopeType);
        return this;
    }

    public void setAuthScopeType(AuthScopeType authScopeType) {
        this.authScopeType = authScopeType;
    }

    public AuthLevelType getAuthLevelType() {
        return this.authLevelType;
    }

    public Role authLevelType(AuthLevelType authLevelType) {
        this.setAuthLevelType(authLevelType);
        return this;
    }

    public void setAuthLevelType(AuthLevelType authLevelType) {
        this.authLevelType = authLevelType;
    }

    public Integer getDisplayOrder() {
        return this.displayOrder;
    }

    public Role displayOrder(Integer displayOrder) {
        this.setDisplayOrder(displayOrder);
        return this;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public OperatorRole getOperatorRole() {
        return this.operatorRole;
    }

    public void setOperatorRole(OperatorRole operatorRole) {
        this.operatorRole = operatorRole;
    }

    public Role operatorRole(OperatorRole operatorRole) {
        this.setOperatorRole(operatorRole);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Role)) {
            return false;
        }
        return getId() != null && getId().equals(((Role) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Role{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", authScopeType='" + getAuthScopeType() + "'" +
            ", authLevelType='" + getAuthLevelType() + "'" +
            ", displayOrder=" + getDisplayOrder() +
            "}";
    }
}
