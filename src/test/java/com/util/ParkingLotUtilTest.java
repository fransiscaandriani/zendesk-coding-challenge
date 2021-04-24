package com.util;

import com.exception.ParkingLotException;
import com.model.ParkingLot;
import com.model.VehicleEntry;
import com.model.VehicleType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.HashMap;
import java.util.Map;

import static com.constant.FileParsingConstants.*;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(JUnit4.class)
public class ParkingLotUtilTest {
    @Test
    public void when_processVehicleData_enter_should_return_accept_string() throws ParkingLotException {
        final Map<String, String> vehicleData = new HashMap<String, String>() {{
            put(DIRECTION, "Enter");
            put(VEHICLE_NUMBER, "SGP1234T");
            put(VEHICLE_TYPE, "car");
            put(TIMESTAMP, "123456788");
        }};

        final ParkingLot parkingLot = new ParkingLot(1,2);

        final String result = ParkingLotUtil.processVehicleData(parkingLot, vehicleData);
        assertEquals("Accept CarLot1", result);
    }

    @Test
    public void when_processVehicleData_enter_full_lot_should_return_reject_string() throws ParkingLotException {
        final Map<String, String> vehicleData = new HashMap<String, String>() {{
            put(DIRECTION, "Enter");
            put(VEHICLE_NUMBER, "SGP1234K");
            put(VEHICLE_TYPE, "car");
            put(TIMESTAMP, "123456788");
        }};

        final ParkingLot parkingLot = new ParkingLot(1,2);
        parkingLot.getParkingMap().get(VehicleType.CAR).set(0, true);
        parkingLot.getVehicleMap().put("SGP1234T", new VehicleEntry(123452788, VehicleType.CAR));

        final String result = ParkingLotUtil.processVehicleData(parkingLot, vehicleData);
        assertEquals("Reject", result);
    }


    @Test
    public void when_processVehicleData_exit_should_return_string() throws ParkingLotException {
        final Map<String, String> vehicleData = new HashMap<String, String>() {{
            put(DIRECTION, "Exit");
            put(VEHICLE_NUMBER, "SGP1234T");
            put(TIMESTAMP, "123456788");
        }};

        final ParkingLot parkingLot = new ParkingLot(1,2);
        parkingLot.getParkingMap().get(VehicleType.CAR).set(0, true);
        parkingLot.getVehicleMap().put("SGP1234T", new VehicleEntry(123452788, VehicleType.CAR));

        final String result = ParkingLotUtil.processVehicleData(parkingLot, vehicleData);
        assertEquals("CarLot1 4", result);
    }

    @Test(expected = ParkingLotException.class)
    public void when_processVehicleData_enter_already_inside_should_throw_ParkingLotException() throws ParkingLotException {
        final Map<String, String> vehicleData = new HashMap<String, String>() {{
            put(DIRECTION, "Enter");
            put(VEHICLE_NUMBER, "SGP1234K");
            put(VEHICLE_TYPE, "car");
            put(TIMESTAMP, "123456788");
        }};

        final ParkingLot parkingLot = new ParkingLot(1,2);
        parkingLot.getParkingMap().get(VehicleType.CAR).set(0, true);
        parkingLot.getVehicleMap().put("SGP1234K", new VehicleEntry(123452788, VehicleType.CAR));

        ParkingLotUtil.processVehicleData(parkingLot, vehicleData);
    }

    @Test(expected = ParkingLotException.class)
    public void when_processVehicleData_exit_does_not_exist_should_throw_ParkingLotException() throws ParkingLotException {
        final Map<String, String> vehicleData = new HashMap<String, String>() {{
            put(DIRECTION, "Exit");
            put(VEHICLE_NUMBER, "SGP1234K");
            put(TIMESTAMP, "123456788");
        }};

        final ParkingLot parkingLot = new ParkingLot(1,2);
        ParkingLotUtil.processVehicleData(parkingLot, vehicleData);
    }
}