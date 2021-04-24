package com.constant;

import com.model.VehicleType;

import java.util.HashMap;
import java.util.Map;

public class ParkingLotConstants {
    public static final Map<VehicleType, Integer> PARKING_FEE = new HashMap<VehicleType, Integer>() {{
        put(VehicleType.CAR, 2);
        put(VehicleType.MOTORCYCLE, 1);
    }};

    public static final double ONE_HOUR_IN_SECONDS = 3600.0;
    public static final String LOT = "Lot";
    public static final String ACCEPT = "Accept";
    public static final String REJECT = "Reject";
}
