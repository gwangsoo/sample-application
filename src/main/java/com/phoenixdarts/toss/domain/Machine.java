package com.phoenixdarts.toss.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.phoenixdarts.toss.domain.enumeration.MachineStatusType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 머신
 */
@Entity
@Table(name = "tb_machine")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Machine implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Size(max = 16)
    @Id
    @GeneratedValue
    @Column(name = "id", length = 16, nullable = false, unique = true)
    private String id;

    /**
     * 순서
     */
    @NotNull
    @Column(name = "machine_no", nullable = false)
    private Integer machineNo;

    /**
     * 머신 상태
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "machine_status_type", nullable = false)
    private MachineStatusType machineStatusType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "matchScores", "home", "away", "tmatch", "division", "machines" }, allowSetters = true)
    private Match match;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "machines", "competition" }, allowSetters = true)
    private MachineArea machineArea;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Machine id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getMachineNo() {
        return this.machineNo;
    }

    public Machine machineNo(Integer machineNo) {
        this.setMachineNo(machineNo);
        return this;
    }

    public void setMachineNo(Integer machineNo) {
        this.machineNo = machineNo;
    }

    public MachineStatusType getMachineStatusType() {
        return this.machineStatusType;
    }

    public Machine machineStatusType(MachineStatusType machineStatusType) {
        this.setMachineStatusType(machineStatusType);
        return this;
    }

    public void setMachineStatusType(MachineStatusType machineStatusType) {
        this.machineStatusType = machineStatusType;
    }

    public Match getMatch() {
        return this.match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public Machine match(Match match) {
        this.setMatch(match);
        return this;
    }

    public MachineArea getMachineArea() {
        return this.machineArea;
    }

    public void setMachineArea(MachineArea machineArea) {
        this.machineArea = machineArea;
    }

    public Machine machineArea(MachineArea machineArea) {
        this.setMachineArea(machineArea);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Machine)) {
            return false;
        }
        return getId() != null && getId().equals(((Machine) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Machine{" +
            "id=" + getId() +
            ", machineNo=" + getMachineNo() +
            ", machineStatusType='" + getMachineStatusType() + "'" +
            "}";
    }
}
