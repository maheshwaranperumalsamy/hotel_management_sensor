package com.hsms.domain.common;

import com.hsms.domain.equipment.AirConditioner;
import com.hsms.domain.equipment.Light;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Represents Main Corridor {@link MainCorridor} of a hotel floor.
 */
public class MainCorridor {

    private final int id;
    private final Light light;
    private final AirConditioner airConditioner;

    private MainCorridor(final Builder builder) {
        this.id = builder.id;
        this.light = builder.light;
        this.airConditioner = builder.airConditioner;
    }

    public int getId() {
        return id;
    }

    public Light getLight() {
        return light;
    }

    public AirConditioner getAirConditioner() {
        return airConditioner;
    }

    public int getTotalPowerConsumption() {
        return light.getConsumedPower() + airConditioner.getConsumedPower();
    }

    public static class Builder {
        private int id;
        private Light light;
        private AirConditioner airConditioner;

        public Builder setId(final int corridorId) {
            this.id = corridorId;
            return this;
        }

        public Builder setLight(final Light light) {
            this.light = light;
            return this;
        }

        public Builder setAirConditioner(final AirConditioner airConditioner) {
            this.airConditioner = airConditioner;
            return this;
        }

        public MainCorridor build() {
            return new MainCorridor(this);
        }
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("id", id)
            .append("light", light)
            .append("airConditioner", airConditioner)
            .toString();
    }
}
