package com.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class VehicleEntry {
    private final int entryTimestamp;
    private final VehicleType vehicleType;
    private int lotIndex;

    public VehicleEntry(int entryTimestamp, VehicleType vehicleType) {
        this.entryTimestamp = entryTimestamp;
        this.vehicleType = vehicleType;
    }
}
