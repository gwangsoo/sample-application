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
 * 머신 배치
 */
@Entity
@Table(name = "tb_machine_area")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MachineArea implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Size(max = 16)
    @Id
    @GeneratedValue
    @Column(name = "id", length = 16, nullable = false, unique = true)
    private String id;

    /**
     * 구역명
     */
    @NotNull
    @Size(max = 256)
    @Column(name = "mame", length = 256, nullable = false)
    private String mame;

    /**
     * 순서
     */
    @NotNull
    @Column(name = "seq", nullable = false)
    private Integer seq;

    /**
     * 머신수
     */
    @Column(name = "num_of_machine")
    private Integer numOfMachine;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "machineArea")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "match", "machineArea" }, allowSetters = true)
    private Set<Machine> machines = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = { "tournaments", "machineAreas", "competitionImage", "reward", "country", "operator", "entryFee" },
        allowSetters = true
    )
    private Competition competition;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public MachineArea id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMame() {
        return this.mame;
    }

    public MachineArea mame(String mame) {
        this.setMame(mame);
        return this;
    }

    public void setMame(String mame) {
        this.mame = mame;
    }

    public Integer getSeq() {
        return this.seq;
    }

    public MachineArea seq(Integer seq) {
        this.setSeq(seq);
        return this;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public Integer getNumOfMachine() {
        return this.numOfMachine;
    }

    public MachineArea numOfMachine(Integer numOfMachine) {
        this.setNumOfMachine(numOfMachine);
        return this;
    }

    public void setNumOfMachine(Integer numOfMachine) {
        this.numOfMachine = numOfMachine;
    }

    public Set<Machine> getMachines() {
        return this.machines;
    }

    public void setMachines(Set<Machine> machines) {
        if (this.machines != null) {
            this.machines.forEach(i -> i.setMachineArea(null));
        }
        if (machines != null) {
            machines.forEach(i -> i.setMachineArea(this));
        }
        this.machines = machines;
    }

    public MachineArea machines(Set<Machine> machines) {
        this.setMachines(machines);
        return this;
    }

    public MachineArea addMachine(Machine machine) {
        this.machines.add(machine);
        machine.setMachineArea(this);
        return this;
    }

    public MachineArea removeMachine(Machine machine) {
        this.machines.remove(machine);
        machine.setMachineArea(null);
        return this;
    }

    public Competition getCompetition() {
        return this.competition;
    }

    public void setCompetition(Competition competition) {
        this.competition = competition;
    }

    public MachineArea competition(Competition competition) {
        this.setCompetition(competition);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MachineArea)) {
            return false;
        }
        return getId() != null && getId().equals(((MachineArea) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MachineArea{" +
            "id=" + getId() +
            ", mame='" + getMame() + "'" +
            ", seq=" + getSeq() +
            ", numOfMachine=" + getNumOfMachine() +
            "}";
    }
}
