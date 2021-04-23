package com.Model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class ParkingLot {
    private final List<VehicleEntry> carLot;
    private final List<VehicleEntry> motorcycleLot;
    private final Map<String, Map.Entry<String, Integer>> vehicleMap = new HashMap<>();


    public ParkingLot(final int carLotSize, final int motorcycleLotSize) {
        carLot = new ArrayList<>(carLotSize);
        motorcycleLot = new ArrayList<>(motorcycleLotSize);
    }
}
