package hu.cubix.transroute.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Milestone {
    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    private Address address;

    private LocalDateTime plannedTime;

    public Milestone() {
    }

    public Milestone(Address address, LocalDateTime plannedTime) {
        this.address = address;
        this.plannedTime = plannedTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public LocalDateTime getPlannedTime() {
        return plannedTime;
    }

    public void setPlannedTime(LocalDateTime plannedTime) {
        this.plannedTime = plannedTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Milestone milestone)) return false;
        return id == milestone.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}