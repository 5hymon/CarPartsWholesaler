package com.wholesaler.backend.model;

import java.io.Serializable;
import java.util.Objects;

public class PartCompatibilityId implements Serializable {
    private Integer partId;
    private Integer carId;

    public PartCompatibilityId() {
    }

    public PartCompatibilityId(Integer partId, Integer carId) {
        this.partId = partId;
        this.carId = carId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof PartCompatibilityId that)) {
            return false;
        }

        return Objects.equals(partId, that.partId) && Objects.equals(carId, that.carId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(partId, carId);
    }

    public Integer getPartId() {
        return partId;
    }

    public void setPartId(Integer partId) {
        this.partId = partId;
    }

    public Integer getCarId() {
        return carId;
    }

    public void setCarId(Integer carId) {
        this.carId = carId;
    }
}
