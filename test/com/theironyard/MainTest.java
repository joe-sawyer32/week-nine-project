package com.theironyard;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Scanner;

import static org.junit.Assert.*;

/**
 * Created by Joe on 7/22/17.
 */
public class MainTest {
    // FIELDS
    private VehicleInfo testVehicleInfo;
    private String[] testData;
    private String testIntStream;
    private String testDoubleStream;
    private String testInputStream;
    private Scanner testScanner;
    private String prompt = "Test User Prompt";
    private static final double DELTA = 1E-9;

    // METHODS
    @Before
    public void setUp() {
        testData = new String[5];
        testData[0] = "123456789"; // vin
        testData[1] = "123456.78"; // miles
        testData[2] = "12.34"; // consumption
        testData[3] = "120456.78"; // miles at last oil change
        testData[4] = "4.5"; // engine size
        testIntStream = testData[0];
        testDoubleStream = testData[1];
        testInputStream = testData[0] + "\n" + testData[1] + "\n" + testData[2] + "\n" + testData[3] +
                "\n" + testData[4];;

        testVehicleInfo = new VehicleInfo();
    }

    @After
    public void tearDown() {
        testScanner.close();
    }

    @Test
    public void getVehicleInfoFromUserTest() throws Exception {
        testScanner = new Scanner(testInputStream);
        Main.getVehicleInfoFromUser(testVehicleInfo, testScanner);
        assertEquals("Vin should be set to value from input stream", Integer.parseInt(testData[0]),
                testVehicleInfo.getVin());
        assertEquals("Miles should be set to value from input stream", Double.parseDouble(testData[1]),
                testVehicleInfo.getMiles(), DELTA);
        assertEquals("GasGallonsConsumed should be set to value from input stream",
                Double.parseDouble(testData[2]), testVehicleInfo.getGasGallonsConsumed(), DELTA);
        assertEquals("MilesAtLastOilChange should be set to value from input stream",
                Double.parseDouble(testData[3]), testVehicleInfo.getMilesAtLastOilChange(), DELTA);
        assertEquals("EngineSize should be set to value from input stream", Double.parseDouble(testData[4]),
                testVehicleInfo.getEngineLiters(), DELTA);
    }

    @Test
    public void promptUserForVinTest() throws Exception {
        testScanner = new Scanner(testIntStream);
        assertEquals("Should return integer from input stream", 123456789,
                Main.promptUserForVin(testScanner, prompt));
    }

    @Test
    public void promptUserForInfoTest() throws Exception {
        testScanner = new Scanner(testDoubleStream);
        assertEquals("Should return double from input stream", 123456.78,
                Main.promptUserForInfo(testScanner, prompt), DELTA);
    }

}