package com.util;

import com.exception.FileParsingException;
import com.model.VehicleEntry;
import com.model.VehicleType;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FileParsingUtil {
    public static List<Integer> parseParkingLotSizes(final String line) throws FileParsingException{
        // Get car and motorcycle lot sizes
        final List<Integer> lotSizes = Arrays.stream(line.split(" "))
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        // Check if lot sizes are valid
        if(lotSizes.size() != 2 || lotSizes.get(0) == null || lotSizes.get(1) == null
                || lotSizes.get(0) < 0 || lotSizes.get(1) < 0) {
            throw new FileParsingException("Error: Invalid first line. First line should contain two integers indicating the " +
                    "size of car parking lot and the size of motorcycle parking lot respectively");
        } else {
            return lotSizes;
        }
    }

    public static Map<String, String> parseVehicleInputLine(final String line) throws FileParsingException{
        // Get each individual data from the string
        final List<String> data = Arrays.asList(line.split(" "));

        final Map<String, String> result = new HashMap<>();

        // If this is an Enter line
        if(data.get(0).equals("Enter") && data.size() == 4){
            result.putAll(getEnteringVehicleData(data));
        }
        // if this is an Exit line
        else if (data.get(0).equals("Exit") && data.size() == 3) {
            result.putAll(getExitingVehicleData(data));
        }
        // Otherwise, the line is invalid
        else {
            throw new FileParsingException("Error: Vehicle input line is in invalid format.");
        }
        return result;
    }

    private static Map<String, String> getEnteringVehicleData (final List<String> data) throws FileParsingException {
        final Map<String, String> result = new HashMap<>();
        result.put("Direction", data.get(0));

        // Check if Vehicle Type is valid
        if(checkIfVehicleTypeIsValid(data.get(1))) {
            result.put("VehicleType", data.get(1));
        } else {
            throw new FileParsingException("Error: Vehicle type specified does not exist.");
        }

        // Check if Vehicle Number is valid
        if(checkIfVehicleNumberIsValid(data.get(2))) {
            result.put("VehicleNumber", data.get(2));
        } else {
            throw new FileParsingException("Error: Vehicle number is invalid.");
        }

        // Check if timestamp is valid
        if(checkIfTimestampIsValid(data.get(3))) {
            result.put("Timestamp", data.get(3));
        } else {
            throw new FileParsingException("Error: Timestamp is invalid.");
        }
        return result;
    }

    private static Map<String, String> getExitingVehicleData(final List<String> data) throws FileParsingException {
        final Map<String, String> result = new HashMap<>();
        result.put("Direction", data.get(0));

        // Check if Vehicle Number is valid
        if(checkIfVehicleNumberIsValid(data.get(1))) {
            result.put("VehicleNumber", data.get(1));
        } else {
            throw new FileParsingException("Error: Vehicle number is invalid.");
        }

        // Check if timestamp is valid
        if(checkIfTimestampIsValid(data.get(2))) {
            result.put("Timestamp", data.get(2));
        } else {
            throw new FileParsingException("Error: Timestamp is invalid.");
        }
        return result;
    }

    private static boolean checkIfVehicleTypeIsValid(final String vehicleTypeString) {
        return Arrays.asList(VehicleType.values()).contains(vehicleTypeString);
    }

    private static boolean checkIfVehicleNumberIsValid(final String vehicleNumberString) {
        return vehicleNumberString.matches("/[A-Za-z]{3}[\\d]{4}[A-Za-z]/");
    }

    private static boolean checkIfTimestampIsValid(final String timestampString) {
        return timestampString.matches("^\\d*$");
    }
}
