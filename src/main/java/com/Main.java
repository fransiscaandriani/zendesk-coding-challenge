package com;

import com.model.ParkingLot;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
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

            // Read first line
            final String firstLine = scanner.nextLine();

            // Get car and motorcycle lot sizes
            final List<Integer> lotSizes = Arrays.stream(firstLine.split(" "))
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());

            // Check if lot sizes are valid
            if(lotSizes.size() != 2 || lotSizes.get(0) == null || lotSizes.get(1) == null
            || lotSizes.get(0) < 0 || lotSizes.get(1) < 0) {
                System.out.println("Error: Invalid first line. First line should contain two integers indicating the " +
                        "size of car parking lot and the size of motorcycle parking lot respectively");
                return;
            }

            // Make new parking lot
            final ParkingLot parkingLot = new ParkingLot(lotSizes.get(0), lotSizes.get(1));

            while (scanner.hasNextLine()) {
                final String data = scanner.nextLine();
                System.out.println(data);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error: File specified does not exist.");
        }
    }
}
