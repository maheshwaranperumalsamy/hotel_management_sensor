package com.hsms.domain.equipment;

import com.hsms.domain.enums.EquipmentState;

/**
 * Represents a Light {@link AirConditioner}, an electrical Light equipment.
 * <p>
 * This class is a sub-class of Equipment {@link Equipment} class.
 */
public class AirConditioner {
    private EquipmentState equipmentState;
    private final int powerConsumption;

    private AirConditioner(final Builder builder) {
        this.equipmentState = builder.equipmentState;
        this.powerConsumption = builder.powerConsumption;
    }

    public EquipmentState getEquipmentState() {
        return equipmentState;
    }

    public int getPowerConsumption() {
        return powerConsumption;
    }

    public static class Builder {
        private EquipmentState equipmentState;
        private int powerConsumption;

        public Builder setEquipmentState(final EquipmentState equipmentState) {
            this.equipmentState = equipmentState;
            return this;
        }

        public Builder setPowerConsumption(final int powerConsumption) {
            this.powerConsumption = powerConsumption;
            return this;
        }

        public AirConditioner build() {
            return new AirConditioner(this);
        }
    }

    public void switchON() {
        this.equipmentState = EquipmentState.ON;
    }

    public void switchOFF() {
        this.equipmentState = EquipmentState.OFF;
    }

    /**
     * This method returns the power consumed by Light at a particular moment
     *
     * @return Power consumed
     */
    public int getConsumedPower() {
        if (this.equipmentState == EquipmentState.ON) {
            return this.powerConsumption;
        } else {
            return 0;
        }
    }
}
