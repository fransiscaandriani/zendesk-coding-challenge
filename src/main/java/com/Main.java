package com;

import com.exception.FileParsingException;
import com.exception.ParkingLotException;
import com.model.ParkingLot;
import com.util.FileParsingUtil;
import com.util.ParkingLotUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        try {
            System.out.println("Enter test file name: ");
            Scanner commandLineScanner = new Scanner(System.in);
            final String fileName = commandLineScanner.nextLine();

            // Read txt file
            final File fileInput = new File("input/" + fileName);
            final Scanner scanner = new Scanner(fileInput);

            // Check that file is not empty
            if(!scanner.hasNextLine()) {
                System.out.println("Error: File input is empty");
                return;
            }

            // Read first line to get parking lot sizes
            final String firstLine = scanner.nextLine();
            final List<Integer> lotSizes = FileParsingUtil.parseParkingLotSizes(firstLine);

            // Make new parking lot
            final ParkingLot parkingLot = new ParkingLot(lotSizes.get(0), lotSizes.get(1));

            // Parse each line after the first line
            while (scanner.hasNextLine()) {
                final String data = scanner.nextLine();
                final Map<String, String> vehicleData = FileParsingUtil.parseVehicleInputLine(data);
                final String output = ParkingLotUtil.processVehicleData(parkingLot, vehicleData);
                System.out.println(output);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error: File specified does not exist.");
        } catch (FileParsingException | ParkingLotException e) {
            System.out.println(e.getMessage());
        }
    }
}
