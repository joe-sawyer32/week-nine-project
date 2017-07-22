package com.theironyard;

import org.junit.Test;

import java.util.Scanner;

import static org.junit.Assert.*;

/**
 * Created by Joe on 7/22/17.
 */
public class MainTest {
    // FIELDS
    private VehicleInfo testVehicleInfo;
    private String[] testData = {"123456789", "123456.78", "12.34","120456.78", "4.5"};
    private String testIntStream =  testData[0];
    private String testDoubleStream = testData[1];
    private String testInputStream = testData[0] + "\n" + testData[1] + "\n" + testData[2] + "\n" + testData[3] +
            "\n" + testData[4];
    private Scanner testScanner;
    private String prompt = "Correct Message";
    private static final double DELTA = 1E-9;

    // METHODS
    @Test
    public void getVehicleInfoFromUserTest() throws Exception {
        testVehicleInfo = new VehicleInfo();
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