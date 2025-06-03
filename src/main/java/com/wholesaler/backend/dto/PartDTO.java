package com.wholesaler.backend.dto;

import java.util.List;

public class PartDTO {
    private Integer partId;
    private String partName;
    private Double unitPrice;
    private String quantityPerUnit;
    private Integer leftOnStock;
    private String partDescription;
    private Boolean available;
    private List<CarDTO> compatibleCars;

    public PartDTO() {
    }

    public PartDTO(Integer partId, String partName, Double unitPrice, String quantityPerUnit, Integer leftOnStock, String partDescription, Boolean available, List<CarDTO> compatibleCars) {
        this.partId = partId;
        this.partName = partName;
        this.unitPrice = unitPrice;
        this.quantityPerUnit = quantityPerUnit;
        this.leftOnStock = leftOnStock;
        this.partDescription = partDescription;
        this.available = available;
        this.compatibleCars = compatibleCars;
    }

    public Integer getPartId() {
        return partId;
    }

    public void setPartId(Integer partId) {
        this.partId = partId;
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

    public String getQuantityPerUnit() {
        return quantityPerUnit;
    }

    public void setQuantityPerUnit(String quantityPerUnit) {
        this.quantityPerUnit = quantityPerUnit;
    }

    public Integer getLeftOnStock() {
        return leftOnStock;
    }

    public void setLeftOnStock(Integer leftOnStock) {
        this.leftOnStock = leftOnStock;
    }

    public String getPartDescription() {
        return partDescription;
    }

    public void setPartDescription(String partDescription) {
        this.partDescription = partDescription;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public List<CarDTO> getCompatibleCars() {
        return compatibleCars;
    }

    public void setCompatibleCars(List<CarDTO> compatibleCars) {
        this.compatibleCars = compatibleCars;
    }
}
