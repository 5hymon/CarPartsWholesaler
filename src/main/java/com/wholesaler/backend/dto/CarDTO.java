package com.wholesaler.backend.dto;

import java.util.List;

public class CarDTO {
    private Integer carId;
    private String carMake;
    private String carModel;
    private String productionYears;
    private String bodyType;
    private String fuelType;
    private String engineType;
    private List<PartDTO> compatibleParts;

    public CarDTO() {
    }

    public CarDTO(Integer carId, String carMake, String carModel, String productionYears, String bodyType, String fuelType, String engineType, List<PartDTO> compatibleParts) {
        this.carId = carId;
        this.carMake = carMake;
        this.carModel = carModel;
        this.productionYears = productionYears;
        this.bodyType = bodyType;
        this.fuelType = fuelType;
        this.engineType = engineType;
        this.compatibleParts = compatibleParts;
    }

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

    public List<PartDTO> getCompatibleParts() {
        return compatibleParts;
    }

    public void setCompatibleParts(List<PartDTO> compatibleParts) {
        this.compatibleParts = compatibleParts;
    }
}
