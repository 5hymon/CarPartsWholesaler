package com.wholesaler.backend.model;

import java.io.Serializable;
import java.util.Objects;

public class OrderDetailId implements Serializable {
    private Integer orderId;
    private Integer partId;

    public OrderDetailId() {
    }

    public OrderDetailId(Integer orderId, Integer partId) {
        this.orderId = orderId;
        this.partId = partId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof OrderDetailId that)) {
            return false;
        }

        return Objects.equals(partId, that.partId) && Objects.equals(orderId, that.orderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(partId, orderId);
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
}