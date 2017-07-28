package com.theironyard;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static org.junit.Assert.*;

/**
 * Created by Joe on 7/22/17.
 */
public class TelematicsServiceTest {
    // FIELDS
    private VehicleInfo testVehicleInfo;
    private String expectedJson;
    private final String TEST_DIRECTORY = "test/test-records/";
    private File testFile;
    private String filename;
    private ObjectMapper testMapper;
//    private String prompt = "Correct Message";
//    private static final double DELTA = 1E-9;

    // METHODS
    @Before
    public void setUp() {
        System.out.println("TelematicsService");
        System.out.println("-----------------------------");
        System.out.println("Setting up method test...");
        testVehicleInfo = new VehicleInfo();
        testVehicleInfo.setVin(123456789);
        testVehicleInfo.setMiles(123456.78);
        testVehicleInfo.setGasGallonsConsumed(12.34);
        testVehicleInfo.setMilesAtLastOilChange(120456.78);
        testVehicleInfo.setEngineLiters(4.5);
        expectedJson ="{\"vin\":123456789,\"miles\":123456.78,\"gasGallonsConsumed\":12.34,\"milesAtLastOilChange\"" +
            ":120456.78,\"engineLiters\":4.5}";

        testMapper = new ObjectMapper();
    }

    @After
    public void tearDown() {
        System.out.println("Deleting test record...");
        testFile.deleteOnExit();
    }

    @Test
    public void convertToJsonFileTest() throws Exception {
        filename = testVehicleInfo.getVin() + ".json";
        assertEquals("Filename should match vehicle vin", "123456789.json", filename);
        testFile = new File(TEST_DIRECTORY + filename);
        System.out.print("Creating test record.");
        TelematicsService.convertToJsonFile(testVehicleInfo, testFile, testMapper);
        try (Scanner testScanner = new Scanner(testFile)) {
            assertEquals("File should contain testVehicleInfo in json format", expectedJson,
                    testScanner.nextLine());
        } catch (FileNotFoundException ex) {
            System.out.printf("A file of name %s should have been created", filename);
        }
        System.out.println("SUCCESS");
    }
}