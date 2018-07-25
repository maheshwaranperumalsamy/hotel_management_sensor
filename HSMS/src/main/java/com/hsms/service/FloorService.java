package com.hsms.service;

import com.hsms.domain.common.Floor;
import com.hsms.domain.common.SubCorridor;

import java.util.List;

public interface FloorService {

    boolean saveElectricity(final List<Floor> floors, final int maxAllowedPowerLimit);

    void showCurrentStatus(final List<Floor> floors);

    SubCorridor getSubCorridorById(final List<Floor> floors, final int floorId, final int subCorridorId);
}
