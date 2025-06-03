package com.wholesaler.backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@IdClass(OrderDetailId.class)
@Table(name = "OrderDetails")
public class OrderDetail {
    @Id
    @Column(name = "orderId")
    private Integer orderId;

    @Id
    @Column(name = "partId")
    private Integer partId;

    @ManyToOne
    @MapsId("orderId")
    @JoinColumn(name = "orderId", foreignKey = @ForeignKey(name = "FK_DETAILS_ORDER"))
    @JsonBackReference
    private Order order;

    @ManyToOne
    @MapsId("partId")
    @JoinColumn(name = "partId", foreignKey = @ForeignKey(name = "FK_DETAILS_PART"))
    @JsonBackReference
    private Part part;

    @Column(name = "unitPrice")
    private Double unitPrice;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "discount")
    private Double discount;

    public OrderDetail() {
    }

    public OrderDetail(Integer orderId, Integer partId, Order order, Part part, Double unitPrice, Integer quantity, Double discount) {
        this.orderId = orderId;
        this.partId = partId;
        this.order = order;
        this.part = part;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.discount = discount;
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

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Part getPart() {
        return part;
    }

    public void setPart(Part part) {
        this.part = part;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
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
}
