package com.hsms.service;

import com.hsms.domain.common.Floor;
import com.hsms.domain.common.MainCorridor;
import com.hsms.domain.common.SubCorridor;
import com.hsms.domain.equipment.AirConditioner;
import com.hsms.domain.equipment.Light;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

@Service
public class DefaultFloorService implements FloorService {

    /**
     * This method saves energy by switching Off the Equipments ({@link Light} & {@link AirConditioner})
     * if required depending on the energy rule
     *
     * @param floors
     * @param maxAllowedPowerLimit
     * @return
     */
    @Override
    public boolean saveElectricity(final List<Floor> floors, final int maxAllowedPowerLimit) {
        boolean hasEnergySaved = false;
        for (final Floor floor : floors) {
            hasEnergySaved = keepTogglingAcsUntilEnergyRuleViolates(floor, maxAllowedPowerLimit);
        }
        return hasEnergySaved;
    }

    /**
     * @param floor Floor Object whose Equipment state needs to be revealed
     */
    @Override
    public void showCurrentStatus(final List<Floor> floors) {
        for (final Floor floor : floors) {
            showCurrentStatusPerFloor(floor);
        }
    }

    private void showCurrentStatusPerFloor(final Floor floor) {
        System.out.println("Floor " + floor.getFloorId());

        final List<MainCorridor> mainCorridors = floor.getMainCorridors();

        for (int j = 0; j < mainCorridors.size(); j++) {
            System.out.println("\t Main corridor " + (j + 1));
            final MainCorridor mainCorridor = mainCorridors.get(j);

            final Light light = mainCorridor.getLight();
            System.out.println("\t\t Light " + (j + 1) + " : " + light.getEquipmentState());

            final AirConditioner airConditioner = mainCorridor.getAirConditioner();
            System.out.println("\t\t AC : " + airConditioner.getEquipmentState());
        }

        final List<SubCorridor> subCorridors = floor.getSubCorridors();
        for (int k = 0; k < subCorridors.size(); k++) {
            System.out.println("\t Sub corridor " + (k + 1));
            final SubCorridor subCorridor = subCorridors.get(k);

            Light light = subCorridor.getLight();
            System.out.println("\t\t Light " + (k + 1) + " : " + light.getEquipmentState());

            final AirConditioner airConditioner = subCorridor.getAirConditioner();
            System.out.println("\t\t AC : " + airConditioner.getEquipmentState());
        }
    }

    /**
     * Total Power consumption at a particular moment
     *
     * @param floor
     * @return
     */
    private Integer getRealTimeTotalPowerConsumption(final Floor floor) {
        final List<MainCorridor> mainCorridors = floor.getMainCorridors();

        Integer totalFloorPowerConsumption = 0;

        for (final MainCorridor mainCorridor : mainCorridors) {
            totalFloorPowerConsumption += mainCorridor.getTotalPowerConsumption();
        }

        final List<SubCorridor> subCorridors = floor.getSubCorridors();
        for (final SubCorridor subCorridor : subCorridors) {
            totalFloorPowerConsumption += subCorridor.getTotalPowerConsumption();
        }

        return totalFloorPowerConsumption;
    }

    /**
     * Keep on switching off ACs of Sub-Corridor until the Floor Energy Consumption limit is
     * not satisfied
     *
     * @param floor
     * @param maxAllowedPowerLimit
     */
    private boolean keepTogglingAcsUntilEnergyRuleViolates(final Floor floor, final int maxAllowedPowerLimit) {

        boolean hasSavedEnergy = false;
        int currentPowerConsumptionPerFloor = getRealTimeTotalPowerConsumption(floor);

        // If Current Power Usage is more than the allowed maximum usage limit
        if (currentPowerConsumptionPerFloor > maxAllowedPowerLimit) {
            final List<SubCorridor> subCorridorList = floor.getSubCorridors();
            for (final SubCorridor subCorridor : subCorridorList) {
                subCorridor.getAirConditioner().switchOFF();
                currentPowerConsumptionPerFloor = getRealTimeTotalPowerConsumption(floor);

                if (currentPowerConsumptionPerFloor <= maxAllowedPowerLimit) {
                    hasSavedEnergy = true;
                    break;
                }
            }
            // If Current Power Usage is Less than the allowed Maximum Usage Limit
        } else if (currentPowerConsumptionPerFloor < maxAllowedPowerLimit) {
            final List<SubCorridor> subCorridorList = floor.getSubCorridors();
            for (final SubCorridor subCorridor : subCorridorList) {
                subCorridor.getAirConditioner().switchON();
                currentPowerConsumptionPerFloor = getRealTimeTotalPowerConsumption(floor);

                if (currentPowerConsumptionPerFloor == maxAllowedPowerLimit) {
                    hasSavedEnergy = true;
                    break;
                }
            }
        }
        return hasSavedEnergy;
    }

    @Override
    public SubCorridor getSubCorridorById(final List<Floor> floors, final int floorId, final int subCorridorId) {
        SubCorridor subCorridor = null;
        if (!CollectionUtils.isEmpty(floors)) {
            final Optional<Floor> floor = floors.stream()
                .filter(floorObj -> floorObj.getFloorId() == floorId)
                .findFirst();
            if (floor.isPresent() && !CollectionUtils.isEmpty(floor.get().getSubCorridors())) {
                subCorridor = floor.get().getSubCorridors().stream()
                    .filter(sc -> sc.getId() == subCorridorId)
                    .findFirst()
                    .orElse(null);
            }
        }
        return subCorridor;
    }
}
