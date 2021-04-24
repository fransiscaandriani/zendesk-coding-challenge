package com.util;

import com.exception.FileParsingException;

import java.util.*;
import java.util.stream.Collectors;

import static com.constant.FileParsingConstants.*;
import static com.constant.RegexConstants.*;

public class FileParsingUtil {
    public static List<Integer> parseParkingLotSizes(final String line) throws FileParsingException{
        // Get car and motorcycle lot sizes
        final List<Integer> lotSizes = Arrays.stream(line.split(SINGLE_SPACE))
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
        final String trimmedLine = trimZeroWidthSpace(line);

        // Get each individual data from the string
        final List<String> data = Arrays.asList(trimmedLine.split(SINGLE_SPACE));

        final Map<String, String> result = new HashMap<>();

        // If this is an Enter line
        if(data.get(0).equals(ENTER) && data.size() == 4){
            result.putAll(getEnteringVehicleData(data));
        }
        // if this is an Exit line
        else if (data.get(0).equals(EXIT) && data.size() == 3) {
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
        result.put(DIRECTION, data.get(0));

        // Check if Vehicle Type is valid
        if(checkIfVehicleTypeIsValid(data.get(1))) {
            result.put(VEHICLE_TYPE, data.get(1));
        } else {
            throw new FileParsingException("Error: Vehicle type specified does not exist.");
        }

        // Check if Vehicle Number is valid
        if(checkIfVehicleNumberIsValid(data.get(2))) {
            result.put(VEHICLE_NUMBER, data.get(2));
        } else {
            throw new FileParsingException("Error: Vehicle number is invalid.");
        }

        // Check if timestamp is valid
        if(checkIfTimestampIsValid(data.get(3))) {
            result.put(TIMESTAMP, data.get(3));
        } else {
            throw new FileParsingException("Error: Timestamp is invalid.");
        }
        return result;
    }

    private static Map<String, String> getExitingVehicleData(final List<String> data) throws FileParsingException {
        final Map<String, String> result = new HashMap<>();
        result.put(DIRECTION, data.get(0));

        // Check if Vehicle Number is valid
        if(checkIfVehicleNumberIsValid(data.get(1))) {
            result.put(VEHICLE_NUMBER, data.get(1));
        } else {
            throw new FileParsingException("Error: Vehicle number is invalid. It has to be a valid Singapore vehicle number");
        }

        // Check if timestamp is valid
        if(checkIfTimestampIsValid(data.get(2))) {
            result.put(TIMESTAMP, data.get(2));
        } else {
            throw new FileParsingException("Error: Timestamp is invalid.");
        }
        return result;
    }

    private static boolean checkIfVehicleTypeIsValid(final String vehicleTypeString) {
        return VEHICLE_TYPE_SET.contains(vehicleTypeString.toUpperCase());
    }

    private static boolean checkIfVehicleNumberIsValid(final String vehicleNumberString) {
        return vehicleNumberString.matches(SINGAPORE_VEHICLE_REGEX);
    }

    private static boolean checkIfTimestampIsValid(final String timestampString) {
        return timestampString.matches(TIMESTAMP_REGEX);
    }

    private static String trimZeroWidthSpace(final String untrimmedStr) {
        return untrimmedStr.replaceAll(ZERO_WIDTH_SPACE, "");
    }
}
