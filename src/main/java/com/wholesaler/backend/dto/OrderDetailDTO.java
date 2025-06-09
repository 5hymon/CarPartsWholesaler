package com.wholesaler.backend.dto;

public class OrderDetailDTO {
    private Integer detailId;
    private Integer orderId;
    private Integer partId;
    private String partName;
    private String partDescription;
    private Double partUnitPrice;
    private Integer quantity;
    private Double discount;
    private Double orderValue;

    public OrderDetailDTO() {
    }

    public OrderDetailDTO(Integer detailId, Integer orderId, Integer partId, String partName, String partDescription, Double partUnitPrice, Integer quantity, Double discount, Double orderValue) {
        this.detailId = detailId;
        this.orderId = orderId;
        this.partId = partId;
        this.partName = partName;
        this.partDescription = partDescription;
        this.partUnitPrice = partUnitPrice;
        this.quantity = quantity;
        this.discount = discount;
        this.orderValue = orderValue;
    }

    public Integer getDetailId() {
        return detailId;
    }

    public void setDetailId(Integer detailId) {
        this.detailId = detailId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
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

    public String getPartDescription() {
        return partDescription;
    }

    public void setPartDescription(String partDescription) {
        this.partDescription = partDescription;
    }

    public Double getPartUnitPrice() {
        return partUnitPrice;
    }

    public void setPartUnitPrice(Double partUnitPrice) {
        this.partUnitPrice = partUnitPrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Double getOrderValue() {
        orderValue = getPartUnitPrice() * getQuantity() * (1 - getDiscount());
        orderValue = Math.round(orderValue * 100.0) / 100.0;
        return orderValue;
    }

    public void setOrderValue(Double orderValue) {
        this.orderValue = orderValue;
    }
}
