package com.hsms.transformer;

import com.hsms.domain.common.Floor;
import com.hsms.domain.common.MainCorridor;
import com.hsms.domain.common.SubCorridor;
import com.hsms.domain.enums.EquipmentState;
import com.hsms.domain.equipment.AirConditioner;
import com.hsms.domain.equipment.Light;
import com.hsms.domain.sensor.HotelRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class Transformer {

    private static final Integer LIGHT_POWER_CONSUMPTION_UNIT = 5;
    private static final Integer AC_POWER_CONSUMPTION_UNIT = 10;

    /**
     * Transform to Floor from HotelRequest
     *
     * @param request
     * @return List<Floor>
     */
    public List<Floor> transformToFloors(final HotelRequest request) {
        return IntStream.range(1, request.getNumberOfFloors() + 1)
            .mapToObj(floorId -> transformToFloor(
                floorId, request.getNumberOfMainCorridorsPerFloor(),
                request.getNumberOfSubCorridorsPerFloor()))
            .collect(Collectors.toList());
    }

    private Floor transformToFloor(final int floorId, final int noOfMainCorridors, final int noOfSubCorridors) {
        final List<MainCorridor> mainCorridors = IntStream.range(1, noOfMainCorridors + 1)
            .mapToObj(this::transformToMainCorridor)
            .collect(Collectors.toList());
        final List<SubCorridor> subCorridors = IntStream.range(1, noOfSubCorridors + 1)
            .mapToObj(this::transformToSubCorridor)
            .collect(Collectors.toList());
        return new Floor.Builder().setFloorId(floorId)
            .setMainCorridors(mainCorridors)
            .setSubCorridors(subCorridors)
            .build();
    }

    private MainCorridor transformToMainCorridor(final int id) {
        return new MainCorridor.Builder().setAirConditioner(getDefaultAirConditioner())
            .setLight(getDefaultLight()).setId(id).build();
    }

    private SubCorridor transformToSubCorridor(final int id) {
        return new SubCorridor.Builder().setAirConditioner(getDefaultAirConditioner())
            .setLight(getDefaultLight()).setId(id).build();
    }

    private Light getDefaultLight() {
        return new Light.Builder().setEquipmentState(EquipmentState.ON)
            .setPowerConsumption(LIGHT_POWER_CONSUMPTION_UNIT)
            .build();
    }

    private AirConditioner getDefaultAirConditioner() {
        return new AirConditioner.Builder().setEquipmentState(EquipmentState.ON)
            .setPowerConsumption(AC_POWER_CONSUMPTION_UNIT)
            .build();
    }
}
