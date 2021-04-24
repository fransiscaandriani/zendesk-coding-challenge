package com.util;

import com.exception.ParkingLotException;
import com.model.ParkingLot;
import com.model.VehicleEntry;
import com.model.VehicleType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParkingLotUtil {
    private static final Map<VehicleType, Integer> parkingFee = new HashMap<VehicleType, Integer>() {{
        put(VehicleType.CAR, 2);
        put(VehicleType.MOTORCYCLE, 1);
    }};

    public static String processVehicleData(final ParkingLot parkingLot, final Map<String, String> vehicleData) throws ParkingLotException {
        final StringBuilder strBuilder = new StringBuilder();
        if(vehicleData.get("Direction").equals("Enter")) {
            strBuilder.append(processEnteringVehicle(parkingLot, vehicleData));
        } else {
            strBuilder.append(processExitingVehicle(parkingLot, vehicleData));
        }
        return strBuilder.toString();
    }

    private static String processEnteringVehicle(final ParkingLot parkingLot, final Map<String, String> vehicleData) {
        final StringBuilder stringBuilder = new StringBuilder();
        final String vehicleNumber = vehicleData.get("VehicleNumber");
        final VehicleEntry vehicleEntry = createVehicleEntry(vehicleData.get("VehicleType"), vehicleData.get("Timestamp"));

        // Get parking lot for the vehicle type
        final List<Boolean> designatedVehicleParkingLot = parkingLot.getParkingMap().get(vehicleEntry.getVehicleType());

        // Find first empty lot
        final int emptyIndex = getFirstEmptyLot(designatedVehicleParkingLot);

        // If there is no empty lot
        if(emptyIndex == -1) {
            return "Reject";
        }

        // Fill in empty lot
        designatedVehicleParkingLot.set(emptyIndex, true);

        // Save vehicle entry in vehicleMap for future use
        vehicleEntry.setLotIndex(emptyIndex);
        parkingLot.getVehicleMap().put(vehicleNumber, vehicleEntry);
        stringBuilder.append("Accept ");
        stringBuilder.append(getVehicleLotString(vehicleEntry));
        return stringBuilder.toString();
    }

    private static String processExitingVehicle(final ParkingLot parkingLot, final Map<String, String> vehicleData) throws ParkingLotException {
        final String vehicleNumber = vehicleData.get("VehicleNumber");
        final int exitTimestamp = Integer.parseInt(vehicleData.get("Timestamp"));
        final VehicleEntry vehicleEntry = parkingLot.getVehicleMap().get(vehicleNumber);

        // Check if vehicle does not exist in parking lot
        if(vehicleEntry == null) {
            throw new ParkingLotException("Error: Vehicle " + vehicleNumber + " does not exist in parking lot");
        }

        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getVehicleLotString(vehicleEntry));
        stringBuilder.append(" ");
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
        stringBuilder.append(vehicleEntryUpperCase.substring(0, 1));
        stringBuilder.append(vehicleEntryUpperCase.substring(1).toLowerCase());

        // Append "Lot"
        stringBuilder.append("Lot");

        // Append index starting from 1
        stringBuilder.append(vehicleEntry.getLotIndex() + 1);
        return stringBuilder.toString();
    }

    private static int calculateFee(final int exitTimestamp, final VehicleEntry vehicleEntry) {
        int secondsParked = exitTimestamp - vehicleEntry.getEntryTimestamp();
        double hoursParked = secondsParked/3600.0;
        int roundedUpHoursParked = (int) Math.ceil(hoursParked);
        return roundedUpHoursParked * parkingFee.get(vehicleEntry.getVehicleType());
    }
}
