package com.phoenixdarts.toss.backend.service.dto;

import com.phoenixdarts.toss.backend.domain.enumeration.AuthLevelType;
import com.phoenixdarts.toss.backend.domain.enumeration.AuthScopeType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.phoenixdarts.toss.backend.domain.Role} entity.
 */
@Schema(description = "권한")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RoleDTO implements Serializable {

    @NotNull
    @Size(max = 16)
    private String id;

    @NotNull
    @Size(max = 50)
    private String name;

    @NotNull
    private AuthScopeType authScopeType;

    @NotNull
    private AuthLevelType authLevelType;

    @NotNull
    private Integer displayOrder;

    private OperatorRoleDTO operatorRole;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AuthScopeType getAuthScopeType() {
        return authScopeType;
    }

    public void setAuthScopeType(AuthScopeType authScopeType) {
        this.authScopeType = authScopeType;
    }

    public AuthLevelType getAuthLevelType() {
        return authLevelType;
    }

    public void setAuthLevelType(AuthLevelType authLevelType) {
        this.authLevelType = authLevelType;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public OperatorRoleDTO getOperatorRole() {
        return operatorRole;
    }

    public void setOperatorRole(OperatorRoleDTO operatorRole) {
        this.operatorRole = operatorRole;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RoleDTO)) {
            return false;
        }

        RoleDTO roleDTO = (RoleDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, roleDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RoleDTO{" +
            "id='" + getId() + "'" +
            ", name='" + getName() + "'" +
            ", authScopeType='" + getAuthScopeType() + "'" +
            ", authLevelType='" + getAuthLevelType() + "'" +
            ", displayOrder=" + getDisplayOrder() +
            ", operatorRole=" + getOperatorRole() +
            "}";
    }
}
