package com.phoenixdarts.toss.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.phoenixdarts.toss.domain.Country} entity.
 */
@Schema(description = "국가")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CountryDTO implements Serializable {

    @NotNull
    @Size(max = 2)
    private String id;

    @NotNull
    @Size(max = 50)
    private String name;

    private FileInfoDTO image;

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

    public FileInfoDTO getImage() {
        return image;
    }

    public void setImage(FileInfoDTO image) {
        this.image = image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CountryDTO)) {
            return false;
        }

        CountryDTO countryDTO = (CountryDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, countryDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CountryDTO{" +
            "id='" + getId() + "'" +
            ", name='" + getName() + "'" +
            ", image=" + getImage() +
            "}";
    }
}
