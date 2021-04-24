package com;

import com.exception.FileParsingException;
import com.model.ParkingLot;
import com.util.FileParsingUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        try {
            // Read txt file
            final File fileInput = new File("input/testCase1.txt");
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
                System.out.println(data);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error: File specified does not exist.");
        } catch (FileParsingException e) {
            System.out.println(e.getMessage());
        }
    }
}
