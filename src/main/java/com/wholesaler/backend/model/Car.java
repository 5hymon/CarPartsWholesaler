package com.wholesaler.backend.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Cars")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "carId")
    private Integer carId;

    @Column(name = "carMake")
    private String carMake;

    @Column(name = "carModel")
    private String carModel;

    @Column(name = "productionYears")
    private String productionYears;

    @Column(name = "bodyType")
    private String bodyType;

    @Column(name = "fuelType")
    private String fuelType;

    @Column(name = "engineType")
    private String engineType;

    @OneToMany(mappedBy = "car")
    private List<PartCompatibility> compatibilities;

    public Integer getCarId() {
        return carId;
    }

    public void setCarId(Integer carId) {
        this.carId = carId;
    }

    public String getCarMake() {
        return carMake;
    }

    public void setCarMake(String carMake) {
        this.carMake = carMake;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getProductionYears() {
        return productionYears;
    }

    public void setProductionYears(String productionYears) {
        this.productionYears = productionYears;
    }

    public String getBodyType() {
        return bodyType;
    }

    public void setBodyType(String bodyType) {
        this.bodyType = bodyType;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public String getEngineType() {
        return engineType;
    }

    public void setEngineType(String engineType) {
        this.engineType = engineType;
    }

    public List<PartCompatibility> getCompatibilities() {
        return compatibilities;
    }

    public void setCompatibilities(List<PartCompatibility> compatibilities) {
        this.compatibilities = compatibilities;
    }
}