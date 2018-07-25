package com.hsms.service;

import com.hsms.domain.common.Floor;
import com.hsms.domain.common.SubCorridor;
import com.hsms.domain.enums.Status;
import com.hsms.domain.sensor.HotelRequest;
import com.hsms.domain.sensor.SensorRequest;
import com.hsms.transformer.Transformer;
import com.hsms.util.PowerConsumptionCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service to Construct Hotel Object from the 1st Input data provided by the Client
 */

@Service
public class DefaultHotelService implements HotelService {

    private List<Floor> floors;
    private final FloorService floorService;
    private final PowerConsumptionCalculator powerConsumptionCalculator;
    private final Transformer transformer;
    private int maxAllowedPowerLimit;

    @Autowired
    public DefaultHotelService(
        final FloorService floorService,
        final PowerConsumptionCalculator powerConsumptionCalculator,
        final Transformer transformer) {
        this.floorService = floorService;
        this.powerConsumptionCalculator = powerConsumptionCalculator;
        this.transformer = transformer;
    }

    @Override
    public boolean initializeHotel(final HotelRequest hotelRequest) {
        try {
            floors = transformer.transformToFloors(hotelRequest);
            maxAllowedPowerLimit = powerConsumptionCalculator.calculateMaxAllowedPowerLimit(hotelRequest);
            floorService.showCurrentStatus(floors);
            return true;
        } catch (final RuntimeException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateHotelEquipmentState(final SensorRequest sensorRequest) {
        try {
            final Status status = sensorRequest.getStatus();
            final int onWhichFloor = sensorRequest.getFloorNumber();
            final int onWhichSubCorridor = sensorRequest.getSubCorridorNumber();

            // Reset the Light of the Sub Corridor Depending on the Movement in Sub-Corridor
            final SubCorridor subCorridor = floorService.getSubCorridorById(
                floors, onWhichFloor, onWhichSubCorridor);
            if (status == Status.MOVEMENT_IN_SUB_CORRIDOR) {
                subCorridor.getLight().switchON();
            } else if (status == Status.NO_MOVEMENT) {
                subCorridor.getLight().switchOFF();
            }

            // Switch Off Other Floor's Equipments
            floorService.saveElectricity(floors, maxAllowedPowerLimit);

            // Displays the current Equipment Status
            floorService.showCurrentStatus(floors);
        } catch (final RuntimeException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
