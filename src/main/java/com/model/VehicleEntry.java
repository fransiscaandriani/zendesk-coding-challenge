package com.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@AllArgsConstructor
@Getter
public class VehicleEntry {
    private final Date entryTime;
    private final String vehicleNumber;
}
