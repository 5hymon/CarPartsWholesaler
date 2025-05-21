package com.wholesaler.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Parts")
public class Part {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "partId")
    private Integer partId;

    @Column(name = "partName")
    private String partName;

    @Column(name = "unitPrice")
    private Double unitPrice;

    @Column(name = "quantityPerUnit")
    private String quantityPerUnit;

    @Column(name = "leftOnStock")
    private Integer leftOnStock;

    @Column(name = "isAvailable")
    private Boolean isAvailable;

    @Column(name = "partDescription")
    private String partDescription;

    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category category;

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

    public Boolean getAvailable() {
        return isAvailable;
    }

    public void setAvailable(Boolean available) {
        isAvailable = available;
    }

    public String getPartDescription() {
        return partDescription;
    }

    public void setPartDescription(String partDescription) {
        this.partDescription = partDescription;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
