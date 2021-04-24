package com.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class VehicleEntry {
    private final Date entryTime;
    private final VehicleType vehicleType;
    private int lotIndex;

    public VehicleEntry(Date entryTime, VehicleType vehicleType) {
        this.entryTime = entryTime;
        this.vehicleType = vehicleType;
    }
}
