package com.phoenixdarts.toss.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.phoenixdarts.toss.domain.enumeration.AttendanceStatusType;
import com.phoenixdarts.toss.domain.enumeration.GenderType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 참가자
 */
@Entity
@Table(name = "tb_entry")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Entry implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Size(max = 16)
    @Id
    @GeneratedValue
    @Column(name = "id", length = 16, nullable = false, unique = true)
    private String id;

    /**
     * 참가번호 (토너먼트기준 unique)
     */
    @NotNull
    @Size(max = 4)
    @Column(name = "entry_no", length = 4, nullable = false)
    private String entryNo;

    /**
     * Phoenix ID
     */
    @Size(max = 50)
    @Column(name = "phoenix_no", length = 50)
    private String phoenixNo;

    /**
     * Card No
     */
    @NotNull
    @Size(max = 16)
    @Column(name = "card_no", length = 16, nullable = false)
    private String cardNo;

    /**
     * 이름
     */
    @NotNull
    @Size(max = 256)
    @Column(name = "name", length = 256, nullable = false)
    private String name;

    /**
     * 영문명
     */
    @NotNull
    @Size(max = 256)
    @Column(name = "english_name", length = 256, nullable = false)
    private String englishName;

    /**
     * Rating
     */
    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "rating", nullable = false)
    private Float rating;

    /**
     * 휴대폰
     */
    @Size(max = 20)
    @Column(name = "mobile_no", length = 20)
    private String mobileNo;

    /**
     * 생년월일
     */
    @Column(name = "birth_date")
    private LocalDate birthDate;

    /**
     * email
     */
    @Size(max = 256)
    @Column(name = "email", length = 256)
    private String email;

    /**
     * 성별
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "gender_type", nullable = false)
    private GenderType genderType;

    /**
     * 출석상태
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "attendance_status_type")
    private AttendanceStatusType attendanceStatusType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = { "entries", "captain", "tteam", "affiliatedInfo", "paymentInfo", "division", "matchTeams" },
        allowSetters = true
    )
    private Team team;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "entry")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "entry", "matchTeam" }, allowSetters = true)
    private Set<MatchAttendance> matchAttendances = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Entry id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEntryNo() {
        return this.entryNo;
    }

    public Entry entryNo(String entryNo) {
        this.setEntryNo(entryNo);
        return this;
    }

    public void setEntryNo(String entryNo) {
        this.entryNo = entryNo;
    }

    public String getPhoenixNo() {
        return this.phoenixNo;
    }

    public Entry phoenixNo(String phoenixNo) {
        this.setPhoenixNo(phoenixNo);
        return this;
    }

    public void setPhoenixNo(String phoenixNo) {
        this.phoenixNo = phoenixNo;
    }

    public String getCardNo() {
        return this.cardNo;
    }

    public Entry cardNo(String cardNo) {
        this.setCardNo(cardNo);
        return this;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getName() {
        return this.name;
    }

    public Entry name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEnglishName() {
        return this.englishName;
    }

    public Entry englishName(String englishName) {
        this.setEnglishName(englishName);
        return this;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public Float getRating() {
        return this.rating;
    }

    public Entry rating(Float rating) {
        this.setRating(rating);
        return this;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public String getMobileNo() {
        return this.mobileNo;
    }

    public Entry mobileNo(String mobileNo) {
        this.setMobileNo(mobileNo);
        return this;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public LocalDate getBirthDate() {
        return this.birthDate;
    }

    public Entry birthDate(LocalDate birthDate) {
        this.setBirthDate(birthDate);
        return this;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getEmail() {
        return this.email;
    }

    public Entry email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public GenderType getGenderType() {
        return this.genderType;
    }

    public Entry genderType(GenderType genderType) {
        this.setGenderType(genderType);
        return this;
    }

    public void setGenderType(GenderType genderType) {
        this.genderType = genderType;
    }

    public AttendanceStatusType getAttendanceStatusType() {
        return this.attendanceStatusType;
    }

    public Entry attendanceStatusType(AttendanceStatusType attendanceStatusType) {
        this.setAttendanceStatusType(attendanceStatusType);
        return this;
    }

    public void setAttendanceStatusType(AttendanceStatusType attendanceStatusType) {
        this.attendanceStatusType = attendanceStatusType;
    }

    public Team getTeam() {
        return this.team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Entry team(Team team) {
        this.setTeam(team);
        return this;
    }

    public Set<MatchAttendance> getMatchAttendances() {
        return this.matchAttendances;
    }

    public void setMatchAttendances(Set<MatchAttendance> matchAttendances) {
        if (this.matchAttendances != null) {
            this.matchAttendances.forEach(i -> i.setEntry(null));
        }
        if (matchAttendances != null) {
            matchAttendances.forEach(i -> i.setEntry(this));
        }
        this.matchAttendances = matchAttendances;
    }

    public Entry matchAttendances(Set<MatchAttendance> matchAttendances) {
        this.setMatchAttendances(matchAttendances);
        return this;
    }

    public Entry addMatchAttendance(MatchAttendance matchAttendance) {
        this.matchAttendances.add(matchAttendance);
        matchAttendance.setEntry(this);
        return this;
    }

    public Entry removeMatchAttendance(MatchAttendance matchAttendance) {
        this.matchAttendances.remove(matchAttendance);
        matchAttendance.setEntry(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Entry)) {
            return false;
        }
        return getId() != null && getId().equals(((Entry) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Entry{" +
            "id=" + getId() +
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
            "}";
    }
}
