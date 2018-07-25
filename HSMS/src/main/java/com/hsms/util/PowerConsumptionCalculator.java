package com.hsms.util;

import com.hsms.domain.common.MainCorridor;
import com.hsms.domain.common.SubCorridor;
import com.hsms.domain.sensor.HotelRequest;
import org.springframework.stereotype.Service;

/**
 * Utility Class has exposed method to calculate max allowed power consumption limit for a hotel.
 */
@Service
public final class PowerConsumptionCalculator {

    /**
     * This method calculates the calculate max allowed power consumption limit for a hotel
     *
     * @param hotelRequest Initial Request having number of {@link MainCorridor}
     * and number of {@link SubCorridor} provided
     * during system initialization
     * @return Integer value representing the total allowed power consumption limit
     */
    public int calculateMaxAllowedPowerLimit(final HotelRequest hotelRequest) {
        int totalAllowedPowerConsumptionLimit = 0;
        if (hotelRequest != null) {
            final Integer numberOfMainCorridorsPerFloor = hotelRequest.getNumberOfMainCorridorsPerFloor();
            final Integer numberOfSubCorridorsPerFloor = hotelRequest.getNumberOfSubCorridorsPerFloor();

            totalAllowedPowerConsumptionLimit = (numberOfMainCorridorsPerFloor * 15)
                + (numberOfSubCorridorsPerFloor * 10);
        }
        return totalAllowedPowerConsumptionLimit;
    }
}
