package com.phoenixdarts.toss.service.dto;

import com.phoenixdarts.toss.domain.enumeration.AttendanceStatusType;
import com.phoenixdarts.toss.domain.enumeration.GenderType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.phoenixdarts.toss.domain.Entry} entity.
 */
@Schema(description = "참가자")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EntryDTO implements Serializable {

    @NotNull
    @Size(max = 16)
    private String id;

    @NotNull
    @Size(max = 4)
    private String entryNo;

    @Size(max = 50)
    private String phoenixNo;

    @NotNull
    @Size(max = 16)
    private String cardNo;

    @NotNull
    @Size(max = 256)
    private String name;

    @NotNull
    @Size(max = 256)
    private String englishName;

    @NotNull
    @DecimalMin(value = "0")
    private Float rating;

    @Size(max = 20)
    private String mobileNo;

    private LocalDate birthDate;

    @Size(max = 256)
    private String email;

    @NotNull
    private GenderType genderType;

    private AttendanceStatusType attendanceStatusType;

    private TeamDTO team;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEntryNo() {
        return entryNo;
    }

    public void setEntryNo(String entryNo) {
        this.entryNo = entryNo;
    }

    public String getPhoenixNo() {
        return phoenixNo;
    }

    public void setPhoenixNo(String phoenixNo) {
        this.phoenixNo = phoenixNo;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public GenderType getGenderType() {
        return genderType;
    }

    public void setGenderType(GenderType genderType) {
        this.genderType = genderType;
    }

    public AttendanceStatusType getAttendanceStatusType() {
        return attendanceStatusType;
    }

    public void setAttendanceStatusType(AttendanceStatusType attendanceStatusType) {
        this.attendanceStatusType = attendanceStatusType;
    }

    public TeamDTO getTeam() {
        return team;
    }

    public void setTeam(TeamDTO team) {
        this.team = team;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EntryDTO)) {
            return false;
        }

        EntryDTO entryDTO = (EntryDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, entryDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EntryDTO{" +
            "id='" + getId() + "'" +
            ", entryNo='" + getEntryNo() + "'" +
            ", phoenixNo='" + getPhoenixNo() + "'" +
            ", cardNo='" + getCardNo() + "'" +
            ", name='" + getName() + "'" +
            ", englishName='" + getEnglishName() + "'" +
            ", rating=" + getRating() +
            ", mobileNo='" + getMobileNo() + "'" +
            ", birthDate='" + getBirthDate() + "'" +
            ", email='" + getEmail() + "'" +
            ", genderType='" + getGenderType() + "'" +
            ", attendanceStatusType='" + getAttendanceStatusType() + "'" +
            ", team=" + getTeam() +
            "}";
    }
}
