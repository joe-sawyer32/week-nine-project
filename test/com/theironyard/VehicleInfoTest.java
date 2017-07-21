package com.theironyard;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Joe on 7/21/17.
 */
public class VehicleInfoTest {
    // FIELD
    private static final double DELTA = 1E-9;

    // METHODS
    @Test
    public void vinGetterAndSetterTest() throws Exception {
        VehicleInfo vehicleInfoTest = new VehicleInfo();
        assertEquals("Getter should return 0 (not yet assigned)", vehicleInfoTest.getVin(), 0);
        vehicleInfoTest.setVin(123456789);
        assertNotEquals("Getter should not return 0 (assigned)", vehicleInfoTest.getVin(), 0);
        assertEquals("Getter should return value passed to setter", vehicleInfoTest.getVin(),
                123456789);
    }

    @Test
    public void milesGetterAndSetterTest() throws Exception {
        VehicleInfo vehicleInfoTest = new VehicleInfo();
        assertEquals("Getter should return 0 (not yet assigned)", vehicleInfoTest.getMiles(),
                0.0, DELTA);
        vehicleInfoTest.setMiles(123456.789);
        assertNotEquals("Getter should not return 0 (assigned)", vehicleInfoTest.getMiles(), 0);
        assertEquals("Getter should return value passed to setter", vehicleInfoTest.getMiles(),
                123456.789, DELTA);
    }

    @Test
    public void gasGallonsConsumedGetterAndSetterTest() throws Exception {
        VehicleInfo vehicleInfoTest = new VehicleInfo();
        assertEquals("Getter should return 0 (not yet assigned)", vehicleInfoTest.getGasGallonsConsumed(),
                0.0, DELTA);
        vehicleInfoTest.setGasGallonsConsumed(12.34);
        assertNotEquals("Getter should not return 0 (assigned)", vehicleInfoTest.getGasGallonsConsumed(),
                0);
        assertEquals("Getter should return value passed to setter", vehicleInfoTest.getGasGallonsConsumed(),
                12.34, DELTA);
    }

    @Test
    public void milesAtLastOilChangeGetterAndSetterTest() throws Exception {
        VehicleInfo vehicleInfoTest = new VehicleInfo();
        assertEquals("Getter should return 0 (not yet assigned)", vehicleInfoTest.getMilesAtLastOilChange(),
                0.0, DELTA);
        vehicleInfoTest.setMilesAtLastOilChange(120456.789);
        assertNotEquals("Getter should not return 0 (assigned)", vehicleInfoTest.getMilesAtLastOilChange(),
                0);
        assertEquals("Getter should return value passed to setter", vehicleInfoTest.getMilesAtLastOilChange(),
                120456.789, DELTA);
    }

    @Test
    public void engineLitersGetterAndSetterTest() throws Exception {
        VehicleInfo vehicleInfoTest = new VehicleInfo();
        assertEquals("Getter should return 0 (not yet assigned)", vehicleInfoTest.getEngineLiters(),
                0.0, DELTA);
        vehicleInfoTest.setEngineLiters(1.2);
        assertNotEquals("Getter should not return 0 (assigned)", vehicleInfoTest.getEngineLiters(),
                0);
        assertEquals("Getter should return value passed to setter", vehicleInfoTest.getEngineLiters(),
                1.2, DELTA);
    }

}