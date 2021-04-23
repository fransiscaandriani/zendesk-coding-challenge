package com.model;

import lombok.Getter;

import java.util.*;

@Getter
public class ParkingLot {

    // Create map to store the car parking lot and motorcycle parking lot availability
    // If the car lot at index 1 is filled, the entry parkingMap.get(CAR).get(1) will be true
    // Otherwise it will be false
    private final Map<VehicleType, List<Boolean>> parkingMap = new HashMap<>();

    // Create map to store the vehicle entry detail, mapping the vehicle number to its details
    private final Map<String, Map.Entry<String, Integer>> vehicleMap = new HashMap<>();


    public ParkingLot(final int carLotSize, final int motorcycleLotSize) {
        // Generate list of Booleans with size carLotSize
        final List<Boolean> carLot = new ArrayList<>(Arrays.asList(new Boolean[carLotSize]));
        // Fill the carLot with false
        Collections.fill(carLot, false);
        // Put carLot in parkingMap
        parkingMap.put(VehicleType.CAR, carLot);

        // Generate list of Booleans with size carLotSize
        final List<Boolean> motorcycleLot = new ArrayList<>(Arrays.asList(new Boolean[motorcycleLotSize]));
        // Fill the carLot with false
        Collections.fill(motorcycleLot, false);
        // Put motorcycleLot in parkingMap
        parkingMap.put(VehicleType.MOTORCYCLE, motorcycleLot);
    }
}
