package com.hsms.service;

import com.hsms.domain.enums.Status;
import com.hsms.domain.sensor.HotelRequest;
import com.hsms.domain.sensor.SensorRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Optional;
import java.util.Scanner;

/**
 * Starting point of the Program Execution.
 * <p>
 * Operations and maintains its equipment status using Rules
 */

@Service
public class HotelController {

    private final HotelService hotelService;

    @Autowired
    public HotelController(final HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @Scheduled(cron = "${sensor.input.interval}")
    public void scheduleControl() {
        final Scanner in = new Scanner(System.in);

        // Initializing Equipments for the first time
        final HotelRequest hotelRequest = getInitializationConfiguration(in);
        hotelService.initializeHotel(hotelRequest);

        final SensorRequest sensorRequest = getLatestEquipmentStatus(in);
        if (sensorRequest != null) {
            hotelService.updateHotelEquipmentState(sensorRequest);
        }
    }

    private HotelRequest getInitializationConfiguration(final Scanner in) {
        System.out.println();
        System.out.print("Number Of Floors : ");
        final int numberOfFloors = in.nextInt();
        in.nextLine();

        System.out.print("Main Corridor per Floor : ");
        final int mainCorridorPerFloor = in.nextInt();

        System.out.print("Sub Corridor per Floor : ");
        final int subCorridorPerFloor = in.nextInt();

        return new HotelRequest(numberOfFloors, mainCorridorPerFloor, subCorridorPerFloor);
    }

    private SensorRequest getLatestEquipmentStatus(final Scanner in) {
        Status status = null;
        Integer floorNumber = 0;
        Integer subCorridorNo = 0;
        do {
            System.out.println("Subsequent Inputs from Sensors : \n "
                + "Input will be below format \n"
                + "0 is No movement in Floor \n"
                + "1 is Movement in Floor alone \n"
                + "2 is Movement in Sub corridor alone \n"
                + "3 is Movement in Floor and Sub corridor");
            final int sensorInput = in.nextInt();
            if (StringUtils.isEmpty(sensorInput)) {
                return null;
            }
            final Optional<Status> filteredStatus = Arrays.stream(Status.values())
                .filter(state -> state.getValue() == sensorInput)
                .findAny();
            if (filteredStatus.isPresent()) {
                status = filteredStatus.get();
            }
        } while (status == null);

        if (status == Status.NO_MOVEMENT) {
            return null;
        }

        if (status == Status.MOVEMENT_IN_FLOOR) {
            do {
                System.out.println("Provide the Floor number:");
                floorNumber = in.nextInt();
            } while (floorNumber == 0);
        }

        if (status == Status.MOVEMENT_IN_SUB_CORRIDOR) {
            do {
                System.out.println("Provide the Sub corridor number:");
                subCorridorNo = in.nextInt();
            } while (subCorridorNo == 0);
        }

        if (status == Status.MOVEMENT_IN_FLOOR_AND_SUB_CORRIDOR) {
            do {
                System.out.println("Provide the Floor number:");
                floorNumber = in.nextInt();
                System.out.println("Provide the Sub corridor number:");
                subCorridorNo = in.nextInt();
            } while (floorNumber == 0 || subCorridorNo == 0);
        }
        return new SensorRequest.Builder().setFloorNumber(floorNumber).setStatus(status).setSubCorridorNumber(
            subCorridorNo).build();
    }
}
