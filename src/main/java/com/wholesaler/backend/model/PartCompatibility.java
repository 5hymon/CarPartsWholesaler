package com.wholesaler.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "PartCompatibility")
public class PartCompatibility {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer compatibilityId;

    @ManyToOne
    @JoinColumn(name = "carId")
    private Car car;

    @ManyToOne
    @JoinColumn(name = "partId")
    private Part part;

    public Integer getCompatibilityId() {
        return compatibilityId;
    }

    public void setCompatibilityId(Integer compatibilityId) {
        this.compatibilityId = compatibilityId;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Part getPart() {
        return part;
    }

    public void setPart(Part part) {
        this.part = part;
    }
}
