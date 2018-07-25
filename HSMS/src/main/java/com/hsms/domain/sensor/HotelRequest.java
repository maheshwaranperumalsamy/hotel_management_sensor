package com.hsms.domain.sensor;

/**
 * First Request for Setting up the Hotel Infrastructure in this system
 */
public class HotelRequest {
    private final int numberOfFloors;
    private final int numberOfMainCorridorsPerFloor;
    private final int numberOfSubCorridorsPerFloor;

    public HotelRequest(
        final int numberOfFloors, final int numberOfMainCorridorsPerFloor,
        final int numberOfSubCorridorsPerFloor) {
        this.numberOfFloors = numberOfFloors;
        this.numberOfMainCorridorsPerFloor = numberOfMainCorridorsPerFloor;
        this.numberOfSubCorridorsPerFloor = numberOfSubCorridorsPerFloor;
    }

    public int getNumberOfFloors() {
        return numberOfFloors;
    }

    public int getNumberOfMainCorridorsPerFloor() {
        return numberOfMainCorridorsPerFloor;
    }

    public int getNumberOfSubCorridorsPerFloor() {
        return numberOfSubCorridorsPerFloor;
    }

    @Override
    public String toString() {
        return "HotelRequest{" +
            "numberOfFloors=" + numberOfFloors +
            ", numberOfMainCorridorsPerFloor=" + numberOfMainCorridorsPerFloor +
            ", numberOfSubCorridorsPerFloor=" + numberOfSubCorridorsPerFloor +
            '}';
    }
}
