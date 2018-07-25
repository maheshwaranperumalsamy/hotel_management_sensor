package com.hsms.service;

import com.hsms.domain.sensor.HotelRequest;
import com.hsms.domain.sensor.SensorRequest;

/**
 * Interface which provides operations to initialize and manage hotel equipment services
 */
public interface HotelService {
    boolean initializeHotel(HotelRequest hotelRequest);
    boolean updateHotelEquipmentState(SensorRequest sensorRequest);
}
