package com.util;

import com.exception.FileParsingException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;
import java.util.Map;

import static com.constant.FileParsingConstants.*;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(JUnit4.class)
public class FileParsingUtilTest {
    @Test
    public void when_parseParkingLotSizes_should_return_list_of_integers () throws FileParsingException {
        final List<Integer> result = FileParsingUtil.parseParkingLotSizes("1 2");
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(1, result.get(0));
        assertEquals(2, result.get(1));
    }

    @Test(expected = FileParsingException.class)
    public void when_parseParkingLotSizes_wrong_size_should_throw_FileParsingException () throws FileParsingException {
        FileParsingUtil.parseParkingLotSizes("1 2 3");
    }

    @Test(expected = FileParsingException.class)
    public void when_parseParkingLotSizes_non_int_should_throw_FileParsingException () throws FileParsingException {
        FileParsingUtil.parseParkingLotSizes("a 2");
    }

    @Test(expected = FileParsingException.class)
    public void when_parseParkingLotSizes_negative_int_should_throw_FileParsingException () throws FileParsingException {
        FileParsingUtil.parseParkingLotSizes("-1 2");
    }

    @Test
    public void when_parseVehicleInput_enter_should_return_map() throws FileParsingException {
        final Map<String, String> result = FileParsingUtil.parseVehicleInputLine("Enter car SGF9283P 1613541902");
        assertNotNull(result);
        assertEquals(4, result.size());

        assertTrue(result.containsKey(DIRECTION));
        assertTrue(result.containsKey(VEHICLE_TYPE));
        assertTrue(result.containsKey(VEHICLE_NUMBER));
        assertTrue(result.containsKey(TIMESTAMP));

        assertEquals("Enter", result.get(DIRECTION));
        assertEquals("car", result.get(VEHICLE_TYPE));
        assertEquals("SGF9283P", result.get(VEHICLE_NUMBER));
        assertEquals("1613541902", result.get(TIMESTAMP));
    }

    @Test
    public void when_parseVehicleInput_exit_should_return_map() throws FileParsingException {
        final Map<String, String> result = FileParsingUtil.parseVehicleInputLine("Exit SGF9283P 1613541902");
        assertNotNull(result);
        assertEquals(3, result.size());

        assertTrue(result.containsKey(DIRECTION));
        assertTrue(result.containsKey(VEHICLE_NUMBER));
        assertTrue(result.containsKey(TIMESTAMP));

        assertEquals("Exit", result.get(DIRECTION));
        assertEquals("SGF9283P", result.get(VEHICLE_NUMBER));
        assertEquals("1613541902", result.get(TIMESTAMP));
    }

    @Test(expected = FileParsingException.class)
    public void when_parseVehicleInput_invalid_format_should_throw_FileParsingException () throws FileParsingException {
        FileParsingUtil.parseVehicleInputLine("stringrandomasfas");
    }

    @Test(expected = FileParsingException.class)
    public void when_parseVehicleInput_invalid_direction_should_throw_FileParsingException () throws FileParsingException {
        FileParsingUtil.parseVehicleInputLine("Enterrr car SGF9283P 1613541902");
    }

    @Test(expected = FileParsingException.class)
    public void when_parseVehicleInput_invalid_vehicle_type_should_throw_FileParsingException () throws FileParsingException {
        FileParsingUtil.parseVehicleInputLine("Enter cruise SGF9283P 1613541902");
    }

    @Test(expected = FileParsingException.class)
    public void when_parseVehicleInput_invalid_vehicle_number_should_throw_FileParsingException () throws FileParsingException {
        FileParsingUtil.parseVehicleInputLine("Enter car car123 1613541902");
    }

    @Test(expected = FileParsingException.class)
    public void when_parseVehicleInput_invalid_timestamp_should_throw_FileParsingException () throws FileParsingException {
        FileParsingUtil.parseVehicleInputLine("Enter car SGF9283P -123");
    }
}