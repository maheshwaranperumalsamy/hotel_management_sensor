package com.hsms.domain.sensor;

import com.hsms.domain.enums.Status;

public class SensorRequest {
    private final Status status;
    private final int floorNumber;
    private final int subCorridorNumber;

    private SensorRequest(final Builder builder) {
        this.status = builder.status;
        this.floorNumber = builder.floorNumber;
        this.subCorridorNumber = builder.subCorridorNumber;
    }

    // Builder to generate a Sensor Input Request
    public static class Builder {
        private Status status;
        private Integer floorNumber;
        private Integer subCorridorNumber;

        public Builder setStatus(final Status status) {
            this.status = status;
            return this;
        }

        public Builder setFloorNumber(final Integer floorNumber) {
            this.floorNumber = floorNumber;
            return this;
        }

        public Builder setSubCorridorNumber(final Integer subCorridorNumber) {
            this.subCorridorNumber = subCorridorNumber;
            return this;
        }

        public SensorRequest build() {
            return new SensorRequest(this);
        }
    }

    public Status getStatus() {
        return status;
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public int getSubCorridorNumber() {
        return subCorridorNumber;
    }
}
