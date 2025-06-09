package com.wholesaler.backend.dto;

public class PartCompatibilityDTO {
    private Integer compatibilityId;
    private Integer carId;
    private Integer partId;
    private String carMake;
    private String carModel;
    private String productionYears;
    private String bodyType;
    private String fuelType;
    private String engineType;
    private String partName;
    private Double unitPrice;
    private Boolean available;

    public PartCompatibilityDTO() {
    }

    public PartCompatibilityDTO(Integer compatibilityId, Integer carId, Integer partId, String carMake, String carModel, String productionYears, String bodyType, String fuelType, String engineType, String partName, Double unitPrice, Boolean available) {
        this.compatibilityId = compatibilityId;
        this.carId = carId;
        this.partId = partId;
        this.carMake = carMake;
        this.carModel = carModel;
        this.productionYears = productionYears;
        this.bodyType = bodyType;
        this.fuelType = fuelType;
        this.engineType = engineType;
        this.partName = partName;
        this.unitPrice = unitPrice;
        this.available = available;
    }

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

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }
}
