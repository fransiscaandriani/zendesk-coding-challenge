package com.constant;

import com.model.VehicleType;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class FileParsingConstants {
    public static final Set<String> VEHICLE_TYPE_SET = Arrays.stream(VehicleType.values()).map(Enum::toString).collect(Collectors.toSet());
    public static final String DIRECTION = "Direction";
    public static final String VEHICLE_NUMBER = "VehicleNumber";
    public static final String TIMESTAMP = "Timestamp";
    public static final String VEHICLE_TYPE = "VehicleType";
    public static final String ENTER = "Enter";
    public static final String EXIT = "Exit";
}
