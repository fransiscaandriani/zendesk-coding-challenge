package com.util;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FileParsingUtil {
    public static List<Integer> parseParkingLotSizes(String line) throws Exception{
        // Get car and motorcycle lot sizes
        final List<Integer> lotSizes = Arrays.stream(line.split(" "))
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        // Check if lot sizes are valid
        if(lotSizes.size() != 2 || lotSizes.get(0) == null || lotSizes.get(1) == null
                || lotSizes.get(0) < 0 || lotSizes.get(1) < 0) {
            throw new Exception("Error: Invalid first line. First line should contain two integers indicating the " +
                    "size of car parking lot and the size of motorcycle parking lot respectively");
        } else {
            return lotSizes;
        }
    }
}
