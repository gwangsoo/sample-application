package com.phoenixdarts.toss.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.phoenixdarts.toss.domain.OperatorRole} entity.
 */
@Schema(description = "관리자 Role (National dealer..)")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OperatorRoleDTO implements Serializable {

    @NotNull
    @Size(max = 16)
    private String id;

    @NotNull
    @Size(max = 50)
    private String name;

    @NotNull
    private Integer displayOrder;

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

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OperatorRoleDTO)) {
            return false;
        }

        OperatorRoleDTO operatorRoleDTO = (OperatorRoleDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, operatorRoleDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OperatorRoleDTO{" +
            "id='" + getId() + "'" +
            ", name='" + getName() + "'" +
            ", displayOrder=" + getDisplayOrder() +
            "}";
    }
}
