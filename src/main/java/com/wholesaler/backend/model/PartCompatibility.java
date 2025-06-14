package com.wholesaler.backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "PartCompatibility")
public class PartCompatibility {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "compatibilityId")
    private Integer compatibilityId;

    @Column(name = "carId")
    private Integer carId;

    @Column(name = "partId")
    private Integer partId;

    @ManyToOne
    @MapsId("carId")
    @JoinColumn(name = "carId", foreignKey = @ForeignKey(name = "FK_COMPATIBILITY_CAR"))
    private Car car;

    @ManyToOne
    @MapsId("partId")
    @JoinColumn(name = "partId", foreignKey = @ForeignKey(name = "FK_COMPATIBILITY_PART"))
    @JsonBackReference
    private Part part;

    public Integer getCompatibilityId() {
        return compatibilityId;
    }

    public void setCompatibilityId(Integer compatibilityId) {
        this.compatibilityId = compatibilityId;
    }

    public Integer getCarId() {
        return carId;
    }

    public void setCarId(Integer carId) {
        this.carId = carId;
    }

    public Integer getPartId() {
        return partId;
    }

    public void setPartId(Integer partId) {
        this.partId = partId;
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
