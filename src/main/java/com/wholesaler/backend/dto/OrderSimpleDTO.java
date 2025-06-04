package com.wholesaler.backend.dto;

import java.util.Date;

public class OrderSimpleDTO {
    private Integer orderId;
    private Date orderDate;
    private String orderStatus;
    private String paymentMethod;

    public OrderSimpleDTO() {
    }

    public OrderSimpleDTO(Integer orderId, Date orderDate, String orderStatus, String paymentMethod) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.paymentMethod = paymentMethod;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
