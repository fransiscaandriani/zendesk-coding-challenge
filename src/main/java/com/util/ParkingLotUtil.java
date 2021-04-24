package com.util;

import com.exception.ParkingLotException;
import com.model.ParkingLot;
import com.model.VehicleEntry;
import com.model.VehicleType;

import java.util.List;
import java.util.Map;

import static com.constant.FileParsingConstants.*;
import static com.constant.ParkingLotConstants.*;
import static com.constant.RegexConstants.SINGLE_SPACE;

public class ParkingLotUtil {
    public static String processVehicleData(final ParkingLot parkingLot, final Map<String, String> vehicleData) throws ParkingLotException {
        final StringBuilder strBuilder = new StringBuilder();
        if(vehicleData.get(DIRECTION).equals(ENTER)) {
            strBuilder.append(processEnteringVehicle(parkingLot, vehicleData));
        } else {
            strBuilder.append(processExitingVehicle(parkingLot, vehicleData));
        }
        return strBuilder.toString();
    }

    private static String processEnteringVehicle(final ParkingLot parkingLot, final Map<String, String> vehicleData) throws ParkingLotException {
        final StringBuilder stringBuilder = new StringBuilder();
        final String vehicleNumber = vehicleData.get(VEHICLE_NUMBER);

        // Check if vehicleNumber already exists in vehicleMap
        if(parkingLot.getVehicleMap().containsKey(vehicleNumber)) {
            throw new ParkingLotException("Error: Vehicle is already inside");
        }

        final VehicleEntry vehicleEntry = createVehicleEntry(vehicleData.get(VEHICLE_TYPE), vehicleData.get(TIMESTAMP));

        // Get parking lot for the vehicle type
        final List<Boolean> designatedVehicleParkingLot = parkingLot.getParkingMap().get(vehicleEntry.getVehicleType());

        // Find first empty lot
        final int emptyIndex = getFirstEmptyLot(designatedVehicleParkingLot);

        // If there is no empty lot
        if(emptyIndex == -1) {
            return REJECT;
        }

        // Fill in empty lot
        designatedVehicleParkingLot.set(emptyIndex, true);

        // Save vehicle entry in vehicleMap for future use
        vehicleEntry.setLotIndex(emptyIndex);
        parkingLot.getVehicleMap().put(vehicleNumber, vehicleEntry);
        stringBuilder.append(ACCEPT);
        stringBuilder.append(SINGLE_SPACE);
        stringBuilder.append(getVehicleLotString(vehicleEntry));
        return stringBuilder.toString();
    }

    private static String processExitingVehicle(final ParkingLot parkingLot, final Map<String, String> vehicleData) throws ParkingLotException {
        final String vehicleNumber = vehicleData.get(VEHICLE_NUMBER);
        final int exitTimestamp = Integer.parseInt(vehicleData.get(TIMESTAMP));

        // Remove vehicle from vehicleMap
        final VehicleEntry vehicleEntry = parkingLot.getVehicleMap().remove(vehicleNumber);

        // Check if vehicle does not exist in parking lot
        if(vehicleEntry == null) {
            throw new ParkingLotException("Error: Vehicle " + vehicleNumber + " does not exist in parking lot");
        }

        // Remove vehicle from parkingMap
        parkingLot.getParkingMap()
                .get(vehicleEntry.getVehicleType())
                .set(vehicleEntry.getLotIndex(), false);

        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getVehicleLotString(vehicleEntry));
        stringBuilder.append(SINGLE_SPACE);
        stringBuilder.append(calculateFee(exitTimestamp, vehicleEntry));
        return stringBuilder.toString();
    }

    private static VehicleEntry createVehicleEntry( final String vehicleTypeString, final String timestampString) {
        return new VehicleEntry(Integer.parseInt(timestampString), VehicleType.valueOf(vehicleTypeString.toUpperCase()));
    }

    private static int getFirstEmptyLot(final List<Boolean> vehicleLot) {
        return vehicleLot.indexOf(false);
    }

    private static String getVehicleLotString(final VehicleEntry vehicleEntry) {
        final StringBuilder stringBuilder = new StringBuilder();
        // Append Vehicle Type
        final String vehicleEntryUpperCase = vehicleEntry.getVehicleType().toString();
        stringBuilder.append(vehicleEntryUpperCase, 0, 1);
        stringBuilder.append(vehicleEntryUpperCase.substring(1).toLowerCase());

        // Append "Lot"
        stringBuilder.append(LOT);

        // Append index starting from 1
        stringBuilder.append(vehicleEntry.getLotIndex() + 1);
        return stringBuilder.toString();
    }

    private static int calculateFee(final int exitTimestamp, final VehicleEntry vehicleEntry) {
        int secondsParked = exitTimestamp - vehicleEntry.getEntryTimestamp();
        double hoursParked = secondsParked/ONE_HOUR_IN_SECONDS;
        int roundedUpHoursParked = (int) Math.ceil(hoursParked);
        return roundedUpHoursParked * PARKING_FEE.get(vehicleEntry.getVehicleType());
    }
}
